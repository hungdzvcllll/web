package com.web.web.security;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.web.web.entity.User;



@Service
public class MyUserDetails implements UserDetails {
    private User us;
    public MyUserDetails(){
    }
    public MyUserDetails(User us) {
        this.us = us;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        System.out.println("role"+us.getRole());
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(us.getRole());
        return Arrays.asList(authority);
        
    }

    @Override
    public String getPassword() {
        return us.getPassword();
    }

    @Override
    public String getUsername() {
        return us.getUsername();
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
}