package com.oasis.TaskManagementApplication.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.oasis.TaskManagementApplication.enums.Priority;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String title;

    private String description;

    // [completed, active, suspended] /write a cron job to complete all active tasks with dueDate less than currentDate
    private String status;

    private LocalDateTime dueDate;

    private String priority;
}
