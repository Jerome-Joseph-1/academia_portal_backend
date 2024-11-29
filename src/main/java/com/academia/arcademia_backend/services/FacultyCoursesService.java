package com.academia.arcademia_backend.services;

import com.academia.arcademia_backend.entity.Course;
import com.academia.arcademia_backend.entity.CourseSchedule;
import com.academia.arcademia_backend.entity.EmployeeInfo;
import com.academia.arcademia_backend.entity.FacultyCourses;
import com.academia.arcademia_backend.repositories.CourseRepository;
import com.academia.arcademia_backend.repositories.CourseScheduleRepository;
import com.academia.arcademia_backend.repositories.EmployeeRepository;
import com.academia.arcademia_backend.repositories.FacultyCoursesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class FacultyCoursesService {

    @Autowired
    private FacultyCoursesRepository facultyCoursesRepository;

    @Autowired
    private CourseScheduleRepository courseScheduleRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CourseRepository courseRepository;

    public String assignCourseToFaculty(Long facultyId, Long courseId) {
        // Fetch faculty and course
        EmployeeInfo faculty = employeeRepository.findById(facultyId)
                .orElseThrow(() -> new RuntimeException("Faculty not found!"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found!"));

        // Fetch schedules for the course to be assigned
        List<CourseSchedule> newSchedules = courseScheduleRepository.findAllByCourse(course);

        // Fetch all existing schedules for the faculty
        List<CourseSchedule> existingSchedules = facultyCoursesRepository.findByFaculty(faculty).stream()
                .flatMap(fc -> courseScheduleRepository.findAllByCourse(fc.getCourse()).stream())
                .toList();

        // Check for time conflicts
        for (CourseSchedule newSchedule : newSchedules) {
            LocalTime newStartTime = LocalTime.parse(newSchedule.getTime(), DateTimeFormatter.ofPattern("HH:mm"));
            LocalTime newEndTime = newStartTime.plusMinutes(90); // Add 1.5 hours to get the end time

            for (CourseSchedule existingSchedule : existingSchedules) {
                LocalTime existingStartTime = LocalTime.parse(existingSchedule.getTime(), DateTimeFormatter.ofPattern("HH:mm"));
                LocalTime existingEndTime = existingStartTime.plusMinutes(90);

                // Check if the schedules overlap
                if (newSchedule.getDay().equals(existingSchedule.getDay()) &&
                        newStartTime.isBefore(existingEndTime) &&
                        existingStartTime.isBefore(newEndTime)) {
                    return "Time conflict detected for faculty: " + newSchedule.getDay() + " at " + newSchedule.getTime();
                }
            }
        }

        // Assign course if no conflicts
        FacultyCourses facultyCourse = FacultyCourses.builder()
                .faculty(faculty)
                .course(course)
                .build();

        facultyCoursesRepository.save(facultyCourse);
        return "Course assigned to faculty successfully!";
    }

    public List<FacultyCourses> getCoursesForFaculty(Long facultyId) {
        EmployeeInfo faculty = employeeRepository.findById(facultyId)
                .orElseThrow(() -> new RuntimeException("Faculty not found!"));
        return facultyCoursesRepository.findByFaculty(faculty);
    }

    public String removeFacultyCourse(Long facultyCourseId) {
        if (!facultyCoursesRepository.existsById(facultyCourseId)) {
            return "Faculty-course assignment not found!";
        }

        facultyCoursesRepository.deleteById(facultyCourseId);
        return "Faculty-course assignment removed successfully!";
    }
}
