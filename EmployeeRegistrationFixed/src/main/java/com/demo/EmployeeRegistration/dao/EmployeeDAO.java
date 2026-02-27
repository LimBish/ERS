// =============================================
// DAO Interface – classic Data Access Object pattern
// Abstracts persistence operations (lab requires DAO)
// =============================================
package com.demo.EmployeeRegistration.dao;

import com.demo.EmployeeRegistration.entity.Employee;

public interface EmployeeDAO {
    Employee save(Employee employee);
    Employee findById(Long id);
}