package com.bjsxt.sorm.core;

import java.util.List;

/**
 * 负责查询对外提供的核心类
 * @author Tian.xiang
 * */
public interface Query extends Cloneable {

    /**
     * @param sql sql语句
     * @param params 参数
     * @return 执行sql影响的行数
     * */
    int executeDML(String sql,Object[] params);

    /**
     * 将一个对象存储到数据库
     * @param obj 要存储的对象
     * */
    void insert(Object obj);

    /**
     * @param clazz 跟表对应的class对象
     * @param id 主键id
     * @return
     * */
    void delete(Class clazz,Object id);

    /**
     * @param obj 删除对象的记录
     * */
    void delete(Object obj);

    /**
     * @param object
     * @param fieldNames 更新的字段
     * @return 影响的行数
     * */
    int update(Object object,String[] fieldNames);

    int update(Object object);

    /**
     * @param sql
     * @param clazz 对象
     * @param params 参数
     * @return List 返回结果
     * */
    List queryRows(String sql,Class clazz,Object[] params);

    /**
     * @param sql
     * @param clazz
     * @param params
     * @return Object
     * */
    Object queryUniqueRow(String sql,Class clazz,Object[] params);

    /**
     * 查询一行一列，查询一个值
     * @param sql
     * @param params
     * @return Object
     * */
    Object queryValue(String sql,Object[] params);

    /**
     * 查询数字
     * @param sql
     * @param params
     * @return Object
     * */
    Number queryNumber(String sql,Object[] params);

    /**
     * 分页查询
     * @param pageNum
     * @param size
     * @return
     */
    List queryPagination(int pageNum,int size);

    Object queryById(Class clazz,Object key);

}
