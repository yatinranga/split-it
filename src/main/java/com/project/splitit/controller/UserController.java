package com.project.splitit.controller;

import com.project.splitit.service.UserService;
import com.project.splitit.view.user.UserRequest;
import com.project.splitit.view.user.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("user")
    public UserResponse save(@Valid @RequestBody UserRequest request) {
        return userService.save(request);
    }

    @GetMapping("user/{id}")
    public UserResponse findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping("users")
    public List<UserResponse> findAll() {
        return userService.findAll();
    }


    @GetMapping("users/{roleId}")
    public List<UserResponse> findAllUsersByRoleId(@PathVariable Long roleId) {
        return userService.findAllUsersByRoleId(roleId);
    }
}
