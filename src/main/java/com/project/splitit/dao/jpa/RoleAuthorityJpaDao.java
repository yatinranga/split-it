package com.project.splitit.dao.jpa;

import com.project.splitit.entity.common.RoleAuthorityKey;
import com.project.splitit.entity.user.RoleAuthority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleAuthorityJpaDao extends JpaRepository<RoleAuthority, RoleAuthorityKey> {
}
