// =============================================
// Service Interface
// =============================================
package com.demo.EmployeeRegistration.service;

import com.demo.EmployeeRegistration.dto.EmployeeDTO;

import java.util.List;

public interface EmployeeService {
    EmployeeDTO saveEmployee(EmployeeDTO dto);

    List<EmployeeDTO> getAllEmployees();
}
