package com.academia.arcademia_backend.services;

import com.academia.arcademia_backend.entity.Department;
import com.academia.arcademia_backend.entity.EmployeeInfo;
import com.academia.arcademia_backend.entity.UserInfo;
import com.academia.arcademia_backend.repositories.DepartmentRepository;
import com.academia.arcademia_backend.repositories.EmployeeRepository;
import com.academia.arcademia_backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;

@Service
public class EmployeeInfoService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DepartmentService departmentService;

    public String addEmployee(UserInfo user, EmployeeInfo employee, Long departmentId) {
        if (!departmentService.canAddEmployeeToDepartment(departmentId)) {
            return "Department is at full capacity!";
        }

        Optional<Department> departmentOpt = departmentRepository.findById(departmentId);
        if (!departmentOpt.isPresent()) {
            return "Department not found!";
        }

        Department department = departmentOpt.get();
        employee.setDepartment(department);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        UserInfo savedUser = userRepository.save(user);

        employee.setUser(savedUser);
        employeeRepository.save(employee);

        return "Employee added successfully!";
    }

    public Optional<EmployeeInfo> getEmployeeByEmployeeId(String employeeId) {
        return employeeRepository.findByEmployeeId(employeeId);
    }

    public String partialUpdateEmployee(String employeeId, Map<String, Object> updates) {
        Optional<EmployeeInfo> existingEmployeeOpt = employeeRepository.findByEmployeeId(employeeId);

        if (!existingEmployeeOpt.isPresent()) {
            return "Employee not found!";
        }

        EmployeeInfo existingEmployee = existingEmployeeOpt.get();

        // Handle updates
        updates.forEach((key, value) -> {
            try {
                if ("departmentId".equals(key)) {
                    // Handle department reassignment with capacity check
                    Long departmentId = Long.valueOf(value.toString());
                    Optional<Department> departmentOpt = departmentRepository.findById(departmentId);

                    if (!departmentOpt.isPresent()) {
                        throw new RuntimeException("Department not found!");
                    }

                    Department department = departmentOpt.get();
                    long employeeCount = employeeRepository.countByDepartment(department);

                    if (employeeCount >= department.getCapacity()) {
                        throw new RuntimeException("Cannot assign employee to department: Capacity full!");
                    }

                    existingEmployee.setDepartment(department);
                } else {
                    // Handle other fields dynamically using reflection
                    Field field = EmployeeInfo.class.getDeclaredField(key);
                    field.setAccessible(true);
                    field.set(existingEmployee, value);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException("Invalid field: " + key, e);
            } catch (RuntimeException e) {
                throw new RuntimeException(e.getMessage());
            }
        });

        employeeRepository.save(existingEmployee);
        return "Employee updated successfully!";
    }

    public String deleteEmployee(Long id) {
        Optional<EmployeeInfo> employeeOpt = employeeRepository.findById(id);

        if (!employeeOpt.isPresent()) {
            return "Employee not found!";
        }

        employeeRepository.deleteById(id);
        return "Employee deleted successfully!";
    }


}
