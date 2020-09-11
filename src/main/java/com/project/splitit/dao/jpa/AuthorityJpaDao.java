package com.project.splitit.dao.jpa;

import com.project.splitit.entity.user.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityJpaDao extends JpaRepository<Authority,Long> {

    public Boolean existsByName(String name);


}
