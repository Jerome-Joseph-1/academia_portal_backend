package com.academia.arcademia_backend.controllers;

import com.academia.arcademia_backend.entity.CourseSchedule;
import com.academia.arcademia_backend.services.CourseScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String addSchedule(@PathVariable Long courseId, @RequestBody CourseSchedule schedule) {
        return courseScheduleService.addCourseSchedule(courseId, schedule);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PatchMapping("/update/{scheduleId}")
    public String updateSchedule(@PathVariable Long scheduleId, @RequestBody CourseSchedule updatedSchedule) {
        return courseScheduleService.updateCourseSchedule(scheduleId, updatedSchedule);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/delete/{scheduleId}")
    public String deleteSchedule(@PathVariable Long scheduleId) {
        return courseScheduleService.deleteCourseSchedule(scheduleId);
    }

    @GetMapping("/course/{courseId}")
    public List<CourseSchedule> getSchedulesByCourse(@PathVariable Long courseId) {
        return courseScheduleService.getSchedulesByCourse(courseId);
    }

    @GetMapping
    public List<CourseSchedule> getAllSchedules() {
        return courseScheduleService.getAllSchedules();
    }
}
