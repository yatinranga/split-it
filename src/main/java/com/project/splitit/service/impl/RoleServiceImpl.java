package com.project.splitit.service.impl;

import com.project.splitit.dao.jpa.AuthorityJpaDao;
import com.project.splitit.dao.jpa.RoleAuthorityJpaDao;
import com.project.splitit.dao.jpa.RoleJpaDao;
import com.project.splitit.entity.user.Role;
import com.project.splitit.entity.user.RoleAuthority;
import com.project.splitit.ex.NotFoundException;
import com.project.splitit.ex.ValidationException;
import com.project.splitit.service.BaseService;
import com.project.splitit.service.RoleService;
import com.project.splitit.view.RoleRequest;
import com.project.splitit.view.RoleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service("roleServiceImpl")
@Transactional
public class RoleServiceImpl extends BaseService implements RoleService {

    @Autowired
    AuthorityJpaDao authorityDao;

    @Autowired
    RoleJpaDao roleDao;

    @Autowired
    RoleAuthorityJpaDao roleAuthorityDao;

    private void validateAuthorityIds(Set<Long> requestAuthorityIds){
        List<Long> authorityIds = authorityDao.findAllIds();
        requestAuthorityIds.removeAll(authorityIds);
        if(!requestAuthorityIds.isEmpty()){
            throw new ValidationException(String.format("some of the authorities (%s) are not valid",requestAuthorityIds.stream().map(id -> mask(id)).collect(Collectors.toSet())));
        }
    }

    private void validateRequest(RoleRequest request){
        if(request==null){
            throw new ValidationException(String.format("request can't be null"));
        }
        validateAuthorityIds(request.getAuthorityIds());
        if(roleDao.existsByName(request.getName())){
            throw new ValidationException(String.format("Role %s already exist",request.getName()));
        }
    }


    //@Secured(AuthorityUtils.ROLE_CREATE)
    @Override
    public RoleResponse save(RoleRequest request) {
        validateRequest(request);
        Role role = request.toEntity();
        roleDao.save(role);
        List<RoleAuthority> roleAuthorities = new ArrayList<>();
        for(Long authorityId : request.getAuthorityIds()){
            if(authorityDao.findResponseById(authorityId) != null){
                roleAuthorities.add(new RoleAuthority(role.getId(),authorityId));
            }
        }
        roleAuthorityDao.saveAll(roleAuthorities);
        RoleResponse response = roleDao.findResponseById(role.getId());
        response.setAuthorities(authorityDao.findByAuthorityRolesRoleId(role.getId()));
        return response;
    }

    @Override
    public List<RoleResponse> getAllRoles() {
        List<RoleResponse> response = new ArrayList<>();
        roleDao.findAll().stream().forEach(role -> response.add(new RoleResponse(role.getId(),role.getName(),role.getActive())));
        response.stream().map(role -> {
            role.setAuthorities(authorityDao.findByAuthorityRolesRoleId(unmask(role.getId())));
            return role;
        }).collect(Collectors.toList());
        return response;
    }

    @Override
    public RoleResponse getRole(Long id) {

        if(id == null){
            throw new ValidationException(String.format("role id can't be null"));
        }
        RoleResponse response = roleDao.findResponseById(unmask(id));
        if(response == null){
            throw new NotFoundException(String.format("role having id %s didn't exist",id));
        }
        response.setAuthorities(authorityDao.findByAuthorityRolesRoleId(unmask(id)));
        return response;
    }
}
