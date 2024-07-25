package com.project.test.service;



import com.project.test.model.dto.GlobalResponse;
import com.project.test.model.entity.UserEntity;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface UserService {
	UserEntity findByUsernameActiveAndBranch(String username, String branchCode);
	UserEntity findByUsernameInActiveAndBranch(String username, String branchCode);
	UserEntity findByUsername(String username);
	UserEntity findById(int userId);
	ResponseEntity<GlobalResponse> findAllUser();
	ResponseEntity<GlobalResponse> findUserByUuid(UUID uuid);
	ResponseEntity<GlobalResponse> save(UserEntity user, String token);
	ResponseEntity<GlobalResponse> update(UserEntity user, String token);
	ResponseEntity<GlobalResponse> changePassword(String param, String token);
	
}
