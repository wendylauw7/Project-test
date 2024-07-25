package com.project.test.repository;


import com.project.test.model.entity.OrganizationEntity;
import com.project.test.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface OrganizationRepository extends JpaRepository<OrganizationEntity, Integer>{
	@Query("select e from OrganizationEntity e where e.orgId not in (:orgId)")
	List<OrganizationEntity> findAllUser(int orgId);
	public OrganizationEntity findByName(String orgName);
	public OrganizationEntity findByUserId(int userId);
//	public UserEntity findByUserUuid(UUID orgUuid);
	
	@Query("delete from OrganizationEntity where org_id =(:userId)")
	public OrganizationEntity  deleteByUserId(int userId);
}


