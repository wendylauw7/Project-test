package com.project.test.service.impl;


import com.project.test.assembler.UserAssembler;
import com.project.test.config.security.JwtTokenProvider;
import com.project.test.enums.Message;
import com.project.test.model.dto.GlobalResponse;
import com.project.test.model.dto.UserDto;
import com.project.test.model.entity.UserEntity;
import com.project.test.repository.UserRepository;
import com.project.test.service.UserService;
import com.project.test.util.BaseHelper;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository repo;
	
	
	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@Autowired
	private UserAssembler assembler;
	
	@Autowired
	private BaseHelper helper;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public UserEntity findByUsernameActiveAndBranch(String username, String branchCode) {
		try {
			UserEntity entity =null;
			entity = repo.findByUsername(username);
			return entity != null ? entity : null;
		} catch (Exception e) {
			log.error("Error find by username active - "+ username + " in " + this.getClass().getName() + " : " , e);
			throw new  ServiceException(e.getMessage());
		}
	}

	@Override
	public UserEntity findByUsernameInActiveAndBranch(String username, String branchCode) {
		try {
			UserEntity entity =null;
			//entity = repo.findByUsernameAndActive(username, 2L);
			return entity != null ? entity : null;
		} catch (Exception e) {
			log.error("Error find by username  inactive - "+ username + " in " + this.getClass().getName() + " : " , e);
			throw new  ServiceException(e.getMessage());
		}
	}

	@Override
	public UserEntity findByUsername(String username) {
		try {
			//UserEntity userEntity = repo.findByUsernameAndActive(username, 1L);
			//UserEntity user = userEntity != null ?  userEntity : null;
			return null;
		} catch (Exception e) {
			log.error("Error find by username - "+ username + " in " + this.getClass().getName() + " : " , e);
			return null;
		}
	}

	@Override
	public UserEntity findById(int userId) {
		try {
			UserEntity userEntity = repo.findByUserId(userId);
			UserEntity user = userEntity != null ?  userEntity : null;
			return user;
		} catch (Exception e) {
			log.error("Error find by user id - "+ userId + " in " + this.getClass().getName() + " : " , e);
			return null;
		}
	}
	
	@Override
	public ResponseEntity<GlobalResponse> findUserByUuid(UUID uuid) {
		GlobalResponse response = null;
		try {
			UserEntity userEntity = repo.findByUserUuid(uuid);
			UserDto dto = assembler.convertToDto(userEntity);
			
			if(!Objects.isNull(userEntity)) {
				response = new GlobalResponse(200, Message.SUCCESS.getValue(), dto);
			}else {
				response = new GlobalResponse(404, Message.NOTFOUND.getValue(), dto);
			}
			return new ResponseEntity<>(response, response.getErrorCode() == 200 ? HttpStatus.OK : HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			log.error("Error find user by uuid in " + this.getClass().getName() + " : " , e);
			response = new GlobalResponse(500, Message.ERROR.getValue(), null);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Override
	public ResponseEntity<GlobalResponse> save(UserEntity user, String token) {
		GlobalResponse response = null;
		try {

			UserEntity userEntity = repo.save(user);
			response = new GlobalResponse(200, Message.CREATED.getValue(), "");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Error create user in " + this.getClass().getName() + " : " , e);
			response = new GlobalResponse(500, Message.ERROR.getValue(), null);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Override
	public ResponseEntity<GlobalResponse> update(UserEntity user, String token) {
		GlobalResponse response = null;
		try {

			UserEntity userEntity = repo.save(user);
			response = new GlobalResponse(200, Message.UPDATED.getValue(), "u");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Error update user in " + this.getClass().getName() + " : " , e);
			response = new GlobalResponse(500, Message.ERROR.getValue(), null);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResponseEntity<GlobalResponse> findAllUser() {
		GlobalResponse response = null;
		try {
			
			
			return new ResponseEntity<>(response, response.getErrorCode() == 200 ? HttpStatus.OK : HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			log.error("Error find user in " + this.getClass().getName() + " : " , e);
			response = new GlobalResponse(500, Message.ERROR.getValue(), null);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	


	@Override
	public ResponseEntity<GlobalResponse> changePassword(String username, String token) {
		GlobalResponse response = null;
		try {
			String password = UUID.randomUUID().toString();		

			UserEntity userEntity = repo.saveByName(password,username);
			response = new GlobalResponse(200, Message.UPDATED.getValue(), "u");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Error update user in " + this.getClass().getName() + " : " , e);
			response = new GlobalResponse(500, Message.ERROR.getValue(), null);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

}
