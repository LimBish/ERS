package com.demo.EmployeeRegistration;

import com.demo.EmployeeRegistration.dto.EmployeeDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class EmployeeFormValidationTest {

    private Validator validator;

    @BeforeEach
    void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldAcceptValidEmployeeFormData() {
        EmployeeDTO dto = new EmployeeDTO("Jane Doe", "jane.doe@example.com", "9800000000", "QA Engineer");

        Set<ConstraintViolation<EmployeeDTO>> violations = validator.validate(dto);

        assertThat(violations).isEmpty();
    }

    @Test
    void shouldRejectInvalidEmployeeFormData() {
        EmployeeDTO dto = new EmployeeDTO("", "invalid-mail", "123", "");

        Set<ConstraintViolation<EmployeeDTO>> violations = validator.validate(dto);

        assertThat(violations).hasSize(4);

        assertThat(violations)
                .extracting(v -> v.getPropertyPath().toString())
                .containsExactlyInAnyOrder("name", "email", "contactNumber", "position");
    }
}