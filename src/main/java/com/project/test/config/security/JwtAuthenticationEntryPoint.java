package com.project.test.config.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * @author Fifin Hernandes
 *
 * Jan 11, 2023
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);
    public static String messErr = "Sorry, You're not authorized to access this resource.";
    public static int codeErr = HttpServletResponse.SC_UNAUTHORIZED;
    public static String description = "";
    
    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException, ServletException {
        logger.error("Responding with unauthorized error. Message - {}", e.getMessage());
        httpServletResponse.sendError(codeErr,
                messErr);
        messErr = "Sorry, You're not authorized to access this resource.";
        codeErr = HttpServletResponse.SC_UNAUTHORIZED;
    }
    
    
}
