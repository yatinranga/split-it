package com.project.splitit.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.splitit.entity.user.Role;

import java.util.List;

@JsonInclude(value = JsonInclude.Include.NON_ABSENT)
public class RoleResponse implements Response{

    public String name;
    public Long id;
    public Boolean active;
    public List<AuthorityResponse> authorities;

    public RoleResponse(Long id, String name, Boolean active) {
        this.id = id;
        this.name = name;
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return mask(id);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<AuthorityResponse> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<AuthorityResponse> authorities) {
        this.authorities = authorities;
    }

    public static RoleResponse get(Role role) {
        return new RoleResponse(role.getId(), role.getName(), role.getActive());
    }


}
