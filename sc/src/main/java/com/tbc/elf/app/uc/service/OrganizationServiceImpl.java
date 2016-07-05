package com.tbc.elf.app.uc.service;

import com.tbc.elf.app.uc.model.Organization;
import com.tbc.elf.app.uc.model.OrganizationRel;
import com.tbc.elf.base.security.util.AuthenticationUtil;
import com.tbc.elf.base.service.BaseServiceImpl;
import com.tbc.elf.base.util.ElfConstant;
import com.tbc.elf.base.util.HqlBuilder;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.*;

/**
 * 人员所属部门的业务逻辑操作实现类
 *
 * @author ELF@TEAM
 * @since 2016年2月26日14:07:16
 */
@Service("organizationService")
public class OrganizationServiceImpl extends BaseServiceImpl<Organization> implements OrganizationService {

    @Resource
    private OrganizationRelService organizationRelService;

    @Override
    public String initOrganization(String corpCode, String lastResultStatus) {
        return null;
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
    public String addOrganization(Organization organization) {
        Assert.notNull(organization, "Organization must be not null");

        String pNamePath = "";
        if (StringUtils.isNotBlank(organization.getParentId())) {
            Organization parentOrg = get(organization.getParentId());
            if (parentOrg != null) {
                pNamePath = parentOrg.getNamePath();
            }

        } else {
            organization.setParentId(ElfConstant.ROOT_PARENT_ID);
        }

        if (StringUtils.isNotEmpty(pNamePath)) {
            organization.setNamePath(pNamePath + "." + organization.getOrganizationName());
        } else {
            organization.setNamePath(organization.getOrganizationName());
        }

        Double maxShowOrder = getMaxShowOrderByParentId(organization.getParentId());
        double showOrder = maxShowOrder == null ? 1 : maxShowOrder + 1;
        organization.setShowOrder(showOrder);
        String organizationId = save(organization);
        organizationRelService.addOrganizationRel(organization, AuthenticationUtil.getCorpCode());
        return organizationId;
    }

    @Override
    public void updateOrganization(Organization organization) {
        Assert.notNull(organization, "Organization must be not null");
        String organizationId = organization.getOrganizationId();
        Assert.hasText(organizationId, "OrganizationId must be not Blank");
        String organizationName = organization.getOrganizationName();
        Organization oldOrg = get(organizationId);
        if (StringUtils.isBlank(organizationName) || ElfConstant.ROOT_PARENT_ID.equals(oldOrg.getParentId())) {
            return;
        }

        if (!oldOrg.getOrganizationName().equals(organizationName) && StringUtils.isNotBlank(organizationName)) {
            Organization parentOrg = get(oldOrg.getParentId());

            String namePath = parentOrg.getNamePath() + ElfConstant.DOT + organizationName;
            List<String> childIds = organizationRelService.getChildOrgIdWithSelfByOrganizationId(organizationId, false);
            HqlBuilder hqlBuilder = new HqlBuilder("from Organization where corpCode=:corpCode");
            hqlBuilder.append("and organizationId in(:organizationIds)");
            hqlBuilder.addParameter("corpCode", AuthenticationUtil.getCorpCode());
            hqlBuilder.addParameter("organizationIds", childIds);
            List<Organization> childrenOrg = baseService.queryList(hqlBuilder);
            for (Organization organization1 : childrenOrg) {
                organization1.setNamePath(organization1.getNamePath().substring(oldOrg.getNamePath().length()));
                organization1.setNamePath(namePath + organization1.getNamePath());
                baseService.saveOrUpdate(organization1);
            }

            oldOrg.setNamePath(namePath);
            oldOrg.setOrganizationName(organizationName);
            baseService.saveOrUpdate(oldOrg);
        }

        if (!oldOrg.getParentId().equals(organization.getParentId())) {
            organizationRelService.deleteOrganizationRelByChildId(organizationId, AuthenticationUtil.getCorpCode());
            organizationRelService.addOrganizationRel(oldOrg, AuthenticationUtil.getCorpCode());
        } else {
            organizationRelService.updateOrganizationNameByChild(organizationId, organizationName);
        }

    }

    public Map<String, Organization> getOrgIdAndOrganizationMap(Set<String> orgIds) {
        Assert.notEmpty(orgIds, "OrgIds must be not empty");

        List<String> columns = new ArrayList<String>(2);
        columns.add("organizationId");
        columns.add("namePath");
        List<Object[]> resultList = getOrganizationsByColumnsAndOrganization(new ArrayList<String>(orgIds), columns, null);
        if (CollectionUtils.isEmpty(resultList)) {
            return new HashMap<String, Organization>(0);
        }

        Map<String, Organization> resultMap = new HashMap<String, Organization>(resultList.size());
        for (Object[] result : resultList) {
            Organization organization = new Organization();
            organization.setNamePath((String) result[1]);
            organization.setOrganizationId((String) result[0]);
            resultMap.put(organization.getOrganizationId(), organization);
        }

        return resultMap;
    }

    @Override
    public List<Organization> findOrganization(Set<String> organizationIds) {
        Assert.notEmpty(organizationIds, "OrganizationIds must not be empty!");
        List<String> columns = new ArrayList<String>(2);
        columns.add("organizationId");
        columns.add("organizationName");
        columns.add("namePath");
        List<Object[]> resultList =
                getOrganizationsByColumnsAndOrganization(new ArrayList<String>(organizationIds), columns, new Organization());
        if (CollectionUtils.isEmpty(resultList)) {
            return new ArrayList<Organization>(0);
        }

        List<Organization> organizations = new ArrayList<Organization>(resultList.size());
        for (Object[] result : resultList) {
            Organization organization = new Organization();
            organization.setNamePath((String) result[1]);
            organization.setOrganizationId((String) result[0]);
            organizations.add(organization);
        }
        return organizations;
    }

    //need
    public String findOrgNodeJson(String orgName, Integer limitOrgNum) {

        return null;
    }

    @Override
    public List<Organization> fillOrgNameByOrgId(List<Organization> orgList) {
        return null;
    }

    @Override
    public String findOrgTreeJson(String orgId, Boolean hasOrgCode) {
        return null;
    }

    @Override
    public String findRootOrgTreeJson(String orgId, Boolean hasOrgCode) {
        return null;
    }

    @Override
    public String getAvailableOrgTreeJson(String currentRootOrgId, String currentOrgId) {
        return null;
    }

    @Override
    public String findOrganizationTreeNew(String root, boolean hasOrgCode, boolean hasNamePath) {
        return null;
    }

    @Override
    public Organization findOrganizationByCode(String OrganizationCode) {
        return null;
    }

    @Override
    public Map<String, String> checkOrganization(Organization Organization) {
        return null;
    }

    @Override
    public List<String> checkOrganizationName(Organization Organization) {
        return null;
    }

    @Override
    public String findOrganizationNodeByUser(String userId) {
        return null;
    }

    @Override
    public String getAvailableParentOrgTree(String currentRootOrgId, String currentOrgId) {
        return null;
    }

    @Override
    public void moveOrganization(String organizationId, Boolean isUp) {
        Organization organization = get(organizationId);
        HqlBuilder hqlBuilder = new HqlBuilder("from Organization");
        hqlBuilder.append("where corpCode=:corpCode");
        hqlBuilder.addParameter("corpCode", AuthenticationUtil.getCorpCode());
        hqlBuilder.append("and parentId =:parentId");
        hqlBuilder.addParameter("parentId", organizationId);
        if (isUp) {
            hqlBuilder.append("and a.showOrder < ? ");
            hqlBuilder.addParameter(organization.getShowOrder());
            hqlBuilder.append(" order by a.showOrder desc");
        } else {
            hqlBuilder.append("and a.showOrder > ? ");
            hqlBuilder.addParameter(organization.getShowOrder());
            hqlBuilder.append(" order by a.showOrder");
        }
        hqlBuilder.setFirstRecordIndex(0);
        hqlBuilder.setMaxRecordNum(1);
        Organization needChangeOrg = baseService.queryUniqueResult(hqlBuilder);
        Double showOrder = needChangeOrg.getShowOrder();
        needChangeOrg.setShowOrder(organization.getShowOrder());
        organization.setShowOrder(showOrder);
        baseService.saveOrUpdate(organization);
        baseService.saveOrUpdate(needChangeOrg);
    }

    @Override
    public Integer getAvailableCountByOrg(Organization Organization, String removeOrgId) {
        return null;
    }

    @Override
    public Long getPersonLimitCountForSecondLevel() {
        return null;
    }

    @Override
    public Integer getPersonCountForSecondLevel() {
        return null;
    }

    @Override
    public Integer findAvailableCountForSecond() {
        return null;
    }

    @Override
    public boolean judgeIsSecondLevel(String OrganizationId) {
        return false;
    }

    @Override
    public Long getCurrentUserCountByOrganizationIdWithChild(String OrganizationId) {
        return null;
    }

    @Override
    public List<String> getChildIdsByOrganizationId(String OrganizationId) {
        return null;
    }

    @Override
    public List<String> getChildIdsByOrganizationdId(List<String> OrganizationIdList) {
        return null;
    }

    @Override
    public List<String> getParentIdsByOrganizationId(String OrganizationId) {
        return null;
    }

    @Override
    public String findCorpRootId() {
        String columnName = "organizationId";
        Organization organization = new Organization();
        organization.setParentId(ElfConstant.ROOT_PARENT_ID);
        return getUniqueColumnByOrganization(columnName, organization);
    }

    @Override
    public String addOrganizationForUpgrade(Organization Organization) {
        return null;
    }

    @Override
    public boolean checkOrganizationIsExists(String OrganizationId) {
        return false;
    }

    @Override
    public void updateOrgNameByCorp(String corpCode, String corpName) {

    }

    @Override
    public List<String> checkOrganizationCode(Organization Organization) {
        return null;
    }

    @Override
    public Map<String, Organization> getOrganizationByCorpCode(String corpCode) {
        return null;
    }

    @Override
    public Map<String, Organization> findOrganizationByIds(List<String> organizationIds) {
        Assert.notEmpty(organizationIds, "OrganizationIds must not be empty!");

        HqlBuilder hqlBuilder = new HqlBuilder("from Organization where corpCode=:corpCode");
        hqlBuilder.append("and organizationId in(:organizationIds)");
        hqlBuilder.addParameter("corpCode", AuthenticationUtil.getCorpCode());
        hqlBuilder.addParameter("organizationIds", organizationIds);
        List<Organization> resultList = baseService.queryList(hqlBuilder);
        if (CollectionUtils.isEmpty(resultList)) {
            return new HashMap<String, Organization>(0);
        }

        Map<String, Organization> idOrganizationMap = new HashMap<String, Organization>(resultList.size());
        for (Organization result : resultList) {
            idOrganizationMap.put(result.getOrganizationId(), result);
        }
        return idOrganizationMap;
    }

    @Override
    public List<Organization> findOrganizationByNamePath(String namePath) {
        return null;
    }

    @Override
    public Map<String, String> findOrganizationIdsByNamePath(List<String> namePath) {
        return null;
    }

    @Override
    public Map<String, String> batchSyncOrganization(List<Organization> Organizations) {
        return null;
    }

    @Override
    public String syncOrganization(Organization Organization) {
        return null;
    }

    @Override
    public void deleteOrgByNoChild(String corpCode) {

    }

    @Override
    public void deleteOrgByNoChildAndUser(String corpCode, String organizationId) {
        Assert.hasText(corpCode, "corpCode must be not blank");
        Assert.hasText(organizationId, "OrganizationId must be not blank");
        HqlBuilder hqlBuilder = new HqlBuilder("delete from Organization where corpCode=:corpCode");
        hqlBuilder.append("and organizationId=:organizationId");
        hqlBuilder.addParameter("corpCode", corpCode);
        hqlBuilder.addParameter("organizationId", organizationId);
        baseService.executeUpdate(hqlBuilder);
        organizationRelService.deleteOrganizationRelByChildId(organizationId, corpCode);
    }

    @Override
    public String getSameNameBrotherId(String parentId, String orgName) {
        return null;
    }

    @Override
    public List<String> findOnlyThreeLevelOrgIds() {
        return null;
    }

    @Override
    public List<Organization> getOrganizationsByLevel(Integer level) {
        return null;
    }

    @Override
    public List<Organization> getOrganizationsByChargeUserId(String chargeUserId) {
        return null;
    }

    @Override
    public Organization getOrganizationByIdAndLevel(String OrganizationId, Integer level) {
        return null;
    }

    @Override
    public List<String> getOrganizationFirstChildrenById(String OrganizationId) {
        return null;
    }

    @Override
    public Organization findRootOrgByCorpCode(String corpCode) {
        if (StringUtils.isBlank(corpCode)) {
            corpCode = AuthenticationUtil.getCorpCode();
        }

        HqlBuilder builder = new HqlBuilder("from");
        builder.append("Organization where corpCode = ?", corpCode);
        builder.append("and parentId =?", ElfConstant.ROOT_PARENT_ID);
        return baseService.queryUniqueResult(builder);
    }

    @Override
    public Long getUserByOrganizationId(String OrganizationId, boolean hasChild) {
        return null;
    }

    @Override
    public void calculateUserCount(String OrganizationId, Integer count, boolean hasMe, Map<String, Organization> mapOrganizationIdToMap) {

    }

    @Override
    public boolean checkOrgName(Organization Organization) {
        return false;
    }

    @Override
    public void synOrgNamePath() {

    }


    @Override
    public List<Organization> getSecOrgNodeList() {
        return null;
    }

    @Override
    public List<Organization> getLevelOrganizationList(Boolean isLimit, int orgLevel) {
        return null;
    }

    @Override
    public List<String> getFirstAndSecLevelOrgIds(String orgId) {
        return null;
    }

    @Override
    public Map<String, String> getOrgIdNamePathMap(Set<String> orgIds) {
        List<String> columns = new ArrayList<String>(2);
        columns.add("organizationId");
        columns.add("namePath");
        List<Object[]> resultList =
                getOrganizationsByColumnsAndOrganization(null, columns, new Organization());
        if (CollectionUtils.isEmpty(resultList)) {
            return new HashMap<String, String>(0);
        }

        Map<String, String> resultMap = new HashMap<String, String>(resultList.size());
        for (Object[] result : resultList) {
            resultMap.put((String) result[0], (String) result[1]);
        }

        return resultMap;
    }

    //need
    @Override
    public String findOrgTreeJsonByDepth(String orgId, int depth) {
        return null;
    }

    @Override
    public List<Organization> getAllOrganization() {
        List<String> columns = new ArrayList<String>(2);
        columns.add("organizationId");
        columns.add("organizationName");
        columns.add("namePath");
        List<Object[]> resultList =
                getOrganizationsByColumnsAndOrganization(null, columns, new Organization());
        if (CollectionUtils.isEmpty(resultList)) {
            return new ArrayList<Organization>(0);
        }

        List<Organization> organizations = new ArrayList<Organization>(resultList.size());
        for (Object[] result : resultList) {
            Organization organization = new Organization();
            organization.setNamePath((String) result[1]);
            organization.setOrganizationId((String) result[0]);
            organizations.add(organization);
        }
        return organizations;
    }

    @Override
    public void save(List<Organization> Organizations) {

    }

    @Override
    public Map<String, List<String>> getUserIdListGroupByOrgId(List<String> orgIdList, boolean includeChildOrg, List<String> accountStatusList) {
        return null;
    }

    private List<Object[]> getOrganizationsByColumnsAndOrganization(List<String> organizationIds
            , List<String> columns, Organization organization) {
        HqlBuilder hqlBuilder = new HqlBuilder("select ");
        hqlBuilder.disableSplit();
        for (String columnName : columns) {
            hqlBuilder.append(columnName + ",");
        }
        hqlBuilder.removeLastCharacter();
        hqlBuilder.enableSplit();
        hqlBuilder.append("from Organization");
        hqlBuilder.append("where corpCode=:corpCode");
        hqlBuilder.addParameter("corpCode", AuthenticationUtil.getCorpCode());
        if (CollectionUtils.isNotEmpty(organizationIds)) {
            hqlBuilder.append("and organizationId in (:orgIds)");
            hqlBuilder.addParameter("orgIds", organizationIds);
        }

        if (organization.getLastModifyTime() != null) {
            hqlBuilder.append("and lastModifyTime >= :lastModifyTime");
            hqlBuilder.addParameter("lastModifyTime", organization.getLastModifyTime());
        }

        if (organization.getShowOrder() > 0) {
            hqlBuilder.append("and showOrder = :showOrder");
            hqlBuilder.addParameter("showOrder", organization.getShowOrder());
        }
        hqlBuilder.append("and corpCode=:corpCode");
        hqlBuilder.addParameter("corpCode", AuthenticationUtil.getCorpCode());
        List<Object[]> result = baseService.queryList(hqlBuilder);
        return result;
    }

    private <T> T getUniqueColumnByOrganization(String column, Organization organization) {
        HqlBuilder hqlBuilder = new HqlBuilder("select ");
        hqlBuilder.append(column);
        hqlBuilder.append("from Organization");
        hqlBuilder.append("where corpCode=:corpCode");
        hqlBuilder.addParameter("corpCode", AuthenticationUtil.getCorpCode());
        if (organization == null) {
            return baseService.queryUniqueResult(hqlBuilder);
        }

        if (StringUtils.isNotBlank(organization.getParentId())) {
            hqlBuilder.append("and parentId=:parentId");
            hqlBuilder.addParameter("parentId", organization.getParentId());
        }

        return baseService.queryUniqueResult(hqlBuilder);
    }

    private Double getMaxShowOrderByParentId(String parentId) {
        Organization organization = new Organization();
        organization.setParentId(parentId);
        return getUniqueColumnByOrganization("max(showOrder)", organization);
    }

}
