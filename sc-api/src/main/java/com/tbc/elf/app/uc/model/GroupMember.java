package com.tbc.elf.app.uc.model;

import com.tbc.elf.base.model.BaseModel;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 群组成员实体
 *
 * @author ELF@TEAM
 * @since 2016年2月23日17:03:00
 */
@Entity
@Table(name = "t_uc_group_member")
public class GroupMember extends BaseModel {

    public enum ReferType {
        ORGANIZATION("ORGANIZATION"), POSITION("POSITION"), USER("USER");
        private final String text;

        private ReferType(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }

    public enum MemberSource {
        DYNAMIC("DYNAMIC"), APPOINT("APPOINT");
        private final String text;

        private MemberSource(String text) {
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
    private String groupMemberId;

    /**
     * 群主键
     */
    @Column(nullable = false, length = 32)
    private String groupId;

    /**
     * 关联的id，根据类型
     */
    @Column(nullable = false, length = 32)
    private String referId;

    /**
     * 关联的类型, ORGANIZATION:组织,POSITION:岗位,USER:用户
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ReferType referType;
    /**
     * 来源
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private MemberSource memberSource;

    /**
     * 如果referType是岗位就在这个列中加这个岗位所在的部门ID
     */
    @Column(length = 32)
    private String referOfOrgId;

    /**
     * 是否为系统管理员
     */
    @Column(nullable = false)
    private boolean admin;

    /**
     * 是否为群主
     */
    @Column(nullable = false)
    private Boolean owner;

    public String getGroupMemberId() {
        return groupMemberId;
    }

    public void setGroupMemberId(String groupMemberId) {
        this.groupMemberId = groupMemberId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getReferId() {
        return referId;
    }

    public void setReferId(String referId) {
        this.referId = referId;
    }

    public ReferType getReferType() {
        return referType;
    }

    public void setReferType(ReferType referType) {
        this.referType = referType;
    }

    public MemberSource getMemberSource() {
        return memberSource;
    }

    public void setMemberSource(MemberSource memberSource) {
        this.memberSource = memberSource;
    }

    public String getReferOfOrgId() {
        return referOfOrgId;
    }

    public void setReferOfOrgId(String referOfOrgId) {
        this.referOfOrgId = referOfOrgId;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public Boolean getOwner() {
        return owner;
    }

    public void setOwner(Boolean owner) {
        this.owner = owner;
    }
}
