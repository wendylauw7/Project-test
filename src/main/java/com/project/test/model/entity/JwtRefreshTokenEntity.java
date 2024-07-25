package com.project.test.model.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;

import static javax.persistence.TemporalType.TIMESTAMP;

@Data
@Entity
@Table(name = "user_session")
public class JwtRefreshTokenEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int sessionId;
	
	@Column(name = "token")
    private String token;
	
	@Column(name = "access_token", nullable = true)
    private String accessToken;
	
	@Column(name = "user_id")
	private int userId;

	@Column(name = "expiration_date_time")
    private Date expirationDateTime;

    @CreatedDate
    @Temporal(TIMESTAMP)
    private Date loginTime;

    private String loginIp;
    private String workgroup;

    @LastModifiedDate
    @Temporal(TIMESTAMP)
    private Date lastRefresh;

    @CreatedDate
    @Temporal(TIMESTAMP)
    private Date createdDate;

    public JwtRefreshTokenEntity() {

    }

	public JwtRefreshTokenEntity(String token) {
        this.token = token;
    }
    
}