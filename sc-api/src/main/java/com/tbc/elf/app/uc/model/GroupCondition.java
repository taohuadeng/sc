package com.tbc.elf.app.uc.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 动态群组条件
 *
 * @author ELF@TEAM
 * @since 2016年2月23日17:03:00
 */
@Entity
@Table(name = "t_uc_group_condition")
public class GroupCondition {
    /**
     * 主键
     */
    @Id
    @Column(nullable = false, length = 32)
    @GeneratedValue(generator = "hibernate-uuid")
    @GenericGenerator(name = "hibernate-uuid", strategy = "uuid")
    private String groupConditionId;

    /**
     * 群组ID
     */
    @Column(nullable = false, length = 32)
    private String groupId;

    /**
     * 条件区域
     */
    @Column(length = 10)
    private String conditionCode;

    /**
     * 条件类型（ORGANIZATION-部门  POSITION-岗位  DUTYLEVEL-职级  RANK-级别  BIRTHDAY-出生日期）
     */
    @Column(length = 20)
    private String conditionType;

    /**
     * 条件值
     */
    @Column(length = 1300)
    private String conditionValue;

    public String getGroupConditionId() {
        return groupConditionId;
    }

    public void setGroupConditionId(String groupConditionId) {
        this.groupConditionId = groupConditionId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getConditionCode() {
        return conditionCode;
    }

    public void setConditionCode(String conditionCode) {
        this.conditionCode = conditionCode;
    }

    public String getConditionType() {
        return conditionType;
    }

    public void setConditionType(String conditionType) {
        this.conditionType = conditionType;
    }

    public String getConditionValue() {
        return conditionValue;
    }

    public void setConditionValue(String conditionValue) {
        this.conditionValue = conditionValue;
    }
}
