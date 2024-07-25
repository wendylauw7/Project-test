package com.project.test.model.dto;

import lombok.Data;

@Data
public class JwtAuthenticationResponse {
    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";
    private Long expiresInMsec;
    private String fullname;
    private String role;

    public JwtAuthenticationResponse() {
    }

    public JwtAuthenticationResponse(String accessToken, String refreshToken, Long expiresInMsec, String role, String accessMenu) {

    }
    public JwtAuthenticationResponse(String accessToken, String refreshToken, Long expiresInMsec, String role, String accessMenu, String branch, String fullname) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresInMsec = expiresInMsec;
        this.fullname = fullname;
        this.role = role;

    }
    
    
}
