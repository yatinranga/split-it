package com.project.splitit.dao.jpa;

import com.project.splitit.entity.oauth.OauthClientDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OauthClientDetailsJpaDao  extends JpaRepository<OauthClientDetails, String> {

    public OauthClientDetails findByClientId(String clientId);

}
