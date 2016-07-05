package com.tbc.elf.base.security.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyLogOutHandler implements LogoutHandler {
    private Log LOG = LogFactory.getLog(MyLogOutHandler.class);

	public void logout(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) {

        LOG.info("用户注销!");
	}

}
