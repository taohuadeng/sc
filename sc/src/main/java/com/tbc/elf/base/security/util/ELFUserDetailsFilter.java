package com.tbc.elf.base.security.util;

import com.tbc.elf.app.uc.model.User;
import com.tbc.elf.app.uc.service.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 人员详细信息过滤器
 *
 * @author ELF@TEAM
 * @since 2016年2月24日16:52:39
 */
public class ELFUserDetailsFilter implements Filter {

    public static final String USER_SERVICE = "userService";
    public static final String ROLE_SERVICE = "roleService";
    public static final String AUTHORITY_SERVICE = "authorityService";
    public static final String ORGANIZATION_SERVICE = "organizationService";
    public static final String LOGIN_SERVICE = "loginService";
    private UserService userService;
    private RoleService roleService;
    private AuthorityService authorityService;
    private OrganizationService organizationService;
    private LoginService loginService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        /*String userServiceName = filterConfig.getInitParameter(USER_SERVICE);// 用户Service名称
        String roleServiceName = filterConfig.getInitParameter(ROLE_SERVICE);// 角色Service名称
        String authorityServiceName = filterConfig.getInitParameter(AUTHORITY_SERVICE);// 权限Service名称
        String organizationServiceName = filterConfig.getInitParameter(ORGANIZATION_SERVICE);// 组织Service名称*/
        WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig
                .getServletContext());

        userService = (UserService) wac.getBean(USER_SERVICE);
        roleService = (RoleService) wac.getBean(ROLE_SERVICE);
        authorityService = (AuthorityService) wac.getBean(AUTHORITY_SERVICE);
        organizationService = (OrganizationService) wac.getBean(ORGANIZATION_SERVICE);
        loginService = (LoginService) wac.getBean(LOGIN_SERVICE);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();
        // 获得用户登录名称
        String username = AuthenticationUtil.getLoginName();
        if (username != null && !username.equals("") && !username.equals("anonymousUser")) {
            // 登录的场合
            Object obj = session.getAttribute(username);
            ELFUserDetails userDetails = null;
            if (obj == null) {
                // 登录第一次访问的场合

                // 根据登录名称查询用户详细信息
                String userId = loginService.getUserIdByLoginAndCorpCode(username, AuthenticationUtil.getCorpCode());
                if (StringUtils.isNotEmpty(userId)) {
                    userDetails = genElfUserDetails(userId);
                }

                session.setAttribute(username, userDetails);
            } else {
                userDetails = (ELFUserDetails) obj;
            }

            AuthenticationUtil.setUserDetails(userDetails);
        }

        // 放行到目的地址
        chain.doFilter(request, response);
    }

    /**
     * 获取线程变量信息
     */
    private ELFUserDetails genElfUserDetails(String userId) {
        ELFUserDetails userDetails = new ELFUserDetails();

        User user = userService.getUser(userId);
        if (user != null) {
            userDetails.setUserId(user.getUserId());
            userDetails.setCorpCode(user.getCorpCode());
            userDetails.setOrganizationName(user.getOrganizationName());
        }

        return userDetails;
    }

    @Override
    public void destroy() {

    }
}
