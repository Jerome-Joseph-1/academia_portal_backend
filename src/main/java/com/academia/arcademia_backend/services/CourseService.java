package com.academia.arcademia_backend.services;

import com.academia.arcademia_backend.entity.Course;
import com.academia.arcademia_backend.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public String addCourse(Course course) {
        if (courseRepository.existsByCourseCode(course.getCourseCode())) {
            return "Course with this code already exists!";
        }

        courseRepository.save(course);
        return "Course added successfully!";
    }

    public String updateCourse(Long courseId, Course updatedCourse) {
        Optional<Course> existingCourseOpt = courseRepository.findById(courseId);

        if (!existingCourseOpt.isPresent()) {
            return "Course not found!";
        }

        Course existingCourse = existingCourseOpt.get();

        if (updatedCourse.getName() != null) {
            existingCourse.setName(updatedCourse.getName());
        }
        if (updatedCourse.getDescription() != null) {
            existingCourse.setDescription(updatedCourse.getDescription());
        }
        if (updatedCourse.getYear() != null) {
            existingCourse.setYear(updatedCourse.getYear());
        }
        if (updatedCourse.getTerm() != null) {
            existingCourse.setTerm(updatedCourse.getTerm());
        }
        if (updatedCourse.getCredits() != null) {
            existingCourse.setCredits(updatedCourse.getCredits());
        }
        if (updatedCourse.getCapacity() != null) {
            existingCourse.setCapacity(updatedCourse.getCapacity());
        }

        courseRepository.save(existingCourse);
        return "Course updated successfully!";
    }

    public String deleteCourse(Long courseId) {
        if (!courseRepository.existsById(courseId)) {
            return "Course not found!";
        }

        courseRepository.deleteById(courseId);
        return "Course deleted successfully!";
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Optional<Course> getCourseById(Long courseId) {
        return courseRepository.findById(courseId);
    }
}
