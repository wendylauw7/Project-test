package com.project.test.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.test.model.dto.JwtAuthenticationResponse;
import com.project.test.model.dto.LoginRequest;

public interface AuthenticationService {

	JwtAuthenticationResponse authenticate(LoginRequest loginRequest, String ipAddress, String remoteHost, String loginIp) throws Exception;

    void logout(String bearerToken, String ipAddress, String remoteHost);
//    void logoutIdle(String bearerToken, String ipAddress, String remoteHost);
//
    JwtAuthenticationResponse refreshAccessToken(String refreshToken, String ipAddress, String remoteHost) throws JsonProcessingException;
//    boolean validateLDAP(LoginRequest loginRequest);
//    User forceLogout(String bearerToken, String ipAddress, String remoteHost, long id);
}
