package com.tbc.elf.app.uc;

import com.tbc.elf.app.uc.model.Authority;
import com.tbc.elf.app.uc.model.Authority.AuthorityType;
import com.tbc.elf.app.uc.model.Role;
import com.tbc.elf.app.uc.service.AuthorityService;
import com.tbc.elf.app.uc.service.RoleService;
import com.tbc.elf.base.BaseTests;
import com.tbc.elf.base.service.HibernateBaseService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 系统权限服务测试类
 *
 * @author ELF@TEAM
 * @since 2016年2月23日17:03:00
 */
public class AuthorityServiceTest extends BaseTests {
    private Log LOG = LogFactory.getLog(AuthorityServiceTest.class);

    @Resource
    private AuthorityService authorityService;
    @Resource
    private HibernateBaseService baseService;
    @Resource
    private RoleService roleService;

    @Test
    @Rollback(false)
    public void testSave() {
        List<Authority> authorities = new ArrayList<Authority>();
        for (int i = 0; i < 10; i++) {
            Authority authority = new Authority();
            authority.setSourceUrl("url-" + i);
            authority.setParentId(".");
            authority.setShowOrder(i + 1);
            authority.setSourceName("name-" + i);
            authority.setAuthorityType(AuthorityType.SYSTEM);
            authorities.add(authority);
        }

        //baseService.batchSaveOrUpdate(authorities);

        Role role = new Role();
        role.setRoleName("roleName");
        role.setRoleType(Role.RoleType.SYSTEM);
        role.setAuthorities(authorities);
        role.setComments("角色描述");
        roleService.save(role);
    }

    @Test
    @Rollback(false)
    public void testFindRole() {
        String hql = "FROM Authority WHERE corpCode = ?";
        List<Authority> authorities = authorityService.listByHQL(hql, new Object[]{"default"});
        for (Authority authority : authorities) {
            authority.setSourceName(authority.getSourceName() + "hello2");
            List<Role> roles = authority.getRoles();
            for (Role role : roles) {
                role.setRoleName(role.getRoleName() + "hello");
                //System.out.println(role.getRoleName());
            }
        }

        baseService.batchSaveOrUpdate(authorities);

        String hql2 = "FROM Role WHERE corpCode = ?";
        List<Role> roles = roleService.listByHQL(hql2, new Object[]{"default"});
        /*for (Role role : roles) {
            role.setRoleName(role.getRoleName() + ":role-name2");
        }*/
        /*Role role = new Role();
        role.setRoleName("roleName_update");
        role.setRoleType(Role.RoleType.CUSTOM);
        role.setAuthorities(null);
        role.setComments("角色描述_update");
        roles.add(role);
        baseService.batchSaveOrUpdate(roles);*/
        //Role role = roles.get(0);
        //role.clearAuthorities();
       /* List<Authority> roleAuthorities = role.getAuthorities();
        for (Authority roleAuthority : roleAuthorities) {
            roleAuthority.setAuthorityType(AuthorityType.SYSTEM);
        }
        //role.setAuthorities(null);
        role.setRoleType(Role.RoleType.SYSTEM);
        roleService.update(role);*/
    }

    @Test
    @Rollback(false)
    public void testUpdateAuthorities() {
        String hql = "FROM Authority WHERE corpCode = ?";
        List<Authority> authorities = authorityService.listByHQL(hql, new Object[]{"default"});
        for (Authority authority : authorities) {
            List<Role> roles = authority.getRoles();
            for (Role role : roles) {
                System.out.println(role.getRoleName());
            }
        }

        String hql2 = "FROM Role WHERE corpCode = ?";
        List<Role> roles = roleService.listByHQL(hql2, new Object[]{"default"});
        Role role = roles.get(0);
        //role.clearAuthorities();
        List<Authority> roleAuthorities = role.getAuthorities();
        for (Authority roleAuthority : roleAuthorities) {
            roleAuthority.setAuthorityType(AuthorityType.SYSTEM);
        }
        //role.setAuthorities(null);
        role.setRoleType(Role.RoleType.SYSTEM);
        roleService.update(role);
    }

    @Test
    @Rollback(false)
    public void testDeleteRole() {
        String hql = "FROM Authority WHERE corpCode = ?";
        List<Authority> authorities = authorityService.listByHQL(hql, new Object[]{"default"});
        for (Authority authority : authorities) {
            List<Role> roles = authority.getRoles();
            for (Role role : roles) {
                System.out.println(role.getRoleName());
            }
        }

        String hql2 = "FROM Role WHERE corpCode = ?";
        List<Role> roles = roleService.listByHQL(hql2, new Object[]{"default"});
        Role role = roles.get(0);
        role.clearAuthorities();
        /*List<Authority> roleAuthorities = role.getAuthorities();
        for (Authority roleAuthority : roleAuthorities) {
            roleAuthority.setAuthorityType(AuthorityType.SYSTEM);
        }*/
        //role.setAuthorities(null);
        //role.setRoleType(Role.RoleType.SYSTEM);
        roleService.delete(role.getRoleId());
    }

    @Test
    public void testGetByCriteria() {

    }


    @Test
    @Rollback(false)
    public void testSave2() {
        List<Role> roles = new ArrayList<Role>();
        for (int i = 0; i < 10; i++) {
            Role role = new Role();
            role.setRoleName("roleName" + i);
            role.setRoleType(Role.RoleType.SYSTEM);
            role.setComments("角色描述" + i);


            roles.add(role);
        }

        baseService.batchSave(roles);

        /*Role role = new Role();
        role.setRoleName("roleName");
        role.setRoleType(Role.RoleType.SYSTEM);
        role.setAuthorities(authorities);
        role.setComments("角色描述");
        roleService.save(role);*/

        Authority authority = new Authority();
        authority.setSourceUrl("url-");
        authority.setParentId(".");
        authority.setShowOrder(1);
        authority.setSourceName("name-");
        authority.setRoles(roles);
        authority.setAuthorityType(AuthorityType.SYSTEM);
        authorityService.save(authority);
    }

    @Test
    public void test() {
        Authority authority = new Authority();
        authority.setParentId("297ea177531873b801531873c21f0000");
        authority.setSourceUrl("/app/uc/user/*");
        authority.setSourceName("人员管理");
        String authorityId = authorityService.save(authority);
        LOG.info(authorityId);
        Assert.assertNotNull(authorityId);

        Authority auth = authorityService.get(authorityId);
        Assert.assertNotNull(auth);
        Assert.assertEquals("人员管理", auth.getSourceName());
        Assert.assertEquals("/app/uc/user/*", auth.getSourceUrl());

        String hql = "FROM Authority WHERE corpCode = ?";
        List<Authority> authorities = authorityService.listByHQL(hql, new Object[]{"default"});
        Assert.assertNotNull(authorities);
    }

    @Test
    public void testGetAuthorities() {
        List<String> list = authorityService.listAuthorityUrls("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu");
        Assert.assertNotNull(list);
        list = authorityService.listAuthorityUrls("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu");
        for (String s : list) {
            LOG.info(s);
        }
    }
}
