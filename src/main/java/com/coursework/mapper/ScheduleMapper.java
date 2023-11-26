package com.coursework.mapper;

import com.coursework.dto.ScheduleDto;
import com.coursework.model.Schedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = CustomMapper.class,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface ScheduleMapper {

    @Mapping(source = "scheduleDto.scheduleId", target = "id")
    @Mapping(source = "employeeId", target = "employee", qualifiedByName = "toEmployee")
    @Mapping(source = "scheduleDto.shiftStartedTime", target = "shiftStartedTime")
    @Mapping(source = "scheduleDto.shiftEndedTime", target = "shiftEndedTime")
    @Mapping(source = "scheduleDto.workLocation", target = "workLocation")
    Schedule toEntity(ScheduleDto scheduleDto, Long employeeId);

    @Mapping(source = "id", target = "scheduleId")
    @Mapping(source = "employee", target = "employeeId", qualifiedByName = "toEmployeeId")
    @Mapping(source = "shiftStartedTime", target = "shiftStartedTime")
    @Mapping(source = "shiftEndedTime", target = "shiftEndedTime")
    @Mapping(source = "workLocation", target = "workLocation")
    ScheduleDto toDto(Schedule schedule);

}

