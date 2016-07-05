package com.tbc.elf.app.uc.model;

import com.tbc.elf.base.model.BaseModel;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 群组信息实体
 *
 * @author ELF@TEAM
 * @since 2016年2月23日17:03:00
 */
@Entity
@Table(name = "t_uc_group")
public class Group extends BaseModel {

    /**
     * 群组状态枚举
     */
    public enum GroupStatus {
        ACTIVE, INIT, INACTIVE;

        private String text;

        private GroupStatus() {
        }

        private GroupStatus(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }

    /**
     * 验证类型
     */
    public enum ValidateType {
        OPEN, DYNAMIC, APPOINT, VALIDATE;

        private String text;

        private ValidateType() {
        }

        private ValidateType(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }

    /**
     * 群组id
     */
    @Id
    @Column(nullable = false, length = 32)
    @GeneratedValue(generator = "hibernate-uuid")
    @GenericGenerator(name = "hibernate-uuid", strategy = "uuid")
    private String groupId;

    /**
     * 群组名称
     */
    @Column(nullable = false, length = 50)
    private String groupName;

    /**
     * 群组分类Id
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @Cascade(value = CascadeType.SAVE_UPDATE)
    private GroupCategory category;

    /**
     * 最大人数
     */
    @Column
    private int maxPersonLimit;

    /**
     * 当前人数
     */
    @Column
    private int currentPersonCount;

    /**
     * 显示顺序
     */
    @Column
    private double showOrder;

    /**
     * 过期时间
     */
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiredTime;

    /**
     * 群状态
     */
    @Column(nullable = false, length = 32)
    @Enumerated(EnumType.STRING)
    private GroupStatus status;

    /**
     * 群生效时间（在群作为班级时会使用）
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date startTime;

    /**
     * 备注说明
     */
    @Column(length = 50)
    private String comments;

    /**
     * 身份验证
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ValidateType validateType;

    /**
     * 群主
     */
    @Column(nullable = false, length = 32)
    private String owner;

    /**
     * 是否限制截止日期
     */
    @Column
    private boolean expireLimit;

    /**
     * 图标
     */
    @Column(length = 50)
    private String icon;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public GroupCategory getCategory() {
        return category;
    }

    public void setCategory(GroupCategory category) {
        this.category = category;
    }

    public int getMaxPersonLimit() {
        return maxPersonLimit;
    }

    public void setMaxPersonLimit(int maxPersonLimit) {
        this.maxPersonLimit = maxPersonLimit;
    }

    public int getCurrentPersonCount() {
        return currentPersonCount;
    }

    public void setCurrentPersonCount(int currentPersonCount) {
        this.currentPersonCount = currentPersonCount;
    }

    public double getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(double showOrder) {
        this.showOrder = showOrder;
    }

    public Date getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Date expiredTime) {
        this.expiredTime = expiredTime;
    }

    public GroupStatus getStatus() {
        return status;
    }

    public void setStatus(GroupStatus status) {
        this.status = status;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public ValidateType getValidateType() {
        return validateType;
    }

    public void setValidateType(ValidateType validateType) {
        this.validateType = validateType;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public boolean isExpireLimit() {
        return expireLimit;
    }

    public void setExpireLimit(boolean expireLimit) {
        this.expireLimit = expireLimit;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
