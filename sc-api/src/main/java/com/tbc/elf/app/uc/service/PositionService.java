package com.tbc.elf.app.uc.service;

import com.tbc.elf.app.uc.model.Position;
import com.tbc.elf.base.service.BaseService;
import com.tbc.elf.base.util.Page;

/**
 * 岗位服务的接口
 *
 * @author ELF@TEAM
 * @since 2016年2月25日 17:13:54
 */
public interface PositionService extends BaseService<Position> {

    /**
     * 获取当前公司最大的岗位类别排序号
     *
     * @return 最大的岗位类别排序号
     */
    double getMaxShowOrder();

    /**
     * 分页查询岗位列表
     *
     * @param keyword 岗位名称
     * @return 分页查询结果
     */
    Page<Position> search(String keyword,Page<Position> page);
}
