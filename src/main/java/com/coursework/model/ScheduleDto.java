package com.coursework.model;

import java.time.LocalDateTime;

public record ScheduleDto(

        Long employee_id,
        LocalDateTime shiftStartedTime,
        LocalDateTime shiftEndedTime,
        String workLocation

) {

    public ScheduleDto {
        if (shiftStartedTime.isAfter(shiftEndedTime)) {
            throw new RuntimeException();  // TODO:
        }
    }

}
