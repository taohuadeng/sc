package com.tbc.elf.app.uc.service;

import com.tbc.elf.app.uc.model.Position;
import com.tbc.elf.base.security.util.AuthenticationUtil;
import com.tbc.elf.base.service.BaseServiceImpl;
import com.tbc.elf.base.util.Page;
import com.tbc.elf.base.util.SqlUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import com.tbc.elf.base.util.HqlBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * 岗位服务接口的实现
 *
 * @author ELF@TEAM
 * @since 2016-2-24
 */
@Service("positionService")
public class PositionServiceImpl extends BaseServiceImpl<Position> implements PositionService {

    @Override
    @Transactional(readOnly = true)
    public double getMaxShowOrder() {
        HqlBuilder hqlBuilder = new HqlBuilder();
        hqlBuilder.append("select max(showOrder) from Position where corpCode = ?", AuthenticationUtil.getCorpCode());
        Double maxShowOrder = baseService.queryUniqueResult(hqlBuilder);
        return maxShowOrder == null ? 0D : maxShowOrder;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Position> search(String keyword, Page<Position> page) {
        HqlBuilder conditionBuilder = new HqlBuilder();
        conditionBuilder.append("from Position where corpCode = ?", AuthenticationUtil.getCorpCode());
        if (StringUtils.isNotEmpty(keyword)) {
            conditionBuilder.append("and positionName like ?","%" + SqlUtil.escapeLike(keyword) + "%");
        }

        if (page.isAutoCount()) {
            HqlBuilder countBuilder = new HqlBuilder();
            countBuilder.append("select count(*)");
            countBuilder.append(conditionBuilder);
            Long count = baseService.queryUniqueResult(countBuilder);
            if (count == null || count == 0) {
                return page;
            }
            page.setTotal(count);
        }

        HqlBuilder hqlBuilder = new HqlBuilder();
        hqlBuilder.append(conditionBuilder);
        hqlBuilder.append("order by lastModifyTime");
        if (page.isAutoPaging()) {
            hqlBuilder.setMaxRecordNum(page.getPageSize());
            hqlBuilder.setFirstRecordIndex(page.getFirst() - 1);
        }
        List<Position> positionList = baseService.queryList(hqlBuilder);
        page.setRows(positionList);
        return page;
    }
}
