package com.project.splitit.service.impl;

import com.project.splitit.service.BaseService;
import com.project.splitit.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("roleServiceImpl")
@Transactional
public class RoleServiceImpl extends BaseService implements RoleService {
}
