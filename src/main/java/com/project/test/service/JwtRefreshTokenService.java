package com.project.test.service;




import com.project.test.model.dto.JwtRefreshToken;
import com.project.test.model.entity.JwtRefreshTokenEntity;

import java.util.Optional;

public interface JwtRefreshTokenService {
	public JwtRefreshToken findByUserId(Long userId);
	public JwtRefreshTokenEntity findByUserIdAndAccessToken(Long userId, String accessToken);
	public Optional<JwtRefreshToken> findById(Integer id);
	public JwtRefreshToken saveJwt(JwtRefreshToken jwt);
	public boolean deleteJwt(JwtRefreshToken jwt);
	public JwtRefreshToken findByAccessToken(String accessToken);
}
