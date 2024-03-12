package com.oasis.TaskManagementApplication.controller;

import com.oasis.TaskManagementApplication.config.security.CurrentUser;
import com.oasis.TaskManagementApplication.config.security.UserPrincipal;
import com.oasis.TaskManagementApplication.dto.req.TaskRequest;
import com.oasis.TaskManagementApplication.dto.res.BaseResponse;
import com.oasis.TaskManagementApplication.dto.res.PageResponse;
import com.oasis.TaskManagementApplication.dto.res.TaskResponse;
import com.oasis.TaskManagementApplication.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/task")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/create")
    public ResponseEntity<BaseResponse<TaskResponse>> createTask(@Valid @RequestBody TaskRequest taskRequest, @CurrentUser UserPrincipal principal) {
        BaseResponse<TaskResponse> response = taskService.createTask(taskRequest, principal);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/update/{taskId}")
    public ResponseEntity<BaseResponse<TaskResponse>> updateTask(@Valid @RequestBody TaskRequest taskRequest, @PathVariable long taskId, @CurrentUser UserPrincipal principal) {
        BaseResponse<TaskResponse> response = taskService.updateTask(taskRequest, taskId, principal);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/enable/{taskId}")
    public ResponseEntity<BaseResponse<TaskResponse>> enableTask(@PathVariable long taskId, @CurrentUser UserPrincipal principal) {
        BaseResponse<TaskResponse> response = taskService.enableTask(taskId, principal);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/disable/{taskId}")
    public ResponseEntity<BaseResponse<TaskResponse>> disableTask(@PathVariable long taskId, @CurrentUser UserPrincipal principal) {
        BaseResponse<TaskResponse> response = taskService.disableTask(taskId, principal);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete/{taskId}")
    public ResponseEntity<BaseResponse<TaskResponse>> deleteTask(@PathVariable long taskId, @CurrentUser UserPrincipal principal) {
        BaseResponse<TaskResponse> response = taskService.deleteTask(taskId, principal);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<BaseResponse<List<TaskResponse>>> findTasks(@CurrentUser UserPrincipal principal) {
        BaseResponse<List<TaskResponse>> response = taskService.findTasks(principal);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/paged")
    public ResponseEntity<PageResponse<TaskResponse>> findTasks(@CurrentUser UserPrincipal principal,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "100") int size
    ) {
        PageResponse<TaskResponse> response = taskService.findTasks(principal, page, size);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
