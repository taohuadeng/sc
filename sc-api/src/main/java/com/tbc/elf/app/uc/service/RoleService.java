package com.tbc.elf.app.uc.service;

import com.tbc.elf.app.uc.model.Role;
import com.tbc.elf.base.service.BaseService;

import java.util.List;

/**
 * 人员信息的业务逻辑操作
 *
 * @author ELF@TEAM
 * @since 2016年2月24日16:52:39
 */
public interface RoleService extends BaseService<Role> {
    /**
     * 该方法用于根据人员ID获得其所有角色ID
     *
     * @param userId 人员主键
     * @return 权限集合
     */
    List<String> listRoleIds(String userId);
}
