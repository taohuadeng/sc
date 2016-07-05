package com.tbc.elf.app.uc.service;


import com.tbc.elf.app.uc.model.Group;
import com.tbc.elf.app.uc.model.GroupJoinReq;
import com.tbc.elf.app.uc.model.GroupMember;
import com.tbc.elf.base.security.util.AuthenticationUtil;
import com.tbc.elf.base.service.BaseServiceImpl;
import com.tbc.elf.base.util.ElfConstant;
import com.tbc.elf.base.util.HqlBuilder;
import com.tbc.elf.base.util.Page;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("groupJoinReqService")
public class GroupJoinReqServiceImpl extends BaseServiceImpl<GroupJoinReq> implements GroupJoinReqService{
    @Resource
    private GroupService groupService;
    @Resource
    private GroupMemberService groupMemberService;

    @Override
    @Transactional(readOnly = true,isolation = Isolation.READ_COMMITTED)
    public Page<GroupJoinReq> list(Page<GroupJoinReq> page) {
        HqlBuilder builder = new HqlBuilder();
        if (page.isAutoCount()) {
            builder.append("SELECT count(*) FROM GroupJoinReq");
            builder.append("WHERE corpCode = ?", AuthenticationUtil.getCorpCode());
            Long count = baseService.queryUniqueResult(builder);
            if(count == null || count <= 0){
                return page;
            }

            page.setTotal(count);
        }

        builder.clear();
        builder.append("SELECT * FROM GroupJoinReq");
        builder.append("WHERE corpCode = ?", AuthenticationUtil.getCorpCode());
        if (StringUtils.isNotEmpty(page.getSortName())) {
            builder.append("ORDER BY").append(page.getSortName()).append(page.getSortOrder());
        }

        if (page.isAutoPaging()) {
            builder.setMaxRecordNum(page.getPageSize());
            builder.setFirstRecordIndex(page.getFirst() - 1);
        }

        List<GroupJoinReq> list = baseService.queryList(builder);
        page.setRows(list);
        return page;
    }

    @Override
    @Transactional(readOnly = false,isolation = Isolation.READ_COMMITTED)
    public void save(List<GroupJoinReq> groupJoinReqList) {
        if(CollectionUtils.isNotEmpty(groupJoinReqList)) {
            baseService.saveOrUpdateEntityList(groupJoinReqList);
        }
    }

    @Override
    @Transactional(readOnly = true,isolation = Isolation.READ_COMMITTED)
    public Integer groupReqCount(String groupId) {
        Assert.hasText(groupId,"GroupId is empty!");

        HqlBuilder builder = new HqlBuilder("SELECT count(*) FROM GroupJoinReq");
        builder.append("WHERE corpCode = ?",AuthenticationUtil.getCorpCode());
        builder.append("AND groupId = ?", groupId);
        builder.append("AND status = ?", ElfConstant.GROUP_JOIN_REQ_TYPE_REQUEST);
        Long count = baseService.queryUniqueResult(builder);
        if (count == null) {
            count = 0l;
        }
        return count.intValue();
    }

    @Override
    @Transactional(readOnly = true,isolation = Isolation.READ_COMMITTED)
    public List<String> fetchGroupReqUserIds(String groupId) {
        Assert.notNull(groupId, "groupId is not null");

        HqlBuilder builder = new HqlBuilder("SELECT userId FROM GroupJoinReq");
        builder.append("WHERE corpCode = ?",AuthenticationUtil.getCorpCode());
        builder.append("AND groupId = ?",groupId);
        builder.append("AND status = ?",ElfConstant.GROUP_JOIN_REQ_TYPE_REQUEST);
        return baseService.queryList(builder);
    }

    @Override
    @Transactional(readOnly = true,isolation = Isolation.READ_COMMITTED)
    public List<String> fetchGroupReqIds(String groupId, String status) {
        HqlBuilder builder = new HqlBuilder();
        builder.append("SELECT groupJoinReqId FROM GroupJoinReq");
        builder.append("WHERE corpCode = ?", AuthenticationUtil.getCorpCode());
        if(StringUtils.isNotEmpty(groupId)){
            builder.append("AND groupId = ?", groupId);
        }

        if(StringUtils.isNotEmpty(status)){
            builder.append("AND status = ?", status);
        }

        return baseService.queryList(builder);
    }

