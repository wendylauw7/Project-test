package com.project.test.util;


import com.project.test.config.security.JwtTokenProvider;
import com.project.test.model.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Component
public class UserUtil {
    @Value("${spring.jwt.client-secret}")
    private String jwtSecret;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public static HashMap<Long, UserEntity> userEntityLogin = new HashMap<>();
    public static HashMap<Long,Long> unitIdUserLogin = new HashMap<>();

    public Long getUnitId(HttpServletRequest request){
        String token= getJwtFromRequest(request);
        Long userId=jwtTokenProvider.getUserIdFromJWT(token);
        if(unitIdUserLogin.get(userId) == null){
            
            throw new BadCredentialsException("You cannot access this resource, because your account is not assigned to any unit management or your unit management inactive");
        }
        return unitIdUserLogin.get(userId);
    }
    public String getUsername(HttpServletRequest request){
        String token=getJwtFromRequest(request);
        Long userId=jwtTokenProvider.getUserIdFromJWT(token);
        return userEntityLogin.get(userId).getUsername();
    }


    public Long getIdUser(HttpServletRequest request){
        String token=getJwtFromRequest(request);
        long userId =jwtTokenProvider.getUserIdFromJWT(token);
        return userId;
    }
    public UserEntity getUser(HttpServletRequest request){
        String token=getJwtFromRequest(request);
        Long userId=jwtTokenProvider.getUserIdFromJWT(token);
        UserEntity user = userEntityLogin.get(userId);
        return user;
    }
    public String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(6, bearerToken.length());
        }
        return null;
    }
    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(claims.getSubject());
    }
}
