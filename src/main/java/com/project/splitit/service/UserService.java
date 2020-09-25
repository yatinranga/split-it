package com.project.splitit.service;

import com.project.splitit.view.user.UserRequest;
import com.project.splitit.view.user.UserResponse;

public interface UserService {
    public UserResponse save(UserRequest request);
}
