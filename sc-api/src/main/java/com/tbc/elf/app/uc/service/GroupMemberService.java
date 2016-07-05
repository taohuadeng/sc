package com.tbc.elf.app.uc.service;

import com.tbc.elf.app.uc.model.GroupMember;
import com.tbc.elf.base.service.BaseService;

import java.util.List;

/**
 * 群组成员业务逻辑接口
 *
 * @author YangLiBo@HF
 * @since 2016年02月29日18:09:42
 */
public interface GroupMemberService extends BaseService<GroupMember> {

    /**
     * 添加群成员
     *
     * @param groupId:群组id
     * @param userIdList:要添加的人员id列表
     * @throws java.lang.IllegalArgumentException <ul>
     *                                            <li>
     *                                            当groupId为空时，抛出IllegalArgumentException
     *                                            </li>
     *                                            <li>
     *                                            当userIds为null或长度为0时，抛出IllegalArgumentException
     *                                            </li>
     *                                            </ul>
     */
    void addGroupMember(String groupId, List<String> userIdList);

    /**
     * 根据成员类型添加群成员
     *
     * @param groupId:群组id
     * @param userIdList:要添加的人员id列表
     * @param memberSource:成员类型
     * @throws java.lang.IllegalArgumentException <ul>
     *                                            <li>
     *                                            当groupId为空时，抛出IllegalArgumentException
     *                                            </li>
     *                                            <li>
     *                                            当userIds为null或长度为0时，抛出IllegalArgumentException
     *                                            </li>
     *                                            <li>
     *                                            当memberSource为空时，抛出IllegalArgumentException
     *                                            </li>
     *                                            </ul>
     */
    void addGroupMember(String groupId, List<String> userIdList, GroupMember.MemberSource memberSource);

    /**
     * 添加群成员-单个人员
     *
     * @param groupId:群组id
     * @param userId:要添加的人员id
     * @throws java.lang.IllegalArgumentException <ul>
     *                                            <li>
     *                                            当groupId为空时，抛出IllegalArgumentException
     *                                            </li>
     *                                            <li>
     *                                            当userIds为null或长度为0时，抛出IllegalArgumentException
     *                                            </li>
     *                                            </ul>
     */
    void addGroupMember(String groupId, String userId);

    void updateMemberSource(String groupId, String memberSource);

    /**
     * 设置为群管理员
     *
     * @param groupId:群组id
     * @param userIds:待设置管理员的用户id列表
     * @param isAdmin:为true时，表示为管理员，否则为非管理员
     * @throws java.lang.IllegalArgumentException <ul>
     *                                            <li>
     *                                            当groupId为空时，抛出IllegalArgumentException
     *                                            </li>
     *                                            <li>
     *                                            当userIds为null或长度为0时，抛出IllegalArgumentException
     *                                            </li>
     *                                            </ul>
     */
    void setAdmin(String groupId, List<String> userIds, boolean isAdmin);

    /**
     * 设置为群管理员
     *
     * @param groupId:群组id
     * @param userIds:待设置管理员的用户id列表
     * @param isOwner:为true时，表示为管理员，否则为非管理员
     * @throws java.lang.IllegalArgumentException <ul>
     *                                            <li>
     *                                            当groupId为空时，抛出IllegalArgumentException
     *                                            </li>
     *                                            <li>
     *                                            当userIds为null或长度为0时，抛出IllegalArgumentException
     *                                            </li>
     *                                            </ul>
     */
    void setOwner(String groupId, List<String> userIds, boolean isOwner);
}