    @Override
    @Transactional(readOnly = false,isolation = Isolation.READ_COMMITTED)
    public void groupReq(String groupId, String userId, String reqComment) {
        Assert.notNull(groupId, "GroupId is not null!");
        Assert.notNull(userId, "UserId is not null!");

        Group group = groupService.load(groupId);
        if (group == null) {
            throw new RuntimeException("group not exist");
        } else if (!ElfConstant.GROUP_STATUS_ACTIVE.equalsIgnoreCase(String.valueOf(group.getStatus()))) {
            throw new RuntimeException("group status not active");
        }

        if (ElfConstant.VID_OPEN.equalsIgnoreCase(String.valueOf(group.getValidateType()))) { //开放，直接加入
            List<String> userIds = new ArrayList<String>();
            userIds.add(userId);
            String memberSource = ElfConstant.VID_DYNAMIC.equals(String.valueOf(group.getValidateType())) ? ElfConstant.VID_DYNAMIC : ElfConstant.VID_APPOINT;
            //groupMemberService.addGroupMember(groupId, userIds, memberSource);
        } else if (ElfConstant.VID_VALIDATE.equalsIgnoreCase(String.valueOf(group.getValidateType()))) { //验证，添加加入请求记录
            // 检查是否已存在
            HqlBuilder builder = new HqlBuilder("SELECT * FROM GroupJoinReq");
            builder.append("WHERE corpCode = ?", AuthenticationUtil.getCorpCode());
            builder.append("AND groupId = ?",groupId);
            builder.append("AND userId = ?", userId);
            List<GroupJoinReq> groupJoinReqList = baseService.queryList(builder);

            if (groupJoinReqList != null && groupJoinReqList.size() > 0) {
                for (GroupJoinReq entity : groupJoinReqList) {
                    if (StringUtils.isNotEmpty(entity.getStatus()) && ElfConstant.GROUP_JOIN_REQ_TYPE_REQUEST.equals(entity.getStatus())) {
                        throw new RuntimeException("request already exist");
                    } else {
                        delete(entity.getGroupJoinReqId());
                    }
                }
            }

            GroupJoinReq groupJoinReq = new GroupJoinReq();
            groupJoinReq.setGroupId(groupId);
            groupJoinReq.setUserId(userId);
            groupJoinReq.setReqComment(reqComment);
            groupJoinReq.setStatus(ElfConstant.GROUP_JOIN_REQ_TYPE_REQUEST);
            save(groupJoinReq);
        } else if (ElfConstant.VID_APPOINT.equalsIgnoreCase(String.valueOf(group.getValidateType()))
                || ElfConstant.VID_DYNAMIC.equalsIgnoreCase(String.valueOf(group.getValidateType()))) { //不开放、条件（动态），不允许请求加入
            throw new RuntimeException("Group not allow join!");
        }
    }

    @Override
    @Transactional(readOnly = false,isolation = Isolation.READ_COMMITTED)
    public boolean groupReqAccept(String groupId, String userId) {
        Assert.notNull(groupId, "groupId is not null");
        Assert.notNull(userId, "userId is not null");

        /**
         * 群人员表加入人员
         */
        try {
            groupMemberService.addGroupMember(groupId, userId);
        } catch (Exception e) {
            throw new RuntimeException("Add user to groupMember fail!");
        }

        /**
         * 更新请求状态
         */
        HqlBuilder builder = new HqlBuilder("UPDATE GroupJoinReq");
        builder.append("SET status= ?", ElfConstant.GROUP_JOIN_REQ_TYPE_ACCEPT);
        builder.append("WHERE corpCode = ?",AuthenticationUtil.getCorpCode());
        builder.append("AND groupId = ?", groupId);
        builder.append("AND userId = ?",userId);
        builder.append("AND status = ?", ElfConstant.GROUP_JOIN_REQ_TYPE_REQUEST);
        int count = baseService.executeUpdate(builder);
        return count > 0;
    }

    @Override
    @Transactional(readOnly = false,isolation = Isolation.READ_COMMITTED)
    public boolean groupReqAcceptForAllStatus(String groupId, List<String> userIdList) {
        Assert.notNull(groupId, "groupId is not null");
        Assert.notNull(userIdList, "userIdList is not null");

        /**
         * 群人员表加入人员
         */
        try {
            groupMemberService.addGroupMember(groupId, userIdList);
        } catch (Exception e) {
            throw new RuntimeException("Add user to groupMember fail!");
        }

        /**
         * 更新请求状态
         */
        HqlBuilder builder = new HqlBuilder("UPDATE GroupJoinReq");
        builder.append("SET status= ?",ElfConstant.GROUP_JOIN_REQ_TYPE_ACCEPT);
        builder.append("WHERE corpCode = ?",AuthenticationUtil.getCorpCode());
        builder.append("AND groupId = ?",groupId);
        builder.append("AND userId IN (:userIds)");
        builder.addParameter("userIds", userIdList);
        int count = baseService.executeUpdate(builder);
        return count > 0;
    }

}
