package com.academia.arcademia_backend.controllers;

import com.academia.arcademia_backend.dto.ResponseWrapper;
import com.academia.arcademia_backend.entity.Course;
import com.academia.arcademia_backend.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<ResponseWrapper<String>> addCourse(@RequestBody Course course) {
        try {
            String result = courseService.addCourse(course);
            return new ResponseEntity<>(new ResponseWrapper<>(
                    HttpStatus.CREATED.value(),
                    "Course added successfully",
                    result),
                    HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(new ResponseWrapper<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Failed to add course: " + ex.getMessage(),
                    null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PatchMapping("/update/{id}")
    public ResponseEntity<ResponseWrapper<String>> updateCourse(@PathVariable Long id, @RequestBody Course course) {
        try {
            String result = courseService.updateCourse(id, course);
            return new ResponseEntity<>(new ResponseWrapper<>(
                    HttpStatus.OK.value(),
                    "Course updated successfully",
                    result),
                    HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(new ResponseWrapper<>(
                    HttpStatus.NOT_FOUND.value(),
                    "Failed to update course: " + ex.getMessage(),
                    null),
                    HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseWrapper<String>> deleteCourse(@PathVariable Long id) {
        try {
            String result = courseService.deleteCourse(id);
            return new ResponseEntity<>(new ResponseWrapper<>(
                    HttpStatus.OK.value(),
                    "Course deleted successfully",
                    result),
                    HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(new ResponseWrapper<>(
                    HttpStatus.NOT_FOUND.value(),
                    "Failed to delete course: " + ex.getMessage(),
                    null),
                    HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<Course>>> getAllCourses() {
        try {
            List<Course> courses = courseService.getAllCourses();
            return new ResponseEntity<>(new ResponseWrapper<>(
                    HttpStatus.OK.value(),
                    "Courses fetched successfully",
                    courses),
                    HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(new ResponseWrapper<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Failed to fetch courses: " + ex.getMessage(),
                    null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ResponseWrapper<Optional<Course>>> getCourseById(@PathVariable Long id) {
        try {
            Optional<Course> course = courseService.getCourseById(id);
            return new ResponseEntity<>(new ResponseWrapper<>(
                    HttpStatus.OK.value(),
                    "Course fetched successfully",
                    course),
                    HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(new ResponseWrapper<>(
                    HttpStatus.NOT_FOUND.value(),
                    "Failed to fetch course: " + ex.getMessage(),
                    null),
                    HttpStatus.NOT_FOUND);
        }
    }
}
