package com.academia.arcademia_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id", nullable = false)
    private Long departmentId;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "capacity", nullable = false)
    private Integer capacity;
}
