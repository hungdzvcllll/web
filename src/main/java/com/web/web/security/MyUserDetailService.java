package com.web.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.web.web.entity.User;
import com.web.web.repository.UserRepository;


@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User us = userRepository.findByUserNameAndRegistered(username,true);

        if (us == null) {
            throw new UsernameNotFoundException("Could not find user");
        }
        return new MyUserDetails(us);
    }

}