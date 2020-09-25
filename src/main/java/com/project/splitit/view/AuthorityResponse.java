package com.project.splitit.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.splitit.entity.user.Authority;

@JsonInclude(value = JsonInclude.Include.NON_ABSENT)
public class AuthorityResponse implements Response{

    public Long id;
    public String name;
    public String description;

    public AuthorityResponse() {
    }

    public AuthorityResponse(Authority authority) {
        if (authority != null) {
            this.id = authority.getId();
            this.name = authority.getName();
            this.description = authority.getDescription();
        }
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
