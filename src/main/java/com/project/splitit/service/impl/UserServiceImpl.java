package com.project.splitit.service.impl;

import com.project.splitit.dao.jpa.AuthorityJpaDao;
import com.project.splitit.dao.jpa.RoleAuthorityJpaDao;
import com.project.splitit.dao.jpa.RoleJpaDao;
import com.project.splitit.dao.jpa.UserJpaDao;
import com.project.splitit.entity.common.RoleAuthorityKey;
import com.project.splitit.entity.common.UserRoleKey;
import com.project.splitit.entity.user.*;
import com.project.splitit.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private static PasswordEncoder userPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private RoleJpaDao roleDao;

    @Autowired
    private RoleAuthorityJpaDao roleAuthorityDao;

    @Autowired
    private AuthorityJpaDao authorityDao;

    @Autowired
    private UserJpaDao userJpaDao;

    @PostConstruct
    public void init() {

        Role role;
        if ((role = roleDao.findByName("SuperAdmin")) == null) {
            role = new Role("SuperAdmin");
        }
        if (userJpaDao.findByUsername("yatin") == null) {
            User user = new User("yatin", userPasswordEncoder.encode("12345"), null);
            user.setName("yatin");
            user.setUserRoles(Arrays.asList(new UserRole(new UserRoleKey(role.getId(), user.getId()), role, user)));
            userJpaDao.saveAll(Arrays.asList(user));
            List<Authority> authorities = authorityDao.findAll();
            for (Authority authority : authorities) {
                roleAuthorityDao.save(
                        new RoleAuthority(new RoleAuthorityKey(role.getId(), authority.getId()), role, authority));
            }
        }

    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userJpaDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("username(%s) not found", username));
        }
        user.setUserId(user.getId());
        Set<Authority> authorities = new HashSet<>();
        Set<Role> roles = new HashSet<>();
        for (UserRole userRole : user.getUserRoles()) {
            for (RoleAuthority roleAuthority : userRole.getRole().getRoleAuthorities()) {
                authorities.add(roleAuthority.getAuthority());
                roles.add(roleAuthority.getRole());
            }
        }
        user.setAuthorities(authorities);
        user.setRoles(roles);
        return user;
    }
}
