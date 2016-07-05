package com.tbc.elf.app.uc.service;

import com.tbc.elf.app.uc.model.GroupCategory;
import com.tbc.elf.base.security.util.AuthenticationUtil;
import com.tbc.elf.base.service.BaseServiceImpl;
import com.tbc.elf.base.util.ElfConstant;
import com.tbc.elf.base.util.HqlBuilder;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * 群组类别服务接口的实现
 *
 * @author ELF@TEAM
 * @since 2016年2月29日 17:03:28
 */
@Service("groupCategoryService")
public class GroupCategoryServiceImpl extends BaseServiceImpl<GroupCategory> implements GroupCategoryService {

    @Override
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
    public String saveOrUpdate(GroupCategory groupCategory) {
        Assert.notNull(groupCategory, "GroupCategory is null while save or update !");

        String parentId = groupCategory.getParentId();
        GroupCategory parent = null;
        if (StringUtils.isEmpty(parentId)) {
            groupCategory.setParentId(ElfConstant.ROOT_PARENT_ID);
            groupCategory.setNamePath(groupCategory.getGroupCategoryName());
        } else {
            parent = super.load(parentId);
            groupCategory.setNamePath(parent.getNamePath() + ElfConstant.DOT + groupCategory.getGroupCategoryName());
        }

        double maxShowOrder = getMaxShowOrder(groupCategory.getParentId());
        groupCategory.setShowOrder(maxShowOrder + 1);
        String id;
        if (StringUtils.isEmpty(groupCategory.getGroupCategoryId())) {
            groupCategory.setIdPath("");
            id = super.save(groupCategory);
        } else {
            super.update(groupCategory);
            id = groupCategory.getGroupCategoryId();
        }

        String idPath = StringUtils.isEmpty(parentId) ? id : parent.getIdPath() + ElfConstant.DOT + id;
        updateIdPath(id, idPath);
        return id;
    }

    private void updateIdPath(String groupCategoryId, String idPath) {
        HqlBuilder hqlBuilder = new HqlBuilder();
        hqlBuilder.append("UPDATE GroupCategory SET idPath = ?", idPath);
        hqlBuilder.append("WHERE groupCategoryId = ?", groupCategoryId);
        baseService.executeUpdate(hqlBuilder);
    }

    private double getMaxShowOrder(String parentId) {
        HqlBuilder hqlBuilder = new HqlBuilder();
        hqlBuilder.append("SELECT max(showOrder) FROM GroupCategory WHERE corpCode = ?", AuthenticationUtil.getCorpCode());
        hqlBuilder.append("and parentId = ?", parentId);
        Double maxShowOrder = baseService.queryUniqueResult(hqlBuilder);
        return maxShowOrder == null ? 0D : maxShowOrder;
    }
}
