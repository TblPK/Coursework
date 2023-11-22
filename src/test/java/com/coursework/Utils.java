package com.coursework;

import com.coursework.model.Employee;
import com.coursework.model.Schedule;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Utils {

    private static final LocalDateTime LOCAL_DATE_TIME = LocalDateTime.now();

    public static Employee createValidEmployee() {
        Long id = 5L;
        String firstName = "John";
        String secondName = "Doe";
        String email = "john.doe@gmail.com";
        String position = "Java Dev";
        LocalDate birthday = LocalDate.of(1990, 1, 1);

        return new Employee(id, firstName, secondName, email, position, birthday);
    }

    public static Schedule createValidSchedule() {
        Long id = 1L;
        LocalDateTime shiftStartedTime = LOCAL_DATE_TIME;
        LocalDateTime shiftEndedTime = shiftStartedTime.plusDays(5);
        String workLocation = "Tinkoff";

        return new Schedule(id, shiftStartedTime, shiftEndedTime, workLocation, createValidEmployee());
    }

}
