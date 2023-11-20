package com.coursework.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record EmployeeDto(
        @NotEmpty(message = "First Name should not be empty.")
        @NotNull(message = "First Name should not be null.")
        @Size(min = 2, max = 15, message = "First Name should be between 2 and 15 characters.")
        String firstName,

        @NotEmpty(message = "Second Name should not be empty.")
        @NotNull(message = "Second Name should not be null.")
        @Size(min = 2, max = 15, message = "Second Name should be between 2 and 15 characters.")
        String secondName,

        @NotEmpty(message = "Email should not be empty.")
        @NotNull(message = "Email should not be null.")
        @Email(message = "Email should be valid.")
        String email,

        @NotEmpty(message = "Position should not be empty.")
        @NotNull(message = "Position should not be null.")
        @Size(min = 2, max = 15, message = "Position should be between 2 and 15 characters.")
        String position,

        @NotNull(message = "Birthday should not be null.")
        @Past(message = "Birthday should be in the past.")
        LocalDate birthday
) {

}
