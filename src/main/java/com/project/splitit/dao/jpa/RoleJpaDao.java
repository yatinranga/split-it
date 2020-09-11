package com.project.splitit.dao.jpa;

import com.project.splitit.entity.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface RoleJpaDao extends JpaRepository<Role,Long> {

    @Query(value = "select id from role where name=?1", nativeQuery = true)
    public  Set<Long> findIdsByName(String name);

    public Role findByName(String name);
}
