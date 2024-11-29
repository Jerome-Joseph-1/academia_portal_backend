package com.academia.arcademia_backend.controllers;

import com.academia.arcademia_backend.entity.Department;
import com.academia.arcademia_backend.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PreAuthorize("hasAuthority('ROLE_HR')")
    @PostMapping("/add")
    public String addDepartment(@RequestBody Department department) {
        return departmentService.addDepartment(department);
    }

    @PreAuthorize("hasAuthority('ROLE_HR')")
    @DeleteMapping("/delete/{id}")
    public String deleteDepartment(@PathVariable Long id) {
        return departmentService.deleteDepartment(id);
    }

    @PreAuthorize("hasAuthority('ROLE_HR')")
    @PatchMapping("/update/{id}")
    public String updateDepartment(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        String name = (String) updates.get("name");
        Integer capacity = updates.containsKey("capacity") ? (Integer) updates.get("capacity") : null;
        return departmentService.updateDepartment(id, name, capacity);
    }
}
