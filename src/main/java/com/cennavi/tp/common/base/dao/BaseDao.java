package com.cennavi.tp.common.base.dao;


import com.cennavi.tp.common.PageResult;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * jdbc基础操作
 * @param
 */
public interface BaseDao<T> {

    public void save(T entity);

    public void update(T entity);

    public void delete(T entity);

    public void delete(Serializable id);

    public void deleteAll();

    void deleteAll(Class entity);

    void delete(Map<String, String> where);

    /**
     * 批量保存
     */
    void batchSave(List<T> list);

    /**
     * 批量保存 ()
     * @param list  要保存的记录
     * @param entityClass 字段和表名对应
     * @param table 表名 优先级最高
     * @param <E>
     */
    <E> void batchSave(final List<E> list, final Class<E> entityClass, String table);

    <E> void batchSave(final List<E> list, final Class<E> entityClass);

    /**
     * 未完成
     */
    public void batchUpdate(List<T> list);

    public T findById(Serializable id);

    public List<T> findAll();

    <E> List<E> findAll(Class<E> entity);

    public PageResult<T> findByPage(int pageNo, int pageSize);


    //例如：name='张三'，map.put("name =","张三"); in 则为map.put("name in","张三,李四")
    public PageResult<T> findByPage(int pageNo, int pageSize, Map<String, String> where);


    public PageResult<T> findByPage(int pageNo, int pageSize, LinkedHashMap<String, String> orderby);


    public PageResult<T> findByPage(int pageNo, int pageSize, Map<String, String> where,
                                    LinkedHashMap<String, String> orderby);

    /**
     *
     * @param where Map  例如：name='张三'，则 map.put("name","张三") 或者 map.put("name","=张三")
     *                              例如：name in ('张三','李四')则map.put("name","in张三,李四");
     *                              例如：name like ('张三%')则map.put("name","like张三%");
     *
     * @param orderby
     * @return
     */
    public List<T> findByConditons(Map<String, String> where, LinkedHashMap<String, String> orderby);

    /**
     * 判断表是否存在 pg
     * @param tablename
     * @return
     */
    boolean isExist(String tablename);
    boolean isExist(Class classname);

    void execSQL(String sql);

    List<T> executeSql(String sql);

    Map<String,Object> queryForMap(String sql);

    List<Map<String, Object>> queryForList(String sql);

    List<String> queryForList2(String sql);
}
