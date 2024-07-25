package com.project.test.model.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "org")
public class OrganizationEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "org_id", length = 50)
	private int orgId;
	@Column(name = "org_uuid")
	private UUID orgUuid = UUID.randomUUID();
	@Column(name = "org_name")
	private String name;
	@Column(name = "user_id")
	private String userId;
}
