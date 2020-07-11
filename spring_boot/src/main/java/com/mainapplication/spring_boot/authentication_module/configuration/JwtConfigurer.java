package com.mainapplication.spring_boot.authentication_module.configuration;

import com.mainapplication.spring_boot.authentication_module.filter.JwtValidateTokenFilter;
import com.mainapplication.spring_boot.user_module.dao.UserRepository;
import com.mainapplication.spring_boot.authentication_module.filter.JwtCreateTokenFilter;
import com.mainapplication.spring_boot.authentication_module.provider.JwtTokenProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.stereotype.Component;

@Component
public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.addFilter(new JwtValidateTokenFilter(authenticationManager, jwtTokenProvider))
            .addFilter(new JwtCreateTokenFilter(authenticationManager, jwtTokenProvider, userRepository));
    }
}