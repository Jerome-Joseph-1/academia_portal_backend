package com.academia.arcademia_backend.controllers;

import com.academia.arcademia_backend.dto.EmployeeRequest;
import com.academia.arcademia_backend.entity.Department;
import com.academia.arcademia_backend.entity.EmployeeInfo;
import com.academia.arcademia_backend.entity.UserInfo;
import com.academia.arcademia_backend.services.DepartmentService;
import com.academia.arcademia_backend.services.EmployeeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    @Autowired
    private EmployeeInfoService employeeService;
    @Autowired
    private DepartmentService departmentService;

    @PreAuthorize("hasAuthority('ROLE_HR')")
    @PostMapping("/add")
    public String addEmployee(@RequestBody EmployeeRequest request) {
        return employeeService.addEmployee(
                new UserInfo(
                        request.getUsername(),
                        request.getPassword(),
                        request.getEmail(),
                        request.getRoles(),
                        request.getFirstName(),
                        request.getLastName()
                ),
                new EmployeeInfo(
                        request.getEmployeeId(),
                        request.getTitle(),
                        request.getPhotograph_path()
                ),
                request.getDepartmentId()
        );
    }

    @PreAuthorize("hasAuthority('ROLE_HR')")
    @GetMapping("/employeeId/{employeeId}")
    public Optional<EmployeeInfo> getEmployeeByEmployeeId(@PathVariable String employeeId) {
        return employeeService.getEmployeeByEmployeeId(employeeId);
    }

    @PreAuthorize("hasAuthority('ROLE_HR')")
    @PatchMapping("/update")
    public String partialUpdateEmployee(@RequestBody Map<String, Object> request) {
        String employeeId = (String) request.get("empid");
        if (employeeId == null) {
            return "Employee ID is required!";
        }

        Map<String, Object> updates = (Map<String, Object>) request.get("update");
        if (updates == null || updates.isEmpty()) {
            return "No updates provided!";
        }

        return employeeService.partialUpdateEmployee(employeeId, updates);
    }

    @PreAuthorize("hasAuthority('ROLE_HR')")
    @DeleteMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        return employeeService.deleteEmployee(id);
    }

}
