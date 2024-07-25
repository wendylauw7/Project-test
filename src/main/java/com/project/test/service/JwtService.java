package com.project.test.service;



import com.project.test.config.security.UserPrincipal;
import com.project.test.model.dto.JwtRefreshToken;

import java.util.Date;

public interface JwtService {
    public boolean saveRefreshToken(UserPrincipal userPrincipal, String refreshToken, JwtRefreshToken isExist, String accessToken, String loginIp, Date date);
}
