package com.tbc.elf.app.uc.model;

import com.tbc.elf.base.model.BaseModel;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 群组申请
 *
 * @author ELF@TEAM
 * @since 2016年2月23日17:03:00
 */
@Entity
@Table(name = "t_uc_group_join_req")
public class GroupJoinReq extends BaseModel{
    /**
     * 主键
     */
    @Id
    @Column(nullable = false, length = 32)
    @GeneratedValue(generator = "hibernate-uuid")
    @GenericGenerator(name = "hibernate-uuid", strategy = "uuid")
    private String groupJoinReqId;

    /**
     * 申请人的用户id
     */
    @Column(nullable = false, length = 32)
    private String userId;

    /**
     * 申请备注信息
     */
    @Column(length = 1300)
    private String reqComment;

    /**
     * 申请的群组id
     */
    @Column(nullable = false, length = 32)
    private String groupId;

    /**
     * 申请的状态
     */
    @Column(nullable = false, length = 50)
    private String status;

    /**
     * 拒绝备注
     */
    @Column(length = 1300)
    private String denialComment;

    public String getGroupJoinReqId() {
        return groupJoinReqId;
    }

    public void setGroupJoinReqId(String groupJoinReqId) {
        this.groupJoinReqId = groupJoinReqId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReqComment() {
        return reqComment;
    }

    public void setReqComment(String reqComment) {
        this.reqComment = reqComment;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDenialComment() {
        return denialComment;
    }

    public void setDenialComment(String denialComment) {
        this.denialComment = denialComment;
    }
}
