package com.mainapplication.spring_boot.base_entity_module.configuration;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UsernameAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
 
        String username = "";
        if (authentication != null) {
            username = authentication.getName();
        } 
        return Optional.of(username);
    }
}