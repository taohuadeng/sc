package com.tbc.elf.app.uc.model;

import com.tbc.elf.base.model.BaseModel;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 群组类别实体
 *
 * @author ELF@TEAM
 * @since 2016年2月23日17:03:00
 */
@Entity
@Table(name = "t_uc_group_category")
public class GroupCategory extends BaseModel {
    /**
     * 主键
     */
    @Id
    @Column(nullable = false, length = 32)
    @GeneratedValue(generator = "hibernate-uuid")
    @GenericGenerator(name = "hibernate-uuid", strategy = "uuid")
    private String groupCategoryId;

    /**
     * 分类名称
     */
    @Column(nullable = false, length = 32)
    private String groupCategoryName;

    /**
     * 类别全路径，方便查询
     */
    @Column(nullable = false, length = 200)
    private String idPath;

    /**
     * 类别路径名
     */
    @Column(nullable = false, length = 200)
    private String namePath;

    /**
     * 上级分类id
     */
    @Column(nullable = false, length = 32)
    private String parentId;

    /**
     * 排序号
     */
    @Column(nullable = false)
    private Double showOrder;

    public String getGroupCategoryId() {
        return groupCategoryId;
    }

    public void setGroupCategoryId(String groupCategoryId) {
        this.groupCategoryId = groupCategoryId;
    }

    public String getGroupCategoryName() {
        return groupCategoryName;
    }

    public void setGroupCategoryName(String groupCategoryName) {
        this.groupCategoryName = groupCategoryName;
    }

    public String getIdPath() {
        return idPath;
    }

    public void setIdPath(String idPath) {
        this.idPath = idPath;
    }

    public String getNamePath() {
        return namePath;
    }

    public void setNamePath(String namePath) {
        this.namePath = namePath;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Double getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(Double showOrder) {
        this.showOrder = showOrder;
    }
}
