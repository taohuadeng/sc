package com.tbc.elf.app.uc.model;

import com.tbc.elf.base.model.BaseModel;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * 登录信息实体
 *
 * @author ELF@TEAM
 * @since 2016年2月23日17:03:00
 */
@Entity
@Table(name = "t_uc_login")
public class Login extends BaseModel {

    /**
     * 群组状态枚举
     */
    public enum AccountStatus {
        UNAPPROVED, ENABLE, FORBIDDEN;

        private String text;

        private AccountStatus() {
        }

        private AccountStatus(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }

    /**
     * 登录信息id
     */
    @Id
    @Column(nullable = false, length = 32)
    @GeneratedValue(generator = "hibernate-uuid")
    @GenericGenerator(name = "hibernate-uuid", strategy = "uuid")
    private String loginId;

    /**
     * 用户主键
     */
    @Column(nullable = false, length = 32)
    private String userId;

    /**
     * 登录帐号
     */
    @Column(nullable = false, length = 50)
    private String loginName;

    /**
     * 注册时间
     */
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date registerTime;

    /**
     * 登录(初始)密码
     */
    @Column(nullable = false, length = 50)
    private String password;

    /**
     * 状态(激活，禁用，注册未审批)
     */
    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    /**
     * 上次登录时间
     */
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLoginTime;

    /**
     * 当前登录时间
     */
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date currentLoginTime;

    /**
     * 激活码
     */
    @Column(length = 50)
    private String activationCode;

    /**
     * 网卡
     */
    @Column(length = 50)
    private String mobileTerminalMac;

    /**
     * 账号过期时间
     */
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date accountExpireTime;

    /**
     * 账号可登录时间(比如周一至周五8:00-19:00)
     */
    @Column(length = 50)
    private String accountActiveRule;

    /**
     * 账号锁定次数
     */
    @Column
    private int accountLockCount;

    /**
     * 密码更新周期
     */
    @Column
    private int passwordUpdatePeriod;

    /**
     * 下此密码更新时间
     */
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date nextPasswordChangeTime;

    /**
     * 最后一次修改密码的时间
     */
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastPwdChangeTime;

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Date getCurrentLoginTime() {
        return currentLoginTime;
    }

    public void setCurrentLoginTime(Date currentLoginTime) {
        this.currentLoginTime = currentLoginTime;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public String getMobileTerminalMac() {
        return mobileTerminalMac;
    }

    public void setMobileTerminalMac(String mobileTerminalMac) {
        this.mobileTerminalMac = mobileTerminalMac;
    }

    public Date getAccountExpireTime() {
        return accountExpireTime;
    }

    public void setAccountExpireTime(Date accountExpireTime) {
        this.accountExpireTime = accountExpireTime;
    }

    public String getAccountActiveRule() {
        return accountActiveRule;
    }

    public void setAccountActiveRule(String accountActiveRule) {
        this.accountActiveRule = accountActiveRule;
    }

    public int getAccountLockCount() {
        return accountLockCount;
    }

    public void setAccountLockCount(int accountLockCount) {
        this.accountLockCount = accountLockCount;
    }

    public int getPasswordUpdatePeriod() {
        return passwordUpdatePeriod;
    }

    public void setPasswordUpdatePeriod(int passwordUpdatePeriod) {
        this.passwordUpdatePeriod = passwordUpdatePeriod;
    }

    public Date getNextPasswordChangeTime() {
        return nextPasswordChangeTime;
    }

    public void setNextPasswordChangeTime(Date nextPasswordChangeTime) {
        this.nextPasswordChangeTime = nextPasswordChangeTime;
    }

    public Date getLastPwdChangeTime() {
        return lastPwdChangeTime;
    }

    public void setLastPwdChangeTime(Date lastPwdChangeTime) {
        this.lastPwdChangeTime = lastPwdChangeTime;
    }
}
