package com.academia.arcademia_backend.repositories;

import com.academia.arcademia_backend.entity.Department;
import com.academia.arcademia_backend.entity.EmployeeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeInfo, Long> {
    Optional<EmployeeInfo> findByEmployeeId(String employeeId);
    long countByDepartment(Department department);
}
