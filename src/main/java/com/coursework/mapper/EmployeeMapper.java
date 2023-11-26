package com.coursework.mapper;

import com.coursework.dto.EmployeeDto;
import com.coursework.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = CustomMapper.class,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface EmployeeMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "secondName", target = "secondName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "position", target = "position")
    @Mapping(source = "birthday", target = "birthday")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "unencodedPassword", target = "password", qualifiedByName = "toEncodePassword")
    @Mapping(source = "role", target = "role", qualifiedByName = "nameToRole")
    @Mapping(target = "authorities", ignore = true)
    Employee toEntity(EmployeeDto employeeDto);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "secondName", target = "secondName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "position", target = "position")
    @Mapping(source = "birthday", target = "birthday")
    @Mapping(source = "username", target = "username")
    @Mapping(target = "unencodedPassword", ignore = true)
    @Mapping(source = "role", target = "role", qualifiedByName = "roleToName")
    EmployeeDto toDto(Employee employee);

}


