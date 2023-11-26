package com.coursework.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record EmployeeDto(

        @NotEmpty(message = "First Name should not be empty.")
        @NotNull(message = "First Name should not be null.")
        @Size(min = 2, max = 30, message = "First Name should be between 2 and 30 characters.")
        String firstName,

        @NotEmpty(message = "Second Name should not be empty.")
        @NotNull(message = "Second Name should not be null.")
        @Size(min = 2, max = 30, message = "Second Name should be between 2 and 30 characters.")
        String secondName,

        @NotEmpty(message = "Email should not be empty.")
        @NotNull(message = "Email should not be null.")
        @Email(message = "Email should be valid.")
        String email,

        @NotEmpty(message = "Position should not be empty.")
        @NotNull(message = "Position should not be null.")
        @Size(min = 2, max = 30, message = "Position should be between 2 and 30 characters.")
        String position,

        @NotNull(message = "Birthday should not be null.")
        @Past(message = "Birthday should be in the past.")
        LocalDate birthday,

        @NotEmpty(message = "Username should not be empty.")
        @NotNull(message = "Username should not be null.")
        @Size(min = 2, max = 30, message = "Username should be between 2 and 30 characters.")
        String username,

        @NotEmpty(message = "Password should not be empty.")
        @NotNull(message = "Password should not be null.")
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        String unencodedPassword,

        @NotEmpty(message = "Role should not be empty.")
        @NotNull(message = "Role should not be null.")
        @Pattern(regexp = "^ROLE_\\w+$", message = "Invalid role format. Role should start with ROLE_.")
        String role

) {

}
