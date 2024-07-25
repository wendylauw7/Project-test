package com.project.test.repository;



import com.project.test.model.entity.JwtRefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JwtRefreshTokenRepository extends JpaRepository<JwtRefreshTokenEntity, Integer> {

	void deleteByToken(String token);
	JwtRefreshTokenEntity findByUserId(int id);
	JwtRefreshTokenEntity findByAccessToken(String Token);
	Optional<JwtRefreshTokenEntity> findByToken(String refreshToken);
	JwtRefreshTokenEntity findByUserIdAndAccessToken(Long userId, String accessToken);

}
