package com.oasis.TaskManagementApplication.service;

import com.oasis.TaskManagementApplication.dto.res.BaseResponse;
import com.oasis.TaskManagementApplication.dto.res.UserResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class LogoutService {

    private final HttpServletRequest request;
    public BaseResponse<UserResponse> logout() {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;

        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return new BaseResponse<>(Boolean.FALSE, "Invalid header", null);
        }

        SecurityContextHolder.clearContext();
        log.info("USER LOGOUT SUCCESSFULLY");
        return new BaseResponse<>(Boolean.FALSE, "User logout successfully", null);

    }
}
