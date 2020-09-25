package com.project.splitit.controller;

import com.project.splitit.service.RoleService;
import com.project.splitit.view.RoleRequest;
import com.project.splitit.view.RoleResponse;
import net.bytebuddy.agent.builder.AgentBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class RoleController {

    @Autowired
    RoleService roleService;


    @PostMapping("role")
    public RoleResponse save(@Valid @RequestBody RoleRequest request){
        return roleService.save(request);
    }

    @GetMapping("roles")
    public List<RoleResponse> getAllRoles(){
        return roleService.getAll();
    }
}
