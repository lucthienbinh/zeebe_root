package com.mainapplication.spring_boot.authentication_module.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(8);
    }

    // Declare a bean
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // https://www.baeldung.com/java-config-spring-security
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                        .csrf().disable() //HTTP with Disable CSRF
                        .authorizeRequests()
                        // .antMatchers("/**").hasRole("ADMIN")
                        // .antMatchers(HttpMethod.GET,"/api/**").permitAll()
                        .antMatchers("/").hasRole("USER")
                        .antMatchers("/api/user/**").hasRole("ADMIN")
                        .antMatchers("/", "/register", "/css/**", "/js/**", "/img/**", "/lib/**", "/bootstrap/**" ).permitAll()
                        .anyRequest().permitAll()
                    .and()
                        .formLogin()
                        // .loginPage("/user/login").permitAll()
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/api/user/list")
                        .failureUrl("/login?error")
                    .and() //Logout Form configuration
                        .logout()
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                    .and()
                        .rememberMe().key("uniqueAndSecret")
                        .tokenValiditySeconds(86400);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

    // @Bean
    // public ViewResolver viewResolver() {
    //     InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    //     viewResolver.setPrefix("/WEB-INF/views/");
    //     viewResolver.setSuffix(".jsp");
    //     return viewResolver;
    // }

    @Bean
    public ViewResolver viewResolver()
    {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

        viewResolver.setPrefix("classpath:templates/");
        viewResolver.setSuffix(".html");

        return viewResolver;
    }
}