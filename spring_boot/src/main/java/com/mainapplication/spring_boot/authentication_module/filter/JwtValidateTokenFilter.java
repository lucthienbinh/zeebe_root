package com.mainapplication.spring_boot.authentication_module.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mainapplication.spring_boot.authentication_module.provider.InvalidJwtAuthenticationException;
import com.mainapplication.spring_boot.authentication_module.provider.JwtTokenProvider;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JwtValidateTokenFilter extends BasicAuthenticationFilter {
    
    private JwtTokenProvider jwtTokenProvider;
    public JwtValidateTokenFilter(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        super(authenticationManager);
        this.jwtTokenProvider = jwtTokenProvider;
    }
    @Override
	protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
            String requestMethod = request.getMethod();
            String requestURI = request.getRequestURI(); 
            String token = jwtTokenProvider.resolveToken(request);
            boolean authenFailOrLogout = false;
            if (token != null) {
                try {
                    if (jwtTokenProvider.validateToken(token)) {
                        if (requestMethod == "GET" && requestURI == "/logout") {
                            authenFailOrLogout = true;
                        } else {
                            Authentication auth = jwtTokenProvider.getAuthentication(token);
                            SecurityContextHolder.getContext().setAuthentication(auth);
                        }
                    }
                } catch (InvalidJwtAuthenticationException ex) {
                    authenFailOrLogout = true;
                }
                if (authenFailOrLogout == true) {
                    SecurityContextHolder.clearContext();
                    response.reset();
                }
            }
            chain.doFilter(request, response);
    }
}