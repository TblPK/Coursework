package com.coursework.controller;

import com.coursework.model.Employee;
import com.coursework.model.EmployeeDto;
import com.coursework.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
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
    public Employee getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    @Operation(summary = "Add a new employee")
    @PostMapping("/")
    public Employee addEmployee(@ParameterObject EmployeeDto employeeDto) {
        return employeeService.addEmployee(employeeDto);
    }

    @Operation(summary = "Update a employee")
    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable Long id, @ParameterObject EmployeeDto employeeDto) {
        return employeeService.updateEmployee(id, employeeDto);
    }

    @Operation(summary = "Delete a employee")
    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
    }
}
