package com.oasis.TaskManagementApplication.dto.mapper;


import com.oasis.TaskManagementApplication.dto.res.UserResponse;
import com.oasis.TaskManagementApplication.entity.User;
import org.springframework.stereotype.Service;

import java.util.function.Function;


@Service
public class UserMapper implements Function<User, UserResponse> {
    /**
     * @param user the function argument
     * @return UserResponse
     */
    @Override
    public UserResponse apply(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .accountNonExpired(user.getAccountNonExpired())
                .accountNonLocked(user.getAccountNonLocked())
                .credentialsNonExpired(user.getCredentialsNonExpired())
                .enabled(user.getEnabled())
                .build();
    }
}
