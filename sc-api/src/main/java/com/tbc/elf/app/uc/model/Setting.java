package com.tbc.elf.app.uc.model;

import com.tbc.elf.base.model.BaseModel;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 系统设置实体
 *
 * @author ELF@TEAM
 * @since 2016年2月23日17:03:00
 */
@Entity
@Table(name = "t_uc_setting")
public class Setting extends BaseModel {

    /**
     * 系统设置id
     */
    @Id
    @Column(nullable = false, length = 32)
    @GeneratedValue(generator = "hibernate-uuid")
    @GenericGenerator(name = "hibernate-uuid", strategy = "uuid")
    private String settingId;

    /**
     * 冻结时是否邮件通知
     */
    @Column
    private boolean userFreezeEmailFlag;

    /**
     * 冻结时是否站内短信通知
     */
    @Column
    private boolean userFreezeInternalSmsFlag;

    /**
     * 冻结时是否手机短信通知
     */
    @Column
    private boolean userFreezeSmsFlag;

    /**
     * 找回密码发送验证码时是否邮件通知
     */
    @Column
    private boolean sendVerifyCodeEmailFlag;

    /**
     * 找回密码发送验证码时是否站内短信通知
     */
    @Column
    private boolean sendVerifyCodeInternalSmsFlag;

    /**
     * 找回密码发送验证码时是否手机短信通知
     */
    @Column
    private boolean sendVerifyCodeSmsFlag;

    /**
     * 新增用户时是否邮件通知
     */
    @Column
    private boolean addUserEmailFlag;

    /**
     * 新增用户时是否站内短信通知
     */
    @Column
    private boolean addUserInternalSmsFlag;

    /**
     * 新增用户时是否手机短信通知
     */
    @Column
    private boolean addUserSmsFlag;

    /**
     * 学员账号激活时是否邮件通知
     */
    @Column
    private boolean userActivateEmailFlag;

    /**
     * 学员账号激活时是否站内短信通知
     */
    @Column
    private boolean userActivateInternalSmsFlag;

    /**
     * 学员账号激活时是否手机短信通知
     */
    @Column
    private boolean userActivateSmsFlag;

    /**
     * 重置密码时是否邮件通知
     */
    @Column
    private boolean resetPwdEmailFlag;

    /**
     * 重置密码时是否站内短信通知
     */
    @Column
    private boolean resetPwdInternalSmsFlag;

    /**
     * 重置密码时是否手机短信通知
     */
    @Column
    private boolean resetPwdSmsFlag;

    /**
     * 部门调动时是否邮件通知
     */
    @Column
    private boolean deptChangeEmailFlag;

    /**
     * 部门调动时是否站内短信通知
     */
    @Column
    private boolean deptChangeInternalSmsFlag;

    /**
     * 部门调动时是否手机短信通知
     */
    @Column
    private boolean deptChangeSmsFlag;

    /**
     * 找回密码申请时是否邮件通知
     */
    @Column
    private boolean findPwdApplyEmailFlag;

    /**
     * 找回密码申请时是否站内短信通知
     */
    @Column
    private boolean findPwdApplyInternalSmsFlag;

    /**
     * 找回密码申请时是否手机短信通知
     */
    @Column
    private boolean findPwdApplySmsFlag;

    /**
     * 找回密码时是否邮件通知
     */
    @Column
    private boolean retrievePwdEmailFlag;

    /**
     * 找回密码时是否站内短信通知
     */
    @Column
    private boolean retrievePwdInternalSmsFlag;

    /**
     * 找回密码时是否手机短信通知
     */
    @Column
    private boolean retrievePwdSmsFlag;

    /**
     * 用户注册是否需要审核
     */
    @Column
    private boolean userRegApprovalFlag;

    /*
     * 部门树显示层级
     */
    @Column
    protected Integer orgTreeShowLevel;

    /**
     * 群组审核通过时是否站内短信通知
     */
    @Column
    private boolean groupJoinReqAcceptInternalSmsFlag;

    /**
     * 群组审核通过时是否手机短信通知
     */
    @Column
    private boolean groupJoinReqAcceptSmsFlag;

    /**
     * 群组审核通过时是否邮件通知
     */
    @Column
    private boolean groupJoinReqAcceptEmailFlag;

    /**
     * 群组审核通过时是否站内短信通知
     */
    @Column
    private boolean groupJoinReqDenialInternalSmsFlag;

    /**
     * 群组审核通过时是否手机短信通知
     */
    @Column
    private boolean groupJoinReqDenialSmsFlag;

    /**
     * 群组审核通过时是否邮件通知
     */
    @Column
    private boolean groupJoinReqDenialEmailFlag;

    public String getSettingId() {
        return settingId;
    }

