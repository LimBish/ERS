package com.demo.EmployeeRegistration.security;

import com.demo.EmployeeRegistration.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtUtil jwtUtil;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // Construct the filter here (not a Spring bean) to prevent
        // Spring Boot from auto-registering it as a Servlet filter,
        // which would cause it to execute twice on every request.
        JwtAuthenticationFilter jwtFilter = new JwtAuthenticationFilter(jwtUtil);

        http
                // Disable CSRF for H2 console and REST APIs
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**", "/auth/**", "/employee/**"))

                // All access control is handled by JwtAuthenticationFilter for /employee/**
                // Spring Security permits everything here; the JWT filter is the gatekeeper
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll())

                // Add JWT Filter before Spring Security's auth filter
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)

                // Disable frame options for H2 console
                .headers(headers -> headers
                        .frameOptions(frame -> frame.disable()))

                // Stateless session (for JWT)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
