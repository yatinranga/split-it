package com.project.splitit.dao.jpa;

import com.project.splitit.entity.user.Role;
import com.project.splitit.view.RoleResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface RoleJpaDao extends JpaRepository<Role, Long> {

    @Query(value = "select id from role where name=?1", nativeQuery = true)
    Set<Long> findIdsByName(String name);

    Role findByName(String name);

    @Query(value = "select id from Role where active=?1")
    List<Long> getAllIdsByActive(boolean active);

    Boolean existsByName(String name);

    RoleResponse findResponseById(Long id);

    Long findIdByName(String customer);
}
