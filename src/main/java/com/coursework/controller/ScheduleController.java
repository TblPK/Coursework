package com.coursework.controller;

import com.coursework.dto.ScheduleDto;
import com.coursework.model.Employee;
import com.coursework.service.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Operation(summary = "Get all schedules")
    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public List<ScheduleDto> getAllSchedules() {
        return scheduleService.getAllSchedules();
    }

    @Operation(summary = "Get schedules in time interval")
    @GetMapping("/inTimeInterval")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<ScheduleDto> getAllSchedulesInTimeIntervalByEmployeeId(
            @AuthenticationPrincipal Employee employee,
            @RequestParam(name = "shiftStartedTime") LocalDateTime shiftStartedTime,
            @RequestParam(name = "shiftEndedTime") LocalDateTime shiftEndedTime
    ) {
        return scheduleService.getAllSchedulesInTimeIntervalByEmployeeId(shiftStartedTime, shiftEndedTime, employee.getId());
    }

    @Operation(summary = "Get schedules by work location")
    @GetMapping("/{workLocation}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<ScheduleDto> getAllSchedulesByWorkLocationAndEmployeeId(
            @AuthenticationPrincipal Employee employee,
            @PathVariable String workLocation
    ) {
        return scheduleService.getAllSchedulesByWorkLocationAndEmployeeId(employee.getId(), workLocation);
    }

    @Operation(summary = "Add a new schedule")
    @PostMapping("/")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ScheduleDto addSchedule(
            @AuthenticationPrincipal Employee employee,
            @RequestBody ScheduleDto scheduleDto
    ) {
        return scheduleService.addSchedule(scheduleDto, employee.getId());
    }

    @Operation(summary = "Update a schedule")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ScheduleDto updateSchedule(
            @AuthenticationPrincipal Employee employee,
            @PathVariable Long id,
            @RequestBody ScheduleDto scheduleDto
    ) {
        return scheduleService.updateSchedule(id, scheduleDto, employee.getId());
    }

    @Operation(summary = "Delete a schedule")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ScheduleDto deleteScheduleByScheduleIdAndEmployeeId(
            @AuthenticationPrincipal Employee employee,
            @PathVariable Long id
    ) {
        return scheduleService.deleteScheduleByScheduleIdAndEmployeeId(id, employee.getId());
    }

}

