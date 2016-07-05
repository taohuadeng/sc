package com.tbc.elf.app.uc.model;

import com.tbc.elf.base.model.BaseModel;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * 系统权限表
 *
 * @author ELF@TEAM
 * @since 2016年2月23日17:03:00
 */
@Entity
@Table(name = "t_elf_user")
public class ELFUser extends BaseModel {
    /**
     * 用户性别类型
     */
    public enum SexType {
        MALE("男"), FEMALE("女"), SECRECY("保密");
        private final String text;

        private SexType(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }

    /**
     * 账户状态
     */
    public enum AccountStatus {
        UNAPPROVED("未审批"), ENABLE("启用"), FORBIDDEN("禁用");

        private final String text;

        private AccountStatus(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }

    /**
     * 主键
     */
    @Id
    @Column(nullable = false, length = 32)
    @GeneratedValue(generator = "hibernate-uuid")
    @GenericGenerator(name = "hibernate-uuid", strategy = "uuid")
    private String userId;

    /**
     * 姓名
     */
    @Column(nullable = false, length = 50)
    private String userName;

    /**
     * 角色类型
     */
    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private SexType sex;

    /**
     * 工号
     */
    @Column(nullable = false, length = 50)
    private String employeeCode;

    @ManyToOne
    @Cascade(value = org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JoinColumn(name = "organization_id")
    private Organization organization;

    /**
     * 显示顺序
     */
    @Column(nullable = false)
    private double showOrder;

    /**
     * 岗位ID
     */
    @Column(length = 32)
    private String positionId;

    /**
     * 岗位名称
     */
    @Column(length = 50)
    private String positionName;

    /**
     * 职级
     */
    @Column(length = 10)
    private String dutyLevel;

    /**
     * 职务名称
     */
    @Column(length = 50)
    private String dutyName;

    /**
     * 隶属上级ID
     */
    @Column(length = 32)
    private String superiorId;

    /**
     * 用户状态
     */
    @Column(nullable = false, length = 10)
    private AccountStatus accountStatus;

    /**
     * 帐号过期时间,
     * 为空时表示该公司无学员过期设置
     * 或者该学员不会自动过期,公司admin账户该字段为空
     */
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date expireTime;

    /**
     * 身份证号码
     */
    @Column(length = 20)
    private String idCard;

    /**
     * 手机号码
     */
    @Column(length = 20)
    private String mobile;

    /**
     * 邮箱
     */
    @Column(length = 50)
    private String email;

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

    public SexType getSex() {
        return sex;
    }

    public void setSex(SexType sex) {
        this.sex = sex;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public double getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(double showOrder) {
        this.showOrder = showOrder;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getDutyLevel() {
        return dutyLevel;
    }

    public void setDutyLevel(String dutyLevel) {
        this.dutyLevel = dutyLevel;
    }

    public String getDutyName() {
        return dutyName;
    }

    public void setDutyName(String dutyName) {
        this.dutyName = dutyName;
    }

    public String getSuperiorId() {
        return superiorId;
    }

    public void setSuperiorId(String superiorId) {
        this.superiorId = superiorId;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }
}
