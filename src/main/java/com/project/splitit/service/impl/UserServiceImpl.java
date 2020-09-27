package com.project.splitit.service.impl;

import com.project.splitit.dao.jpa.*;
import com.project.splitit.entity.common.RoleAuthorityKey;
import com.project.splitit.entity.common.UserRoleKey;
import com.project.splitit.entity.user.*;
import com.project.splitit.ex.NotFoundException;
import com.project.splitit.ex.ValidationException;
import com.project.splitit.service.BaseService;
import com.project.splitit.service.UserService;
import com.project.splitit.util.AuthorityUtils;
import com.project.splitit.util.MailUtil;
import com.project.splitit.view.AuthorityResponse;
import com.project.splitit.view.RoleResponse;
import com.project.splitit.view.SuccessResponse;
import com.project.splitit.view.user.UserRequest;
import com.project.splitit.view.user.UserResponse;
import com.sendgrid.helpers.mail.objects.Content;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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
import java.io.IOException;
import java.util.*;

@Service("userService")
@Transactional
public class UserServiceImpl extends BaseService implements UserService, UserDetailsService {

    @Value("${sendgrid.api-key}")
    private String sendGripApiKey;

    @Value("${sendgrid.sender.email}")
    private String fromEmailId;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private static final PasswordEncoder userPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private RoleJpaDao roleDao;

    @Autowired
    private RoleAuthorityJpaDao roleAuthorityDao;

    @Autowired
    private AuthorityJpaDao authorityDao;

    @Autowired
    private UserJpaDao userJpaDao;

    @Autowired
    private UserRoleJpaDao userRoleJpa;

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
     * this method used to validate user request like username already exist
     *
     * @param request
     */
    private void validate(UserRequest request) {
        if (request == null) {
            throw new ValidationException("request can't be null");
        }
        if (userJpaDao.existsByUsername(request.getUsername())) {
            throw new ValidationException(String.format("Username %s already exist", request.getUsername()));
        } else if (userJpaDao.findIdByEmailAndActive(request.getEmail(), true) != null) {
            throw new ValidationException(String.format("user's email %s already exist", request.getEmail()));
        }

        validateRoleIds(request.getRoleIds());

    }

    private UserResponse fetch(User user, Set<Long> roleIds) {
        UserResponse response = UserResponse.get(user);
        response.setRoles(new HashSet<>());
        RoleResponse roleResponse;
        for (Long roleId : roleIds) {
            roleResponse = roleDao.findResponseById(roleId);
            roleResponse.setAuthorities(authorityDao.findByAuthorityRolesRoleId(roleId));
            response.getRoles().add(roleResponse);
        }
        return response;
    }

    @Override
    @Secured(AuthorityUtils.USER_CREATE)
    public UserResponse save(UserRequest request) {
        validate(request);
        User user = request.toEntity();
        user.setPassword(userPasswordEncoder.encode("12345"));
        user = userJpaDao.save(user);

        for (Long roleId : request.getRoleIds()) {
            userRoleJpa.save(user.getId(), roleId);
        }
        return fetch(user, request.getRoleIds());
    }

    @Override
    public UserResponse findById(Long id) {
        if (id == null) {
            throw new ValidationException(String.format("user id can't be null"));
        }
        long unmaskId = unmask(id);
        UserResponse response = userJpaDao.findResponseById(unmaskId);
        response.setRoles(roleDao.findByRoleUsersUserId(unmaskId));
        for (RoleResponse role : response.getRoles()) {
            role.setAuthorities(authorityDao.findByAuthorityRolesRoleId(unmask(role.getId())));
        }
        return response;
    }

    @Override
    public List<UserResponse> findAll() {
        List<UserResponse> userResponseList = new ArrayList<>();
        userJpaDao.findAll().stream().forEach(user -> userResponseList.add(new UserResponse(user.getId(), user.getName(), user.getActive(), user.getUsername(), user.getEmail(), user.getContactNo())));
        //fetching all roles
        List<Long> roleIds = roleDao.getAllIdsByActive(true);
        //creating map of role and its authorities
        Map<Long, List<AuthorityResponse>> roleAuthoritiesMap = new HashMap<>();
        for (Long roleId : roleIds) {
            roleAuthoritiesMap.put(roleId, authorityDao.findByAuthorityRolesRoleId(roleId));
        }
        //looping over userResponse list, setting roles, looping over user roles and setting the authorities
        for (UserResponse user : userResponseList) {
            user.setRoles(roleDao.findByRoleUsersUserId(unmask(user.getId())));
            for (RoleResponse role : user.getRoles()) {
                role.setAuthorities(roleAuthoritiesMap.get(unmask(role.getId())));
            }
        }
        return userResponseList;
    }

    @Override
    public List<UserResponse> findAllUsersByRoleId(Long roleId) {
        if (roleId == null) {
            throw new ValidationException("role id can't be null");
        }
        Long unmaskRoleId = unmask(roleId);
        if (!roleDao.existsById(unmaskRoleId)) {
            throw new NotFoundException(String.format("role having id %s didn't exist", roleId));
        }

        List<UserResponse> userResponseList = userJpaDao.findByUserRolesRoleId(unmaskRoleId);
        return userResponseList;
    }

    @Override
    public SuccessResponse forgotPassword(String username) {
        if (!username.matches("^[@A-Za-z0-9_]{3,20}$")) {
            throw new ValidationException(String.format("Incorrect username [%s]", username));
        }
        Map<String, String> response = userJpaDao.findEmailAndContactByUsername(username);
        if (response == null) {
            throw new NotFoundException(String.format("user[username-%s] not found", username));
        }
        if (response.get("email") == null && response.get("contactNo") == null) {
            throw new ValidationException("User email/contact not register with us");
        }

        String generatedPassword = UUID.randomUUID().toString().substring(0, 6);
        try {
            MailUtil.sendEmail(sendGripApiKey, fromEmailId, response.get("email").toString(), new ArrayList<>(),
                    "Forgot password",
                    new Content("text/plain", "Temporary generated password is :" + generatedPassword));
        } catch (IOException e) {
            throw new ValidationException("Couldn't send email to customer");
        }

        logger.info("Password {} has been sent to email {}/contact {}", generatedPassword, response.get("email"),
                response.get("contactNo"));

        userJpaDao.setGeneratedPassword(username, userPasswordEncoder.encode(generatedPassword));
        return new SuccessResponse(HttpStatus.OK.value(), "New generated password has been sent to your email and contact number");
    }


}
