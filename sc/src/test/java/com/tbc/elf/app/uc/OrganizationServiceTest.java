package com.tbc.elf.app.uc;

import com.tbc.elf.app.uc.model.Organization;
import com.tbc.elf.app.uc.model.User;
import com.tbc.elf.app.uc.service.OrganizationService;
import com.tbc.elf.app.uc.service.UserService;
import com.tbc.elf.base.BaseTests;
import com.tbc.elf.base.security.util.AuthenticationUtil;
import com.tbc.elf.base.service.HibernateBaseService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import javax.annotation.Resource;
import java.util.*;

/**
 * 测试测试类
 */
public class OrganizationServiceTest extends BaseTests {
    private Log LOG = LogFactory.getLog(OrganizationServiceTest.class);
    @Resource
    private HibernateBaseService baseService;
    @Resource
    OrganizationService organizationService;


    @Test
    @Rollback(false)
    public void testAddOrganization() {
        Organization organization = new Organization();
        organization.setShowOrder(1);
        organization.setNamePath("1");
        organization.setComments("1");
        String rootId = "";
        rootId = organizationService.findCorpRootId();
       rootId = "4028819d532c3fa401532c3fabb50000";
        organization.setParentId(rootId);
        organization.setOrganizationName("根");
        organizationService.addOrganization(organization);

    }

    @Test
    @Rollback(false)
    public void testUpdateOrganization() {
        String orgId = organizationService.findCorpRootId();
        Organization root = new Organization();
        root.setOrganizationId("4028819d532c3f4001532c3f48060000");
        root.setOrganizationName("新根2");
        organizationService.updateOrganization(root);
    }

    @Test
    public void testList() {
        Set<String> set1 = new HashSet<String>(1);
        set1.add("402881115317922e0153179235020000");
        set1.add("40288111531792f601531792fcd50000");
        List<Organization> result = organizationService.findOrganization(set1);
        System.out.println(result.size());
    }

    @Test
    public void testFindRootOrgByCorpCode() {
        organizationService.findRootOrgByCorpCode("default");
    }

}
