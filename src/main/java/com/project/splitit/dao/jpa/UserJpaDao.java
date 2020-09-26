package com.project.splitit.dao.jpa;

import com.project.splitit.entity.user.User;
import com.project.splitit.view.user.UserResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserJpaDao extends JpaRepository<User, Long> {

    User findByUsername(String username);

    Boolean existsByUsername(String username);

    @Query(value = "select id from User where  email = ?1 and active = ?2")
    Long findIdByEmailAndActive(String email, Boolean active);

    UserResponse findResponseById(long unmaskId);

    List<UserResponse> findByUserRolesRoleId(Long unmaskRoleId);
}
