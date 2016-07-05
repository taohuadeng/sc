package com.tbc.elf.app.uc.service;

import com.tbc.elf.app.uc.model.Group;
import com.tbc.elf.app.uc.model.OrganizationRel;
import com.tbc.elf.base.service.BaseServiceImpl;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * 群组业务逻辑实现类
 *
 * @author YangLiBo@HF
 * @since 2016年02月29日18:03:28
 */
@Service("groupService")
public class GroupServiceImpl extends BaseServiceImpl<Group> implements GroupService {


    @Override
    public void removeUserGroup(String groupId) {
        Assert.hasText(groupId, "GroupId must be not blank");
        //need
    }
}
