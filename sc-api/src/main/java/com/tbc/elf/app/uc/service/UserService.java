package com.tbc.elf.app.uc.service;

import com.tbc.elf.app.uc.model.User;
import com.tbc.elf.base.service.BaseService;

/**
 * 人员信息的业务逻辑操作
 *
 * @author ELF@TEAM
 * @since 2016年2月23日17:03:00
 */
public interface UserService extends BaseService<User> {

    /**
     * 该方法用于根据用户主键获取用户信息
     *
     * @param userId 用户主键
     * @return 用户对象
     */
    User getUser(String userId);
}
