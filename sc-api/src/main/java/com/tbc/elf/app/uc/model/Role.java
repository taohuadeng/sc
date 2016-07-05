package com.tbc.elf.app.uc.model;

import com.tbc.elf.base.model.BaseModel;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

import static org.hibernate.annotations.CascadeType.*;

/**
 * 系统角色表
 *
 * @author TaoFaDeng@ELF@TEAM
 * @since 2016年2月23日17:03:00
 */
@Entity
@Table(name = "t_uc_role",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"corpCode", "roleName"})})
@DynamicUpdate(true)
public class Role extends BaseModel {
    /**
     * 角色类型
     */
    public enum RoleType {
        SYSTEM("系统角色"), CUSTOM("自定义角色");
        private final String text;

        private RoleType(String text) {
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
    private String roleId;

    /**
     * 角色名称
     */
    @Column(nullable = false, length = 50)
    private String roleName;

    /**
     * 角色描述
     */
    @Column(nullable = false, length = 50)
    private String comments;

    /**
     * 角色类型
     */
    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "t_uc_role_authorities",
            joinColumns = {@JoinColumn(name = "role_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_id")})
    @Cascade(value = {SAVE_UPDATE})
    @Fetch(FetchMode.SELECT)
    public List<Authority> authorities = new ArrayList<Authority>();

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }

    public void removeAuthority(Authority authority) {
        if (authority != null) {
            authority.getRoles().remove(this);
            this.getAuthorities().remove(authority);
        }
    }

    public void clearAuthorities() {
        for (Authority authority : this.getAuthorities()) {
            authority.getRoles().remove(this);
        }

        this.getAuthorities().clear();
    }
}
