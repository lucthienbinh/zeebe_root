package com.mainapplication.spring_boot.authentication_module.filter;

import com.mainapplication.spring_boot.authentication_module.provider.JwtTokenProvider;
import com.mainapplication.spring_boot.user_module.dao.UserRepository;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtCreateTokenFilter extends UsernamePasswordAuthenticationFilter {
    
    private UserRepository userRepository;
    private JwtTokenProvider jwtTokenProvider;
    private AuthenticationManager authenticationManager;
    private boolean postOnly = true;
    
    public JwtCreateTokenFilter(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,HttpServletResponse res) throws AuthenticationException {
        if (postOnly && !request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException(
					"Authentication method not supported: " + request.getMethod());
		}

		String username = obtainUsername(request);
		String password = obtainPassword(request);

		if (username == null) {
			username = "";
		}

		if (password == null) {
			password = "";
		}

		username = username.trim();

		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
				username, password);

		// Allow subclasses to set the "details" property
		setDetails(request, authRequest);

		return authenticationManager.authenticate(authRequest);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
        Authentication auth) throws IOException, ServletException  {
            SecurityContextHolder.getContext().setAuthentication(auth);
            String username = ((User) auth.getPrincipal()).getUsername();
            String token = jwtTokenProvider.createToken(username, this.userRepository.findByUsername(username).get().getAuthoritiesStringList());
            res.addHeader("Authorization", "Bearer " + token);
            res.setHeader("Access-Control-Allow-Credentials", "true");
            res.setHeader("Access-Control-Allow-Origin", "*");
            res.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
            res.setHeader("Access-Control-Max-Age", "3600");
            res.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
    }
}