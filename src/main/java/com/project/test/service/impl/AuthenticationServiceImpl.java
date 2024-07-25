package com.project.test.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.test.assembler.JwtRefreshTokenAssembler;
import com.project.test.config.security.JwtAuthenticationEntryPoint;
import com.project.test.config.security.JwtTokenProvider;
import com.project.test.config.security.UserPrincipal;
import com.project.test.model.dto.JwtAuthenticationResponse;
import com.project.test.model.dto.JwtRefreshToken;
import com.project.test.model.dto.LoginRequest;
import com.project.test.model.entity.JwtRefreshTokenEntity;
import com.project.test.model.entity.UserEntity;
import com.project.test.model.exception.CustomException;
import com.project.test.repository.JwtRefreshTokenRepository;
import com.project.test.service.AuthenticationService;
import com.project.test.service.JwtRefreshTokenService;
import com.project.test.service.JwtService;
import com.project.test.service.UserService;
import com.project.test.util.BaseHelper;
import com.project.test.util.UserUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
	
	@Autowired
	private UserService userService;

	@Autowired
	private JwtRefreshTokenRepository refreshTokenRepo;
	
	@Autowired
    JwtTokenProvider tokenProvider;
	
	@Autowired
    JwtRefreshTokenService jwtTokenService;
	
	@Autowired
	private UserUtil userUtil;
	
	@Autowired
    private JwtService jwtService;
	
	@Autowired
	private JwtRefreshTokenAssembler assembler;
	
	@Value("${spring.jwt.expiredTimeToken}")
    private int jwtExpiredToken;
	
	//private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@SuppressWarnings({ "static-access", "unlikely-arg-type" })
	@Override
	public JwtAuthenticationResponse authenticate(LoginRequest loginRequest, String ipAddress, String remoteHost,
                                                  String loginIp) throws Exception {
		
		int IDLE_TIME = 0;
        String refreshToken = null;
        String accessMenus = null;
        BaseHelper baseHelper =  new BaseHelper();
        JwtAuthenticationResponse jwtAuthenticationResponse = null;
        UserEntity user = userService.findByUsernameActiveAndBranch(loginRequest.getUsernameOrEmail(), loginRequest.getBranchCode());
        
        if (user == null){
            UserEntity userInactive = userService.findByUsernameInActiveAndBranch(loginRequest.getUsernameOrEmail(), loginRequest.getBranchCode());
            if(userInactive!=null){
            	JwtAuthenticationEntryPoint.messErr ="Login Failed. Current user is inactive";
                throw new CustomException(401, "Current user is inactive");
            }

            JwtAuthenticationEntryPoint.messErr ="Login Failed. User Id Not Found";
            throw new CustomException(401, "User Id Not Found");
        }
        
        if(loginRequest.getPassword()!=null) {
        	
            if (!loginRequest.getPassword().equals(user.getPassword())) {
            	JwtAuthenticationEntryPoint.messErr = "Login Failed. User Id or Password is incorrect";
                throw new CustomException(401, "User Id Or Password incorrect");
            }
        }
        

        CustomAuthentication auth = new CustomAuthentication(loginRequest, user);
        SecurityContextHolder.getContext().setAuthentication(auth);
        
        UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
        String accessToken = tokenProvider.generateToken(userPrincipal);
        //check if tokenRefresh Exist
        JwtRefreshToken isExist = jwtTokenService.findByUserId(userPrincipal.getId());
        
        IDLE_TIME = jwtExpiredToken * 60 * 1000;
        
        if (isExist == null) {
            refreshToken = tokenProvider.generateRefreshToken();
        } else {
            refreshToken = isExist.getToken();
        }
        
        if(userUtil.userEntityLogin.get(user.getUserId())==null){
            userUtil.userEntityLogin.put(Long.valueOf(user.getUserId()), user);
        }else {
        	userUtil.userEntityLogin.put(Long.valueOf(user.getUserId()), user);
        }
        
        Date now = new Date();
        Date expiryDate = new Date(now.getTime()+jwtExpiredToken*60*1000);
        jwtService.saveRefreshToken(userPrincipal, refreshToken, isExist, accessToken, loginIp, expiryDate);
        //BranchEntity branch = branchRepo.findByUserId(user.getUserId())
;        jwtAuthenticationResponse = new JwtAuthenticationResponse(accessToken, refreshToken, Long.valueOf(IDLE_TIME), user.getRoles(), accessMenus, "", user.getName());
        user.setPassword(baseHelper.encrypt(loginRequest.getPassword()));
        
		return jwtAuthenticationResponse;
	}
	
	@SuppressWarnings("serial")
	private class CustomAuthentication implements Authentication {
        private  LoginRequest loginRequest;
        private UserEntity user;

        public  CustomAuthentication(LoginRequest loginRequest, UserEntity user){
            this.loginRequest = loginRequest;
            this.user = user;
        }

        @Override
        public String getName() {
            return loginRequest.getUsernameOrEmail();
        }

        @Override
        public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        }

        @Override
        public boolean isAuthenticated() {
            return true;
        }

        @Override
        public Object getPrincipal() {
        	Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

                authorities.add(new SimpleGrantedAuthority(user.getRoles()));

            
            UserPrincipal userPrincipal = UserPrincipal.create(user);
            return userPrincipal;
        }

        @Override
        public Object getDetails() {
            return null;
        }

        @Override
        public Object getCredentials() {
            return null;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            List<String> roles = new ArrayList<>();
            roles.add(user.getRoles());
        	List<GrantedAuthority> authorities = roles.stream().map(role ->
            new SimpleGrantedAuthority(role)
        			).collect(Collectors.toList());
            return authorities;
        }
    }
	
		@Override
	    //@Transactional
	    public void logout(@NotNull String bearerToken, String ipAddress, String remoteHost){
	        //String bearerToken = request.getHeader("Authorization");
	        String token = bearerToken.substring(7, bearerToken.length());

	        if (token==null) {
	            JwtAuthenticationEntryPoint.messErr ="Your session has been expired";
	            throw new CustomException(400, "Sorry, You're not authorized to access this resource.");
	        }
	        JwtRefreshToken refreshToken = jwtTokenService.findByAccessToken(token);

	        if (refreshToken!=null) {
	        	tokenProvider.invalidateToken(refreshToken);
			}else {
	            JwtAuthenticationEntryPoint.messErr ="User Already Logout";
	            JwtAuthenticationEntryPoint.codeErr = HttpServletResponse.SC_FORBIDDEN;
	            throw new CustomException(400, "Sorry, You're not authorized to access this resource.");
	        }
	    }

		@Override
		public JwtAuthenticationResponse refreshAccessToken(String refreshToken, String ipAddress, String remoteHost)
				throws JsonProcessingException {
			JwtRefreshToken token = null;
			
			Optional<JwtRefreshTokenEntity> jwtRefreshToken = refreshTokenRepo.findByToken(refreshToken);
			
			if(!jwtRefreshToken.isPresent()){
	            throw new CustomException(500, "Refresh token is invalid");
	        } else{
	        	token = assembler.convertToDto(jwtRefreshToken.get());
	        	
	        }
			
			UserEntity user = userService.findById(token.getUserId());
			
			String accessToken = tokenProvider.generateToken(UserPrincipal.create(user));
			//Collection<SimpleGrantedAuthority> authorities = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getRolesName())).collect(Collectors.toList());
			UserPrincipal userPrincipal = UserPrincipal.create(user);
			Date now = new Date();
	        Date expiryDate = new Date(now.getTime()+jwtExpiredToken*60*1000);
	        
			jwtService.saveRefreshToken(userPrincipal, refreshToken, token, accessToken, ipAddress, expiryDate);
			
			return new JwtAuthenticationResponse(accessToken, token.getToken(), Long.valueOf(jwtExpiredToken * 60*1000),user.getRoles(),null,"", user.getName());
		}
		

}
