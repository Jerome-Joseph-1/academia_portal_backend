package com.academia.arcademia_backend.repositories;

import com.academia.arcademia_backend.entity.FacultyCourses;
import com.academia.arcademia_backend.entity.EmployeeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacultyCoursesRepository extends JpaRepository<FacultyCourses, Long> {
    List<FacultyCourses> findByFaculty(EmployeeInfo faculty);
}
