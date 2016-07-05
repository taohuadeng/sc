package com.tbc.elf.app.uc.service;

import com.tbc.elf.app.uc.model.Organization;
import com.tbc.elf.app.uc.model.OrganizationRel;
import com.tbc.elf.base.service.BaseService;

import java.util.List;

/**
 * 部门-子部门关系业务逻辑接口
 *
 * @author YangLiBo@HF
 * @since 2016年02月26日14:04:36
 */
public interface OrganizationRelService extends BaseService<OrganizationRel> {
    List<String> getChildOrgIdWithSelfByOrganizationId(String organizationId, boolean hasSelf);

    /**
     * 添加部门与其所有父部门的关系
     *
     * @param organization
     * @param corpCode
     * @throws IllegalArgumentException#IllegalArgumentException <ul>
     *                                                           <li>
     *                                                           当查询的用户organization为空时,错误类型为
     *                                                           IllegalArgumentException#IllegalArgumentException
     *                                                           </li>
     *                                                           <li>
     *                                                           当查询的用户organization#organizationId为空时,错误类型为
     *                                                           IllegalArgumentException#IllegalArgumentException
     *                                                           </li>
     *                                                           </ul>
     */
    void addOrganizationRel(Organization organization, String corpCode);

    /**
     * 获取所有organizationId指定的部门的父部门(直接父部门以及间接父部门)的关系
     *
     * @param organizationId
     * @return List<OrganizationRel>:父部门关系的实体列表
     * @throws IllegalArgumentException#IllegalArgumentException <ul>
     *                                                           <li>
     *                                                           当查询的用户organizationId为空时,错误类型为
     *                                                           IllegalArgumentException#IllegalArgumentException
     *                                                           </li>
     *                                                           </ul>
     */
    List<OrganizationRel> getParentOrganizationRelWithSelfByOrganizationId(String organizationId);

    /**
     * 删除childId指定的部门与所有父部门(直接父部门以及间接父部门)的关系
     *
     * @param childId:子部门的id
     * @param corpCode:公司编号
     * @throws IllegalArgumentException#IllegalArgumentException <ul>
     *                                                           <li>
     *                                                           当查询的用户childId为空时,错误类型为
     *                                                           IllegalArgumentException#IllegalArgumentException
     *                                                           </li>
     *                                                           </ul>
     */
    void deleteOrganizationRelByChildId(String childId, String corpCode);

    /**
     * 根据部门id判断是否有子部门
     *
     * @param organizationId:部门id
     * @return 当部门有子部门返回false, 否则返回true
     * @throws IllegalArgumentException#IllegalArgumentException <ul>
     *                                                           <li>
     *                                                           当查询的用户childId为空时,错误类型为
     *                                                           IllegalArgumentException#IllegalArgumentException
     *                                                           </li>
     *                                                           </ul>
     */
    boolean hasChild(String organizationId);

    /**
     * 更新部门id为organizationId的部门名称
     *
     * @param organizationId:部门id
     * @return 当部门有子部门返回false, 否则返回true
     * @throws IllegalArgumentException#IllegalArgumentException <ul>
     *                                                           <li>
     *                                                           当查询的用户organizationId为空时,错误类型为
     *                                                           IllegalArgumentException#IllegalArgumentException
     *                                                           </li>
     *                                                           </ul>
     */
    void updateOrganizationNameByChild(String organizationId, String organizationName);
}
