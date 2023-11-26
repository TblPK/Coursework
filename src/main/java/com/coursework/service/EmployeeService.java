package com.coursework.service;

import com.coursework.dto.EmployeeDto;
import com.coursework.exception.EmployeeAlreadyExistsException;
import com.coursework.exception.EmployeeNotFoundException;
import com.coursework.exception.IncorrectUsernameOrPasswordException;
import com.coursework.mapper.EmployeeMapper;
import com.coursework.model.Employee;
import com.coursework.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    /**
     * Retrieves a list of all employees.
     *
     * @return List of all employees.
     */
    public List<EmployeeDto> getAllEmployees() {
        return employeeRepository.findAll().stream().map(employeeMapper::toDto).toList();
    }

    /**
     * Retrieves an employee by their ID.
     *
     * @param id The ID of the employee to retrieve.
     * @return The employee with the specified ID.
     * @throws EmployeeNotFoundException if no employee is found with the given ID.
     */
    public EmployeeDto getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() ->
                new EmployeeNotFoundException("Employee not found with id: " + id)
        );

        return employeeMapper.toDto(employee);
    }

    /**
     * Retrieves an employee by their email.
     *
     * @param email The email of the employee to retrieve.
     * @return The employee with the specified email.
     * @throws EmployeeNotFoundException if no employee is found with the given email.
     */
    public EmployeeDto getEmployeeByEmail(String email) {
        Employee employee = employeeRepository.findByEmail(email).orElseThrow(() ->
                new EmployeeNotFoundException("Employee not found with email: " + email)
        );

        return employeeMapper.toDto(employee);
    }

    /**
     * Adds a new employee.
     *
     * @param employeeDto The employee to add.
     * @return The added employee.
     * @throws EmployeeAlreadyExistsException if an employee with the same email already exists.
     */
    public EmployeeDto addEmployee(EmployeeDto employeeDto) {
        employeeRepository.findByEmail(employeeDto.email()).ifPresent(employee -> {
            throw new EmployeeAlreadyExistsException("Employee already exists with email: " + employeeDto.email());
        });
        Employee employee = employeeMapper.toEntity(employeeDto);

        return employeeMapper.toDto(employeeRepository.save(employee));
    }

    /**
     * Updates an existing employee.
     *
     * @param id          The ID of the employee to update.
     * @param employeeDto The updated employee data.
     * @return The updated employee.
     * @throws EmployeeNotFoundException if no employee is found with the given ID.
     */
    public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDto) {
        getEmployeeById(id);
        Employee employee = employeeMapper.toEntity(employeeDto);
        employee.setId(id);

        return employeeMapper.toDto(employeeRepository.save(employee));
    }

    /**
     * Deletes an employee by their ID.
     *
     * @param id The ID of the employee to delete.
     * @return The deleted employee.
     * @throws EmployeeNotFoundException if no employee is found with the given ID.
     */
    public EmployeeDto deleteEmployee(Long id) {
        EmployeeDto employeeDto = getEmployeeById(id);
        employeeRepository.deleteById(id);

        return employeeDto;
    }

    /**
     * Load user by username (used by Spring Security).
     *
     * @param username The username to load the user.
     * @return UserDetails object representing the loaded user.
     * @throws IncorrectUsernameOrPasswordException if no user is found with the given username.
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        return employeeRepository.findByUsername(username).orElseThrow(() ->
                new IncorrectUsernameOrPasswordException("Incorrect username or password")
        );
    }
}
