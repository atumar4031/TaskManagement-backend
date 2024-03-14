package com.oasis.TaskManagementApplication.controller;


import com.oasis.TaskManagementApplication.config.security.CurrentUser;
import com.oasis.TaskManagementApplication.config.security.UserPrincipal;
import com.oasis.TaskManagementApplication.dto.req.UserUpdateRequest;
import com.oasis.TaskManagementApplication.dto.res.BaseResponse;
import com.oasis.TaskManagementApplication.dto.res.UserResponse;
import com.oasis.TaskManagementApplication.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    @GetMapping("/details")
    public ResponseEntity<BaseResponse<UserResponse>> getUserById(@CurrentUser UserPrincipal userPrincipal) {
        BaseResponse<UserResponse> response = userService.getUserById(userPrincipal);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<BaseResponse<UserResponse>> updateUser(@Valid @RequestBody UserUpdateRequest userUpdateRequest,
                                                                @CurrentUser UserPrincipal principal) {
        BaseResponse<UserResponse> response = userService.updateUser(userUpdateRequest, principal);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<BaseResponse<UserResponse>> deleteUser(@CurrentUser UserPrincipal principal) {
        BaseResponse<UserResponse> response = userService.deleteUser(principal);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

}
