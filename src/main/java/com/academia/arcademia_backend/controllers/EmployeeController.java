package com.academia.arcademia_backend.controllers;

import com.academia.arcademia_backend.dto.EmployeeRequest;
import com.academia.arcademia_backend.dto.ResponseWrapper;
import com.academia.arcademia_backend.entity.EmployeeInfo;
import com.academia.arcademia_backend.entity.UserInfo;
import com.academia.arcademia_backend.services.DepartmentService;
import com.academia.arcademia_backend.services.EmployeeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ResponseWrapper<String>> addEmployee(@RequestBody EmployeeRequest request) {
        try {
            String result = employeeService.addEmployee(
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
            return new ResponseEntity<>(new ResponseWrapper<>(
                    HttpStatus.CREATED.value(),
                    "Employee added successfully",
                    result),
                    HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(new ResponseWrapper<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Failed to add employee: " + ex.getMessage(),
                    null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAuthority('ROLE_HR')")
    @GetMapping("/employeeId/{employeeId}")
    public ResponseEntity<ResponseWrapper<Optional<EmployeeInfo>>> getEmployeeByEmployeeId(@PathVariable String employeeId) {
        try {
            Optional<EmployeeInfo> employee = employeeService.getEmployeeByEmployeeId(employeeId);
            return new ResponseEntity<>(new ResponseWrapper<>(
                    HttpStatus.OK.value(),
                    "Employee fetched successfully",
                    employee),
                    HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(new ResponseWrapper<>(
                    HttpStatus.NOT_FOUND.value(),
                    "Employee not found: " + ex.getMessage(),
                    Optional.empty()),
                    HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('ROLE_HR')")
    @PatchMapping("/update")
    public ResponseEntity<ResponseWrapper<String>> partialUpdateEmployee(@RequestBody Map<String, Object> request) {
        try {
            String employeeId = (String) request.get("empid");
            if (employeeId == null) {
                return new ResponseEntity<>(new ResponseWrapper<>(
                        HttpStatus.BAD_REQUEST.value(),
                        "Employee ID is required",
                        null),
                        HttpStatus.BAD_REQUEST);
            }

            Map<String, Object> updates = (Map<String, Object>) request.get("update");
            if (updates == null || updates.isEmpty()) {
                return new ResponseEntity<>(new ResponseWrapper<>(
                        HttpStatus.BAD_REQUEST.value(),
                        "No updates provided",
                        null),
                        HttpStatus.BAD_REQUEST);
            }

            String result = employeeService.partialUpdateEmployee(employeeId, updates);
            return new ResponseEntity<>(new ResponseWrapper<>(
                    HttpStatus.OK.value(),
                    "Employee updated successfully",
                    result),
                    HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(new ResponseWrapper<>(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Failed to update employee: " + ex.getMessage(),
                    null),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAuthority('ROLE_HR')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseWrapper<String>> deleteEmployee(@PathVariable Long id) {
        try {
            String result = employeeService.deleteEmployee(id);
            return new ResponseEntity<>(new ResponseWrapper<>(
                    HttpStatus.OK.value(),
                    "Employee deleted successfully",
                    result),
                    HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(new ResponseWrapper<>(
                    HttpStatus.NOT_FOUND.value(),
                    "Failed to delete employee: " + ex.getMessage(),
                    null),
                    HttpStatus.NOT_FOUND);
        }
    }
}
