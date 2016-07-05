package com.tbc.elf.app.uc.model;

import com.tbc.elf.base.model.BaseModel;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 部门-子部门关系表
 *
 * @author YangLiBo@HF
 * @since 2016年02月26日13:43:38
 */
@Entity
@Table(name = "t_uc_organization_rel")
public class OrganizationRel extends BaseModel {
    /**
     * 主键
     */
    @Id
    @Column(nullable = false, length = 32)
    @GeneratedValue(generator = "hibernate-uuid")
    @GenericGenerator(name = "hibernate-uuid", strategy = "uuid")
    private String OrganizationRelId;

    /**
     * 部门id
     */
    @Column(nullable = false, length = 32)
    private String organizationId;
    /**
     * 子部门的部门id
     */

    @Column(nullable = false, length = 32)
    private String childId;

    /**
     * 部门名称
     */
    @Column(length = 50)
    private String organizationName;

    /**
     * 部门层级
     */
    @Column(nullable = false)
    private int organizationLevel;

    public String getOrganizationRelId() {
        return OrganizationRelId;
    }

    public void setOrganizationRelId(String organizationRelId) {
        OrganizationRelId = organizationRelId;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public int getOrganizationLevel() {
        return organizationLevel;
    }

    public void setOrganizationLevel(int organizationLevel) {
        this.organizationLevel = organizationLevel;
    }
}
