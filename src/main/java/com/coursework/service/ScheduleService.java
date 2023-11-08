package com.coursework.service;

import com.coursework.model.Schedule;
import com.coursework.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepositorytory;

    public List<Schedule> getAllSchedules() {
        return null;
    }

    public Schedule getScheduleById(Long id) {
        return null;
    }

    public Schedule addSchedule(Schedule schedule) {
        return null;
    }

    public Schedule updateSchedule(Long id, Schedule schedule) {
        return null;
    }

    public void deleteSchedule(Long id) {
        return;
    }
}
