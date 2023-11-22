package com.coursework.mapper;

import com.coursework.dto.ScheduleDto;
import com.coursework.model.Schedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UserIdMapper.class)
public interface ScheduleMapper {

    @Mapping(target = "id", ignore = true)
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

