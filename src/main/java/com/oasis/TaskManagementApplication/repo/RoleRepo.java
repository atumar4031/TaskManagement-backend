package com.oasis.TaskManagementApplication.repo;


import com.oasis.TaskManagementApplication.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String roleName);
}
