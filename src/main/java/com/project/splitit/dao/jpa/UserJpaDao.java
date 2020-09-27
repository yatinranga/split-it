package com.project.splitit.dao.jpa;

import com.project.splitit.entity.user.User;
import com.project.splitit.view.user.UserResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserJpaDao extends JpaRepository<User, Long> {

    User findByUsername(String username);

    Boolean existsByUsername(String username);

    @Query(value = "select id from User where  email = ?1 and active = ?2")
    Long findIdByEmailAndActive(String email, Boolean active);

    UserResponse findResponseById(long unmaskId);

    List<UserResponse> findByUserRolesRoleId(Long unmaskRoleId);

    @Query(value = "select email, contact_no as contactNo from User where username =?1", nativeQuery = true)
    Map<String, String> findEmailAndContactByUsername(String username);

    @Modifying
    @Query(value = "update user set generated_password=:password where username =:username", nativeQuery = true)
    int setGeneratedPassword(@Param("username") String username, @Param("password") String password);
}
