package com.project.splitit.service;

import com.project.splitit.entity.user.Authority;
import com.project.splitit.view.AuthorityResponse;

import java.util.List;

public interface AuthorityService {

    /**
     * this method is used to save authority
     * @param authority
     */
    public void save(Authority authority);

    /**
     * this method is used to fetch all the authorities
     * @return List<AuthorityResponse>
     */
    public List<AuthorityResponse> getAllAuthorities();
}
