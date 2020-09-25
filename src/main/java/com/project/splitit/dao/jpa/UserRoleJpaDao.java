package com.project.splitit.dao.jpa;

import com.project.splitit.entity.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleJpaDao extends JpaRepository<UserRole, Long> {

    @Modifying
    @Query(value = "insert into user_role(user_id,role_id) values (?1,?2)", nativeQuery = true)
    int save(Long userId, Long roleId);

}
