package com.coursework.service;

import com.coursework.model.Employee;
import com.coursework.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return null;
    }

    public Employee getEmployeeById(Long id) {
        return null;
    }

    public Employee addEmployee(Employee employee) {
        return null;
    }

    public Employee updateEmployee(Long id, Employee employee) {
        return null;
    }

    public void deleteEmployee(Long id) {
        return;
    }
}
