package com.project.test.model.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "users")
public class UserEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", length = 50)
	private int userId;
	@Column
	private UUID userUuid = UUID.randomUUID();
	@Column(name = "name")
	private String name;
	private String username;
	@Column
	private String password = UUID.randomUUID().toString();
	private String roles;
	
}
