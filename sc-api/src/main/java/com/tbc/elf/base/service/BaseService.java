package com.tbc.elf.base.service;


import java.util.List;
import java.util.Set;

/**
 * 这个借口抽象出一些常用服务的通用方法。可以被其它服务接口集成。
 *
 * @param <T> 要操作的实体类。
 * @author ELF@TEAM
 */
public interface BaseService<T> {
    /**
     * 根据传入的实体是否主键被设置来判断是执行插入（主键未设置）
     * 或者更新操作（主键被调用方设置了）
     *
     * @param model 要保存的实体。
     * @return 保存实体的主键。
     */
    String save(T model);

    /**
     * 该方法用于根据实体主键获取实体类
     * <p>load Hibernate load</p>
     *
     * @param modelId 实体主键
     * @return 对应实体T
     */
    T load(String modelId);

    /**
     * 该方法用于根据实体主键获取实体类
     * <p>load Hibernate get</p>
     *
     * @param modelId 实体主键
     * @return 对应实体T
     */
    T get(String modelId);

    /**
     * 该方法用于根据实体主键删除对应实体类
     *
     * @param modelId 实体主键
     */
    void delete(String modelId);

    /**
     * 根据主键id批量删除对应的实体类
     *
     * @param modelIds 实体主键列表
     * @return 删除数据的条数
     */
    int delete(List<String> modelIds);

    /**
     * 根据主键更新当前实体，如果实体主键未设置，则会抛出错误。
     * 未设置的值，会保留数据库原有值，设置的值为被更新成新值。
     *
     * @param model 要更新的实体。
     */
    void update(T model);

    /**
     * 根据hql获取对应实体集合
     *
     * @param hql   hql语句
     * @param value 查询参数
     * @return 查询实体集合
     */
    List<T> listByHQL(String hql, Object[] value);

    /**
     * 批量保存实体
     *
     * @param models 实体集合
     * @param <T>    实体类型
     * @return 保存成功的主键
     */
    <T> List<String> batchSave(List<T> models);

    /**
     * 批量保存或更新实体
     *
     * @param models 实体集合
     * @param <T>    实体类型
     */
    <T> void batchSaveOrUpdate(List<T> models);
}
