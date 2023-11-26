package com.coursework.controller;

import com.coursework.dto.ScheduleDto;
import com.coursework.mapper.CustomMapper;
import com.coursework.mapper.EmployeeMapperImpl;
import com.coursework.mapper.ScheduleMapper;
import com.coursework.mapper.ScheduleMapperImpl;
import com.coursework.model.Schedule;
import com.coursework.repository.EmployeeRepository;
import com.coursework.repository.ScheduleRepository;
import com.coursework.service.EmployeeService;
import com.coursework.service.ScheduleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.coursework.Utils.createValidSchedule;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ScheduleController.class)
@Import({ScheduleMapperImpl.class, CustomMapper.class, EmployeeMapperImpl.class})
class ScheduleControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ScheduleMapper scheduleMapper;

    @SpyBean
    CustomMapper customMapper;

    @SpyBean
    ScheduleService scheduleService;

    @SpyBean
    EmployeeService employeeService;

    @MockBean
    EmployeeRepository employeeRepository;

    @MockBean
    ScheduleRepository scheduleRepository;

    final String URL = "/api/v1/schedules";

    @Test
    void shouldReturnAllSchedules() throws Exception {
        Schedule schedule1 = createValidSchedule();
        Schedule schedule2 = createValidSchedule();
        schedule1.setId(1L);
        schedule2.setId(2L);
        schedule1.getEmployee().setId(1L);
        schedule2.getEmployee().setId(2L);
        List<Schedule> schedules = Arrays.asList(schedule1, schedule2);

        when(scheduleRepository.findAll()).thenReturn(schedules);

        mockMvc.perform(get(URL + "/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(schedules.size()))
                .andExpect(jsonPath("$[0].id").value(schedule1.getId()))
                .andExpect(jsonPath("$[0].shiftStartedTime").isNotEmpty())
                .andExpect(jsonPath("$[0].shiftEndedTime").isNotEmpty())
                .andExpect(jsonPath("$[0].workLocation").value(schedule1.getWorkLocation()))
                .andExpect(jsonPath("$[1].id").value(schedule2.getId()))
                .andExpect(jsonPath("$[1].shiftStartedTime").isNotEmpty())
                .andExpect(jsonPath("$[1].shiftEndedTime").isNotEmpty())
                .andExpect(jsonPath("$[1].workLocation").value(schedule2.getWorkLocation()));
    }

    @Test
    void shouldReturnAllSchedulesByEmployeeId() throws Exception {
        Schedule schedule1 = createValidSchedule();
        Schedule schedule2 = createValidSchedule();
        schedule1.setId(1L);
        schedule2.setId(2L);
        schedule1.getEmployee().setId(1L);
        schedule2.getEmployee().setId(1L);
        List<Schedule> schedules = Arrays.asList(schedule1, schedule2);

        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(schedule1.getEmployee()));
        when(scheduleRepository.findAllByEmployee_Id(any())).thenReturn(schedules);

        mockMvc.perform(get(URL + "/employeeId/{employeeId}", schedule1.getEmployee().getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(schedules.size()))
                .andExpect(jsonPath("$[0].employeeId").value(schedule1.getEmployee().getId()))
                .andExpect(jsonPath("$[0].shiftStartedTime").isNotEmpty())
                .andExpect(jsonPath("$[0].shiftEndedTime").isNotEmpty())
                .andExpect(jsonPath("$[0].workLocation").value(schedule1.getWorkLocation()))
                .andExpect(jsonPath("$[1].employeeId").value(schedule1.getEmployee().getId()))
                .andExpect(jsonPath("$[1].shiftStartedTime").isNotEmpty())
                .andExpect(jsonPath("$[1].shiftEndedTime").isNotEmpty())
                .andExpect(jsonPath("$[1].workLocation").value(schedule2.getWorkLocation()));
    }

    @Test
    void shouldThrowEmployeeNotFoundExceptionWhenGettingSchedulesByNonExistingEmployeeId() throws Exception {
        Schedule schedule = createValidSchedule();
        schedule.getEmployee().setId(1L);

        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get(URL + "/employeeId/{employeeId}", schedule.getEmployee().getId()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value("Employee not found with id: " + schedule.getEmployee().getId()));
    }

    @Test
    void shouldReturnAllSchedulesByWorkLocation() throws Exception {
        Schedule schedule1 = createValidSchedule();
        Schedule schedule2 = createValidSchedule();
        schedule1.setWorkLocation("Tinkoff");
        schedule2.setWorkLocation("Tinkoff");
        List<Schedule> schedules = Arrays.asList(schedule1, schedule2);

        when(scheduleRepository.findAllByWorkLocation(anyString())).thenReturn(schedules);

        mockMvc.perform(get(URL + "/workLocation/{workLocation}", schedule1.getWorkLocation()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(schedules.size()))
                .andExpect(jsonPath("$[0].employeeId").value(schedule1.getEmployee().getId()))
                .andExpect(jsonPath("$[0].shiftStartedTime").isNotEmpty())
                .andExpect(jsonPath("$[0].shiftEndedTime").isNotEmpty())
                .andExpect(jsonPath("$[0].workLocation").value(schedule1.getWorkLocation()))
                .andExpect(jsonPath("$[1].employeeId").value(schedule2.getEmployee().getId()))
                .andExpect(jsonPath("$[1].shiftStartedTime").isNotEmpty())
                .andExpect(jsonPath("$[1].shiftEndedTime").isNotEmpty())
                .andExpect(jsonPath("$[1].workLocation").value(schedule2.getWorkLocation()));
    }

    @Test
    void shouldAddSchedule() throws Exception {
        Schedule schedule = createValidSchedule();
        ScheduleDto scheduleDto = scheduleMapper.toDto(schedule);

        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(schedule.getEmployee()));
        when(scheduleRepository.save(any())).thenReturn(schedule);

        mockMvc.perform(post(URL + "/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(scheduleDto))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeId").value(schedule.getEmployee().getId()))
                .andExpect(jsonPath("$.shiftStartedTime").isNotEmpty())
                .andExpect(jsonPath("$.shiftEndedTime").isNotEmpty())
                .andExpect(jsonPath("$.workLocation").value(schedule.getWorkLocation()));
    }

    @Test
    void shouldThrowEmployeeNotFoundExceptionWhenAddingScheduleWithNonExistingEmployeeId() throws Exception {
        Schedule schedule = createValidSchedule();
        ScheduleDto scheduleDto = scheduleMapper.toDto(schedule);

        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(post(URL + "/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(scheduleDto))
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value("Employee not found with id: " + schedule.getEmployee().getId()));
    }

    @Test
    void shouldUpdateSchedule() throws Exception {
        Schedule schedule = createValidSchedule();
        ScheduleDto scheduleDto = scheduleMapper.toDto(schedule);

        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(schedule.getEmployee()));
        when(scheduleRepository.findById(anyLong())).thenReturn(Optional.of(schedule));
        when(scheduleRepository.save(any())).thenReturn(schedule);

        mockMvc.perform(put(URL + "/{id}", schedule.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(scheduleDto))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeId").value(schedule.getEmployee().getId()))
                .andExpect(jsonPath("$.shiftStartedTime").isNotEmpty())
                .andExpect(jsonPath("$.shiftEndedTime").isNotEmpty())
                .andExpect(jsonPath("$.workLocation").value(schedule.getWorkLocation()));
    }

    @Test
    void shouldThrowEmployeeNotFoundExceptionWhenUpdatingScheduleWithNonExistingEmployeeId() throws Exception {
        Schedule schedule = createValidSchedule();
        ScheduleDto scheduleDto = scheduleMapper.toDto(schedule);

        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(scheduleRepository.findById(anyLong())).thenReturn(Optional.of(schedule));

        mockMvc.perform(put(URL + "/{id}", schedule.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(scheduleDto))
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value("Employee not found with id: " + schedule.getEmployee().getId()));
    }

    @Test
    void shouldThrowScheduleNotFoundExceptionWhenUpdatingScheduleWithNonExistingEmployeeId() throws Exception {
        Schedule schedule = createValidSchedule();
        ScheduleDto scheduleDto = scheduleMapper.toDto(schedule);

        when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(schedule.getEmployee()));
        when(scheduleRepository.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(put(URL + "/{id}", schedule.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(scheduleDto))
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value("Schedule not found for id: " + schedule.getId()));
    }

    @Test
    void shouldDeleteSchedule() throws Exception {
        Schedule schedule = createValidSchedule();

        when(scheduleRepository.findById(anyLong())).thenReturn(Optional.of(schedule));

        mockMvc.perform(delete(URL + "/{id}", schedule.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeId").value(schedule.getEmployee().getId()))
                .andExpect(jsonPath("$.shiftStartedTime").isNotEmpty())
                .andExpect(jsonPath("$.shiftEndedTime").isNotEmpty())
                .andExpect(jsonPath("$.workLocation").value(schedule.getWorkLocation()));
    }

    @Test
    void shouldThrowScheduleNotFoundExceptionWhenDeletingNonExistingSchedule() throws Exception {
        Schedule schedule = createValidSchedule();

        when(scheduleRepository.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(delete(URL + "/{id}", schedule.getId()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value("Schedule not found for id: " + schedule.getId()));
    }

}