package com.coursework.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "schedule")
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime shiftStartedTime;

    @Column(nullable = false)
    private LocalDateTime shiftEndedTime;

    @Column(nullable = false)
    private String workLocation;

    @ManyToOne
    @JoinColumn(name = "employeeId", nullable = false)
    private Employee employee;

}
