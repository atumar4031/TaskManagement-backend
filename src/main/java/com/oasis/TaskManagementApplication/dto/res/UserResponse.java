package com.oasis.TaskManagementApplication.dto.res;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {
    private Long id;

    private String firstName;

    private String lastName;

    private String username;

    private String email;

    private Boolean accountNonExpired;

    private Boolean accountNonLocked;

    private Boolean credentialsNonExpired;

    private Boolean enabled;
}
