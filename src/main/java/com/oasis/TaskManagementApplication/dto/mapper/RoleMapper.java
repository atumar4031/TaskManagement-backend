package com.oasis.TaskManagementApplication.dto.mapper;


import com.oasis.TaskManagementApplication.dto.res.RoleResponse;
import com.oasis.TaskManagementApplication.entity.Role;
import org.springframework.stereotype.Service;

import java.util.function.Function;
@Service
public class RoleMapper implements Function<Role, RoleResponse> {
    /**
     * @param role the function argument
     * @return
     */
    @Override
    public RoleResponse apply(Role role) {
        return null;
    }
}
