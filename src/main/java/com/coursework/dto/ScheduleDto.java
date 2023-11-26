package com.coursework.dto;

import com.coursework.exception.InvalidShiftTimeException;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record ScheduleDto(

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Long employeeId,

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Long scheduleId,

        @NotNull(message = "Shift started time should not be null.")
        LocalDateTime shiftStartedTime,

        @NotNull(message = "Shift ended time should not be null.")
        LocalDateTime shiftEndedTime,

        @NotEmpty(message = "Work location should not be empty.")
        @NotNull(message = "Work location should not be null.")
        @Size(min = 2, max = 30, message = "Work location should be between 2 and 30 characters.")
        String workLocation

) {

    public ScheduleDto {
        if (shiftStartedTime.isAfter(shiftEndedTime)) {
            throw new InvalidShiftTimeException("Shift start time cannot be after shift end time.");
        }
    }

}
