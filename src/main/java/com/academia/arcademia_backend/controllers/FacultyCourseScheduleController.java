package com.academia.arcademia_backend.controllers;

import com.academia.arcademia_backend.entity.FacultyCourses;
import com.academia.arcademia_backend.services.FacultyCoursesService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String assignCourseToFaculty(@RequestParam Long facultyId, @RequestParam Long courseId) {
        return facultyCoursesService.assignCourseToFaculty(facultyId, courseId);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/{facultyId}/courses")
    public List<FacultyCourses> getCoursesForFaculty(@PathVariable Long facultyId) {
        return facultyCoursesService.getCoursesForFaculty(facultyId);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/remove/{facultyCourseId}")
    public String removeFacultyCourseAssignment(@PathVariable Long facultyCourseId) {
        return facultyCoursesService.removeFacultyCourse(facultyCourseId);
    }
}
