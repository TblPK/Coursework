package com.coursework.service;

import com.coursework.dto.ScheduleDto;
import com.coursework.exception.ScheduleNotFoundException;
import com.coursework.mapper.ScheduleMapper;
import com.coursework.model.Schedule;
import com.coursework.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final EmployeeService employeeService;
    private final ScheduleMapper scheduleMapper;

    /**
     * Retrieves a list of all schedules.
     *
     * @return List of all schedules as scheduleDto objects.
     */
    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    /**
     * Retrieves a list of schedules for a specific employee.
     *
     * @param employeeId The ID of the employee for whom to retrieve schedules.
     * @return List of schedules for the specified employee.
     */
    public List<ScheduleDto> getAllSchedulesByEmployeeId(Long employeeId) {
        employeeService.getEmployeeById(employeeId);

        return scheduleRepository.findAllByEmployee_Id(employeeId).stream().map(scheduleMapper::toDto).toList();
    }

    /**
     * Retrieves a list of schedules for a specific work location.
     *
     * @param workLocation The work location for which to retrieve schedules.
     * @return List of schedules for the specified work location.
     */
    public List<ScheduleDto> getAllSchedulesByWorkLocation(String workLocation) {
        return scheduleRepository.findAllByWorkLocation(workLocation).stream().map(scheduleMapper::toDto).toList();
    }

    /**
     * Adds a new schedule.
     *
     * @param scheduleDto The schedule to add.
     * @return The added schedule.
     */
    public ScheduleDto addSchedule(ScheduleDto scheduleDto) {
        Schedule schedule = scheduleMapper.toEntity(scheduleDto);

        return scheduleMapper.toDto(scheduleRepository.save(schedule));
    }

    /**
     * Updates an existing schedule.
     *
     * @param id           The ID of the schedule to update.
     * @param scheduleDto The updated schedule data.
     * @return The updated schedule.
     * @throws ScheduleNotFoundException if no schedule is found with the given ID.
     */
    public ScheduleDto updateSchedule(Long id, ScheduleDto scheduleDto) {
        Schedule schedule = scheduleMapper.toEntity(scheduleDto);
        scheduleRepository.findById(id).orElseThrow(() ->
                new ScheduleNotFoundException("Schedule not found for id: " + id)
        );

        return scheduleMapper.toDto(scheduleRepository.save(schedule));
    }

    /**
     * Deletes a schedule by its ID.
     *
     * @param id The ID of the schedule to delete.
     * @return The deleted schedule.
     * @throws ScheduleNotFoundException if no schedule is found with the given ID.
     */
    public ScheduleDto deleteSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(() ->
                new ScheduleNotFoundException("Schedule not found for id: " + id)
        );
        scheduleRepository.deleteById(id);

        return scheduleMapper.toDto(schedule);
    }
}
