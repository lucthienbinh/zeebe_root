package com.mainapplication.spring_boot.authentication_module.service;

import javax.transaction.Transactional;

import com.mainapplication.spring_boot.user_module.dao.UserRepository;
import com.mainapplication.spring_boot.user_module.model.UserModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class MyUserDetailsService implements UserDetailsService {
 
    @Autowired
    private UserRepository userRepository;
 
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
  
        UserModel user = null ;
        if (userRepository.findByUsername(username).isPresent()) {
            user = userRepository.findByUsername(username).get();
            return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), user.isEnabled(),   
                user.isAccountNonExpired(), user.isCredentialsNonExpired(), 
                user.isAccountNonLocked(), user.getAuthorities());
        } else {
            throw new UsernameNotFoundException("Username: " + username + " not found");
        }
    }
}