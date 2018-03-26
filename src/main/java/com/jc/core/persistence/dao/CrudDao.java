package com.jc.core.persistence.dao;

import java.util.List;

public interface CrudDao<T> extends BaseDao {

    /**
     * 保存方法
     *
     * @param o 实体类
     * @return 新增的数目数
     * @author
     */
    public Integer save(T o);

    /**
     * 批量保存方法
     *
     * @param list 实体类集合
     * @return Integer 添加的记录数
     * @author
     */
    public Integer saveList(List<T> list);

    /**
     * 修改方法
     *
     * @param o 实体类
     * @return Integer 修改的记录数
     * @author
     */
    public Integer update(T o);

    /**
     * 逻辑删除
     *
     * @param o 实体类
     * @return Integer 删除的记录数
     * @author
     */
    public Integer delete(T o);

    /**
     * 物理删除
     *
     * @param o 实体类
     * @return 数据库影响的记录数
     */
    public Integer deletePhysical(T o);

    /**
     * 查询所有记录方法
     *
     * @param entity 实体类
     * @return List 查询的所有记录
     * @author
     * @version 2014-03-24
     */
    public List<T> findList(T entity);

    /**
     * 查询所有数据列表
     *
     * @param entity
     * @return
     */
    public List<T> findAllList(T entity);

    /**
     * 获取单条数据
     *
     * @param id
     * @return
     */
    public T get(Long id);

    /**
     * 查询单条记录方法
     *
     * @param o 实体类
     * @return T 查询的记录
     * @author
     */
    public T get(T o);

}
