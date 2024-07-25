package com.project.test.config.security;


import com.project.test.model.entity.UserEntity;
import com.project.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService samUserService;
    
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail)
            throws UsernameNotFoundException {
        UserEntity user = samUserService.findByUsername(usernameOrEmail);
        if(user == null) {
        	new UsernameNotFoundException("User not found with username or email : " + usernameOrEmail);
        }
        return UserPrincipal.create(user);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        try {
            UserEntity user = samUserService.findById(id.intValue());

            return UserPrincipal.create(user);
		} catch (Exception e) {
			new ResourceNotFoundException("User id="+id);
			return null;
		}
    }
}