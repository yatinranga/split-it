package com.project.splitit.dao.jpa;

import com.project.splitit.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaDao extends JpaRepository<User,Long> {

    public User findByUsername(String username);

}
