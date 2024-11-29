package com.academia.arcademia_backend.controllers;

import com.academia.arcademia_backend.dto.ResponseWrapper;
import com.academia.arcademia_backend.entity.FacultyCourses;
import com.academia.arcademia_backend.services.FacultyCoursesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/facultyCourses")
public class FacultyCourseScheduleController {

    @Autowired
    private FacultyCoursesService facultyCoursesService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/assign")
    public ResponseEntity<ResponseWrapper<String>> assignCourseToFaculty(@RequestParam Long facultyId, @RequestParam Long courseId) {
        try {
            String result = facultyCoursesService.assignCourseToFaculty(facultyId, courseId);
            return new ResponseEntity<>(new ResponseWrapper<>(
                    HttpStatus.CREATED.value(),
                    "Course assigned to faculty successfully",
                    result),
                    HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(new ResponseWrapper<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Failed to assign course: " + ex.getMessage(),
                    null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/{facultyId}/courses")
    public ResponseEntity<ResponseWrapper<List<FacultyCourses>>> getCoursesForFaculty(@PathVariable Long facultyId) {
        try {
            List<FacultyCourses> courses = facultyCoursesService.getCoursesForFaculty(facultyId);
            return new ResponseEntity<>(new ResponseWrapper<>(
                    HttpStatus.OK.value(),
                    "Courses retrieved successfully",
                    courses),
                    HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(new ResponseWrapper<>(
                    HttpStatus.NOT_FOUND.value(),
                    "Failed to retrieve courses: " + ex.getMessage(),
                    null),
                    HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/remove/{facultyCourseId}")
    public ResponseEntity<ResponseWrapper<String>> removeFacultyCourseAssignment(@PathVariable Long facultyCourseId) {
        try {
            String result = facultyCoursesService.removeFacultyCourse(facultyCourseId);
            return new ResponseEntity<>(new ResponseWrapper<>(
                    HttpStatus.OK.value(),
                    "Faculty course assignment removed successfully",
                    result),
                    HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(new ResponseWrapper<>(
                    HttpStatus.NOT_FOUND.value(),
                    "Failed to remove faculty course assignment: " + ex.getMessage(),
                    null),
                    HttpStatus.NOT_FOUND);
        }
    }
}
