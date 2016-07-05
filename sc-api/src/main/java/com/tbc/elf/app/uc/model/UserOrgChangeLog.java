package com.tbc.elf.app.uc.model;

import com.tbc.elf.base.model.BaseModel;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 用户部门更改日志表
 *
 * @author ELF@TEAM
 * @since 2016年2月23日17:03:00
 */
@Entity
@Table(name = "t_uc_user_org_change_log")
public class UserOrgChangeLog extends BaseModel {

    /**
     * 主键
     */
    @Id
    @Column(nullable = false, length = 32)
    @GeneratedValue(generator = "hibernate-uuid")
    @GenericGenerator(name = "hibernate-uuid", strategy = "uuid")
    private String userOrgChangeId;

    /**
     * 关联类型
     */
    @Column(length = 20)
    private String referType;

    /**
     * 原关联Id
     */
    @Column(nullable = false, length = 32)
    private String oldReferId;

    /**
     * 现关联Id
     */
    @Column(nullable = false, length = 32)
    private String curReferId;

    /**
     * 用户Id
     */
    @Column(nullable = false, length = 32)
    private String userId;

    /**
     * 操作类型
     */
    @Column(length = 32)
    private String optType;

    public String getUserOrgChangeId() {
        return userOrgChangeId;
    }

    public void setUserOrgChangeId(String userOrgChangeId) {
        this.userOrgChangeId = userOrgChangeId;
    }

    public String getReferType() {
        return referType;
    }

    public void setReferType(String referType) {
        this.referType = referType;
    }

    public String getOldReferId() {
        return oldReferId;
    }

    public void setOldReferId(String oldReferId) {
        this.oldReferId = oldReferId;
    }

    public String getCurReferId() {
        return curReferId;
    }

    public void setCurReferId(String curReferId) {
        this.curReferId = curReferId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOptType() {
        return optType;
    }

    public void setOptType(String optType) {
        this.optType = optType;
    }
}
