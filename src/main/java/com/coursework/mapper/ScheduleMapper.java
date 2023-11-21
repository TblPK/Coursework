package com.coursework.mapper;

import com.coursework.dto.ScheduleDto;
import com.coursework.exception.EmployeeNotFoundException;
import com.coursework.model.Employee;
import com.coursework.model.Schedule;
import com.coursework.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", uses = UserIdMapper.class)
public interface ScheduleMapper {

    @Mapping(source = "employeeId", target = "employee")
    @Mapping(source = "shiftStartedTime", target = "shiftStartedTime")
    @Mapping(source = "shiftEndedTime", target = "shiftEndedTime")
    @Mapping(source = "workLocation", target = "workLocation")
    Schedule toEntity(ScheduleDto scheduleDto);

    @Mapping(source = "employee", target = "employeeId")
    @Mapping(source = "shiftStartedTime", target = "shiftStartedTime")
    @Mapping(source = "shiftEndedTime", target = "shiftEndedTime")
    @Mapping(source = "workLocation", target = "workLocation")
    ScheduleDto toDto(Schedule schedule);

}

@Component
@RequiredArgsConstructor
class UserIdMapper {

    private final EmployeeRepository employeeRepository;

    public Employee toEmployee(Long id) {
        if (id == null) return null;
        return employeeRepository.findById(id).orElseThrow(() ->
                new EmployeeNotFoundException("Employee not found with id: " + id)
        );
    }

    public Long toEmployeeId(Employee employee) {
        return employee == null ? null : employee.getId();
    }

}
