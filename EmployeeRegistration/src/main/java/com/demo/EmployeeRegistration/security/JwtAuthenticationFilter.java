package com.demo.EmployeeRegistration.security;

import com.demo.EmployeeRegistration.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.Collections;

/**
 * JWT Authentication Filter
 *
 * - Validate JWT token on protected endpoints (/employee/**)
 * - Reply "Incorrect JWT token" for invalid/missing tokens
 * - Reply "Expired token" for expired tokens
 * - Allow request to proceed only when token is valid
 *
 * NOTE: Not annotated @Component to prevent Spring Boot from auto-registering
 * this as a Servlet filter (which would cause it to run twice).
 * It is registered exclusively via SecurityConfig.addFilterBefore().
 */
public class JwtAuthenticationFilter implements Filter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Only protect /employee/** endpoints
        if (httpRequest.getRequestURI().startsWith("/employee")) {

            String authHeader = httpRequest.getHeader("Authorization");

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                httpResponse.getWriter().write("Incorrect JWT token");
                return;
            }

            String token = authHeader.substring(7);

            try {
                Claims claims = jwtUtil.validateToken(token);

                // Set authentication in SecurityContext so Spring Security
                // recognises this request as authenticated
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                claims.getSubject(), null, Collections.emptyList());
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (ExpiredJwtException e) {
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                httpResponse.getWriter().write("Expired token");
                return;
            } catch (JwtException e) {
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                httpResponse.getWriter().write("Incorrect JWT token");
                return;
            }
        }

        chain.doFilter(request, response);
    }
}