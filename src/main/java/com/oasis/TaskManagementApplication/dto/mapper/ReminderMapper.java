package com.oasis.TaskManagementApplication.dto.mapper;

import com.oasis.TaskManagementApplication.dto.res.ReminderResponse;
import com.oasis.TaskManagementApplication.entity.Reminder;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ReminderMapper implements Function<Reminder, ReminderResponse> {

    @Override
    public ReminderResponse apply(Reminder reminder) {

        ReminderResponse reminderResponse = new ReminderResponse();
        reminderResponse.setId(reminder.getId());
        reminderResponse.setReminderTime(reminder.getReminderTime());
        reminderResponse.setTaskId(reminder.getTaskId());
        reminderResponse.setIsSent(reminder.getIsSent());

        return reminderResponse;
    }
}