    public void setSettingId(String settingId) {
        this.settingId = settingId;
    }

    public boolean isUserFreezeEmailFlag() {
        return userFreezeEmailFlag;
    }

    public void setUserFreezeEmailFlag(boolean userFreezeEmailFlag) {
        this.userFreezeEmailFlag = userFreezeEmailFlag;
    }

    public boolean isUserFreezeInternalSmsFlag() {
        return userFreezeInternalSmsFlag;
    }

    public void setUserFreezeInternalSmsFlag(boolean userFreezeInternalSmsFlag) {
        this.userFreezeInternalSmsFlag = userFreezeInternalSmsFlag;
    }

    public boolean isUserFreezeSmsFlag() {
        return userFreezeSmsFlag;
    }

    public void setUserFreezeSmsFlag(boolean userFreezeSmsFlag) {
        this.userFreezeSmsFlag = userFreezeSmsFlag;
    }

    public boolean isSendVerifyCodeEmailFlag() {
        return sendVerifyCodeEmailFlag;
    }

    public void setSendVerifyCodeEmailFlag(boolean sendVerifyCodeEmailFlag) {
        this.sendVerifyCodeEmailFlag = sendVerifyCodeEmailFlag;
    }

    public boolean isSendVerifyCodeInternalSmsFlag() {
        return sendVerifyCodeInternalSmsFlag;
    }

    public void setSendVerifyCodeInternalSmsFlag(boolean sendVerifyCodeInternalSmsFlag) {
        this.sendVerifyCodeInternalSmsFlag = sendVerifyCodeInternalSmsFlag;
    }

    public boolean isSendVerifyCodeSmsFlag() {
        return sendVerifyCodeSmsFlag;
    }

    public void setSendVerifyCodeSmsFlag(boolean sendVerifyCodeSmsFlag) {
        this.sendVerifyCodeSmsFlag = sendVerifyCodeSmsFlag;
    }

    public boolean isAddUserEmailFlag() {
        return addUserEmailFlag;
    }

    public void setAddUserEmailFlag(boolean addUserEmailFlag) {
        this.addUserEmailFlag = addUserEmailFlag;
    }

    public boolean isAddUserInternalSmsFlag() {
        return addUserInternalSmsFlag;
    }

    public void setAddUserInternalSmsFlag(boolean addUserInternalSmsFlag) {
        this.addUserInternalSmsFlag = addUserInternalSmsFlag;
    }

    public boolean isAddUserSmsFlag() {
        return addUserSmsFlag;
    }

    public void setAddUserSmsFlag(boolean addUserSmsFlag) {
        this.addUserSmsFlag = addUserSmsFlag;
    }

    public boolean isUserActivateEmailFlag() {
        return userActivateEmailFlag;
    }

    public void setUserActivateEmailFlag(boolean userActivateEmailFlag) {
        this.userActivateEmailFlag = userActivateEmailFlag;
    }

    public boolean isUserActivateInternalSmsFlag() {
        return userActivateInternalSmsFlag;
    }

    public void setUserActivateInternalSmsFlag(boolean userActivateInternalSmsFlag) {
        this.userActivateInternalSmsFlag = userActivateInternalSmsFlag;
    }

    public boolean isUserActivateSmsFlag() {
        return userActivateSmsFlag;
    }

    public void setUserActivateSmsFlag(boolean userActivateSmsFlag) {
        this.userActivateSmsFlag = userActivateSmsFlag;
    }

    public boolean isResetPwdEmailFlag() {
        return resetPwdEmailFlag;
    }

    public void setResetPwdEmailFlag(boolean resetPwdEmailFlag) {
        this.resetPwdEmailFlag = resetPwdEmailFlag;
    }

    public boolean isResetPwdInternalSmsFlag() {
        return resetPwdInternalSmsFlag;
    }

    public void setResetPwdInternalSmsFlag(boolean resetPwdInternalSmsFlag) {
        this.resetPwdInternalSmsFlag = resetPwdInternalSmsFlag;
    }

    public boolean isResetPwdSmsFlag() {
        return resetPwdSmsFlag;
    }

    public void setResetPwdSmsFlag(boolean resetPwdSmsFlag) {
        this.resetPwdSmsFlag = resetPwdSmsFlag;
    }

    public boolean isDeptChangeEmailFlag() {
        return deptChangeEmailFlag;
    }

    public void setDeptChangeEmailFlag(boolean deptChangeEmailFlag) {
        this.deptChangeEmailFlag = deptChangeEmailFlag;
    }

    public boolean isDeptChangeInternalSmsFlag() {
        return deptChangeInternalSmsFlag;
    }

    public void setDeptChangeInternalSmsFlag(boolean deptChangeInternalSmsFlag) {
        this.deptChangeInternalSmsFlag = deptChangeInternalSmsFlag;
    }

