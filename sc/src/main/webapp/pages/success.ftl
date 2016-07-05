<#assign sec=JspTaglibs["http://www.springframework.org/security/tags"]/>
<@sec.authorize ifAllGranted="ROLE_USER">
用户角色
</@sec.authorize>
<@sec.authorize ifAllGranted="ROLE_ADMIN">
管理员角色
</@sec.authorize>
<@sec.authorize ifAllGranted="ROLE_ADMIN,ROLE_USER">
管理员角色、用户角色
</@sec.authorize>
<@sec.authorize ifAllGranted="ROLE_/app/uc/user/*">
-----用户管理-----
</@sec.authorize>
<@sec.authorize ifAllGranted="ROLE_/app/uc/organization/*">
-----机构管理-----
</@sec.authorize>
