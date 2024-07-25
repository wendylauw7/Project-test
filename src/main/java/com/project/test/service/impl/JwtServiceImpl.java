package com.project.test.service.impl;


import com.project.test.config.security.UserPrincipal;
import com.project.test.model.dto.JwtRefreshToken;
import com.project.test.repository.UserRepository;
import com.project.test.service.JwtRefreshTokenService;
import com.project.test.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Transactional
@Service
public class JwtServiceImpl implements JwtService {

	@Autowired
    private UserRepository repo;
	
    @Autowired
    JwtRefreshTokenService jwtTokenService;

    @Override
    public boolean saveRefreshToken(UserPrincipal userPrincipal, String refreshToken, JwtRefreshToken isExist, String accessToken, String loginIp, Date date) {
    	Date today = new Date();
        if(isExist!=null) {
        	jwtTokenService.deleteJwt(isExist);
            isExist.setAccessToken(accessToken);
            isExist.setLoginIp(loginIp);
            isExist.setCreatedDate(today);
            jwtTokenService.saveJwt(isExist);
        }
        else {
            JwtRefreshToken jwtRefreshToken = new JwtRefreshToken(refreshToken);
            jwtRefreshToken.setExpirationDateTime(date);
            jwtRefreshToken.setAccessToken(accessToken);
            jwtRefreshToken.setLoginIp(loginIp);
            jwtRefreshToken.setCreatedDate(today);
            jwtRefreshToken.setUserId(repo.findByUserId(Integer.parseInt(userPrincipal.getId().toString())).getUserId());
            jwtTokenService.saveJwt(jwtRefreshToken);
        }
        return false;
    }
}