    public boolean isDeptChangeSmsFlag() {
        return deptChangeSmsFlag;
    }

    public void setDeptChangeSmsFlag(boolean deptChangeSmsFlag) {
        this.deptChangeSmsFlag = deptChangeSmsFlag;
    }

    public boolean isFindPwdApplyEmailFlag() {
        return findPwdApplyEmailFlag;
    }

    public void setFindPwdApplyEmailFlag(boolean findPwdApplyEmailFlag) {
        this.findPwdApplyEmailFlag = findPwdApplyEmailFlag;
    }

    public boolean isFindPwdApplyInternalSmsFlag() {
        return findPwdApplyInternalSmsFlag;
    }

    public void setFindPwdApplyInternalSmsFlag(boolean findPwdApplyInternalSmsFlag) {
        this.findPwdApplyInternalSmsFlag = findPwdApplyInternalSmsFlag;
    }

    public boolean isFindPwdApplySmsFlag() {
        return findPwdApplySmsFlag;
    }

    public void setFindPwdApplySmsFlag(boolean findPwdApplySmsFlag) {
        this.findPwdApplySmsFlag = findPwdApplySmsFlag;
    }

    public boolean isRetrievePwdEmailFlag() {
        return retrievePwdEmailFlag;
    }

    public void setRetrievePwdEmailFlag(boolean retrievePwdEmailFlag) {
        this.retrievePwdEmailFlag = retrievePwdEmailFlag;
    }

    public boolean isRetrievePwdInternalSmsFlag() {
        return retrievePwdInternalSmsFlag;
    }

    public void setRetrievePwdInternalSmsFlag(boolean retrievePwdInternalSmsFlag) {
        this.retrievePwdInternalSmsFlag = retrievePwdInternalSmsFlag;
    }

    public boolean isRetrievePwdSmsFlag() {
        return retrievePwdSmsFlag;
    }

    public void setRetrievePwdSmsFlag(boolean retrievePwdSmsFlag) {
        this.retrievePwdSmsFlag = retrievePwdSmsFlag;
    }

    public boolean isUserRegApprovalFlag() {
        return userRegApprovalFlag;
    }

    public void setUserRegApprovalFlag(boolean userRegApprovalFlag) {
        this.userRegApprovalFlag = userRegApprovalFlag;
    }

    public Integer getOrgTreeShowLevel() {
        return orgTreeShowLevel;
    }

    public void setOrgTreeShowLevel(Integer orgTreeShowLevel) {
        this.orgTreeShowLevel = orgTreeShowLevel;
    }

    public boolean isGroupJoinReqAcceptInternalSmsFlag() {
        return groupJoinReqAcceptInternalSmsFlag;
    }

    public void setGroupJoinReqAcceptInternalSmsFlag(boolean groupJoinReqAcceptInternalSmsFlag) {
        this.groupJoinReqAcceptInternalSmsFlag = groupJoinReqAcceptInternalSmsFlag;
    }

    public boolean isGroupJoinReqAcceptSmsFlag() {
        return groupJoinReqAcceptSmsFlag;
    }

    public void setGroupJoinReqAcceptSmsFlag(boolean groupJoinReqAcceptSmsFlag) {
        this.groupJoinReqAcceptSmsFlag = groupJoinReqAcceptSmsFlag;
    }

    public boolean isGroupJoinReqAcceptEmailFlag() {
        return groupJoinReqAcceptEmailFlag;
    }

    public void setGroupJoinReqAcceptEmailFlag(boolean groupJoinReqAcceptEmailFlag) {
        this.groupJoinReqAcceptEmailFlag = groupJoinReqAcceptEmailFlag;
    }

    public boolean isGroupJoinReqDenialInternalSmsFlag() {
        return groupJoinReqDenialInternalSmsFlag;
    }

    public void setGroupJoinReqDenialInternalSmsFlag(boolean groupJoinReqDenialInternalSmsFlag) {
        this.groupJoinReqDenialInternalSmsFlag = groupJoinReqDenialInternalSmsFlag;
    }

    public boolean isGroupJoinReqDenialSmsFlag() {
        return groupJoinReqDenialSmsFlag;
    }

    public void setGroupJoinReqDenialSmsFlag(boolean groupJoinReqDenialSmsFlag) {
        this.groupJoinReqDenialSmsFlag = groupJoinReqDenialSmsFlag;
    }

    public boolean isGroupJoinReqDenialEmailFlag() {
        return groupJoinReqDenialEmailFlag;
    }

    public void setGroupJoinReqDenialEmailFlag(boolean groupJoinReqDenialEmailFlag) {
        this.groupJoinReqDenialEmailFlag = groupJoinReqDenialEmailFlag;
    }
}
