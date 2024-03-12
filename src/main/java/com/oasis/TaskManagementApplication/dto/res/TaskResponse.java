package com.oasis.TaskManagementApplication.dto.res;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskResponse {

    private Long id;

    private Long userId;

    private String title;

    private String description;

    private String status;

    private LocalDateTime dueDate;

    private String priority;
}
