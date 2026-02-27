package com.demo.EmployeeRegistration.controller;

import com.demo.EmployeeRegistration.dao.UserDAO;
import com.demo.EmployeeRegistration.dto.LoginRequest;
import com.demo.EmployeeRegistration.entity.User;
import com.demo.EmployeeRegistration.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * AuthController – handles user authentication.
 *
 * LAB REQUIREMENT:
 * "Create an API for basic authentication with username and password.
 * Upon matching the username and password, generate JWT token and
 * return the JWT token to the client."
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Login API – validates credentials against the database and returns a JWT
     * token.
     *
     * POST /auth/login
     * Form params: username, password
     *
     * Success response: {"token": "eyJhbG..."}
     * Failure response: HTTP 401 + {"error": "Invalid username or password"}
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest) {

        User user = userDAO.findByUsername(loginRequest.getUsername());

        if (user != null && loginRequest.getPassword().equals(user.getPassword())) {
            String token = jwtUtil.generateToken(loginRequest.getUsername());


            Map<String, String> response = new HashMap<>();
            response.put("token", token);

            return ResponseEntity.ok(response);
        }

        // Authentication failed
        Map<String, String> error = new HashMap<>();
        error.put("error", "Invalid username or password");
        return ResponseEntity.status(401).body(error);
    }
}