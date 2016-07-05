package com.tbc.elf.app.uc.service;

import com.tbc.elf.app.uc.model.Group;
import com.tbc.elf.app.uc.model.GroupMember;
import com.tbc.elf.base.security.util.AuthenticationUtil;
import com.tbc.elf.base.service.BaseServiceImpl;
import com.tbc.elf.base.util.HqlBuilder;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * @author YangLiBo@HF
 * @since 2016年02月29日18:10:27
 */
@Service("groupMemberService")
public class GroupMemberServiceImpl extends BaseServiceImpl<GroupMember> implements GroupMemberService {

    @Override
    public void addGroupMember(String groupId, List<String> userIdList) {
        Assert.hasText(groupId, "GroupId must not be blank!");
        Assert.notEmpty(userIdList, "UserIdList must be not empty");
        GroupMember groupMember = new GroupMember();
        groupMember.setGroupId(groupId);
        addGroupMember(groupMember, userIdList);
    }

    @Override
    public void addGroupMember(String groupId, List<String> userIdList, GroupMember.MemberSource memberSource) {
        Assert.hasText(groupId, "GroupId must not be blank!");
        Assert.notEmpty(userIdList, "UserIdList must be not empty");
        GroupMember groupMember = new GroupMember();
        groupMember.setGroupId(groupId);
        groupMember.setMemberSource(memberSource);
        addGroupMember(groupMember, userIdList);
    }

    @Override
    public void addGroupMember(String groupId, String userId) {
        GroupMember groupMember = new GroupMember();
        groupMember.setGroupId(groupId);
        List<String> userIdList = new ArrayList<String>(1);
        userIdList.add(userId);
        addGroupMember(groupMember, userIdList);
    }

    @Override
    public void updateMemberSource(String groupId, String memberSource) {

    }

    public void setAdmin(String groupId, List<String> userIds, boolean isAdmin) {
        Assert.hasText(groupId, "GroupId must not be blank!");
        Assert.notEmpty(userIds, "UserIds must be not empty");
        GroupMember groupMember = new GroupMember();
        groupMember.setGroupId(groupId);
        groupMember.setAdmin(isAdmin);
        updateGroupMember(groupMember, userIds);
    }

    public void setOwner(String groupId, List<String> userIds, boolean isOwner) {
        Assert.hasText(groupId, "GroupId must not be blank!");
        Assert.notEmpty(userIds, "UserIds must be not empty");
        GroupMember groupMember = new GroupMember();
        groupMember.setGroupId(groupId);
        groupMember.setOwner(isOwner);
        updateGroupMember(groupMember, userIds);
    }

    private void updateGroupMember(GroupMember groupMember, List<String> userIds) {
        String groupId = groupMember.getGroupId();
        HqlBuilder builder = new HqlBuilder();
        builder.append("update GroupMember ");
        boolean flag = false;
        if (groupMember.getOwner() != null) {
            builder.append("set  owner = ?", groupMember.getOwner());
            flag = true;
        }

        if (groupMember.isAdmin() && flag) {
            builder.append(", admin = ?", groupMember.getOwner());
        } else if (groupMember.getOwner() != null) {
            builder.append("set admin = ?", groupMember.getOwner());
        }

        builder.append(" where corpCode = ? ");
        builder.addParameter(AuthenticationUtil.getCorpCode());
        builder.append(" and referId in (:userIds)");
        builder.addParameter("userIds", userIds);
        builder.append(" and groupId = ? ");
        builder.addParameter(groupId);
        builder.append(" and referType = ? ");
        builder.addParameter(GroupMember.ReferType.USER);
        baseService.executeUpdate(builder);
    }

    private void addGroupMember(GroupMember groupMember, List<String> userIdList) {
        String groupId = groupMember.getGroupId();
        GroupMember.MemberSource memberSource = groupMember.getMemberSource();
        if (memberSource == null) {
            memberSource = GroupMember.MemberSource.APPOINT;
        }

        List<GroupMember> groupMemberList = new ArrayList<GroupMember>(userIdList.size());
        for (String userId : userIdList) {
            GroupMember member = new GroupMember();
            member.setCorpCode(AuthenticationUtil.getCorpCode());
            member.setGroupId(groupId);
            member.setReferId(userId);
            member.setReferType(GroupMember.ReferType.USER);
            member.setMemberSource(memberSource);
            groupMemberList.add(member);
        }

        baseService.saveOrUpdateEntityList(groupMemberList);

    }
}
