package com.oasis.TaskManagementApplication.service;


import com.oasis.TaskManagementApplication.config.security.UserPrincipal;
import com.oasis.TaskManagementApplication.dto.mapper.UserMapper;
import com.oasis.TaskManagementApplication.dto.req.UserRequest;
import com.oasis.TaskManagementApplication.dto.req.UserUpdateRequest;
import com.oasis.TaskManagementApplication.dto.res.APIResponse;
import com.oasis.TaskManagementApplication.dto.res.BaseResponse;
import com.oasis.TaskManagementApplication.dto.res.PageResponse;
import com.oasis.TaskManagementApplication.dto.res.UserResponse;
import com.oasis.TaskManagementApplication.entity.User;
import com.oasis.TaskManagementApplication.exception.BadRequestException;
import com.oasis.TaskManagementApplication.exception.UnauthorizedException;
import com.oasis.TaskManagementApplication.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.oasis.TaskManagementApplication.util.Constant.ADMIN_ROLE;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;

    public BaseResponse<UserResponse> updateUser(UserUpdateRequest userRequest, UserPrincipal userPrincipal) {
        User user = userRepo.findById(userPrincipal.getId())
                .orElseThrow(() ->
                        new BadRequestException("User not found"));

        if (userRequest.getEmail() != null) {
            user.setEmail(userRequest.getEmail());
            user.setUsername(userRequest.getEmail());
        }

        if (userRequest.getFirstName() != null) {
            user.setFirstName(userRequest.getFirstName());
        }

        if (userRequest.getLastName() != null) {
            user.setLastName(userRequest.getLastName());
        }

        User save = userRepo.save(user);

        UserResponse apply = userMapper.apply(save);
        return new BaseResponse<>(Boolean.TRUE, "User updated", apply);
    }

    public BaseResponse<UserResponse> deleteUser(UserPrincipal userPrincipal) {
        User user = userRepo.findById(userPrincipal.getId())
                .orElseThrow(() ->
                        new BadRequestException("User not found"));

        userRepo.deleteById(userPrincipal.getId());
        UserResponse apply = userMapper.apply(user);

        return new BaseResponse<>(Boolean.TRUE, "User deleted", apply);
    }


    public BaseResponse<List<UserResponse>> getUsers() {
        List<UserResponse> responses = userRepo.findAll().stream().map(userMapper).collect(Collectors.toList());
        return new BaseResponse<>(Boolean.TRUE, "Users found", responses);
    }

    public PageResponse<UserResponse> getUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<User> userPage = userRepo.findAll(pageable);

        List<UserResponse> responses = userPage.stream().map(userMapper).collect(Collectors.toList());
        return new PageResponse<>(true, "users page", responses,
                userPage.getNumber(),
                userPage.getSize(),
                userPage.getTotalElements(),
                userPage.getTotalPages(),
                userPage.isFirst(),
                userPage.isLast());
    }

    public BaseResponse<UserResponse> getUserById(UserPrincipal userPrincipal) {
        User user = userRepo.findById(userPrincipal.getId())
                .orElseThrow(() ->
                        new BadRequestException("User not found"));
        UserResponse apply = userMapper.apply(user);
        return new BaseResponse<>(Boolean.TRUE, "User data", apply);
    }
}