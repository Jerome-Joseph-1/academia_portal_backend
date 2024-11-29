package com.academia.arcademia_backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(unique = false, nullable = false)
    private String firstName;

    @Column(unique = false, nullable = true)
    private String lastName;

    @Column(nullable = false)
    private String password;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String roles;

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

    // Add this constructor
    public UserInfo(String username, String password, String email, String roles, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.roles = roles;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}