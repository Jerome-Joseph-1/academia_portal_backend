package com.academia.arcademia_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "course_schedule")
public class CourseSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(nullable = false)
    private String time; // 24-hour format, e.g., "14:30"

    @Column(nullable = false)
    private String day; // Day of the week, e.g., "Monday"

    @Column(nullable = false)
    private String room;

    @Column(nullable = false)
    private String building;
}
