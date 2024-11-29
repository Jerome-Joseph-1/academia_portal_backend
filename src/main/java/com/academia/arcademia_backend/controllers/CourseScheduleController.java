package com.academia.arcademia_backend.controllers;

import com.academia.arcademia_backend.dto.ResponseWrapper;
import com.academia.arcademia_backend.entity.CourseSchedule;
import com.academia.arcademia_backend.services.CourseScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/schedules")
public class CourseScheduleController {

    @Autowired
    private CourseScheduleService courseScheduleService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/add/{courseId}")
    public ResponseEntity<ResponseWrapper<String>> addSchedule(@PathVariable Long courseId, @RequestBody CourseSchedule schedule) {
        try {
            String result = courseScheduleService.addCourseSchedule(courseId, schedule);
            return new ResponseEntity<>(new ResponseWrapper<>(
                    HttpStatus.CREATED.value(),
                    "Schedule added successfully",
                    result),
                    HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(new ResponseWrapper<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Failed to add schedule: " + ex.getMessage(),
                    null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PatchMapping("/update/{scheduleId}")
    public ResponseEntity<ResponseWrapper<String>> updateSchedule(@PathVariable Long scheduleId, @RequestBody CourseSchedule updatedSchedule) {
        try {
            String result = courseScheduleService.updateCourseSchedule(scheduleId, updatedSchedule);
            return new ResponseEntity<>(new ResponseWrapper<>(
                    HttpStatus.OK.value(),
                    "Schedule updated successfully",
                    result),
                    HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(new ResponseWrapper<>(
                    HttpStatus.NOT_FOUND.value(),
                    "Failed to update schedule: " + ex.getMessage(),
                    null),
                    HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/delete/{scheduleId}")
    public ResponseEntity<ResponseWrapper<String>> deleteSchedule(@PathVariable Long scheduleId) {
        try {
            String result = courseScheduleService.deleteCourseSchedule(scheduleId);
            return new ResponseEntity<>(new ResponseWrapper<>(
                    HttpStatus.OK.value(),
                    "Schedule deleted successfully",
                    result),
                    HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(new ResponseWrapper<>(
                    HttpStatus.NOT_FOUND.value(),
                    "Failed to delete schedule: " + ex.getMessage(),
                    null),
                    HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<ResponseWrapper<List<CourseSchedule>>> getSchedulesByCourse(@PathVariable Long courseId) {
        try {
            List<CourseSchedule> schedules = courseScheduleService.getSchedulesByCourse(courseId);
            return new ResponseEntity<>(new ResponseWrapper<>(
                    HttpStatus.OK.value(),
                    "Schedules fetched successfully",
                    schedules),
                    HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(new ResponseWrapper<>(
                    HttpStatus.NOT_FOUND.value(),
                    "Failed to fetch schedules: " + ex.getMessage(),
                    null),
                    HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<CourseSchedule>>> getAllSchedules() {
        try {
            List<CourseSchedule> schedules = courseScheduleService.getAllSchedules();
            return new ResponseEntity<>(new ResponseWrapper<>(
                    HttpStatus.OK.value(),
                    "All schedules fetched successfully",
                    schedules),
                    HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(new ResponseWrapper<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Failed to fetch schedules: " + ex.getMessage(),
                    null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
