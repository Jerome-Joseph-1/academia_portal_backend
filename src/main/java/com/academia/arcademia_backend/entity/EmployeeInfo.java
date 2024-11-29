package com.academia.arcademia_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employees")
public class EmployeeInfo {

    public EmployeeInfo(String employeeId, String title, String photographPath) {
        this.employeeId = employeeId;
        this.title = title;
        this.photograph_path = photographPath;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserInfo user;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = true)
    private Department department;

    @Column(name = "employee_id", unique = true, nullable = false)
    private String employeeId;

    private String title;

    private String photograph_path;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
