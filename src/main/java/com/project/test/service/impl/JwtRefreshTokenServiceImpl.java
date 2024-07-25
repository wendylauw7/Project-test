package com.project.test.service.impl;


import com.project.test.assembler.JwtRefreshTokenAssembler;
import com.project.test.model.dto.JwtRefreshToken;
import com.project.test.model.entity.JwtRefreshTokenEntity;
import com.project.test.repository.JwtRefreshTokenRepository;
import com.project.test.service.JwtRefreshTokenService;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
@Service
public class JwtRefreshTokenServiceImpl implements JwtRefreshTokenService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private JwtRefreshTokenRepository repo;

	@Autowired
	private JwtRefreshTokenAssembler assembler;

	@Override
	public JwtRefreshToken findByUserId(Long userId) {
		try {
			JwtRefreshTokenEntity jwt = repo.findByUserId(userId.intValue());
			return assembler.convertToDto(jwt) ;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public Optional<JwtRefreshToken> findById(Integer id) {
		try {
			Optional<JwtRefreshTokenEntity> jwtRefreshTokenEntity = repo.findById(id);
			Optional<JwtRefreshToken> jwtRefreshToken = jwtRefreshTokenEntity.isPresent() ? Optional.of( assembler.convertToDto(jwtRefreshTokenEntity.get())) : Optional.empty();
			return jwtRefreshToken;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public JwtRefreshToken saveJwt(JwtRefreshToken jwt) {
		try {
			return assembler.convertToDto(repo.save(assembler.convertToEntity(jwt)));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public boolean deleteJwt(JwtRefreshToken jwt) {
		try {
			repo.deleteByToken(jwt.getToken());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	@Override
	public JwtRefreshTokenEntity findByUserIdAndAccessToken(Long userId, String accessToken) {
		// TODO Auto-generated method stub
		try {JwtRefreshTokenEntity entity = repo.findByUserIdAndAccessToken(userId, accessToken);
			return entity;
		} catch (Exception e) {
			log.error("Error find by Token - "+ userId.toString() + " in " + this.getClass().getName() + " : " , e);
			throw new  ServiceException(e.getMessage());
		}
	}
	@Override
	public JwtRefreshToken findByAccessToken(String token) {
		try {
			return assembler.convertToDto(repo.findByAccessToken(token)) ;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
