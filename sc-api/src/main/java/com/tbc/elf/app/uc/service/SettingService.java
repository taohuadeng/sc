package com.tbc.elf.app.uc.service;

import com.tbc.elf.app.uc.model.Setting;
import com.tbc.elf.base.service.BaseService;

/**
 * 系统设置服务
 *
 * @author ELF@TEAM
 * @since 2016-2-29
 */
public interface SettingService extends BaseService<Setting> {
    /**
     * 取得公司的设置，如果不存在，创建一个新的设置
     * 所有值参考数据库的默认值
     *
     * @return 系统设置
     */
    Setting getCorpSetting();

    /**
     * 取得公司的设置
     *
     * @param corpCode 公司编码
     * @return 系统设置
     */
    Setting loadSettingByCorpCode(String corpCode);
}
