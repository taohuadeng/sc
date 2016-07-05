package com.tbc.elf.app.uc.service;

import com.tbc.elf.app.uc.model.Organization;
import com.tbc.elf.app.uc.model.OrganizationRel;
import com.tbc.elf.base.security.util.AuthenticationUtil;
import com.tbc.elf.base.service.BaseServiceImpl;
import com.tbc.elf.base.util.HqlBuilder;
import com.tbc.elf.base.util.SqlBuilder;
import com.tbc.elf.base.util.SqlUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * 部门-子部门关系业务操作实现
 *
 * @author YangLiBo@HF
 * @since 2016年02月26日14:08:32
 */
@Service("organizationRelService")
public class OrganizationRelServiceImpl extends BaseServiceImpl<OrganizationRel> implements OrganizationRelService {

    @Override
    public List<String> getChildOrgIdWithSelfByOrganizationId(String organizationId, boolean hasSelf) {
        Assert.hasText(organizationId, "OrganizationId must be not blank");
        HqlBuilder sqlBuilder = new HqlBuilder("select childId from OrganizationRel");
        sqlBuilder.append("where corpCode = :corpCode");
        sqlBuilder.append("and organizationId = :organizationId");
        sqlBuilder.addParameter("corpCode", AuthenticationUtil.getCorpCode());
        sqlBuilder.addParameter("organizationId", organizationId);
        if (!hasSelf) {
            sqlBuilder.append("and organizationId != childId");
        }
        return baseService.queryList(sqlBuilder);
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
    public void addOrganizationRel(Organization organization, String corpCode) {
        Assert.notNull(organization, "Organization must be not null");
        String organizationId = organization.getOrganizationId();
        Assert.hasText(organizationId, "OrganizationId must be not blank");
        OrganizationRel organizationRel = new OrganizationRel();
        organizationRel.setOrganizationId(organizationId);
        organizationRel.setChildId(organizationId);
        organizationRel.setCorpCode(corpCode);
        organizationRel.setOrganizationLevel(0);
        organizationRel.setOrganizationName(organization.getOrganizationName());
        List<OrganizationRel> parents = getParentOrganizationRelWithSelfByOrganizationId(organization.getParentId());
        List<OrganizationRel> needAddOrganizationRels = new ArrayList<OrganizationRel>();
        needAddOrganizationRels.add(organizationRel);
        if (CollectionUtils.isNotEmpty(parents)) {
            for (OrganizationRel orgRel : parents) {
                OrganizationRel needUpdate = getOrgRelByOrganizationRel(organizationId, corpCode, orgRel);
                needAddOrganizationRels.add(needUpdate);
            }
        }

        baseService.saveOrUpdateEntityList(needAddOrganizationRels);
    }

    public List<OrganizationRel> getParentOrganizationRelWithSelfByOrganizationId(String organizationId) {
        Assert.hasText(organizationId, "OrganizationId must be not blank");
        HqlBuilder sqlBuilder = new HqlBuilder("from OrganizationRel");
        sqlBuilder.append("where corpCode = :corpCode");
        sqlBuilder.append("and childId = :organizationId");
        sqlBuilder.addParameter("corpCode", AuthenticationUtil.getCorpCode());
        sqlBuilder.addParameter("organizationId", organizationId);
        return baseService.queryList(sqlBuilder);
    }

    @Override
    public void deleteOrganizationRelByChildId(String childId, String corpCode) {
        HqlBuilder hqlBuilder = new HqlBuilder("delete from OrganizationRel where corpCode=:corpCode");
        hqlBuilder.append("and childId=:childId");
        hqlBuilder.addParameter("corpCode", corpCode);
        hqlBuilder.addParameter("childId", childId);
        baseService.executeUpdate(hqlBuilder);
    }

    @Override
    public boolean hasChild(String organizationId) {
        HqlBuilder hqlBuilder = new HqlBuilder("select count(*) from where corpCode=:corpCode");
        hqlBuilder.append("and organizationId=:organizationId");
        hqlBuilder.addParameter("corpCode", AuthenticationUtil.getCorpCode());
        hqlBuilder.addParameter("organizationId", organizationId);
        Long result = baseService.queryUniqueResult(hqlBuilder);
        return result != null && result > 1;
    }

    public void updateOrganizationNameByChild(String organizationId, String organizationName) {
        Assert.hasText(organizationId, "ChildId must be not blank");
        HqlBuilder hqlBuilder = new HqlBuilder("update OrganizationRel set organizationName=:organizationName where corpCode=:corpCode");
        hqlBuilder.append("and organizationId=:organizationId");
        hqlBuilder.addParameter("corpCode", AuthenticationUtil.getCorpCode());
        hqlBuilder.addParameter("organizationId", organizationId);
        hqlBuilder.addParameter("organizationName", organizationName);
        baseService.executeUpdate(hqlBuilder);
    }

    private OrganizationRel getOrgRelByOrganizationRel(String organizationId
            , String corpCode, OrganizationRel organizationRel) {
        OrganizationRel newParent = new OrganizationRel();
        newParent.setChildId(organizationId);
        newParent.setCorpCode(corpCode);
        newParent.setOrganizationId(organizationRel.getOrganizationId());
        newParent.setOrganizationLevel(organizationRel.getOrganizationLevel() + 1);
        newParent.setOrganizationName(organizationRel.getOrganizationName());
        return newParent;
    }
}
