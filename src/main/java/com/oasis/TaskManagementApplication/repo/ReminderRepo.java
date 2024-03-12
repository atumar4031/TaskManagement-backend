package com.oasis.TaskManagementApplication.repo;

import com.oasis.TaskManagementApplication.entity.Reminder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReminderRepo extends JpaRepository<Reminder, Long> {
    Page<Reminder> findByTaskId(long taskId, Pageable pageable);
}
