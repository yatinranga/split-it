package com.project.splitit.view.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.splitit.entity.user.Authority;
import com.project.splitit.entity.user.Role;
import com.project.splitit.entity.user.User;
import com.project.splitit.view.AuthorityResponse;
import com.project.splitit.view.Response;
import com.project.splitit.view.RoleResponse;

import java.util.HashSet;
import java.util.Set;

@JsonInclude(value = JsonInclude.Include.NON_ABSENT)
public class UserResponse implements Response {

    private Long id;
    private String name;
    private Boolean active;
    private String username;
    private String email;
    private String contactNo;
    private Set<RoleResponse> roles;
    private Set<AuthorityResponse> authorities;


    public UserResponse(Long id, String name, Boolean active, String username, String email,
                        String contactNo) {
        super();
        this.id = id;
        this.active = active;
        this.name = name;
        this.username = username;
        this.email = email;
        this.contactNo = contactNo;
    }

    public static UserResponse get(User user) {
        if (user != null) {
            UserResponse response = new UserResponse(user.getId() == null ? user.getUserId() : user.getId(), user.getName(), user.getActive(), user.getUsername(), user.getEmail(), user.getContactNo());
            if (user.getAuthorities() != null) {
                response.authorities = new HashSet<>();
                for (Authority authority : user.getAuthorities()) {
                    response.authorities.add(new AuthorityResponse(authority));
                }
            }

            if (user.getRoles() != null) {
                response.roles = new HashSet<>();
                for (Role role : user.getRoles()) {
                    response.roles.add(RoleResponse.get(role));
                }
            }
            return response;
        }
        return null;
    }

    public Long getId() {
        return mask(id);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public Set<RoleResponse> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleResponse> roles) {
        this.roles = roles;
    }

    public Set<AuthorityResponse> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<AuthorityResponse> authorities) {
        this.authorities = authorities;
    }
}
