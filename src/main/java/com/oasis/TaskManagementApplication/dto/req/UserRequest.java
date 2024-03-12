package com.oasis.TaskManagementApplication.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import static com.oasis.TaskManagementApplication.util.Constant.EMAIL_REGEX;


@Data
public class UserRequest {
    @NotBlank(message = "Firstname is required")
    private String firstName;

    @NotBlank(message = "Lastname is required")
    private String lastName;

    @Pattern(message = "Invalid Email", regexp = EMAIL_REGEX)
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "password is required")
    private String password;

    @NotBlank(message = "Username is required")
    private String confirmPassword;


}
