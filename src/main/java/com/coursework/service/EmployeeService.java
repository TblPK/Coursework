package com.coursework.service;

import com.coursework.model.Employee;
import com.coursework.model.EmployeeDto;
import com.coursework.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElseThrow(
                () -> new RuntimeException() // TODO:
        );
    }

    public Employee addEmployee(EmployeeDto employeeDto) {
        if (employeeRepository.existsByEmail(employeeDto.email())) {
            throw new RuntimeException(); // TODO:
        }
        Employee employee = new Employee(
                null,
                employeeDto.firstName(),
                employeeDto.secondName(),
                employeeDto.email(),
                employeeDto.position(),
                employeeDto.birthday()
        );
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Long id, EmployeeDto employeeDto) {
        Optional<Employee> existingEmployee = employeeRepository.findById(id);

        if (existingEmployee.isEmpty()) {
            throw new RuntimeException(); // TODO:
        }

        Employee employee = new Employee(
                id,
                employeeDto.firstName(),
                employeeDto.secondName(),
                employeeDto.email(),
                employeeDto.position(),
                employeeDto.birthday()
        );
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}
