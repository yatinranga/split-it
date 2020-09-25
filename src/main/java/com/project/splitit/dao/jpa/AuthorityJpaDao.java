package com.project.splitit.dao.jpa;

import com.project.splitit.entity.user.Authority;
import com.project.splitit.view.AuthorityResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorityJpaDao extends JpaRepository<Authority,Long> {

    public Boolean existsByName(String name);

    @Query(value="select id from Authority")
    public List<Long> findAllIds();

    public AuthorityResponse findResponseById(Long authorityId);

    public List<AuthorityResponse> findByAuthorityRolesRoleId(Long roleId);
}
