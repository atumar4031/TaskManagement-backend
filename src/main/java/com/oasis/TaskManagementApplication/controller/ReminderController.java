package com.oasis.TaskManagementApplication.controller;

import com.oasis.TaskManagementApplication.config.security.CurrentUser;
import com.oasis.TaskManagementApplication.config.security.UserPrincipal;
import com.oasis.TaskManagementApplication.dto.req.ReminderRequest;
import com.oasis.TaskManagementApplication.dto.req.UpdateReminderRequest;
import com.oasis.TaskManagementApplication.dto.res.BaseResponse;
import com.oasis.TaskManagementApplication.dto.res.PageResponse;
import com.oasis.TaskManagementApplication.dto.res.ReminderResponse;
import com.oasis.TaskManagementApplication.dto.res.TaskResponse;
import com.oasis.TaskManagementApplication.service.ReminderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reminder")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ReminderController {

    private final ReminderService reminderService;
    @PostMapping("/create")
    public ResponseEntity<BaseResponse<ReminderResponse>> createReminder(@RequestBody ReminderRequest reminderRequest,
                                                                         @CurrentUser UserPrincipal principal) {
        BaseResponse<ReminderResponse> response = reminderService.createReminder(reminderRequest, principal);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/update/{reminderId}")
    public ResponseEntity<BaseResponse<ReminderResponse>> updateReminder(@PathVariable long reminderId,
                                                                         @RequestBody UpdateReminderRequest reminderRequest,
                                                                         @CurrentUser UserPrincipal principal) {
        BaseResponse<ReminderResponse> response = reminderService.updateReminder(reminderId, reminderRequest, principal);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete/{reminderId}")
    public ResponseEntity<BaseResponse<ReminderResponse>> deleteReminder(@PathVariable long reminderId,
                                                                         @CurrentUser UserPrincipal principal) {
        BaseResponse<ReminderResponse> response = reminderService.deleteReminder(reminderId, principal);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<PageResponse<ReminderResponse>> findReminders(@RequestParam long taskId,
                                                                        @RequestParam(defaultValue = "0") int page,
                                                                        @RequestParam(defaultValue = "100") int size) {
        PageResponse<ReminderResponse> reminders = reminderService.findReminders(taskId, page, size);
        return ResponseEntity.status(HttpStatus.OK).body(reminders);
    }
}
