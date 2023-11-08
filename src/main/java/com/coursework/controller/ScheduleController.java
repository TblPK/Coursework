package com.coursework.controller;

import com.coursework.model.Schedule;
import com.coursework.model.ScheduleDto;
import com.coursework.service.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Operation(summary = "Get all schedules")
    @GetMapping("/")
    public List<Schedule> getAllSchedules() {
        return scheduleService.getAllSchedules();
    }

    @Operation(summary = "Get schedules by employee ID")
    @GetMapping("/{employeeId}")
    public List<Schedule> getAllSchedulesByEmployeeId(@PathVariable Long employeeId) {
        return scheduleService.getAllSchedulesByEmployeeId(employeeId);
    }

    @Operation(summary = "Add a new schedule")
    @PostMapping("/")
    public Schedule addSchedule(@ParameterObject ScheduleDto scheduleDto) {
        return scheduleService.addSchedule(scheduleDto);
    }

    @Operation(summary = "Update a schedule")
    @PutMapping("/{id}")
    public Schedule updateSchedule(@PathVariable Long id, @ParameterObject ScheduleDto scheduleDto) {
        return scheduleService.updateSchedule(id, scheduleDto);
    }

    @Operation(summary = "Delete a schedule")
    @DeleteMapping("/{id}")
    public void deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
    }
}

