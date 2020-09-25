package com.project.splitit.service;

import com.project.splitit.view.user.UserRequest;
import com.project.splitit.view.user.UserResponse;

public interface UserService {
    UserResponse save(UserRequest request);

    UserResponse findById(Long id);
}
