package com.academia.arcademia_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "faculty_courses")
public class FacultyCourses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "faculty_id", nullable = false)
    private EmployeeInfo faculty;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;
}
