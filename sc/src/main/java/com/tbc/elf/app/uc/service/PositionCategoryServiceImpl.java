package com.tbc.elf.app.uc.service;

import com.tbc.elf.app.uc.model.PositionCategory;
import com.tbc.elf.base.security.util.AuthenticationUtil;
import com.tbc.elf.base.service.BaseServiceImpl;
import com.tbc.elf.base.util.ElfConstant;
import com.tbc.elf.base.util.HqlBuilder;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * 岗位类别服务的实现
 *
 * @author ELF@TEAM
 * @since 2016-2-24
 */
@Service("positionCategoryService")
public class PositionCategoryServiceImpl extends BaseServiceImpl<PositionCategory> implements PositionCategoryService {

    @Override
    @Transactional(readOnly = false,isolation = Isolation.READ_COMMITTED)
    public String saveOrUpdate(PositionCategory positionCategory) {
        Assert.notNull(positionCategory,"PositionCategory is null while save or update !");

        String parentId = positionCategory.getParentId();
        PositionCategory parent = null;
        if (StringUtils.isEmpty(parentId)) {
            positionCategory.setParentId(ElfConstant.ROOT_PARENT_ID);
            positionCategory.setNamePath(positionCategory.getCategoryName());
        } else {
            parent = super.load(parentId);
            positionCategory.setNamePath(parent.getNamePath() + ElfConstant.DOT + positionCategory.getCategoryName());
        }

        double maxShowOrder = getMaxShowOrder(positionCategory.getParentId());
        positionCategory.setShowOrder(maxShowOrder + 1);
        String id;
        if (StringUtils.isEmpty(positionCategory.getPositionCategoryId())) {
            id = super.save(positionCategory);
        }else{
            super.update(positionCategory);
            id = positionCategory.getPositionCategoryId();
        }

        String idPath = StringUtils.isEmpty(parentId) ? id : parent.getIdPath() + ElfConstant.DOT + id;
        updateIdPath(id,idPath);
        return id;
    }

    private void updateIdPath(String positionCategoryId,String idPath) {
        HqlBuilder hqlBuilder = new HqlBuilder();
        hqlBuilder.append("UPDATE PositionCategory SET idPath = ?",idPath);
        hqlBuilder.append("WHERE positionCategoryId = ?",positionCategoryId);
        baseService.executeUpdate(hqlBuilder);
    }

    private double getMaxShowOrder(String parentId) {
        HqlBuilder hqlBuilder = new HqlBuilder();
        hqlBuilder.append("SELECT max(showOrder) FROM PositionCategory WHERE corpCode = ?", AuthenticationUtil.getCorpCode());
        hqlBuilder.append("and parentId = ?",parentId);
        Double maxShowOrder = baseService.queryUniqueResult(hqlBuilder);
        return maxShowOrder == null ? 0D : maxShowOrder;
    }

    @Override
    @Transactional(readOnly = false,isolation = Isolation.READ_COMMITTED)
    public void move(String positionCategoryId, boolean isUp) {
        Assert.hasLength(positionCategoryId,"PositionCategoryId is empty while move !");

        PositionCategory category = super.get(positionCategoryId);
        Assert.notNull(category,"Cannot find position category by id : " + positionCategoryId);

        HqlBuilder hqlBuilder = new HqlBuilder();
        hqlBuilder.append("from PositionCategory where corpCode = ?",AuthenticationUtil.getCorpCode());
        hqlBuilder.append("and parentId = ?",category.getParentId());
        if (isUp) {
            hqlBuilder.append("and showOrder < ?",category.getShowOrder());
            hqlBuilder.append("order by showOrder desc");
            hqlBuilder.setMaxRecordNum(1);
        } else {
            hqlBuilder.append("and showOrder > ?",category.getShowOrder());
            hqlBuilder.append("order by showOrder asc");
            hqlBuilder.setMaxRecordNum(1);
        }
        PositionCategory siblingCategory = baseService.queryUniqueResult(hqlBuilder);
        if (siblingCategory == null) {
            return;
        }

        double tempShowOrder = siblingCategory.getShowOrder();
        siblingCategory.setShowOrder(category.getShowOrder());
        category.setShowOrder(tempShowOrder);
        baseService.saveOrUpdate(category);
        baseService.saveOrUpdate(siblingCategory);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PositionCategory> list() {
        HqlBuilder hqlBuilder = new HqlBuilder();
        hqlBuilder.append("from PositionCategory where corpCode = ?",AuthenticationUtil.getCorpCode());
        hqlBuilder.append("order by showOrder asc");
        return baseService.queryList(hqlBuilder);
    }
}
