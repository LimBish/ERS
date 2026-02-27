package com.demo.EmployeeRegistration.controller;

import com.demo.EmployeeRegistration.dto.EmployeeDTO;
import com.demo.EmployeeRegistration.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * EmployeeController – handles employee data submission.
 *
 * LAB REQUIREMENT:
 * "The client sends a POST request for form data submission along with a JWT
 * token.
 * The server should validate the JWT token first then insert the data into the
 * database for persistence."
 *
 * JWT validation is handled BEFORE this method runs, inside
 * JwtAuthenticationFilter.
 */
@RestController
@RequestMapping("/employee")
public class    EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * Save employee data to the database.
     *
     * POST /employee/save
     * Header: Authorization: Bearer <token>
     * Form params: name, email, contactNumber, position
     *
     * The JwtAuthenticationFilter validates the token before this runs.
     * If token is invalid → filter returns 401 "Incorrect JWT token"
     * If token is expired → filter returns 401 "Expired token"
     * If token is valid → this method runs and saves the data
     */
    @PostMapping("/save")
    public ResponseEntity<Map<String, String>> saveEmployee(
            @RequestBody EmployeeDTO employeeDTO) {

        // Build DTO from form params

        // Save to database
        employeeService.saveEmployee(employeeDTO);

        // Return success response
        Map<String, String> response = new HashMap<>();
        response.put("message", "Employee saved successfully!");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {

        List<EmployeeDTO> employees = employeeService.getAllEmployees();

        return ResponseEntity.ok(employees);
    }


}