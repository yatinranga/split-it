package com.project.splitit.dao.jpa;

import com.project.splitit.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaDao extends JpaRepository<User,Long> {

    public User findByUsername(String username);

    public Boolean existsByUsername(String username);

    @Query(value = "select id from User where  email = ?1 and active = ?2")
    public Long findIdByEmailAndActive(String email, Boolean active);
}
