package com.tbc.elf.app.uc.service;

import com.tbc.elf.app.uc.model.Authority;
import com.tbc.elf.base.service.BaseService;

import java.util.List;

/**
 * 人员信息的业务逻辑操作
 *
 * @author ELF@TEAM
 * @since 2016年2月24日16:52:39
 */
public interface AuthorityService extends BaseService<Authority> {

    /**
     * 该方法用于根据人员ID获得其所有权限集合
     *
     * @param userId 人员主键
     * @return 权限集合
     */
    List<String> listAuthorityUrls(String userId);

    /**
     * 该方法用于根据人员ID获得其所有权限集合
     *
     * @return 权限集合
     */
    List<String> listAuthorityUrls();

    /**
     * 批量保存或更新实体
     *
     * @param authorities 实体集合
     */
    void saveOrUpdate(List<Authority> authorities);
}
