package com.project.splitit.controller;

import com.project.splitit.service.UserService;
import com.project.splitit.view.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasicController {

    @Autowired
    private UserService userService;


    @GetMapping("/forget-passsword/{username}")
    public SuccessResponse forgotPassword(@PathVariable String username) {
        return userService.forgotPassword(username);
    }

}
