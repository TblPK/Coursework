package com.coursework.mapper;

import com.coursework.exception.EmployeeNotFoundException;
import com.coursework.exception.RoleNotFoundException;
import com.coursework.model.Employee;
import com.coursework.model.Role;
import com.coursework.repository.EmployeeRepository;
import com.coursework.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomMapper {

    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Named("toEmployee")
    public Employee toEmployee(Long id) {
        if (id == null) return null;
        return employeeRepository.findById(id).orElseThrow(() ->
                new EmployeeNotFoundException("Employee not found with id: " + id)
        );
    }

    @Named("toEmployeeId")
    public Long toEmployeeId(Employee employee) {
        return employee == null ? null : employee.getId();
    }

    @Named("toEncodePassword")
    public String toEncodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Named("nameToRole")
    public Role nameToRole(String role) {
        if (role == null) return null;
        return roleRepository.findByName(role).orElseThrow(() ->
                new RoleNotFoundException("Role " + role + " not found")
        );
    }

    @Named("roleToName")
    public String roleToName(Role role) {
        if (role == null) return null;
        return role.getName();
    }

}