package com.coursework.controller;

import com.coursework.dto.EmployeeDto;
import com.coursework.model.Employee;
import com.coursework.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @Operation(summary = "Get all employees")
    @GetMapping("/")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @Operation(summary = "Get employee by ID")
    @GetMapping("/{id}")
    public EmployeeDto getEmployeeById(
            @PathVariable Long id
    ) {
        return employeeService.getEmployeeById(id);
    }

    @Operation(summary = "Get employee by Email")
    @GetMapping("/emails/{email}")
    public Employee getEmployeeByEmail(
            @PathVariable String email
    ) {
        return employeeService.getEmployeeByEmail(email);
    }

    @Operation(summary = "Add a new employee")
    @PostMapping("/")
    public Employee addEmployee(
            @Valid @RequestBody EmployeeDto employeeDto
    ) {
        return employeeService.addEmployee(employeeDto);
    }

    @Operation(summary = "Update a employee")
    @PutMapping("/{id}")
    public Employee updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeDto employeeDto
    ) {
        return employeeService.updateEmployee(id, employeeDto);
    }

    @Operation(summary = "Delete a employee")
    @DeleteMapping("/{id}")
    public Employee deleteEmployee(
            @PathVariable Long id
    ) {
        return employeeService.deleteEmployee(id);
    }

}
