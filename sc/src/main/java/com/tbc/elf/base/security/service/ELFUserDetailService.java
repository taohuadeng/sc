package com.tbc.elf.base.security.service;

import com.tbc.elf.app.uc.model.Login;
import com.tbc.elf.app.uc.service.AuthorityService;
import com.tbc.elf.app.uc.service.LoginService;
import com.tbc.elf.app.uc.service.UserService;
import com.tbc.elf.base.security.model.ResourceDetails;
import com.tbc.elf.base.security.model.SecurityResource;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

public class ELFUserDetailService implements UserDetailsService {
    @Resource
    private UserService userService;
    @Resource
    private LoginService loginService;
    @Resource
    private AuthorityService authorityService;

    /**
     * 根据用户名，登录处理
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String corpCode = "";
        String loginName = "";
        if (username.contains("|")) {
            String[] split = username.split("\\|");
            corpCode = split[0];
            loginName = split[1];
        }

        if (StringUtils.isEmpty(corpCode) || !"default".equals(corpCode)) {
            throw new RuntimeException("CorpCode is not exist!");
        }

        if (StringUtils.isEmpty(loginName)) {
            throw new RuntimeException("LoginName can not be null!");
        }

        Login login = loginService.getLogin(loginName, corpCode);
        if (login == null || StringUtils.isEmpty(login.getUserId())) {
            throw new RuntimeException("User is not exist!");
        }

        List<String> authorityUrls = authorityService.listAuthorityUrls(login.getUserId());
        List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>(authorityUrls.size());
        for (String authorityUrl : authorityUrls) {
            String rolePrefix = "ROLE_";
            String roleName = rolePrefix + authorityUrl;
            GrantedAuthority authority = new SimpleGrantedAuthority(roleName);
            auths.add(authority);
        }

        return new User(loginName, login.getPassword(), true, true, true, true, auths);
    }

    /**
     * 取得所有权限
     */
    public List<ResourceDetails> findAuthority() {
        // 去数据库中查询出所有权限
        List<String> authorityUrls = authorityService.listAuthorityUrls();

        return getResourceByPrivResource(authorityUrls);
    }

    private List<ResourceDetails> getResourceByPrivResource(List<String> authorityUrls) {
        List<ResourceDetails> result = new ArrayList<ResourceDetails>();
        for (String authorityUrl : authorityUrls) {
            String rolePrefix = "ROLE_";
            String roleName = rolePrefix + authorityUrl;
            GrantedAuthority authority = new SimpleGrantedAuthority(roleName);
            result.add(new SecurityResource(authorityUrl, SecurityResource.RESOURCE_TYPE_URL, authority));
        }

        return result;
    }
}
