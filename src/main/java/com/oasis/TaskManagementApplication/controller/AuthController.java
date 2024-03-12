package com.oasis.TaskManagementApplication.controller;

import com.oasis.TaskManagementApplication.dto.req.AuthRequest;
import com.oasis.TaskManagementApplication.dto.req.UserRequest;
import com.oasis.TaskManagementApplication.dto.res.AuthResponse;
import com.oasis.TaskManagementApplication.dto.res.BaseResponse;
import com.oasis.TaskManagementApplication.dto.res.UserResponse;
import com.oasis.TaskManagementApplication.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authenticate;

    @PostMapping("/register")
    public ResponseEntity<BaseResponse<UserResponse>> createUser(@Valid @RequestBody UserRequest userRequest) {
        BaseResponse<UserResponse> response = authenticate.registerUser(userRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<AuthResponse>> login(@Valid @RequestBody AuthRequest authRequest) {
        BaseResponse<AuthResponse> response = authenticate.authenticate(authRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
