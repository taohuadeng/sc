package com.tbc.elf.app.uc.service;

import com.tbc.elf.app.uc.model.User;
import com.tbc.elf.base.security.util.AuthenticationUtil;
import com.tbc.elf.base.service.BaseServiceImpl;
import com.tbc.elf.base.util.HqlBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    @Override
    @Transactional(readOnly = true)
    public User getUser(String userId) {
        Assert.hasText(userId, "UserId is empty!");

        HqlBuilder builder = new HqlBuilder("FROM User");
        builder.append("WHERE userId = ?", userId);
        builder.append("AND corpCode = ?", AuthenticationUtil.getCorpCode());

        return baseService.queryUniqueResult(builder);
    }
}
