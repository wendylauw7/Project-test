package com.project.test.config.security;


import com.project.test.model.dto.JwtRefreshToken;
import com.project.test.repository.UserRepository;
import com.project.test.service.JwtRefreshTokenService;
import com.project.test.util.UserUtil;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class JwtTokenProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
    @Value("${spring.jwt.client-secret}")
    private String jwtSecret;
    @Value("${spring.jwt.expiredTimeToken}")
    private int jwtExpiredToken;

    @Autowired
    private JwtRefreshTokenService jwtTokenService;
    
    @Autowired
    private UserRepository repo;
    
    @Autowired
    private UserUtil userUtil;

    public String generateToken(UserPrincipal userPrincipal) {
        Date now = new Date();

        Date expiryDate = new Date(now.getTime()+jwtExpiredToken*60*1000);
        return Jwts.builder()
                .setSubject(userPrincipal.getUser().getUserUuid().toString())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }

    public String generateRefreshToken() {
        return UUID.randomUUID().toString();
    }

    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        
        Long userId = Long.valueOf(repo.findByUserUuid(UUID.fromString(claims.getSubject())).getUserId());
        
        return userId;
    }
    
    public UUID getUserUuidFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        
        return UUID.fromString(claims.getSubject());
    }
    
    public String getBranchCodeFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        
        return claims.get("branch").toString();
    }

    public boolean validateToken(String authToken) {
        try {

        	JwtRefreshToken jwtEnt = jwtTokenService.findByUserId(getUserIdFromJWT(authToken));
        	if(jwtEnt==null){
        	    return false;
        	}else {
        	    if (!jwtEnt.getAccessToken().equals(authToken)) { 
        	    	return false;
        	    }
        	}
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
            JwtAuthenticationEntryPoint.description = "Invalid JWT signature";
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
            JwtAuthenticationEntryPoint.description = "Invalid JWT token";
        } catch (ExpiredJwtException ex) {
        	JwtAuthenticationEntryPoint.messErr = "Expired JWT token";
        	JwtAuthenticationEntryPoint.description = "Expired JWT token";
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
            JwtAuthenticationEntryPoint.description = "Unsupported JWT token";
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
            JwtAuthenticationEntryPoint.description = "JWT claims string is empty.";
        }
        return false;
    }

    @SuppressWarnings("static-access")
	public void invalidateTokenByUserId(Long userId) {
        try {
            JwtRefreshToken refreshToken = jwtTokenService.findByUserId(userId);
            //delete all static variable from user util
            jwtTokenService.deleteJwt(refreshToken);
            userUtil.userEntityLogin.remove(userId);
            userUtil.unitIdUserLogin.remove(userId);
        } catch (Exception e) {
            logger.error(e.getMessage());
            //;e.printStackTrace();
        }
    }
    
    @SuppressWarnings("static-access")
	public void invalidateToken(JwtRefreshToken refreshToken) {
        try {
            jwtTokenService.deleteJwt(refreshToken);
            long id = getUserIdFromJWT(refreshToken.getAccessToken());
            userUtil.userEntityLogin.remove(id);
            userUtil.unitIdUserLogin.remove(id);
        } catch (Exception e) {
            logger.error(e.getMessage());
            //;e.printStackTrace();
        }
    }
}
