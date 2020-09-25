package com.project.splitit.controller;

import com.project.splitit.service.UserService;
import com.project.splitit.view.user.UserRequest;
import com.project.splitit.view.user.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("user")
    public UserResponse save(@Valid @RequestBody UserRequest request){
        return userService.save(request);
    }

}
