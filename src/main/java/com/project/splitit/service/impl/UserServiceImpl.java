package com.project.splitit.service.impl;

import com.project.splitit.dao.jpa.AuthorityJpaDao;
import com.project.splitit.dao.jpa.RoleAuthorityJpaDao;
import com.project.splitit.dao.jpa.RoleJpaDao;
import com.project.splitit.dao.jpa.UserJpaDao;
import com.project.splitit.entity.common.RoleAuthorityKey;
import com.project.splitit.entity.common.UserRoleKey;
import com.project.splitit.entity.user.*;
import com.project.splitit.ex.ValidationException;
import com.project.splitit.service.UserService;
import com.project.splitit.util.AuthorityUtils;
import com.project.splitit.view.user.UserRequest;
import com.project.splitit.view.user.UserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    /**
     * this method used to validate role ids that these role ids exist in database
     *
     * @param requestRoleIds
     */
    private void validateRoleIds(Set<Long> requestRoleIds) {
        List<Long> roleIds = roleDao.getAllIdsByActive(true);
        if (!roleIds.containsAll(requestRoleIds)) {
            requestRoleIds.removeAll(roleIds);
            throw new ValidationException(String.format("Some of the roles (%s) are not valid or not active",
                    StringUtils.arrayToCommaDelimitedString(requestRoleIds.toArray())));

        }
    }

    /**
     * this method used to validate user request like username already exist or not
     *
     * @param request
     */
    private void validate(UserRequest request) {
        if(request== null){
            throw new ValidationException("request can't be null");
        }
        if(userJpaDao.existsByUsername(request.getUsername())){
            throw new ValidationException(String.format("This user %s already exist",request.getUsername()));
        }else if(userJpaDao.findIdByEmailAndActive(request.getEmail(),true) != null){
            throw new ValidationException(String.format("This user's email %s already exist",request.getEmail()));
        }

        validateRoleIds(request.getRoleIds());

    }

    @Override
    @Secured(AuthorityUtils.USER_CREATE)
    public UserResponse save(UserRequest request) {
//        validate(request);
//        User user = request.toEntity();
//        user.setPassword(userPasswordEncoder.encode("12345"));
//        user = userJpaDao.save(user);
        return null;
    }


}
