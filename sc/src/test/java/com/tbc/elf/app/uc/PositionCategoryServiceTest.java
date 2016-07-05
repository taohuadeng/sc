package com.tbc.elf.app.uc;

import com.tbc.elf.app.uc.model.PositionCategory;
import com.tbc.elf.app.uc.service.PositionCategoryService;
import com.tbc.elf.base.BaseTests;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import javax.annotation.Resource;

/**
 * 岗位类别的单元测试类
 *
 * @author ELF@TEAM
 * @since 2016年2月25日 15:00:09
 */
public class PositionCategoryServiceTest extends BaseTests {

    @Resource
    private PositionCategoryService positionCategoryService;

    @Test
    @Rollback(false)
    public void testSavePositionCategory() {
        PositionCategory positionCategory = new PositionCategory();
        positionCategory.setCategoryName("abc-2");
        positionCategory.setParentId("402881ae5317519001531751954d0000");
        positionCategoryService.saveOrUpdate(positionCategory);
    }
}
