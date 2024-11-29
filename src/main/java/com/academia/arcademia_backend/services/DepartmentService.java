package com.academia.arcademia_backend.services;

import com.academia.arcademia_backend.entity.Department;
import com.academia.arcademia_backend.entity.EmployeeInfo;
import com.academia.arcademia_backend.repositories.DepartmentRepository;
import com.academia.arcademia_backend.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public String addDepartment(Department department) {
        departmentRepository.save(department);
        return "Department added successfully!";
    }

    public String deleteDepartment(Long departmentId) {
        Optional<Department> departmentOpt = departmentRepository.findById(departmentId);

        if (!departmentOpt.isPresent()) {
            return "Department not found!";
        }

        Department department = departmentOpt.get();

        departmentRepository.delete(department);

        return "Department deleted successfully, and employees disassociated.";
    }

    public String updateDepartment(Long departmentId, String name, Integer capacity) {
        Optional<Department> departmentOpt = departmentRepository.findById(departmentId);

        if (!departmentOpt.isPresent()) {
            return "Department not found!";
        }

        Department department = departmentOpt.get();
        if (name != null) {
            department.setName(name);
        }
        if (capacity != null && capacity >= 0) {
            department.setCapacity(capacity);
        }

        departmentRepository.save(department);
        return "Department updated successfully!";
    }

    public boolean canAddEmployeeToDepartment(Long departmentId) {
        Optional<Department> departmentOpt = departmentRepository.findById(departmentId);

        if (!departmentOpt.isPresent()) {
            return false; // Department does not exist
        }

        Department department = departmentOpt.get();
        long employeeCount = employeeRepository.countByDepartment(department);
        return employeeCount < department.getCapacity();
    }
}
