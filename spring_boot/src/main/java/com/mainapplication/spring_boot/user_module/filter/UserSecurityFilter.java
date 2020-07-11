// package com.mainapplication.spring_boot.user_module.filter;

// import java.io.IOException;

// import javax.servlet.FilterChain;
// import javax.servlet.ServletException;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;

// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

// public class UserSecurityFilter extends BasicAuthenticationFilter {

//     public UserSecurityFilter(AuthenticationManager authenticationManager) {
//         super(authenticationManager);
//     }

//     @Override
//     protected void doFilterInternal(HttpServletRequest req,
//                                     HttpServletResponse res,
//                                     FilterChain chain) throws IOException, ServletException {
        
//         if (header == null || !header.startsWith(jwtConfiguration.TOKEN_PREFIX)) {
//             chain.doFilter(req, res);
//             return;
//         }

//         UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

//         SecurityContextHolder.getContext().setAuthentication(authentication);
//         chain.doFilter(req, res);
//     }
// }