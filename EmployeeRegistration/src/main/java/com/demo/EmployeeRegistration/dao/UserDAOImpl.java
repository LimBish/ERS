package com.demo.EmployeeRegistration.dao;

import com.demo.EmployeeRegistration.entity.User;
import com.demo.EmployeeRegistration.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * UserDAO Implementation
 *
 * PURPOSE (Week 4 Lab):
 * Provides concrete database access for User entity.
 * Used by AuthServiceImpl to validate login credentials.
 *
 * FLOW:
 * AuthService → UserDAO → UserRepository (Spring Data JPA) → H2 DB
 */
@Repository
@RequiredArgsConstructor
public class UserDAOImpl implements UserDAO {

    private final UserRepository userRepository;

    /**
     * Find user by username for login authentication.
     * LAB REQUIREMENT: Match username from login request.
     */
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Save a user to the database.
     * Used by DataInitializer to create test user on startup.
     */
    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}
