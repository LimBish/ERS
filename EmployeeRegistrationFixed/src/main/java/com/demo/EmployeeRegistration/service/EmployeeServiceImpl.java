package com.demo.EmployeeRegistration.service;

import com.demo.EmployeeRegistration.dto.EmployeeDTO;
import com.demo.EmployeeRegistration.entity.Employee;
import com.demo.EmployeeRegistration.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public EmployeeDTO saveEmployee(EmployeeDTO dto) {

        try {
            if (dto == null) {
                log.error("EmployeeDTO is null");
                throw new IllegalArgumentException("Employee data cannot be null");
            }

            Employee entity = mapToEntity(dto);

            // Repository handles persist/merge automatically
            Employee saved = employeeRepository.save(entity);

            log.info("Employee registered successfully: id={}, email={}",
                    saved.getId(), saved.getEmail());

            return mapToDto(saved);

        } catch (DataIntegrityViolationException e) {
            log.error("Duplicate email or constraint violation: {}", dto.getEmail(), e);
            throw new RuntimeException("Email already exists", e);

        } catch (DataAccessException e) {
            log.error("Database access error", e);
            throw new RuntimeException("Database connection failed", e);

        } catch (Exception e) {
            log.error("Unexpected error during registration", e);
            throw new RuntimeException("Employee registration failed", e);
        }
    }

    // GET ALL EMPLOYEES
    @Override
    public List<EmployeeDTO> getAllEmployees() {
        try {
            log.info("Fetching all employees");

            return employeeRepository.findAll()
                    .stream()
                    .map(this::mapToDto)
                    .collect(Collectors.toList());

        } catch (DataAccessException e) {
            log.error("Database access error while fetching employees", e);
            throw new RuntimeException("Failed to fetch employees", e);
        }
    }

    private Employee mapToEntity(EmployeeDTO dto) {
        Employee e = new Employee();
        e.setName(dto.getName());
        e.setEmail(dto.getEmail());
        e.setContactNumber(dto.getContactNumber());
        e.setPosition(dto.getPosition());
        return e;
    }

    private EmployeeDTO mapToDto(Employee entity) {
        return new EmployeeDTO(
                entity.getName(),
                entity.getEmail(),
                entity.getContactNumber(),
                entity.getPosition());
    }
}
