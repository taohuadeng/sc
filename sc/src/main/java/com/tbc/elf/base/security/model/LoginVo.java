package com.tbc.elf.base.security.model;

/**
 * 登录实体VO类
 *
 * @author ELF@TEAM
 * @since 2016年2月23日17:03:00
 */
public class LoginVo {
    /**
     * 公司编号
     */
    private String corpCode;

    /**
     * 登录姓名
     */
    private String j_username;

    /**
     * 登录密码
     */
    private String j_password;

    public String getCorpCode() {
        return corpCode;
    }

    public void setCorpCode(String corpCode) {
        this.corpCode = corpCode;
    }

    public String getJ_username() {
        return j_username;
    }

    public void setJ_username(String j_username) {
        this.j_username = j_username;
    }

    public String getJ_password() {
        return j_password;
    }

    public void setJ_password(String j_password) {
        this.j_password = j_password;
    }
}
