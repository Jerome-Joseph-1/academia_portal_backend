package com.academia.arcademia_backend.controllers;

import com.academia.arcademia_backend.dto.ResponseWrapper;
import com.academia.arcademia_backend.entity.Department;
import com.academia.arcademia_backend.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ResponseWrapper<String>> addDepartment(@RequestBody Department department) {
        try {
            String result = departmentService.addDepartment(department);
            return new ResponseEntity<>(new ResponseWrapper<>(
                    HttpStatus.CREATED.value(),
                    "Department added successfully",
                    result),
                    HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(new ResponseWrapper<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Failed to add department: " + ex.getMessage(),
                    null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAuthority('ROLE_HR')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseWrapper<String>> deleteDepartment(@PathVariable Long id) {
        try {
            String result = departmentService.deleteDepartment(id);
            return new ResponseEntity<>(new ResponseWrapper<>(
                    HttpStatus.OK.value(),
                    "Department deleted successfully",
                    result),
                    HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(new ResponseWrapper<>(
                    HttpStatus.NOT_FOUND.value(),
                    "Failed to delete department: " + ex.getMessage(),
                    null),
                    HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('ROLE_HR')")
    @PatchMapping("/update/{id}")
    public ResponseEntity<ResponseWrapper<String>> updateDepartment(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        try {
            String name = (String) updates.get("name");
            Integer capacity = updates.containsKey("capacity") ? (Integer) updates.get("capacity") : null;
            String result = departmentService.updateDepartment(id, name, capacity);
            return new ResponseEntity<>(new ResponseWrapper<>(
                    HttpStatus.OK.value(),
                    "Department updated successfully",
                    result),
                    HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(new ResponseWrapper<>(
                    HttpStatus.BAD_REQUEST.value(),
                    "Failed to update department: " + ex.getMessage(),
                    null),
                    HttpStatus.BAD_REQUEST);
        }
    }
}
