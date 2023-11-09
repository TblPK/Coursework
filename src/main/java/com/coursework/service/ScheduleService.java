package com.coursework.service;

import com.coursework.exception.ScheduleNotFoundException;
import com.coursework.model.Schedule;
import com.coursework.model.ScheduleDto;
import com.coursework.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final EmployeeService employeeService;

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> getAllSchedulesByEmployeeId(Long employeeId) {
        employeeService.getEmployeeById(employeeId);
        return scheduleRepository.findAllByEmployee_Id(employeeId);
    }

    public Schedule addSchedule(ScheduleDto scheduleDto) {
        Schedule schedule = new Schedule(
                null,
                scheduleDto.shiftStartedTime(),
                scheduleDto.shiftEndedTime(),
                scheduleDto.workLocation(),
                employeeService.getEmployeeById(scheduleDto.employee_id())
        );
        return scheduleRepository.save(schedule);
    }

    public Schedule updateSchedule(Long id, ScheduleDto scheduleDto) {
        Optional<Schedule> existingSchedule = scheduleRepository.findById(id);

        if (existingSchedule.isEmpty()) {
            throw new ScheduleNotFoundException("Schedule not found for id: " + id);
        }

        Schedule schedule = new Schedule(
                id,
                scheduleDto.shiftStartedTime(),
                scheduleDto.shiftEndedTime(),
                scheduleDto.workLocation(),
                employeeService.getEmployeeById(scheduleDto.employee_id())
        );
        return scheduleRepository.save(schedule);
    }

    public void deleteSchedule(Long id) {
        scheduleRepository.deleteById(id);
    }

}
