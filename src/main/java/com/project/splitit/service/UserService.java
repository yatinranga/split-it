package com.project.splitit.service;

import com.project.splitit.view.SuccessResponse;
import com.project.splitit.view.user.UserRequest;
import com.project.splitit.view.user.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse save(UserRequest request);

    UserResponse findById(Long id);

    List<UserResponse> findAll();

    List<UserResponse> findAllUsersByRoleId(Long roleId);

    SuccessResponse forgotPassword(String username);
}
