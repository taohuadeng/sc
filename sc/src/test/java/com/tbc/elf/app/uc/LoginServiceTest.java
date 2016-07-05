package com.tbc.elf.app.uc;

import com.tbc.elf.app.uc.model.Login;
import com.tbc.elf.app.uc.model.Login.AccountStatus;
import com.tbc.elf.app.uc.service.LoginService;
import com.tbc.elf.base.BaseTests;
import com.tbc.elf.base.util.UUIDGenerator;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.test.annotation.Rollback;

import javax.annotation.Resource;

public class LoginServiceTest extends BaseTests {
    @Resource
    private LoginService loginService;

    @Test
    @Rollback(false)
    public void testGetPassword() {
        Login login = new Login();
        Md5PasswordEncoder encoder = new Md5PasswordEncoder();
        //encoder.setEncodeHashAsBase64(true);
        String password = encoder.encodePassword("user", "");
        System.out.println(password);
        login.setPassword(password);
        login.setLoginName("admin");
        login.setUserId(UUIDGenerator.uuid());
        login.setAccountStatus(AccountStatus.ENABLE);
        String loginId = loginService.save(login);
        Assert.assertNotNull(loginId);

        String newPassword = loginService.getPasswordByLoginAndCorpCode("admin", "default");
        Assert.assertNotNull(newPassword);
        Assert.assertEquals(password, newPassword);
    }
}
