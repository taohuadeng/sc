package com.tbc.elf.base.security.util;

import com.tbc.elf.app.uc.model.User.SexType;

/**
 * 封装线程数据值
 */
public class ELFUserDetails {
    /**
     * 主键
     */
    private String userId;

    /**
     * 姓名
     */
    private String userName;

    /**
     * 工号
     */
    private String employeeCode;

    /**
     * 直属部门id
     */
    private String organizationId;

    /**
     * 直属部门 名称
     */
    private String organizationName;

    /**
     * 用户所在公司编号
     */
    private String corpCode;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getCorpCode() {
        return corpCode;
    }

    public void setCorpCode(String corpCode) {
        this.corpCode = corpCode;
    }
}
