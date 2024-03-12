package com.oasis.TaskManagementApplication.service;


import com.oasis.TaskManagementApplication.config.jwt.JwtProvider;
import com.oasis.TaskManagementApplication.dto.mapper.UserMapper;
import com.oasis.TaskManagementApplication.dto.req.AuthRequest;
import com.oasis.TaskManagementApplication.dto.req.EmailRequest;
import com.oasis.TaskManagementApplication.dto.req.UserRequest;
import com.oasis.TaskManagementApplication.dto.res.APIResponse;
import com.oasis.TaskManagementApplication.dto.res.AuthResponse;
import com.oasis.TaskManagementApplication.dto.res.BaseResponse;
import com.oasis.TaskManagementApplication.dto.res.UserResponse;
import com.oasis.TaskManagementApplication.entity.Role;
import com.oasis.TaskManagementApplication.entity.User;
import com.oasis.TaskManagementApplication.exception.BadRequestException;
import com.oasis.TaskManagementApplication.exception.ResourceNotFoundException;
import com.oasis.TaskManagementApplication.repo.RoleRepo;
import com.oasis.TaskManagementApplication.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.oasis.TaskManagementApplication.util.Constant.USER_ROLE;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final UserRepo userRepo;
    private final JwtProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepo roleRepo;
    private final UserMapper userMapper;
    private final EmailService emailService;

    public BaseResponse<UserResponse> registerUser(UserRequest userRequest) {

        List<Role> roles = new ArrayList<>();
        boolean isUserExist = userRepo.existsByEmail(userRequest.getEmail());
        if (isUserExist){
            throw new BadRequestException(new APIResponse(Boolean.FALSE, "User already exist"));
        }

        roleRepo.findByName(USER_ROLE)
                    .orElseThrow(() -> new ResourceNotFoundException(new APIResponse(Boolean.FALSE, "Role doesn't exist")));

        if(!userRequest.getPassword().equals(userRequest.getConfirmPassword())){
            throw new BadRequestException(new APIResponse(Boolean.FALSE, "Passwords not match"));
        }

        User newUser = User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .username(userRequest.getEmail())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .email(userRequest.getEmail())
                .accountNonLocked(Boolean.TRUE)
                .accountNonExpired(Boolean.TRUE)
                .enabled(Boolean.TRUE)
                .credentialsNonExpired(Boolean.TRUE)
                .roles(roles)
                .build();

        User save = userRepo.save(newUser);
        UserResponse apply = userMapper.apply(save);

        //Send email
        emailService.sendEmail(EmailRequest.builder()
                .messageBody("Registration Successful with mail id: "+save.getEmail())
                .recipient(save.getEmail())
                .subject("REGISTRATION SUCCESS")
                .build());

        return new BaseResponse<>(Boolean.TRUE, "User registered", apply);
    }

    public BaseResponse<AuthResponse> authenticate(AuthRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword())
        );

        var user = userRepo.findByUsername(request.getUsername())
                .orElseThrow(() -> new BadRequestException(new APIResponse(Boolean.FALSE, "User not found")));

        var jwtToken = jwtTokenProvider.generateToken(user);
        var refreshToken = jwtTokenProvider.generateRefreshToken(user);
        AuthResponse authResponse = AuthResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();

        return new BaseResponse<>(Boolean.TRUE, "User logged in", authResponse);
    }
}
