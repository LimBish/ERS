package com.demo.EmployeeRegistration.config;

import com.demo.EmployeeRegistration.dao.UserDAO;
import com.demo.EmployeeRegistration.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Database Initializer
 *
 * PURPOSE (Week 4 Lab):
 * Creates default test user for authentication testing
 *
 * RUNS AUTOMATICALLY:
 * This runs when application starts (CommandLineRunner)
 * Creates a test user if it doesn't exist
 *
 * TEST USER CREDENTIALS:
 * Username: admin
 * Password: password123
 *
 * USAGE:
 * Use these credentials to test login API and get JWT token
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserDAO userDAO;

    /**
     * Runs on application startup
     * Creates default test user for lab testing
     */
    @Override
    public void run(String... args) {
        try {
            // Check if user already exists
            User existingUser = userDAO.findByUsername("admin");

            if (existingUser == null) {
                // Create test user
                User testUser = new User();
                testUser.setUsername("admin");
                testUser.setPassword("password123");  // Plain text for lab
                testUser.setRole("USER");

                userDAO.save(testUser);

                log.info("================================================");
                log.info("TEST USER CREATED FOR WEEK 4 LAB");
                log.info("Username: admin");
                log.info("Password: password123");
                log.info("Use these credentials to test JWT authentication");
                log.info("================================================");
            } else {
                log.info("Test user already exists: admin");
            }
        } catch (Exception e) {
            log.error("Error initializing test user: {}", e.getMessage());
        }
    }
}