package com.tbc.elf.app.uc.service;

import com.tbc.elf.app.uc.model.Role;
import com.tbc.elf.base.service.BaseServiceImpl;
import com.tbc.elf.base.util.HqlBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {

    @Override
    @Transactional(readOnly = true)
    public List<String> listRoleIds(String userId) {
        Assert.hasText(userId, "UserId is empty!");

        HqlBuilder builder = new HqlBuilder("SELECT roleId FROM UserRoles");
        builder.append("WHERE userId = ?", userId);
        return baseService.queryList(builder);
    }
}
