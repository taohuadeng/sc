package com.tbc.elf.app.uc.service;

import com.tbc.elf.app.uc.model.Login;
import com.tbc.elf.base.service.BaseServiceImpl;
import com.tbc.elf.base.util.HqlBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service("loginService")
public class LoginServiceImpl extends BaseServiceImpl<Login> implements LoginService {

    @Override
    @Transactional(readOnly = true)
    public String getPasswordByLoginAndCorpCode(String loginName, String corpCode) {
        Assert.hasText(loginName, "LoginName is empty!");
        Assert.hasText(corpCode, "CorpCode is empty!");

        HqlBuilder builder = new HqlBuilder("SELECT password FROM Login");
        builder.append("WHERE corpCode = ?", corpCode);
        builder.append("AND loginName = ?", loginName);

        return baseService.queryUniqueResult(builder);
    }

    @Override
    @Transactional(readOnly = true)
    public String getUserIdByLoginAndCorpCode(String loginName, String corpCode) {
        Assert.hasText(loginName, "LoginName is empty!");
        Assert.hasText(corpCode, "CorpCode is empty!");

        HqlBuilder builder = new HqlBuilder("SELECT userId FROM Login");
        builder.append("WHERE corpCode = ?", corpCode);
        builder.append("AND loginName = ?", loginName);

        return baseService.queryUniqueResult(builder);
    }

    @Override
    @Transactional(readOnly = true)
    public Login getLogin(String loginName, String corpCode) {
        Assert.hasText(loginName, "LoginName is empty!");
        Assert.hasText(corpCode, "CorpCode is empty!");

        HqlBuilder builder = new HqlBuilder("FROM Login");
        builder.append("WHERE corpCode = ?", corpCode);
        builder.append("AND loginName = ?", loginName);
        
        return baseService.queryUniqueResult(builder);
    }
}
