package com.project.test.config.security;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.test.model.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class UserPrincipal implements UserDetails {
	private static final long serialVersionUID = 3545972395145669882L;
    private UserEntity user;
	private Long id;
	@SuppressWarnings("unused")
	private String branchCode;

    @SuppressWarnings("unused")
	private String username;
    @JsonIgnore
    private String email;
    @JsonIgnore
    private String password;
    private Collection<SimpleGrantedAuthority> authorities;

    public UserPrincipal(String username, String password){
        this.username = username;
        this.password = password;
    }

    public UserPrincipal(UserEntity user){
        this.user = user;
        this.username = user.getUsername();
        this.id = Long.valueOf(user.getUserId());
    }

    public static UserPrincipal create(UserEntity user) {
        return new UserPrincipal(user);
    }
    
    
    
    public Long getId() {
        return id;
    }


    public UserEntity getUser() {
        return user;
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }
    
    public String getBranchCode() {
//    	List<BranchEntity> branch = new ArrayList<BranchEntity>(user.getBranch());
//        return branch.get(0).getBranchCode();
        return "";
    }
    /*
    public String getLoginIp() {
        return loginIp;
    }

    public String getWorkgroup() {
        return workgroup;
    }

     */

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPrincipal that = (UserPrincipal) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
