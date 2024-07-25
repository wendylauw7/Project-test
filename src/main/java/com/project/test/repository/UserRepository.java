package com.project.test.repository;


import com.project.test.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, Integer>{
	@Query("select e from UserEntity e where e.userId not in (:userId)")
	List<UserEntity> findAllUser(int userId);
	public UserEntity findByUsername(String username);
	public UserEntity findByUserId(int userId);
	public UserEntity findByUserUuid(UUID userUuid);
	
	@Query("update UserEntity set password =(:password) where username =:name")
	public UserEntity saveByName(String password,String name);
}
