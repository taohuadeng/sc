package com.tbc.elf.app.uc;

import com.tbc.elf.app.uc.model.Setting;
import com.tbc.elf.app.uc.service.SettingService;
import com.tbc.elf.base.BaseTests;
import com.tbc.elf.base.service.HibernateBaseService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * 系统设置测试类
 */
public class SettingServiceImplTest extends BaseTests {
    private Log LOG = LogFactory.getLog(SettingServiceImplTest.class);
    @Resource
    private HibernateBaseService baseService;
    @Resource
    private SettingService settingService;

    @Test
    public void testGetCorpSetting() {
        Setting corpSetting = settingService.getCorpSetting();
        System.out.println(corpSetting.getSettingId());
    }
}
