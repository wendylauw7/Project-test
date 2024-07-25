package com.project.test.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.test.model.dto.GlobalResponse;
import com.project.test.model.dto.JwtAuthenticationResponse;
import com.project.test.model.dto.LoginRequest;
import com.project.test.model.dto.RefreshTokenRequest;
import com.project.test.service.AuthenticationService;
import com.project.test.util.HttpRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

	
	
	@Value("${app.nameHeader}")
	private String nameHeader;

	@Autowired
	private AuthenticationService service;

	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(HttpServletRequest request, @Valid @RequestBody LoginRequest loginRequest)
			throws AuthenticationException, Exception {
		String loginIp = HttpRequestUtil.getRealIP(nameHeader, request);
		JwtAuthenticationResponse jwtAuthenticationResponse = service.authenticate(loginRequest,
				request.getRemoteAddr(), request.getRemoteHost(), loginIp);

		return ResponseEntity.ok(jwtAuthenticationResponse);
	}

	@PostMapping("/logout")
	public ResponseEntity<Object> logout(HttpServletRequest request) {
		service.logout(request.getHeader("Authorization"), request.getRemoteAddr(), request.getRemoteHost());
		// tokenProvider.invalidateToken(request);
		return new ResponseEntity<>(new GlobalResponse(200, "Success Logout", null), HttpStatus.OK);
	}

	@PostMapping("/refreshToken")
	public ResponseEntity<?> refreshAccessToken(HttpServletRequest request,
			@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) throws JsonProcessingException {
		JwtAuthenticationResponse jwtAuthenticationResponse = service.refreshAccessToken(
				refreshTokenRequest.getRefreshToken(), request.getRemoteAddr(), request.getRemoteHost());
		return ResponseEntity.ok(jwtAuthenticationResponse);
	}
}
