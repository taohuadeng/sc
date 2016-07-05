package com.tbc.elf.app.uc.service;

import com.tbc.elf.app.uc.model.GroupJoinReq;
import com.tbc.elf.base.service.BaseService;
import com.tbc.elf.base.util.Page;

import java.util.List;

/**
 * 群组申请服务
 *
 * @author ELF@TEAM
 * @since 2016-2-29
 */
public interface GroupJoinReqService extends BaseService<GroupJoinReq> {
    /**
     * 分页查看群组申请
     *
     * @param page 分页
     * @return 查询到的群组分页信息
     */
    Page<GroupJoinReq> list(Page<GroupJoinReq> page);

    /**
     * 批量保存
     *
     * @param groupJoinReqList 群组申请列表
     */
    void save(List<GroupJoinReq> groupJoinReqList);

    /**
     * 请求加入人员总数
     *
     * @param groupId 群组id
     * @return 请求加入人员总数
     */
    Integer groupReqCount(String groupId);

    /**
     * 群组请求加入人员ID
     *
     * @param groupId 群组id
     * @return 请求加入的人员id列表
     */
    List<String> fetchGroupReqUserIds(String groupId);

    /**
     * 根据群组ID，获得群组请求加入ID
     *
     * @param groupId 群组id
     * @param status 群组状态
     */
    List<String> fetchGroupReqIds(String groupId, String status);

    /**
     * 请求加入
     *
     * @param groupId 群组id
     * @param userId 用户id
     * @param reqComment 请求备注
     */
    void groupReq(String groupId, String userId, String reqComment);

    /**
     * 通过加入请求
     * 仅更新原状态为“UcConstant.GROUP_JOIN_REQ_TYPE_REQUEST”的数据
     *
     * @param groupId 群组id
     * @return 影响记录条数
     */
    boolean groupReqAccept(String groupId, String userId);

    /**
     * 通过加入请求
     * 更新所有状态的数据
     *
     * @param groupId 群组id
     * @return 影响记录条数
     */
    boolean groupReqAcceptForAllStatus(String groupId, List<String> userIdList);
}
