package com.oasis.TaskManagementApplication.dto.mapper;

import com.oasis.TaskManagementApplication.dto.res.TaskResponse;
import com.oasis.TaskManagementApplication.entity.Task;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class TaskMapper implements Function<Task, TaskResponse> {

    @Override
    public TaskResponse apply(Task task) {
        TaskResponse taskResponse = new TaskResponse();
        taskResponse.setId(task.getId());
        taskResponse.setUserId(task.getUserId());
        taskResponse.setTitle(task.getTitle());
        taskResponse.setDescription(task.getDescription());
        taskResponse.setStatus(task.getStatus());
        taskResponse.setDueDate(task.getDueDate());
        taskResponse.setPriority(task.getPriority());
        return taskResponse;
    }
}
