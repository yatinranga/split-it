package com.project.splitit.service.impl;

import com.project.splitit.dao.jpa.OauthClientDetailsJpaDao;
import com.project.splitit.entity.oauth.OauthClientDetails;
import com.project.splitit.service.OauthClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service("oauthClientService")
public class OauthClientServiceImpl implements OauthClientService {

    @Autowired
    private OauthClientDetailsJpaDao oauthClientDetailsDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        if (oauthClientDetailsDao.findByClientId("split-it") == null) {
            OauthClientDetails oauthClientDetails = new OauthClientDetails();
            oauthClientDetails.setAccessTokenValidity(-1);
            oauthClientDetails.setScope("read,write");
            oauthClientDetails.setClientId("split-it");
            oauthClientDetails.setAuthorizedGrantTypes("password");
            oauthClientDetails.setAutoapprove("1");
            oauthClientDetails.setClientSecret(passwordEncoder.encode("splitit"));
            oauthClientDetails.setRefreshTokenValidity(-1);
            oauthClientDetails.setResourceIds("splitit-key-api");
            oauthClientDetailsDao.save(oauthClientDetails);
        }
    }

}