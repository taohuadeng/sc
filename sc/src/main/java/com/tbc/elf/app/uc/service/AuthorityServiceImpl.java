package com.tbc.elf.app.uc.service;

import com.googlecode.ehcache.annotations.Cacheable;
import com.tbc.elf.app.uc.model.Authority;
import com.tbc.elf.base.security.util.AuthenticationUtil;
import com.tbc.elf.base.service.BaseServiceImpl;
import com.tbc.elf.base.util.SqlBuilder;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("authorityService")
public class AuthorityServiceImpl extends BaseServiceImpl<Authority> implements AuthorityService {
    @Resource
    private RoleService roleService;

    @Override
    @Cacheable(cacheName = "commonCache")
    @Transactional(readOnly = true)
    public List<String> listAuthorityUrls(String userId) {
        Assert.hasText(userId, "UserId is empty!");

        List<String> roleIds = roleService.listRoleIds(userId);
        if (CollectionUtils.isEmpty(roleIds)) {
            return new ArrayList<String>(0);
        }

        SqlBuilder builder = new SqlBuilder("SELECT auth.source_url FROM t_uc_role_authorities as ra");
        builder.append("LEFT JOIN t_uc_authority as auth ON ra.authority_id=auth.authority_id");
        builder.append("WHERE ra.role_id IN (:roleIds)");
        builder.addParameter("roleIds", roleIds);

        return baseService.queryBySQL(builder, String.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> listAuthorityUrls() {
        String corpCode = AuthenticationUtil.getCorpCode();
        if (StringUtils.isEmpty(corpCode)) {
            return new ArrayList<String>(0);
        }

        SqlBuilder builder = new SqlBuilder("SELECT source_url FROM t_uc_authority");
        builder.append("WHERE corp_code =:corpCode");
        builder.addParameter("corpCode", corpCode);

        return baseService.queryBySQL(builder, String.class);
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
    public void saveOrUpdate(List<Authority> authorities) {
        Assert.notNull(authorities, "Authorities is null!");

        baseService.batchSaveOrUpdate(authorities);
    }
}
