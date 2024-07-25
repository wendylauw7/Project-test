package com.project.test.config.security;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.test.model.entity.UserEntity;
import com.project.test.model.exception.ErrorMessage;
import com.project.test.service.UserService;
import com.project.test.util.BaseHelper;
import com.project.test.util.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private UserUtil userUtil;
    @Autowired
    private BaseHelper helper;
    @Autowired
    private UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @SuppressWarnings({ "static-access", "resource" })
	@Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    	
    	try {
            String jwt = helper.getJwtFromRequest(request);
            if (request.getRequestURI().matches("/api/auth/(.*)") == false) {
            
            	//if(request.getRequestURI().matches("/message-endpoint(.*)") == false) {
            		
            		if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                        Long userId = tokenProvider.getUserIdFromJWT(jwt);
                        UserDetails userDetails = customUserDetailsService.loadUserById(userId);
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);

                        //=======================================================================================//
                        if(userUtil.userEntityLogin.get(userId)==null){
                            UserEntity user = userService.findById(userId.intValue());
                            userUtil.userEntityLogin.put(userId, user);
                            
                        }
                    }else {
                    	String message = "";
                        if(StringUtils.hasText(jwt)) {
                        	message = JwtAuthenticationEntryPoint.description;
                        }else {
                        	message = "JWT Token is empty";
                        }
                        
                    	ServletServerHttpResponse res = new ServletServerHttpResponse(response);
        		        res.setStatusCode(HttpStatus.UNAUTHORIZED);
        		        res.getServletResponse().setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        		        res.getBody().write(new ObjectMapper().writeValueAsString(new ErrorMessage(
        		        		HttpStatus.UNAUTHORIZED.value(),
        		 		       "Unauthorized",
        		 		       new Date(),
        		 		       message)).getBytes());
        		        return ;
                    }
            	//}
            	
            }
            
        } catch (Exception ex) {
            logger.error("Could not set user authentication in security context", ex);
        }
        filterChain.doFilter(request, response);
    }

}
