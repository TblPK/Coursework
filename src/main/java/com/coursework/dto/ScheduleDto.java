package com.coursework.dto;

import com.coursework.exception.InvalidShiftTimeException;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record ScheduleDto(

        @NotEmpty(message = "Employee ID should not be empty.")
        @NotNull(message = "Employee ID should not be null.")
        Long employee_id,

        @NotNull(message = "Shift started time should not be null.")
        LocalDateTime shiftStartedTime,

        @NotNull(message = "Shift ended time should not be null.")
        LocalDateTime shiftEndedTime,

        @NotEmpty(message = "Work location should not be empty.")
        @NotNull(message = "Work location should not be null.")
        @Size(min = 2, max = 15, message = "Work location should be between 2 and 15 characters.")
        String workLocation

) {

    public ScheduleDto {
        if (shiftStartedTime.isAfter(shiftEndedTime)) {
            throw new InvalidShiftTimeException("Shift start time cannot be after shift end time.");
        }
    }

}
