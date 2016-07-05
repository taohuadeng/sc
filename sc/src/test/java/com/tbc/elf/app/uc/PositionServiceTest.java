package com.tbc.elf.app.uc;

import com.tbc.elf.app.uc.model.Position;
import com.tbc.elf.app.uc.service.PositionService;
import com.tbc.elf.base.BaseTests;
import com.tbc.elf.base.util.Page;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * 岗位的单元测试类
 *
 * @author ELF@TEAM
 * @since 2016年2月25日 17:29:18
 */
public class PositionServiceTest extends BaseTests {

    private Log LOG = LogFactory.getLog(this.getClass());

    @Resource
    private PositionService positionService;

    @Test
    @Rollback(false)
    public void testSave() {
        Position position = new Position();
        position.setPositionName("abc");
        position.setCategoryId("402881ae5317519001531751954d0000");
        position.setShowOrder(2);
        positionService.save(position);
    }

    @Test
    public void testGetMaxShowOrder() {
        double maxShowOrder = positionService.getMaxShowOrder();
        LOG.info(">>>>>>>>>>>>>>>>>>>>>>>>" + maxShowOrder);
    }

    @Test
    public void testSearch() {
        Page<Position> page = positionService.search("", new Page<Position>());
        Assert.assertNotNull(page.getRows());
    }

    @Test
    @Rollback(false)
    public void test() {
        int deleteCount = positionService.delete(Arrays.asList("402881ae5317c4b5015317c4ba460000", "402881ae5317c736015317c73bb50000"));
        Assert.assertTrue(deleteCount == 2);
    }
}
