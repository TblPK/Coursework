package com.coursework.service;

import com.coursework.exception.EmployeeAlreadyExistsException;
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

    /**
     * Retrieves a list of all employees.
     *
     * @return List of all employees.
     */
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    /**
     * Retrieves an employee by their ID.
     *
     * @param id The ID of the employee to retrieve.
     * @return The employee with the specified ID.
     * @throws EmployeeNotFoundException if no employee is found with the given ID.
     */
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElseThrow(
                () -> new EmployeeNotFoundException("Employee not found with id: " + id)
        );
    }

    /**
     * Retrieves an employee by their email.
     *
     * @param email The email of the employee to retrieve.
     * @return The employee with the specified email.
     * @throws EmployeeNotFoundException if no employee is found with the given email.
     */
    public Employee getEmployeeByEmail(String email) {
        return employeeRepository.findByEmail(email).orElseThrow(() ->
                new EmployeeNotFoundException("Employee not found with email: " + email)
        );
    }

    /**
     * Adds a new employee.
     *
     * @param employee The employee to add.
     * @return The added employee.
     * @throws EmployeeAlreadyExistsException if an employee with the same email already exists.
     */
    public Employee addEmployee(Employee employee) {
        employeeRepository.findByEmail(employee.getEmail()).ifPresent(existingEmployee -> {
            throw new EmployeeAlreadyExistsException("Employee already exists with email: " + employee.getEmail());
        });

        return employeeRepository.save(employee);
    }

    /**
     * Updates an existing employee.
     *
     * @param id       The ID of the employee to update.
     * @param employee The updated employee data.
     * @return The updated employee.
     * @throws EmployeeNotFoundException if no employee is found with the given ID.
     */
    public Employee updateEmployee(Long id, Employee employee) {
        getEmployeeById(id);
        employee.setId(id);

        return employeeRepository.save(employee);
    }

    /**
     * Deletes an employee by their ID.
     *
     * @param id The ID of the employee to delete.
     * @throws EmployeeNotFoundException if no employee is found with the given ID.
     */
    public Employee deleteEmployee(Long id) {
        Employee employee = getEmployeeById(id);
        employeeRepository.deleteById(id);

        return employee;
    }

}
