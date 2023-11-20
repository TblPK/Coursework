package com.coursework.controller;

import com.coursework.dto.EmployeeDto;
import com.coursework.mapper.EmployeeMapper;
import com.coursework.mapper.EmployeeMapperImpl;
import com.coursework.model.Employee;
import com.coursework.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
@Import(EmployeeMapperImpl.class)
class EmployeeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    EmployeeMapper employeeMapper;

    @MockBean
    EmployeeService employeeService;

    @Test
    void shouldReturnAllEmployees() throws Exception {
        Employee employee1 = createValidEmployee();
        employee1.setId(1L);
        Employee employee2 = createValidEmployee();
        employee1.setId(2L);
        List<Employee> employees = Arrays.asList(employee1, employee2);

        when(employeeService.getAllEmployees()).thenReturn(employees);

        mockMvc.perform(get("/api/v1/employees/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").value(2))
                .andExpect(jsonPath("$[0].id").value(employee1.getId()))
                .andExpect(jsonPath("$[0].firstName").value(employee1.getFirstName()))
                .andExpect(jsonPath("$[0].secondName").value(employee1.getSecondName()))
                .andExpect(jsonPath("$[0].email").value(employee1.getEmail()))
                .andExpect(jsonPath("$[0].position").value(employee1.getPosition()))
                .andExpect(jsonPath("$[0].birthday").value(employee1.getBirthday().toString()))
                .andExpect(jsonPath("$[1].id").value(employee2.getId()))
                .andExpect(jsonPath("$[1].firstName").value(employee2.getFirstName()))
                .andExpect(jsonPath("$[1].secondName").value(employee2.getSecondName()))
                .andExpect(jsonPath("$[1].email").value(employee2.getEmail()))
                .andExpect(jsonPath("$[1].position").value(employee2.getPosition()))
                .andExpect(jsonPath("$[1].birthday").value(employee2.getBirthday().toString()));
    }

    @Test
    void shouldReturnEmployeeById() throws Exception {
        Employee employee = createValidEmployee();

        when(employeeService.getEmployeeById(anyLong())).thenReturn(employee);

        mockMvc.perform(get("/api/v1/employees/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(employee.getId()))
                .andExpect(jsonPath("$.firstName").value(employee.getFirstName()))
                .andExpect(jsonPath("$.secondName").value(employee.getSecondName()))
                .andExpect(jsonPath("$.email").value(employee.getEmail()))
                .andExpect(jsonPath("$.position").value(employee.getPosition()))
                .andExpect(jsonPath("$.birthday").value(employee.getBirthday().toString()));
    }

    @Test
    void shouldReturnEmployeeByEmail() throws Exception {
        Employee employee = createValidEmployee();

        when(employeeService.getEmployeeByEmail(anyString())).thenReturn(employee);

        mockMvc.perform(get("/api/v1/employees/search").param("email", employee.getEmail()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(employee.getId()))
                .andExpect(jsonPath("$.firstName").value(employee.getFirstName()))
                .andExpect(jsonPath("$.secondName").value(employee.getSecondName()))
                .andExpect(jsonPath("$.email").value(employee.getEmail()))
                .andExpect(jsonPath("$.position").value(employee.getPosition()))
                .andExpect(jsonPath("$.birthday").value(employee.getBirthday().toString()));
    }

    @Test
    void shouldAddEmployee() throws Exception {
        Employee employee = createValidEmployee();
        EmployeeDto employeeDto = employeeMapper.toDto(employee);

        when(employeeService.addEmployee(any())).thenReturn(employee);

        mockMvc.perform(post("/api/v1/employees/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(employeeDto))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(employee.getId()))
                .andExpect(jsonPath("$.firstName").value(employee.getFirstName()))
                .andExpect(jsonPath("$.secondName").value(employee.getSecondName()))
                .andExpect(jsonPath("$.email").value(employee.getEmail()))
                .andExpect(jsonPath("$.position").value(employee.getPosition()))
                .andExpect(jsonPath("$.birthday").value(employee.getBirthday().toString()));
    }

    @Test
    void shouldUpdateEmployee() throws Exception {
        Employee employee = createValidEmployee();
        EmployeeDto employeeDto = employeeMapper.toDto(employee);

        when(employeeService.updateEmployee(anyLong(), any())).thenReturn(employee);

        mockMvc.perform(put("/api/v1/employees/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(employeeDto))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(employee.getId()))
                .andExpect(jsonPath("$.firstName").value(employee.getFirstName()))
                .andExpect(jsonPath("$.secondName").value(employee.getSecondName()))
                .andExpect(jsonPath("$.email").value(employee.getEmail()))
                .andExpect(jsonPath("$.position").value(employee.getPosition()))
                .andExpect(jsonPath("$.birthday").value(employee.getBirthday().toString()));
    }

    @Test
    void shouldDeleteEmployee() throws Exception{
        Employee employee = createValidEmployee();

        when(employeeService.deleteEmployee(anyLong())).thenReturn(employee);

        mockMvc.perform(delete("/api/v1/employees/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(employee.getId()))
                .andExpect(jsonPath("$.firstName").value(employee.getFirstName()))
                .andExpect(jsonPath("$.secondName").value(employee.getSecondName()))
                .andExpect(jsonPath("$.email").value(employee.getEmail()))
                .andExpect(jsonPath("$.position").value(employee.getPosition()))
                .andExpect(jsonPath("$.birthday").value(employee.getBirthday().toString()));
    }

    Employee createValidEmployee() {
        Long id = 1L;
        String firstName = "John";
        String secondName = "Doe";
        String email = "john.doe@gmail.com";
        String position = "Java Dev";
        LocalDate birthday = LocalDate.of(1990, 1, 1);

        return new Employee(id, firstName, secondName, email, position, birthday);
    }

}