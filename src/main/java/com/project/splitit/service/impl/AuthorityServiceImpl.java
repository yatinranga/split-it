package com.project.splitit.service.impl;

import com.project.splitit.dao.jpa.AuthorityJpaDao;
import com.project.splitit.dao.jpa.RoleAuthorityJpaDao;
import com.project.splitit.dao.jpa.RoleJpaDao;
import com.project.splitit.entity.user.Authority;
import com.project.splitit.entity.user.RoleAuthority;
import com.project.splitit.service.AuthorityService;
import com.project.splitit.service.BaseService;
import com.project.splitit.util.AuthorityUtils;
import com.project.splitit.view.AuthorityResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service("authorityServiceImpl")
@Transactional
public class AuthorityServiceImpl extends BaseService implements AuthorityService {

    private static Logger logger = (Logger) LoggerFactory.getLogger(AuthorityServiceImpl.class);

    @Autowired
    private AuthorityJpaDao authorityDao;

    @Autowired
    private RoleJpaDao roleDao;

    @Autowired
    private RoleAuthorityJpaDao roleAuthorityDao;

    /**
     *
     * it will loads the authorities defined in AuthorityUtil at the time when
     * program runs If Authority not exist then Logger will give info
     *
     */
    @PostConstruct
    public void init() {
        Field[] fields = AuthorityUtils.class.getDeclaredFields();
        for (Field f : fields) {
            if (Modifier.isStatic(f.getModifiers()) && Modifier.isFinal(f.getModifiers())) {
                logger.info("Found authority {} ", f.getName());
                Authority authority;
                if (!authorityDao.existsByName(f.getName())) {
                    authority = new Authority();
                    authority.setName(f.getName());
                    authorityDao.save(authority);
                    logger.info("Not in db, saved {}", f.getName());
                    Set<Long> roleIds = roleDao.findIdsByName("SuperAdmin");
                    for (Long roleId : roleIds) {
                        roleAuthorityDao.save(new RoleAuthority(roleId, authority.getId()));
                    }

                }
            }
        }
    }

    /**
     * save the Authority
     *
     * @Param authortiy call save method of jpa
     * @return <tt>RoleResponse</tt>
     */
    @Override
    public void save(Authority authority) {
        authorityDao.save(authority);
    }

    @Override
    @Secured(AuthorityUtils.AUTHORITY_FETCH)
    public List<AuthorityResponse> getAllAuthorities() {
        return authorityDao.findAll().stream().map(AuthorityResponse::new).collect(Collectors.toList());
    }


}
