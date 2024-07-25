package com.project.test.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Date;

@Data
public class JwtRefreshToken {
	private int sessionId;
    private String token;
    private String accessToken;
    @NotNull
    private int userId;

    private Date expirationDateTime;
    private Date createdDate;
    private String loginIp;
    private String workgroup;

    public JwtRefreshToken() {
    }

    public JwtRefreshToken(String token) {
        this.token = token;
    }

    public JwtRefreshToken(String token, int userId, Date expirationDateTime) {
        this.token = token;
        this.userId = userId;
        this.expirationDateTime = expirationDateTime;
    }
    
    public JwtRefreshToken(String token,String accessToken, int userId, Date expirationDateTime, Date createdDate) {
        this.token = token;
        this.accessToken = accessToken;
        this.createdDate = createdDate;
        this.userId = userId;
        this.expirationDateTime = expirationDateTime;
    }
    
}
