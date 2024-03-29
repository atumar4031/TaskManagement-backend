package com.oasis.TaskManagementApplication.service;

import com.oasis.TaskManagementApplication.config.security.UserPrincipal;
import com.oasis.TaskManagementApplication.dto.mapper.TaskMapper;
import com.oasis.TaskManagementApplication.dto.req.TaskRequest;
import com.oasis.TaskManagementApplication.dto.res.APIResponse;
import com.oasis.TaskManagementApplication.dto.res.BaseResponse;
import com.oasis.TaskManagementApplication.dto.res.PageResponse;
import com.oasis.TaskManagementApplication.dto.res.TaskResponse;
import com.oasis.TaskManagementApplication.entity.Task;
import com.oasis.TaskManagementApplication.exception.BadRequestException;
import com.oasis.TaskManagementApplication.repo.TaskRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepo taskRepo;
    private final TaskMapper taskMapper;

    public BaseResponse<TaskResponse> createTask(TaskRequest taskRequest, UserPrincipal userPrincipal) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime scheduleTime = LocalDateTime.parse(taskRequest.getDueDate(), formatter);
        if (scheduleTime.isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Date time can not be less than current time");
        }

        Task task = new Task();
        task.setUserId(userPrincipal.getId());
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setStatus("ENABLE");
        task.setDueDate(scheduleTime);
        task.setPriority(taskRequest.getPriority());
        Task save = taskRepo.save(task);

        TaskResponse apply = taskMapper.apply(save);
        return new BaseResponse<>(Boolean.TRUE, "Task created", apply);
    }

    public BaseResponse<TaskResponse> updateTask(TaskRequest taskRequest, long taskId, UserPrincipal userPrincipal) {

        Task task = taskRepo.findById(taskId)
                .orElseThrow(() -> new BadRequestException("Task not found"));
        if (taskRequest.getTitle() != null) {
            task.setTitle(taskRequest.getTitle());
        }
        if (taskRequest.getDescription() != null) {
            task.setDescription(taskRequest.getDescription());
        }
        if (taskRequest.getPriority() != null) {
            task.setPriority(taskRequest.getPriority());
        }
        if (taskRequest.getDueDate() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime scheduleTime = LocalDateTime.parse(taskRequest.getDueDate(), formatter);
            if (scheduleTime.isBefore(LocalDateTime.now())) {
                throw new BadRequestException("Date time can not be less than current time");
            }

            task.setDueDate(scheduleTime);
        }

        Task save = taskRepo.save(task);
        TaskResponse apply = taskMapper.apply(save);
        return new BaseResponse<>(Boolean.TRUE, "Task updated", apply);

    }

    public BaseResponse<TaskResponse> enableTask(long taskId, UserPrincipal userPrincipal) {

        Task task = taskRepo.findById(taskId)
                .orElseThrow(() -> new BadRequestException("Task not found"));

        task.setStatus("ENABLED");
        Task save = taskRepo.save(task);
        TaskResponse apply = taskMapper.apply(save);

        return new BaseResponse<>(Boolean.TRUE, "Task enabled", apply);
    }

    public BaseResponse<TaskResponse> disableTask(long taskId, UserPrincipal userPrincipal) {

        Task task = taskRepo.findById(taskId)
                .orElseThrow(() -> new BadRequestException( "Task not found"));

        task.setStatus("DISABLED");
        Task save = taskRepo.save(task);
        TaskResponse apply = taskMapper.apply(save);

        return new BaseResponse<>(Boolean.TRUE, "Task enabled", apply);

    }

    public BaseResponse<TaskResponse> deleteTask(long taskId, UserPrincipal userPrincipal) {

        Task task = taskRepo.findByIdAndUserId(taskId, userPrincipal.getId())
                .orElseThrow(() -> new BadRequestException( "Task not found"));

        taskRepo.delete(task);
        TaskResponse apply = taskMapper.apply(task);
        return new BaseResponse<>(Boolean.TRUE, "Task deleted", apply);
    }

    public BaseResponse<List<TaskResponse>> findTasks(UserPrincipal principal) {
        List<TaskResponse> taskResponses = taskRepo.findAllByUserIdOrderByIdDesc(principal.getId())
                .stream().map(taskMapper).collect(Collectors.toList());
        return new BaseResponse<>(Boolean.TRUE, "All task", taskResponses);
    }

    public PageResponse<TaskResponse> findTasks(UserPrincipal principal, int page, int size, String sortBy, String sortOrder) {
        if (!List.of("asc", "desc").contains(sortOrder)){
            throw new BadRequestException("Sort direction must be either asc or desc");
        }
        Sort.Direction direction = sortOrder.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<Task> taskPage = taskRepo.findAllByUserId(principal.getId(), pageable);
        List<TaskResponse> taskResponseList = taskPage.getContent()
                .stream().map(taskMapper).collect(Collectors.toList());

        return new PageResponse<>(true, "task page", taskResponseList,
                taskPage.getNumber(),
                taskPage.getSize(),
                taskPage.getTotalElements(),
                taskPage.getTotalPages(),
                taskPage.isFirst(),
                taskPage.isLast());
    }
}
