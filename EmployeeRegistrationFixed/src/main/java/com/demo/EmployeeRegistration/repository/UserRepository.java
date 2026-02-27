package com.demo.EmployeeRegistration.repository;

import com.demo.EmployeeRegistration.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Used by UserDAOImpl for login authentication
    User findByUsername(String username);
}
