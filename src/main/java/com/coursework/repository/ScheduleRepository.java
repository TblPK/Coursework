package com.coursework.repository;

import com.coursework.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByEmployee_Id(Long employeeId);
}
