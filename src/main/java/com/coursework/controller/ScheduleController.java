package com.coursework.controller;

import com.coursework.dto.ScheduleDto;
import com.coursework.model.Schedule;
import com.coursework.service.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
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
    @GetMapping("/employeeId/{employeeId}")
    public List<Schedule> getAllSchedulesByEmployeeId(
            @PathVariable Long employeeId
    ) {
        return scheduleService.getAllSchedulesByEmployeeId(employeeId);
    }

    @Operation(summary = "Get schedules by work location")
    @GetMapping("/workLocation/{workLocation}")
    public List<Schedule> getAllSchedulesByWorkLocation(
            @PathVariable String workLocation
    ) {
        return scheduleService.getAllSchedulesByWorkLocation(workLocation);
    }

    @Operation(summary = "Add a new schedule")
    @PostMapping("/")
    public Schedule addSchedule(
            @RequestBody ScheduleDto scheduleDto
    ) {
        return scheduleService.addSchedule(scheduleDto);
    }

    @Operation(summary = "Update a schedule")
    @PutMapping("/{id}")
    public Schedule updateSchedule(
            @PathVariable Long id,
            @RequestBody ScheduleDto scheduleDto
    ) {
        return scheduleService.updateSchedule(id, scheduleDto);
    }

    @Operation(summary = "Delete a schedule")
    @DeleteMapping("/{id}")
    public Schedule deleteSchedule(
            @PathVariable Long id
    ) {
        return scheduleService.deleteSchedule(id);
    }

}

