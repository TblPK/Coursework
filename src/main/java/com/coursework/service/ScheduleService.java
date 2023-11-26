package com.coursework.service;

import com.coursework.dto.ScheduleDto;
import com.coursework.exception.ScheduleNotFoundException;
import com.coursework.mapper.ScheduleMapper;
import com.coursework.model.Schedule;
import com.coursework.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;

    /**
     * Retrieves a list of all schedules.
     *
     * @return List of all schedules as ScheduleDto objects.
     */
    public List<ScheduleDto> getAllSchedules() {
        return scheduleRepository.findAll().stream().map(scheduleMapper::toDto).toList();
    }

    /**
     * Retrieves a list of schedules for a specific employee.
     *
     * @param employeeId The ID of the employee for whom to retrieve schedules.
     * @return List of schedules for the specified employee.
     */
    public List<ScheduleDto> getAllSchedulesInTimeIntervalByEmployeeId(
            LocalDateTime shiftStartedTime,
            LocalDateTime shiftEndedTime,
            Long employeeId
    ) {
        return scheduleRepository.findAllByEmployee_IdAndShiftStartedTimeGreaterThanAndShiftEndedTimeLessThan(
                employeeId,
                shiftStartedTime,
                shiftEndedTime
        ).stream().map(scheduleMapper::toDto).toList();
    }

    /**
     * Retrieves a list of schedules for a specific work location.
     *
     * @param workLocation The work location for which to retrieve schedules.
     * @return List of schedules for the specified work location.
     */
    public List<ScheduleDto> getAllSchedulesByWorkLocationAndEmployeeId(Long employeeId, String workLocation) {
        return scheduleRepository.findAllByWorkLocationAndEmployee_Id(
                workLocation,
                employeeId
        ).stream().map(scheduleMapper::toDto).toList();
    }

    /**
     * Adds a new schedule.
     *
     * @param scheduleDto The schedule to add.
     * @return The added schedule.
     */
    public ScheduleDto addSchedule(ScheduleDto scheduleDto, Long employeeId) {
        Schedule schedule = scheduleMapper.toEntity(scheduleDto, employeeId);

        return scheduleMapper.toDto(scheduleRepository.save(schedule));
    }

    /**
     * Updates an existing schedule.
     *
     * @param id          The ID of the schedule to update.
     * @param scheduleDto The updated schedule data.
     * @return The updated schedule.
     * @throws ScheduleNotFoundException if no schedule is found with the given ID.
     */
    public ScheduleDto updateSchedule(Long id, ScheduleDto scheduleDto, Long employeeId) {
        scheduleRepository.findByIdAndEmployeeId(id, employeeId).orElseThrow(() ->
                new ScheduleNotFoundException("Schedule not found for id: " + id)
        );
        Schedule schedule = scheduleMapper.toEntity(scheduleDto, employeeId);

        return scheduleMapper.toDto(scheduleRepository.save(schedule));
    }

    /**
     * Deletes a schedule by its ID.
     *
     * @param id The ID of the schedule to delete.
     * @return The deleted schedule.
     * @throws ScheduleNotFoundException if no schedule is found with the given ID.
     */
    public ScheduleDto deleteScheduleByScheduleIdAndEmployeeId(Long id, Long employeeId) {
        Schedule schedule = scheduleRepository.findByIdAndEmployeeId(id, employeeId).orElseThrow(() ->
                new ScheduleNotFoundException("Schedule not found for id: " + id)
        );
        scheduleRepository.deleteByIdAndEmployeeId(id, employeeId);

        return scheduleMapper.toDto(schedule);
    }
}
