package com.tbc.elf.app.uc.service;

import com.tbc.elf.app.uc.model.GroupCategory;
import com.tbc.elf.base.service.BaseService;

/**
 * 群组类别的服务接口
 *
 * @author ELF@TEAM
 * @since 2016年2月29日 16:53:02
 */
public interface GroupCategoryService extends BaseService<GroupCategory> {

    /**
     * 保存或更新群组类别（自动维护idPath、namePath和showOrder字段）
     *
     * @param groupCategory 群组类别
     * @return 群组类别id
     */
    String saveOrUpdate(GroupCategory groupCategory);
}
