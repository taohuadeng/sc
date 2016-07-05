package com.tbc.elf.app.uc;

import com.tbc.elf.app.uc.model.User;
import com.tbc.elf.base.BaseTests;
import com.tbc.elf.base.service.HibernateBaseService;
import com.tbc.elf.base.util.HqlBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import javax.annotation.Resource;

/**
 * Created by zhaoyue on 2016-02-25
 */
public class HibernateBaseServiceTest extends BaseTests {

    private Log LOG = LogFactory.getLog(this.getClass());

    @Resource
    private HibernateBaseService hibernateBaseService;

    @Test
    @Rollback(false)
    public void testExecuteUpdate() {
        HqlBuilder hqlBuilder = new HqlBuilder();
        hqlBuilder.append("update User set showOrder = ?",1000D);
        int num = hibernateBaseService.executeUpdate(hqlBuilder);
        LOG.info(">>>>" + num);
    }

    @Test
    public void testLoad() {
        User user = hibernateBaseService.load(User.class, "sdf");
        Assert.assertNotNull(user);
    }
}
