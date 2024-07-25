package com.project.test.util;



import com.project.test.config.security.UserPrincipal;
import com.project.test.model.entity.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

public class AuthenticationUtil {
    public static Optional<String> getAuthenticationName(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.of("System"); //TODO
        }
        //String ipAddress = ((WebAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getRemoteAddress();
        return Optional.of(authentication.getName());
    }

    public static Authentication getAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static UserEntity getUser(){
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userPrincipal.getUser();
    }

    public static Optional<String> getIpAddress(){
        WebAuthenticationDetails authenticationDetails = (WebAuthenticationDetails)SecurityContextHolder.getContext().getAuthentication().getDetails();

        return Optional.of(authenticationDetails.getRemoteAddress());
    }

    public static Optional<String> getWorkStationName(){
        WebAuthenticationDetails authenticationDetails = (WebAuthenticationDetails)SecurityContextHolder.getContext().getAuthentication().getDetails();
        InetAddress address = null;
        String hostName = null;
        try {
            address = InetAddress.getByName(authenticationDetails.getRemoteAddress());
            hostName = address.getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return Optional.of(hostName);
    }
}
