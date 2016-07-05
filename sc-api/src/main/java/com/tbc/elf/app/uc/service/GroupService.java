package com.tbc.elf.app.uc.service;

import com.tbc.elf.app.uc.model.Group;
import com.tbc.elf.base.service.BaseService;

/**
 * 群组业务逻辑接口
 *
 * @author YangLiBo@HF
 * @since 2016年02月29日18:02:23
 */
public interface GroupService extends BaseService<Group> {

    /**
     * 删除群组, 同时删除t_uc_group_member，t_uc_gen_group_role，t_uc_user_gen_group_role关联信息
     *
     * @param groupId 群组Id
     * @author 付东明
     */
    void removeUserGroup(String groupId);
}
