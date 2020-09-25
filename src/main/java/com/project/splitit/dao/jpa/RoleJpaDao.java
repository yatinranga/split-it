package com.project.splitit.dao.jpa;

import com.project.splitit.entity.user.Role;
import com.project.splitit.view.RoleResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface RoleJpaDao extends JpaRepository<Role,Long> {

    @Query(value = "select id from role where name=?1", nativeQuery = true)
    public  Set<Long> findIdsByName(String name);

    public Role findByName(String name);

    public List<Long> getAllIdsByActive(boolean b);

    public Boolean existsByName(String name);

    public RoleResponse findResponseById(Long id);
}
