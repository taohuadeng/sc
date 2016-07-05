package com.tbc.elf.app.uc.service;

import com.tbc.elf.app.uc.model.PositionCategory;
import com.tbc.elf.base.service.BaseService;

import java.util.List;

/**
 * 岗位类别服务接口
 *
 * @author ELF@TEAM
 * @since 2016-2-24
 */
public interface PositionCategoryService extends BaseService<PositionCategory>{

    /**
     * 保存岗位类别信息（自动维护idPath、namePath和showOrder字段）
     *
     * @param positionCategory 岗位类别
     * @return 岗位类别id
     */
    String saveOrUpdate(PositionCategory positionCategory);

    /**
     * 移动位置（上移或下移）
     *
     * @param positionCategoryId 主键id
     * @param isUp true表示上移，false表示下移
     */
    void move(String positionCategoryId,boolean isUp);

    /**
     * 获取当前公司所有的岗位类别
     *
     * @return 岗位类别列表
     */
    List<PositionCategory> list();
}
