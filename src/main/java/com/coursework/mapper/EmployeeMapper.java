package com.coursework.mapper;

import com.coursework.dto.EmployeeDto;
import com.coursework.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "secondName", target = "secondName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "position", target = "position")
    @Mapping(source = "birthday", target = "birthday")
    Employee toEntity(EmployeeDto employeeDto);

    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "secondName", target = "secondName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "position", target = "position")
    @Mapping(source = "birthday", target = "birthday")
    EmployeeDto toDto(Employee employee);

}


