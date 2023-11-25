package com.coursework.controller;

import com.coursework.dto.EmployeeDto;
import com.coursework.model.Employee;
import com.coursework.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @Operation(summary = "Get all employees")
    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @Operation(summary = "Get employee by ID")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public EmployeeDto getEmployeeById(
            @PathVariable Long id
    ) {
        return employeeService.getEmployeeById(id);
    }

    @Operation(summary = "Get employee by Email")
    @GetMapping("/emails/{email}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public EmployeeDto getEmployeeByEmail(
            @PathVariable String email
    ) {
        return employeeService.getEmployeeByEmail(email);
    }

    @Operation(summary = "Add a new employee")
    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public Employee addEmployee(
            @Valid @RequestBody Employee employee
    ) {
        return employeeService.addEmployee(employee);
    }

    @Operation(summary = "Update a employee")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Employee updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody Employee employee
    ) {
        return employeeService.updateEmployee(id, employee);
    }

    @Operation(summary = "Delete a employee")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Employee deleteEmployee(
            @PathVariable Long id
    ) {
        return employeeService.deleteEmployee(id);
    }

}
