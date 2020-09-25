package com.project.splitit.service;

import com.project.splitit.view.RoleRequest;
import com.project.splitit.view.RoleResponse;

import java.util.List;

public interface RoleService {

    public RoleResponse save(RoleRequest request);

    public List<RoleResponse> getAllRoles();
}
