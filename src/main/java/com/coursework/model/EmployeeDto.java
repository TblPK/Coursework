package com.coursework.model;

import jakarta.validation.constraints.Email;

import java.time.LocalDate;

public record EmployeeDto (
    String firstName,
    String secondName,
    @Email String email,
    String position,
    LocalDate birthday
) {

}
