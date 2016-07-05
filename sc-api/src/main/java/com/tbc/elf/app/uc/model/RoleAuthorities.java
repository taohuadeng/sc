package com.tbc.elf.app.uc.model;

import com.tbc.elf.base.model.BaseModel;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * 系统角色权限关联表
 *
 * @author TaoFaDeng@ELF@TEAM
 * @since 2016年2月23日17:03:00
 */
//@Entity
@Table(name = "t_uc_role_authorities")
public class RoleAuthorities extends BaseModel {
    /**
     * 主键
     */
    @Id
    @Column(nullable = false, length = 32)
    @GeneratedValue(generator = "hibernate-uuid")
    @GenericGenerator(name = "hibernate-uuid", strategy = "uuid")
    private String roleAuthoritiesId;

    /**
     * 角色ID
     */
    @Column(nullable = false, length = 32)
    private String roleId;

    /**
     * 权限ID
     */
    @Column(nullable = false, length = 32)
    private String authorityId;

    @Transient
    private Authority authority;

    public String getRoleAuthoritiesId() {
        return roleAuthoritiesId;
    }

    public void setRoleAuthoritiesId(String roleAuthoritiesId) {
        this.roleAuthoritiesId = roleAuthoritiesId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(String authorityId) {
        this.authorityId = authorityId;
    }

    public Authority getAuthority() {
        return authority;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }
}
