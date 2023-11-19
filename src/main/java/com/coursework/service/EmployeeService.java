package com.coursework.service;

import com.coursework.exception.EmployeeNotFoundException;
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
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElseThrow(
                () -> new EmployeeNotFoundException("Employee not found for id: " + id)
        );
    }

    public Employee getEmployeeByEmail(String email) {
        return employeeRepository.findByEmail(email).orElseThrow(() ->
                new EmployeeNotFoundException("Employee not found for email: " + email)
        );
    }

    public Employee addEmployee(Employee employee) {
        getEmployeeByEmail(employee.getEmail());
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Long id, Employee employee) {
        employeeRepository.findById(id).orElseThrow(
                () -> new EmployeeNotFoundException("Employee not found for id: " + id)
        );

        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}
