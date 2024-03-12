package com.oasis.TaskManagementApplication.repo;

import com.oasis.TaskManagementApplication.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepo extends JpaRepository<Task, Long> {
    List<Task> findAllByUserIdOrderByIdDesc(long userId);
    Page<Task> findAllByUserId(long userId, Pageable pageable);
    Optional<Task> findByIdAndUserId(long taskId, long id);
}
