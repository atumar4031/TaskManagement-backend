package com.oasis.TaskManagementApplication.dto.req;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReminderRequest {
    @NotNull(message = "Task is required")
    private Long taskId;

    @NotNull(message = "Time is required")
    private String reminderTime;
}
