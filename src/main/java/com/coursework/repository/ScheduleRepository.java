package com.coursework.repository;

import com.coursework.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findAllByEmployee_IdAndShiftStartedTimeGreaterThanAndShiftEndedTimeLessThan(
            Long employee_id,
            LocalDateTime shiftStartedTime,
            LocalDateTime shiftEndedTime
    );

    List<Schedule> findAllByWorkLocationAndEmployee_Id(String workLocation, Long employee_id);

    Optional<Schedule> findByIdAndEmployeeId(Long id, Long employeeId);

    void deleteByIdAndEmployeeId(Long id, Long employeeId);

}
