package com.tbc.elf.base.controller;

import com.tbc.elf.app.uc.model.Login;
import com.tbc.elf.app.uc.service.LoginService;
import com.tbc.elf.base.security.model.LoginVo;
import com.tbc.elf.base.util.MD5Util;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 处理登录：控制登录流程
 *
 * @author ELF@TEAM
 * @since 2016年2月23日17:03:00
 */
@Controller
@RequestMapping("/login/*")
public class LoginController {
    private Log LOG = LogFactory.getLog(LoginController.class);

    @Resource
    private LoginService loginService;

    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));// 去掉空格
    }

    @ResponseBody
    @RequestMapping("/checkLogin")
    public ModelMap checkLogin(LoginVo login) throws Exception {
        String corpCode = login.getCorpCode();

        ModelMap map = new ModelMap();
        map.put("success", false);
        if (!"default".equals(corpCode)) {
            map.put("msg", "公司不存在!");
            return map;
        }

        String username = login.getJ_username();
        String password = loginService.getPasswordByLoginAndCorpCode(username, corpCode);
        if (StringUtils.isEmpty(password)) {
            map.put("msg", "用户名不存在!");
            return map;
        }

        String loginPassword = login.getJ_password();
        if (StringUtils.isEmpty(loginPassword) || !password.equals(MD5Util.md5(loginPassword))) {
            map.put("msg", "密码错误!");
            return map;
        }
        
        map.put("success", true);
        return map;
    }

    /**
     * 未登录，跳转登录页面
     *
     * @param request
     * @param response
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping("/login")
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ModelAndView("/login");
    }

    /**
     * 登出，跳转登录页面
     *
     * @param request
     * @param response
     * @return ModelAndView
     * @throws Exception
     */
//    @RequestMapping("/loginOut")
//    public ModelAndView loginOut(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        loginService.saveLoginHis(LoginHis.LoginType.LOGOUT);
//        if (logger.isDebugEnabled()) {
//            logger.debug("登出系统！");
//        }
//        return new ModelAndView("/login");
//    }

    /**
     * 登录成功，跳转到菜单页面
     *
     * @param request
     * @param response
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping("/loginSuccess")
    public ModelAndView loginSuccess(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        request.getSession().setAttribute("uname", AuthenticationUtil.getMyUserDetails().getOperName());
        ModelAndView modelAndView = new ModelAndView("/base/login/main");
//        loginService.saveLoginHis(LoginHis.LoginType.LOGIN);
        return modelAndView;
    }

    @RequestMapping("/mainHead")
    public ModelAndView mainHead(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView modelAndView = new ModelAndView("/base/login/main-head");
        return modelAndView;
    }

    @RequestMapping("/mainLeft")
    public ModelAndView mainLeft(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView modelAndView = new ModelAndView("/base/login/main-left");
        return modelAndView;
    }

    /**
     * 登录成功，跳转到菜单页面
     *
     * @param request
     * @param response
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping("/loginFailed")
    public ModelAndView loginFailed(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ModelAndView("/base/exception/exceptionLogin");
    }

    /**
     * 登录成功，跳转到菜单页面
     *
     * @param request
     * @param response
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping("/error")
    public ModelAndView error(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ModelAndView("/base/exception/exceptionDenied");
    }

    /**
     * session会话超时
     *
     * @param request
     * @param response
     * @return ModelAndView
     * @throws Exception
     */
    @RequestMapping("/sessionOut")
    public ModelAndView sessionOut(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return new ModelAndView("/base/exception/exceptionSessionTimeOut");
    }

}
