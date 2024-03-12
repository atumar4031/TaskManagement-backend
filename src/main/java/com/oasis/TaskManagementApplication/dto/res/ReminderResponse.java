package com.oasis.TaskManagementApplication.dto.res;

import lombok.Data;

import java.time.LocalTime;

@Data
public class ReminderResponse {

    private Long id;

    private LocalTime reminderTime;

    private Long taskId;

    private Boolean isSent;
}
