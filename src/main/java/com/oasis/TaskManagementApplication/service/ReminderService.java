package com.oasis.TaskManagementApplication.service;

import com.oasis.TaskManagementApplication.config.security.UserPrincipal;
import com.oasis.TaskManagementApplication.dto.mapper.ReminderMapper;
import com.oasis.TaskManagementApplication.dto.req.ReminderRequest;
import com.oasis.TaskManagementApplication.dto.req.UpdateReminderRequest;
import com.oasis.TaskManagementApplication.dto.res.APIResponse;
import com.oasis.TaskManagementApplication.dto.res.BaseResponse;
import com.oasis.TaskManagementApplication.dto.res.PageResponse;
import com.oasis.TaskManagementApplication.dto.res.ReminderResponse;
import com.oasis.TaskManagementApplication.entity.Reminder;
import com.oasis.TaskManagementApplication.exception.ResourceNotFoundException;
import com.oasis.TaskManagementApplication.repo.ReminderRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReminderService {

    private final ReminderRepo reminderRepo;
    private final ReminderMapper reminderMapper;

    public BaseResponse<ReminderResponse> createReminder(ReminderRequest reminderRequest, UserPrincipal principal) {

        Reminder reminder = new Reminder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm:ss");
        LocalTime localTime = LocalTime.parse(reminderRequest.getReminderTime(), formatter);
        reminder.setTaskId(reminderRequest.getTaskId());
        reminder.setReminderTime(localTime);
        reminder.setIsSent(Boolean.FALSE);

        Reminder save = reminderRepo.save(reminder);
        ReminderResponse apply = reminderMapper.apply(save);

        return new BaseResponse<>(Boolean.TRUE, "Reminder added", apply);
    }

    public BaseResponse<ReminderResponse> updateReminder(long reminderId, UpdateReminderRequest reminderRequest, UserPrincipal principal) {

        Reminder reminder = reminderRepo.findById(reminderId)
                .orElseThrow(() -> new ResourceNotFoundException("Reminder not found"));
            if (reminderRequest.getReminderTime() != null){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm:ss");
                LocalTime localTime = LocalTime.parse(reminderRequest.getReminderTime(), formatter);
                reminder.setReminderTime(localTime);
            }

        Reminder save = reminderRepo.save(reminder);
        ReminderResponse apply = reminderMapper.apply(save);

        return new BaseResponse<>(Boolean.TRUE, "Reminder updated", apply);
    }

    public BaseResponse<ReminderResponse> deleteReminder(long reminderId, UserPrincipal principal) {

        Reminder reminder = reminderRepo.findById(reminderId)
                .orElseThrow(() -> new ResourceNotFoundException("Reminder not found"));

        reminderRepo.deleteById(reminderId);
        ReminderResponse apply = reminderMapper.apply(reminder);
        return new BaseResponse<>(Boolean.TRUE, "Reminder updated", apply);
    }

    public PageResponse<ReminderResponse> findReminders(long taskId, int page, int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Reminder> reminderPage = reminderRepo.findByTaskId(taskId, pageable);
        List<ReminderResponse> responses = reminderPage.getContent().stream().map(reminderMapper).collect(Collectors.toList());

        return new PageResponse<>(true, "Reminder page", responses,
                reminderPage.getNumber(),
                reminderPage.getSize(),
                reminderPage.getTotalElements(),
                reminderPage.getTotalPages(),
                reminderPage.isFirst(),
                reminderPage.isLast());
    }


}
