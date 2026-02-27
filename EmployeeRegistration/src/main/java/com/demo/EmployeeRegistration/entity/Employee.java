// =============================================
// Entity – maps directly to the "employees" table
// =============================================
package com.demo.EmployeeRegistration.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "employees")                  // Explicit table name (good practice)
@Data                                       // Lombok: getters, setters, toString, equals, hashCode
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-increment primary key
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)   // Prevents duplicate emails at DB level
    private String email;

    @Column(nullable = false)
    private String contactNumber;

    @Column(nullable = false)
    private String position;
}