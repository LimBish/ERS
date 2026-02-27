package com.demo.EmployeeRegistration.dao;

import com.demo.EmployeeRegistration.entity.User;

/**
 * User Data Access Object Interface
 *
 * PURPOSE (Week 4 Lab):
 * Defines database operations for User entity
 * Used for authentication (finding users by username)
 */
public interface UserDAO {

    User findByUsername(String username);
    User save(User user);
}
