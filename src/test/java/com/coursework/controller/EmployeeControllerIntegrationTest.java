package com.coursework.controller;

import com.coursework.dto.EmployeeDto;
import com.coursework.mapper.EmployeeMapper;
import com.coursework.mapper.EmployeeMapperImpl;
import com.coursework.model.Employee;
import com.coursework.repository.EmployeeRepository;
import com.coursework.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
@Import(EmployeeMapperImpl.class)
class EmployeeControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    EmployeeMapper employeeMapper;

    @SpyBean
    EmployeeService employeeService;

    @MockBean
    EmployeeRepository employeeRepository;

    @Test
    void shouldReturnAllEmployees() throws Exception {
        Employee employee1 = createValidEmployee();
        Employee employee2 = createValidEmployee();
        employee1.setId(1L);
        employee1.setId(2L);
        List<Employee> employees = Arrays.asList(employee1, employee2);

        when(employeeRepository.findAll()).thenReturn(employees);

        mockMvc.perform(get("/api/v1/employees/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(employees.size()))
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

        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(employee));

        mockMvc.perform(get("/api/v1/employees/{id}", employee.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(employee.getId()))
                .andExpect(jsonPath("$.firstName").value(employee.getFirstName()))
                .andExpect(jsonPath("$.secondName").value(employee.getSecondName()))
                .andExpect(jsonPath("$.email").value(employee.getEmail()))
                .andExpect(jsonPath("$.position").value(employee.getPosition()))
                .andExpect(jsonPath("$.birthday").value(employee.getBirthday().toString()));
    }

    @Test
    void shouldThrowEmployeeNotFoundExceptionWhenGettingNonExistingEmployeeById() throws Exception {
        Long nonExistingEmployeeId = createValidEmployee().getId();

        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/employees/{id}", nonExistingEmployeeId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value("Employee not found with id: " + nonExistingEmployeeId));
    }

    @Test
    void shouldReturnEmployeeByEmail() throws Exception {
        Employee employee = createValidEmployee();

        when(employeeRepository.findByEmail(anyString())).thenReturn(Optional.of(employee));

        mockMvc.perform(get("/api/v1/employees/emails/{email}", employee.getEmail()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(employee.getId()))
                .andExpect(jsonPath("$.firstName").value(employee.getFirstName()))
                .andExpect(jsonPath("$.secondName").value(employee.getSecondName()))
                .andExpect(jsonPath("$.email").value(employee.getEmail()))
                .andExpect(jsonPath("$.position").value(employee.getPosition()))
                .andExpect(jsonPath("$.birthday").value(employee.getBirthday().toString()));
    }

    @Test
    void shouldThrowEmployeeNotFoundExceptionWhenGettingNonExistingEmployeeByEmail() throws Exception {
        String nonExistingEmail = createValidEmployee().getEmail();

        when(employeeRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/employees/emails/{email}", nonExistingEmail))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value("Employee not found with email: " + nonExistingEmail));
    }

    @Test
    void shouldAddEmployee() throws Exception {
        Employee employee = createValidEmployee();
        EmployeeDto employeeDto = employeeMapper.toDto(employee);

        when(employeeRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(employeeRepository.save(any())).thenReturn(employee);

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
    void shouldThrowEmployeeAlreadyExistsExceptionWhenAddingDuplicateEmployee() throws Exception {
        Employee existingEmployee = createValidEmployee();
        EmployeeDto employeeDto = employeeMapper.toDto(existingEmployee);

        when(employeeRepository.findByEmail(anyString())).thenReturn(Optional.of(existingEmployee));

        mockMvc.perform(post("/api/v1/employees/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(employeeDto))
                )
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$").value("Employee already exists with email: " + existingEmployee.getEmail()));
    }

    @Test
    void shouldUpdateEmployee() throws Exception {
        Employee employee = createValidEmployee();
        EmployeeDto employeeDto = employeeMapper.toDto(employee);

        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any())).thenReturn(employee);

        mockMvc.perform(put("/api/v1/employees/{id}", employee.getId())
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
    void shouldThrowEmployeeNotFoundExceptionWhenUpdatingNonExistingEmployee() throws Exception {
        Employee employee = createValidEmployee();
        EmployeeDto employeeDto = employeeMapper.toDto(employee);

        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/v1/employees/{id}", employee.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(employeeDto))
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value("Employee not found with id: " + employee.getId()));
    }

    @Test
    void shouldDeleteEmployee() throws Exception{
        Employee employee = createValidEmployee();

        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(employee));

        mockMvc.perform(delete("/api/v1/employees/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(employee.getId()))
                .andExpect(jsonPath("$.firstName").value(employee.getFirstName()))
                .andExpect(jsonPath("$.secondName").value(employee.getSecondName()))
                .andExpect(jsonPath("$.email").value(employee.getEmail()))
                .andExpect(jsonPath("$.position").value(employee.getPosition()))
                .andExpect(jsonPath("$.birthday").value(employee.getBirthday().toString()));
    }

    @Test
    void shouldThrowEmployeeNotFoundExceptionWhenDeletingNonExistingEmployee() throws Exception {
        Employee employee = createValidEmployee();

        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/v1/employees/{id}", employee.getId()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value("Employee not found with id: " + employee.getId()));
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