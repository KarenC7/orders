package com.ecommerce.orders.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Slf4j
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    // For initial version, We add some plain text credentials for testing purposes
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Attempting to load user: {}", username);
        if ("user".equals(username)) {
            return User.withUsername("user")
                    .password("{noop}password") // {noop} indicates plain text password
                    .authorities(Collections.emptyList())
                    .build();
        }
        log.error("User not found: {}", username);
        throw new UsernameNotFoundException("User not found");
    }
}