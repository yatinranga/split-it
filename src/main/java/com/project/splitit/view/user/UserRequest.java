package com.project.splitit.view.user;

import com.project.splitit.entity.user.User;
import com.project.splitit.view.Request;

import javax.validation.constraints.*;
import java.util.Set;
import java.util.stream.Collectors;

public class UserRequest implements Request {

    @NotNull(message = "User's name can't be null")
    private String name;

    @NotNull(message = "User's name can't be null")
    @Pattern(regexp = "^[@A-Za-z0-9_]{3,20}$", message = "username should contains only alphabets/digit/@ and length should be in between 4 to 20")
    private String username;

    @Email(message = "Email pattern isn't correct")
    private String email;

    @Size(min = 10, max = 10)
    @Pattern(regexp = "^[0-9]*$", message = "Contact no should contain only digit")
    private String contactNo;

    @NotEmpty(message = "Role ids can't be null or empty")
    private Set<Long> roleIds;

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getContactNo() {
        return contactNo;
    }

    public Set<Long> getRoleIds() {
        if (roleIds != null) {
            return roleIds.stream().map(id -> unmask(id)).collect(Collectors.toSet());
        }
        return roleIds;
    }

    public User toEntity() {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setContactNo(contactNo);
        user.setUsername(username);
        return user;
    }
}
