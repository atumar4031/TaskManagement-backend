package com.oasis.TaskManagementApplication.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Schedule date is required")
    private String  dueDate;

    @NotNull(message = "Priority is required")
    private String priority;
}
