package com.academia.arcademia_backend.dto;

import lombok.Data;

@Data
public class EmployeeRequest {
    // User Info fields
    private String username;
    private String password;
    private String email;
    private String roles;
    private String firstName;
    private String lastName;

    // Employee Info fields
    private String employeeId;
    private Long departmentId;
    private String title;
    private String photograph_path;
}
