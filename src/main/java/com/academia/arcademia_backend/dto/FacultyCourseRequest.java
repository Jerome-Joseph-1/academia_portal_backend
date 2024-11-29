package com.academia.arcademia_backend.dto;

import lombok.Data;

@Data
public class FacultyCourseRequest {
    private Long facultyId;
    private Long courseId;
}
