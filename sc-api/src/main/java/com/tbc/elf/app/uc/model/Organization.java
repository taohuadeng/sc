package com.tbc.elf.app.uc.model;

import com.tbc.elf.base.model.BaseModel;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户组织实体
 *
 * @author TaoFaDeng@HF
 * @since 2016年2月23日16:00:37
 */
@Entity
@Table(name = "t_uc_organization")
public class Organization extends BaseModel {
    /**
     * 主键
     */
    @Id
    @Column(nullable = false, length = 32)
    @GeneratedValue(generator = "hibernate-uuid")
    @GenericGenerator(name = "hibernate-uuid", strategy = "uuid")
    private String organizationId;

    /**
     * 组织编号
     */
    @Column(length = 50)
    private String organizationCode;

    /**
     * 组织名称
     */
    @Column(length = 50, nullable = false)
    private String organizationName;

    /**
     * 组织名称路径
     */
    @Column(length = 200, nullable = false)
    private String namePath;

    /**
     * 组织名称
     */
    @Column(length = 50, nullable = false)
    private String comments;

    /**
     * 最大账户数
     */
    @Column
    private int maxAccountNum;

    /**
     * 组织排序
     */
    @Column
    private double showOrder;

    /**
     * 组织下当前人数总数
     */
    @Column
    private int currentAccountNum;

    /**
     * 是否限制组织人数
     */
    @Column
    private boolean needLimitAccount;

    /**
     * 组织负责人ID
     */
    @Column(length = 32)
    private String manageUserId;

    /**
     * 父组织ID
     */
    @Column(nullable = false, length = 32)
    private String parentId;

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getNamePath() {
        return namePath;
    }

    public void setNamePath(String namePath) {
        this.namePath = namePath;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getMaxAccountNum() {
        return maxAccountNum;
    }

    public void setMaxAccountNum(int maxAccountNum) {
        this.maxAccountNum = maxAccountNum;
    }

    public double getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(double showOrder) {
        this.showOrder = showOrder;
    }

    public int getCurrentAccountNum() {
        return currentAccountNum;
    }

    public void setCurrentAccountNum(int currentAccountNum) {
        this.currentAccountNum = currentAccountNum;
    }

    public boolean isNeedLimitAccount() {
        return needLimitAccount;
    }

    public void setNeedLimitAccount(boolean needLimitAccount) {
        this.needLimitAccount = needLimitAccount;
    }

    public String isManageUserId() {
        return manageUserId;
    }

    public void setManageUserId(String manageUserId) {
        this.manageUserId = manageUserId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
