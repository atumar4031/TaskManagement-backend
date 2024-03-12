package com.oasis.TaskManagementApplication.controller;


import com.oasis.TaskManagementApplication.config.security.CurrentUser;
import com.oasis.TaskManagementApplication.config.security.UserPrincipal;
import com.oasis.TaskManagementApplication.dto.req.UserRequest;
import com.oasis.TaskManagementApplication.dto.req.UserUpdateRequest;
import com.oasis.TaskManagementApplication.dto.res.BaseResponse;
import com.oasis.TaskManagementApplication.dto.res.PageResponse;
import com.oasis.TaskManagementApplication.dto.res.UserResponse;
import com.oasis.TaskManagementApplication.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    @GetMapping
    public ResponseEntity<BaseResponse<List<UserResponse>>> getUsers() {
        BaseResponse<List<UserResponse>> response = userService.getUsers();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/paged")
    public ResponseEntity<PageResponse<UserResponse>> getUsers(@RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "500") int size) {
        PageResponse<UserResponse> response = userService.getUsers(page, size);
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

//    @PutMapping("/enable/{userId}")
//    public ResponseEntity<UserResponse> enableUser(@PathVariable long userId,
//                                                   @CurrentUser UserPrincipal principal) {
//        System.out.println("user id "+ userId);
//        System.out.println("user name "+ principal.getFirstName());
//        UserResponse response = userService.enableUser(userId, principal);
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(response);
//    }
//
//    @PutMapping("/disable/{userId}")
//    public ResponseEntity<UserResponse> disableUser(@PathVariable long userId,
//                                                   @CurrentUser UserPrincipal principal) {
//        UserResponse response = userService.disableUser(userId, principal);
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(response);
//    }
//
//    @PutMapping("/lock/{userId}")
//    public ResponseEntity<UserResponse> lockUser(@PathVariable long userId,
//                                                   @CurrentUser UserPrincipal principal) {
//        UserResponse response = userService.lockUser(userId, principal);
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(response);
//    }
//
//    @PutMapping("/unlock/{userId}")
//    public ResponseEntity<UserResponse> unlockUser(@PathVariable long userId,
//                                                 @CurrentUser UserPrincipal principal) {
//        UserResponse response = userService.unlockUser(userId, principal);
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(response);
//    }
//
//    @PutMapping("/expire/{userId}")
//    public ResponseEntity<UserResponse> expireUser(@PathVariable long userId,
//                                                   @CurrentUser UserPrincipal principal) {
//        UserResponse response = userService.expireUser(userId, principal);
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(response);
//    }
//
//    @PutMapping("/inspire/{userId}")
//    public ResponseEntity<UserResponse> inspireUser(@PathVariable long userId,
//                                                    @CurrentUser UserPrincipal principal) {
//        UserResponse response = userService.inspireUser(userId, principal);
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(response);
//    }
//
//    @PutMapping("/expire-credentials/{userId}")
//    public ResponseEntity<UserResponse> expireUserCredentials(@PathVariable long userId,
//                                                   @CurrentUser UserPrincipal principal) {
//        UserResponse response = userService.expireUserCredentials(userId, principal);
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(response);
//    }
//
//    @PutMapping("/inspire-credentials/{userId}")
//    public ResponseEntity<UserResponse> inspireUserCredentials(@PathVariable long userId,
//                                                               @CurrentUser UserPrincipal principal) {
//        UserResponse response = userService.inspireUserCredentials(userId, principal);
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(response);
//    }

}
