package com.tbc.elf.app.uc;

import com.tbc.elf.app.uc.model.Authority;
import com.tbc.elf.app.uc.model.Role;
import com.tbc.elf.app.uc.model.RoleAuthorities;
import com.tbc.elf.app.uc.model.UserRoles;
import com.tbc.elf.app.uc.service.RoleService;
import com.tbc.elf.base.BaseTests;
import com.tbc.elf.base.service.HibernateBaseService;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import javax.annotation.Resource;

public class RoleServiceTest extends BaseTests {
    @Resource
    private RoleService roleService;
    @Resource
    private HibernateBaseService baseService;

    @Test
    @Rollback(false)
    public void testSaveUserRoles() {
        UserRoles userRoles = new UserRoles();
        userRoles.setUserId("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu");
        userRoles.setRoleId("rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");

        baseService.save(userRoles);
    }

    @Test
    public void testSaveRole() {
        Role role = new Role();
        role.setRoleName("考试管理");
        role.setRoleType(Role.RoleType.SYSTEM);
        role.setComments("具有考试管理的所有权限");
        UserRoles userRoles = new UserRoles();
        userRoles.setUserId("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu");
        userRoles.setRoleId("rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");

        String roleId = roleService.save(role);

        RoleAuthorities roleAuthorities = new RoleAuthorities();
        roleAuthorities.setRoleId(roleId);
        roleAuthorities.setAuthorityId("297ea177531873b801531873c21f0000");
        baseService.save(roleAuthorities);

        RoleAuthorities roleAuthorities2 = new RoleAuthorities();
        roleAuthorities2.setRoleId(roleId);
        roleAuthorities2.setAuthorityId("297ea1775318750c0153187515830000");
        baseService.save(roleAuthorities2);
    }
}
