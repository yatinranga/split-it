package com.project.splitit.view;

import com.project.splitit.entity.user.Role;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.stream.Collectors;

public class RoleRequest implements Request {

    @NotNull(message = "Role name can't be null")
    private String name;

    @NotEmpty(message = "Authority ids can't be null or empty")
    private Set<Long> authorityIds;

    public Role toEntity(){
        Role role = new Role();
        role.setName(name);
        return role;
    }

    public String getName() {
        return name;
    }

    public Set<Long> getAuthorityIds() {
        return authorityIds.stream().map(id -> unmask(id)).collect(Collectors.toSet());
    }
}
