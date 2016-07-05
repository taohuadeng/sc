package com.tbc.elf.app.uc;

import com.tbc.elf.app.uc.model.GroupCategory;
import com.tbc.elf.app.uc.service.GroupCategoryService;
import com.tbc.elf.base.BaseTests;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * 群组类别的单元测试
 *
 * @author ELF@TEAM
 * @since 2016年2月29日 17:18:30
 */
public class GroupCategoryServiceTest extends BaseTests {

    @Resource
    private GroupCategoryService groupCategoryService;

    @Test
    @Rollback(false)
    public void testSaveOrUpdate() {
        GroupCategory groupCategory = new GroupCategory();
        groupCategory.setGroupCategoryName("1-1");
        groupCategory.setGroupCategoryId("402881b7532c5f5c01532c5f62420000");
        String id = groupCategoryService.saveOrUpdate(groupCategory);
        Assert.assertNotNull(id);
    }

    @Test
    public void testGet() {
        GroupCategory category = groupCategoryService.get("402881fe535f881601535f881feb0001");
        category = groupCategoryService.get("402881fe535f881601535f881feb0001");
        category = groupCategoryService.load("402881fe535f881601535f881feb0001");

        String hql = "from GroupCategory where groupCategoryId = ?";
        List<GroupCategory> groupCategories = groupCategoryService.listByHQL(hql, new Object[]{"402881fe535f881601535f881feb0001"});
        groupCategories = groupCategoryService.listByHQL(hql, new Object[]{"402881fe535f881601535f881feb0001"});
    }

}
