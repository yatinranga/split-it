package com.project.splitit.controller;

import com.project.splitit.service.AuthorityService;
import com.project.splitit.view.AuthorityResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class AuthorityController {

    @Autowired
    AuthorityService authorityService;

    @GetMapping("authorities")
    public List<AuthorityResponse> getAllAuthorities(){
        return authorityService.getAllAuthorities();
    }

}
