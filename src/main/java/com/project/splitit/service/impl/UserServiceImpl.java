package com.project.splitit.service.impl;

import com.project.splitit.dao.jpa.UserJpaDao;
import com.project.splitit.entity.user.*;
import com.project.splitit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserJpaDao userJpaDao;

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
