package com.tbc.elf.app.uc.model;

import com.tbc.elf.base.model.BaseModel;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * 登录日志信息实体
 *
 * @author ELF@TEAM
 * @since 2016年2月23日17:03:00
 */
@Entity
@Table(name = "t_uc_login_log")
public class LoginLog extends BaseModel {

    /**
     * 主键
     */
    @Id
    @Column(nullable = false, length = 32)
    @GeneratedValue(generator = "hibernate-uuid")
    @GenericGenerator(name = "hibernate-uuid", strategy = "uuid")
    private String loginLogId;

    /**
     * SESSION ID
     */
    @Column(nullable = false, length = 20)
    private String sessionId;

    /**
     * 用户名
     */
    @Column(length = 50)
    private String loginName;

    /**
     * 姓名
     */
    @Column(length = 50)
    private String userName;

    /**
     * 用户ID
     */
    @Column(nullable = false, length = 32)
    private String userId;

    /**
     * 登出时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date logoutTime;

    /**
     * 登入时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date loginTime;

    /**
     * 登入IP
     */
    @Column(length = 20)
    private String loginIp;

    /**
     * 是否已真实登出
     */
    @Column(nullable = false)
    private boolean realLogout;

    /**
     * 登出方式
     * click 点击退出,timeout 超时退出,kickoff 被踢出,timelimit 时间段限制,close_browser 关闭浏览器
     */
    @Column(length = 50)
    private String logoutType;

    /**
     * 登出时踢出人的userId;
     */
    @Column(length = 100)
    private String exceptUser;

    /**
     * 登陆状态(SUCCESS/FAILED)
     */
    @Column(length = 20)
    private String status;

    /**
     * 登陆方式
     */
    @Column(length = 20)
    private String loginType;

    public String getLoginLogId() {
        return loginLogId;
    }

    public void setLoginLogId(String loginLogId) {
        this.loginLogId = loginLogId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(Date logoutTime) {
        this.logoutTime = logoutTime;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public boolean isRealLogout() {
        return realLogout;
    }

    public void setRealLogout(boolean realLogout) {
        this.realLogout = realLogout;
    }

    public String getLogoutType() {
        return logoutType;
    }

    public void setLogoutType(String logoutType) {
        this.logoutType = logoutType;
    }

    public String getExceptUser() {
        return exceptUser;
    }

    public void setExceptUser(String exceptUser) {
        this.exceptUser = exceptUser;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }
}
