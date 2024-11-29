package com.academia.arcademia_backend.services;

import com.academia.arcademia_backend.entity.Course;
import com.academia.arcademia_backend.entity.CourseSchedule;
import com.academia.arcademia_backend.repositories.CourseRepository;
import com.academia.arcademia_backend.repositories.CourseScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseScheduleService {

    @Autowired
    private CourseScheduleRepository courseScheduleRepository;

    @Autowired
    private CourseRepository courseRepository;

    public String addCourseSchedule(Long courseId, CourseSchedule schedule) {
        Optional<Course> courseOpt = courseRepository.findById(courseId);
        if (!courseOpt.isPresent()) {
            return "Course not found!";
        }

        Course course = courseOpt.get();
        schedule.setCourse(course);

        courseScheduleRepository.save(schedule);
        return "Course schedule added successfully!";
    }

    public String updateCourseSchedule(Long scheduleId, CourseSchedule updatedSchedule) {
        // Fetch the existing schedule
        Optional<CourseSchedule> existingScheduleOpt = courseScheduleRepository.findById(scheduleId);
        if (!existingScheduleOpt.isPresent()) {
            return "Schedule not found!";
        }

        CourseSchedule existingSchedule = existingScheduleOpt.get();

        // Update fields
        if (updatedSchedule.getDay() != null) {
            existingSchedule.setDay(updatedSchedule.getDay());
        }
        if (updatedSchedule.getTime() != null) {
            existingSchedule.setTime(updatedSchedule.getTime());
        }
        if (updatedSchedule.getRoom() != null) {
            existingSchedule.setRoom(updatedSchedule.getRoom());
        }
        if (updatedSchedule.getBuilding() != null) {
            existingSchedule.setBuilding(updatedSchedule.getBuilding());
        }

        // Check for room conflicts
        if (courseScheduleRepository.existsByDayAndTimeAndRoom(existingSchedule.getDay(), existingSchedule.getTime(), existingSchedule.getRoom())) {
            return "Conflict: Another class is scheduled in the same room at this time!";
        }

        courseScheduleRepository.save(existingSchedule);
        return "Course schedule updated successfully!";
    }

    public String deleteCourseSchedule(Long scheduleId) {
        if (!courseScheduleRepository.existsById(scheduleId)) {
            return "Schedule not found!";
        }

        courseScheduleRepository.deleteById(scheduleId);
        return "Course schedule deleted successfully!";
    }

    public List<CourseSchedule> getSchedulesByCourse(Long courseId) {
        Optional<Course> courseOpt = courseRepository.findById(courseId);
        if (!courseOpt.isPresent()) {
            throw new RuntimeException("Course not found!");
        }

        return courseScheduleRepository.findAllByCourse(courseOpt.get());
    }

    public List<CourseSchedule> getAllSchedules() {
        return courseScheduleRepository.findAll();
    }
}
