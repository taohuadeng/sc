package com.tbc.elf.base.security.filter;


import com.tbc.elf.base.security.util.AuthenticationUtil;
import com.tbc.elf.base.util.ElfConstant;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CustomLoginFilter extends UsernamePasswordAuthenticationFilter {
    private Log LOG = LogFactory.getLog(CustomLoginFilter.class);

    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        HttpSession session = request.getSession();
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        //String username = obtainUsername(request).toUpperCase().trim();
        String username = obtainUsername(request).trim();
        String password = obtainPassword(request);
        String corpCode = obtainCorpCode(request);

        session.setAttribute(ElfConstant.CORP_CODE, corpCode);
        UsernamePasswordAuthenticationToken authRequest = new CustomAuthenticationToken(corpCode + "|" + username, password, corpCode);

        AuthenticationUtil.setCorpCode("test");
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    private String obtainCorpCode(HttpServletRequest request) {
        return request.getParameter(ElfConstant.CORP_CODE);
    }
}
