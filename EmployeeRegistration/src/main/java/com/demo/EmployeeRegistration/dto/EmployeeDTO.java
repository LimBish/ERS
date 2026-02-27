// =============================================
// DTO – carries data from HTML form to service layer
// Validation annotations are processed by @Valid in controller
// =============================================
package com.demo.EmployeeRegistration.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Contact number is required")
    @Size(min = 10, max = 15, message = "Contact number must be between 10 and 15 digits")
    private String contactNumber;

    @NotBlank(message = "Position is required")
    private String position;
}