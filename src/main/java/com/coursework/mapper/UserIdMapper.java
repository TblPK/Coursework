package com.coursework.mapper;

import com.coursework.exception.EmployeeNotFoundException;
import com.coursework.model.Employee;
import com.coursework.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserIdMapper {

    private final EmployeeRepository employeeRepository;

    public Employee toEmployee(Long id) {
        if (id == null) return null;
        return employeeRepository.findById(id).orElseThrow(() ->
                new EmployeeNotFoundException("Employee not found with id: " + id)
        );
    }

    public Long toEmployeeId(Employee employee) {
        return employee == null ? null : employee.getId();
    }

}
