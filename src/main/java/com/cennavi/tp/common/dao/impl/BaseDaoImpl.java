package com.cennavi.tp.common.dao.impl;

import com.cennavi.tp.common.IgnoreColumn;
import com.cennavi.tp.common.MyTable;
import com.cennavi.tp.common.PageResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import com.cennavi.tp.common.dao.BaseDao;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by sunpengyan on 2017/5/15.
 */
public class BaseDaoImpl<T> implements BaseDao<T> {

    public static boolean isHump = false;//makeSql 是否使用驼峰转下划线
    /** 设置一些操作的常量 */
    public static final String SQL_INSERT = "insert";
    public static final String SQL_UPDATE = "update";
    public static final String SQL_DELETE = "delete";

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Class<T> entityClass;

    @SuppressWarnings("unchecked")
    public BaseDaoImpl() {
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        entityClass = (Class<T>) type.getActualTypeArguments()[0];
    }

    @Override
    public void save(T entity) {

        /*String sql = this.makeSql(SQL_INSERT);
        Object[] args = this.setArgs(entity, SQL_INSERT ,null);
        int[] argTypes = this.setArgTypes(entity, SQL_INSERT,null);*/
        String sql = makeSql(SQL_INSERT);
        Object[] args = this.setArgs(entity, SQL_INSERT ,null);
        int[] argTypes = this.setArgTypes(entity, SQL_INSERT,null);
        jdbcTemplate.update(sql.toString(), args);
    }

    @Override
    public void update(T entity) {
        String sql = this.makeSql(SQL_UPDATE);
        Object[] args = this.setArgs(entity, SQL_UPDATE ,null);
        int[] argTypes = this.setArgTypes(entity, SQL_UPDATE, null);
        jdbcTemplate.update(sql, args, argTypes);
    }

    @Override
    public void delete(T entity) {
        String sql = this.makeSql(SQL_DELETE);
        Object[] args = this.setArgs(entity, SQL_DELETE, null);
        int[] argTypes = this.setArgTypes(entity, SQL_DELETE, null);
        jdbcTemplate.update(sql, args, argTypes);
    }

    @Override
    public void delete(Serializable id) {
        String sql = " DELETE FROM " + this.getTableNameByClassName(entityClass) + " WHERE id=?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void deleteAll() {
        String sql = " TRUNCATE TABLE " + this.getTableNameByClassName(entityClass);
        jdbcTemplate.execute(sql);
    }

    @Override
    public void deleteAll(Class entity) {
        String sql = " truncate table " + this.getTableNameByClassName(entity);
        jdbcTemplate.execute(sql);
    }

    @Override
    public void delete(Map<String, String> where) {
        StringBuffer sql = new StringBuffer("delete from "+this.getTableNameByClassName(entityClass)+" where 1=1 and ");
        if (where != null && where.size() > 0) {
            for (Map.Entry<String, String> me : where.entrySet()) {
                String columnName = me.getKey();
                String value = me.getValue();
                String link = " = '";
                if(value.contains("in")){
                    link = " in ('";
                    value = value.replace("in","").replace(",","','");
                }
                sql.append(columnName).append(link).append(value).append("' AND "); // 没有考虑or的情况
            }
            int endIndex = sql.lastIndexOf("AND");
            if (endIndex > 0) {
                sql = new StringBuffer(sql.substring(0, endIndex));
            }
        }
        jdbcTemplate.update(sql.toString());
    }

    @Override
    public void batchSave(final List<T> list) {
        String sql = this.makeSql(SQL_INSERT);
        batchUpdate(list, sql);
        /*int[] ints = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            public int getBatchSize() {
                return list.size();
            }
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                T t = list.get(i);
                Field[] fields = t.getClass().getDeclaredFields();
                try {
                    for (int j = 0; j < fields.length; j++) {
                        boolean isColumn = fields[j].isAnnotationPresent(IgnoreColumn.class);//判断是否有自定义注解,如果有则忽略
                        if(isColumn){
                            continue;
                        }
                        fields[j].setAccessible(true); // 暴力反射
                        Object obj = fields[j].get(t);
                        if (obj!=null && obj.getClass().getName().equals("java.util.Date")){
                            Date parse = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US).parse(obj.toString());
                            ps.setTimestamp(j + 1,  new java.sql.Timestamp(parse.getTime()));
                        }else {
                            ps.setObject(j + 1, obj);
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });*/
    }

    @Override
    public <E> void batchSave(final List<E> list,final Class<E> entityClass,String table) {
        String sql = this.makeSql(SQL_INSERT,entityClass,table);
        batchUpdate(list, sql);
    }

    @Override
    public <E> void batchSave(final List<E> list,final Class<E> entityClass) {
        batchSave(list,entityClass,null);
    }

    private <E> void batchUpdate(final List<E> list, String sql) {
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            public int getBatchSize() {
                return list.size();
            }
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                E e = list.get(i);
                Field[] fields = e.getClass().getDeclaredFields();
                try {
                    int k = 1;
                    for (int j = 0; j < fields.length; j++) {
                        fields[j].setAccessible(true); // 暴力反射
                        String column = fields[j].getName();
                        boolean isColumn = fields[j].isAnnotationPresent(IgnoreColumn.class);//判断是否有自定义注解(排除bean中的某些字段),
                        if(isColumn){
                            continue;
                        }

                        Object obj = fields[j].get(e);
                        if (obj!=null && obj.getClass().getName().equals("java.util.Date")){
                            Date parse = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US).parse(obj.toString());
                            ps.setTimestamp(j,  new java.sql.Timestamp(parse.getTime()));
                        }else if (obj!=null && obj.getClass().getName().equals("java.util.HashSet")){//去掉hibernate的关系映射
                            continue;
                        }else {
                            ps.setObject(k, obj);
                            k++;
                        }
                    }
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    /**
     * 未完成
     */
    @Override
    public void batchUpdate(List<T> list) {
        StringBuilder sb = new StringBuilder();
        sb.append("update ").append(this.getTableNameByClassName(entityClass)).append(" set ");
        List<Object[]> params = new ArrayList<Object[]>();
        Object primaryKey = "id";
        for (T t : list) {
            int index = 0;
            Field[] fields = entityClass.getDeclaredFields();
            if (fields != null && fields.length > 0) {
                Object id = null;
                Object[] objVal = new Object[fields.length];
                for (Field field : fields) {
                    try {
                        field.setAccessible(true);
                        Object obj = field.get(t);
                        if (field.getName().equalsIgnoreCase("id")) {
                            //primaryKey = obj;
                            id = obj;
                        } else {
                            if (params.size() == 0) {
                                sb.append(field.getName()).append(" = ? ,");
                            }
                            objVal[index++] = obj;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    objVal[index] = id;
                }
                params.add(objVal);
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(" where ").append(primaryKey).append(" = ? ");
        String sql = sb.toString();
        jdbcTemplate.batchUpdate(sql, params);
    }


    @Override
    public T findById(Serializable id) {
        String sql = "SELECT * FROM " + this.getTableNameByClassName(entityClass) + " WHERE id=?";
        RowMapper<T> rowMapper = BeanPropertyRowMapper.newInstance(entityClass);
        List<T> list = jdbcTemplate.query(sql, rowMapper, id);
        return list.size()==0?null:list.get(0);
    }

    @Override
    public <E> List<E> findAll(Class<E> entity) {
        if(entity == null) {
            return new ArrayList<>();
        } else {
            String sql = "SELECT * FROM " + getTableNameByClassName(entity);
            RowMapper<E> rowMapper = BeanPropertyRowMapper.newInstance(entity);
            return jdbcTemplate.query(sql, rowMapper);
        }
    }

    @Override
    public List<T> findAll() {
        String sql = "SELECT * FROM " + this.getTableNameByClassName(entityClass);
        RowMapper<T> rowMapper = BeanPropertyRowMapper.newInstance(entityClass);
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public PageResult<T> findByPage(int pageNo, int pageSize) {
        List<T> list = this.find(pageNo, pageSize, null, null);
        int totalRow = this.count(null);
        return new PageResult<T>(list, totalRow);
    }

    @Override
    public PageResult<T> findByPage(int pageNo, int pageSize, Map<String, String> where) {
        List<T> list = this.find(pageNo, pageSize, where, null);
        int totalRow = this.count(where);
        return new PageResult<T>(list, totalRow);
    }

    @Override
    public PageResult<T> findByPage(int pageNo, int pageSize, LinkedHashMap<String, String> orderby) {
        List<T> list = this.find(pageNo, pageSize, null, orderby);
        int totalRow = this.count(null);
        return new PageResult<T>(list, totalRow);
    }

    @Override
    public PageResult<T> findByPage(int pageNo, int pageSize, Map<String, String> where,
                                    LinkedHashMap<String, String> orderby) {
        List<T> list = this.find(pageNo, pageSize, where, orderby);
        int totalRow = this.count(where);
        return new PageResult<T>(list, totalRow);
    }

    @Override
    public List<T> findByConditons(Map<String, String> where, LinkedHashMap<String, String> orderby) {
        return this.find(where,orderby);
    }

    @Override
    public boolean isExist(String tablename) {
        String sql = "select tablename from pg_tables where tablename='"+tablename+"'";
        List<String> tables = jdbcTemplate.queryForList(sql, String.class);
        if(tables == null || tables.size()==0)  {
            return false;
        }
        return true;
    }

    @Override
    public boolean isExist(Class classname) {
        return this.isExist(this.getTableNameByClassName(classname));
    }

    @Override
    public void execSQL(String sql) {
        jdbcTemplate.execute(sql);
    }

    @Override
    public List<T> executeSql(String sql) {
        RowMapper<T> rowMapper = BeanPropertyRowMapper.newInstance(entityClass);
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Map<String,Object> queryForMap(String sql) {
        return jdbcTemplate.queryForMap(sql);
    }

    @Override
    public List<Map<String, Object>> queryForList(String sql) {
        return jdbcTemplate.queryForList(sql);
    }

    @Override
    public List<String> queryForList2(String sql) {
        return jdbcTemplate.queryForList(sql, String.class);
    }


    // 组装SQL
    private String makeSql(String sqlFlag) {
        return makeSql(sqlFlag,null,null);
    }

    // 组装SQL
    private <E> String makeSql(String sqlFlag,final Class<E> entity,String table) {
        StringBuffer sql = new StringBuffer();
        String tablename = "";
        Field[] fields = null;
        if(entity == null) {
            fields = entityClass.getDeclaredFields();
            tablename =  this.getTableNameByClassName(entityClass);
        } else {
            fields = entity.getDeclaredFields();
            tablename = this.getTableNameByClassName(entity);
        }
        if(StringUtils.isNotBlank(table)) {
            tablename = table;
        }
        if (sqlFlag.equals(SQL_INSERT)) {
            sql.append(" INSERT INTO " + tablename);
            sql.append("(");
            for (int i = 0; fields != null && i < fields.length; i++) {
                fields[i].setAccessible(true); // 暴力反射
                String column = fields[i].getName();
                boolean isColumn = fields[i].isAnnotationPresent(IgnoreColumn.class);//判断是否有自定义注解(排除bean中的某些字段),
                if(!isColumn){
                    //sql.append(column).append(",");
                    if(isHump) {
                        sql.append(camelToUnderline(column)).append(",");
                    } else {
                        sql.append(column).append(",");
                    }
                }
            }
            sql = sql.deleteCharAt(sql.length() - 1);
            sql.append(") VALUES (");
            //sql.append(" VALUES (");
            for (int i = 0; fields != null && i < fields.length; i++) {
                String column = fields[i].getName();
                boolean isColumn = fields[i].isAnnotationPresent(IgnoreColumn.class);//判断是否有自定义注解(排除bean中的某些字段),
                if(!isColumn){
                    sql.append("?,");
                }
            }
            sql = sql.deleteCharAt(sql.length() - 1);
            sql.append(")");
        } else if (sqlFlag.equals(SQL_UPDATE)) {
            sql.append(" UPDATE " + tablename + " SET ");
            for (int i = 0; fields != null && i < fields.length; i++) {
                fields[i].setAccessible(true); // 暴力反射
                String column = fields[i].getName();
                if (column.equals("id")) { // id 代表主键
                    continue;
                }
                boolean isColumn = fields[i].isAnnotationPresent(IgnoreColumn.class);//判断是否有自定义注解(排除bean中的某些字段),
                if(!isColumn){
                    sql.append(column).append("=").append("?,");
                    if(isHump) {
                        sql.append(camelToUnderline(column)).append("=").append("?,");
                    } else {
                        sql.append(column).append("=").append("?,");
                    }
                }
            }
            sql = sql.deleteCharAt(sql.length() - 1);
            sql.append(" WHERE id=?");
        } else if (sqlFlag.equals(SQL_DELETE)) {
            sql.append(" DELETE FROM " + tablename + " WHERE id=?");
        }
        return sql.toString();
    }

    // 设置参数
    private Object[] setArgs(T entity, String sqlFlag, List<String> excludes) {
        Field[] fields = entityClass.getDeclaredFields();
        if (sqlFlag.equals(SQL_INSERT)) {
            Object[] args = new Object[fields.length];;
            if(excludes!=null) { // 20170918 用于判断排除bean中的某些字段
                args = new Object[fields.length - excludes.size()];
            }
            int n = 0;//tmepArr 和 bean的长度不一样
            int icNum=0;
            for (int i = 0; fields != null && i < fields.length; i++) {
                try {
                    fields[i].setAccessible(true); // 暴力反射
                    Object field = fields[i].get(entity);
                    String column = fields[i].getName();
                    if(excludes!=null && excludes.contains(column)) { // 20170918 用于判断排除bean中的某些字段
                        continue;
                    }
                    boolean isColumn = fields[i].isAnnotationPresent(IgnoreColumn.class);//判断是否有自定义注解(排除bean中的某些字段),
                    if(isColumn){
                        icNum++;
                        continue;
                    }

                    if(field!=null) {
                        args[n] = field;
                    }
                    n++;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //return args;
            return Arrays.copyOfRange(args, 0, args.length-icNum);
        } else if (sqlFlag.equals(SQL_UPDATE)) {
            Object[] tempArr = new Object[fields.length];
            if(excludes!=null) { // 20170918 用于判断排除bean中的某些字段
                tempArr = new Object[fields.length - excludes.size()];
            }
            int n = 0;//tmepArr 和 bean的长度不一样
            for (int i = 0; fields != null && i < fields.length; i++) {
                try {
                    fields[i].setAccessible(true); // 暴力反射
                    String column = fields[i].getName();
                    if(excludes!=null && excludes.contains(column)) { // 20170918 用于判断排除bean中的某些字段
                        continue;
                    }
                    if(fields[i].get(entity)!=null) {
                        tempArr[n] = fields[i].get(entity);
                    }
                    n++;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Object[] args = new Object[tempArr.length];
            System.arraycopy(tempArr, 1, args, 0, tempArr.length - 1); // 数组拷贝
            args[args.length - 1] = tempArr[0];
            return args;
        } else if (sqlFlag.equals(SQL_DELETE)) {
            Object[] args = new Object[1]; // 长度是1
            fields[0].setAccessible(true); // 暴力反射
            try {
                args[0] = fields[0].get(entity);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return args;
        }
        return null;
    }

    // 设置参数类型（写的不全，只是一些常用的）
    private int[] setArgTypes(T entity, String sqlFlag, List<String> excludes) {
        Field[] fields = entityClass.getDeclaredFields();
        if (sqlFlag.equals(SQL_INSERT)) {
            int[] argTypes = new int[fields.length];
            if(excludes!=null) { // 20170918 用于判断排除bean中的某些字段
                argTypes = new int[fields.length - excludes.size()];
            }
            int n = 0;//argTypes 和 bean的长度不一样
            try {
                for (int i = 0; fields != null && i < fields.length; i++) {
                    fields[i].setAccessible(true); // 暴力反射
                    String column = fields[i].getName();
                    if(excludes!=null && excludes.contains(column)) { // 20170918 用于判断排除bean中的某些字段
                        continue;
                    }
                    if(fields[i].get(entity)!=null) {
                        if (fields[i].get(entity).getClass().getName().equals("java.lang.String")) {
                            argTypes[n] = Types.VARCHAR;
                        } else if (fields[i].get(entity).getClass().getName().equals("java.lang.Double")) {
                            argTypes[n] = Types.DECIMAL;
                        } else if (fields[i].get(entity).getClass().getName().equals("java.lang.Integer")) {
                            argTypes[n] = Types.INTEGER;
                        } else if (fields[i].get(entity).getClass().getName().equals("java.util.Date")) {
                            argTypes[n] = Types.TIMESTAMP;
                        } else if (fields[i].get(entity).getClass().getName().equals("java.sql.Timestamp")) {
                            argTypes[n] = Types.TIMESTAMP;
                        } else if (fields[i].get(entity).getClass().getName().equals("java.lang.Boolean")) {
                            argTypes[n] = Types.BOOLEAN;
                        }else if (fields[i].get(entity).getClass().getName().equals("java.lang.Float")) {
                            argTypes[n] = Types.FLOAT;
                        }
                    }
                    n++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return argTypes;
        } else if (sqlFlag.equals(SQL_UPDATE)) {
            int[] tempArgTypes = new int[fields.length];
            if(excludes!=null) { // 20170918 用于判断排除bean中的某些字段
                tempArgTypes = new int[fields.length - excludes.size()];
            }
            int[] argTypes = new int[tempArgTypes.length];
            int n = 0;//argTypes 和 bean的长度不一样
            try {
                for (int i = 0; tempArgTypes != null && n < tempArgTypes.length; i++) {
                    fields[i].setAccessible(true); // 暴力反射
                    String column = fields[i].getName();
                    if(excludes!=null && excludes.contains(column)) { // 20170918 用于判断排除bean中的某些字段
                        continue;
                    }
                    if(fields[i].get(entity)!=null) {
                        if (fields[i].get(entity).getClass().getName().equals("java.lang.String")) {
                            tempArgTypes[n] = Types.VARCHAR;
                        } else if (fields[i].get(entity).getClass().getName().equals("java.lang.Double")) {
                            tempArgTypes[n] = Types.DECIMAL;
                        } else if (fields[i].get(entity).getClass().getName().equals("java.lang.Integer")) {
                            tempArgTypes[n] = Types.INTEGER;
                        } else if (fields[i].get(entity).getClass().getName().equals("java.util.Date")) {
                            tempArgTypes[n] = Types.TIMESTAMP;
                        }else if (fields[i].get(entity).getClass().getName().equals("java.sql.Timestamp")) {
                            tempArgTypes[n] = Types.TIMESTAMP;
                        }else if (fields[i].get(entity).getClass().getName().equals("java.lang.Boolean")) {
                            tempArgTypes[n] = Types.BOOLEAN;
                        }else if (fields[i].get(entity).getClass().getName().equals("java.lang.Float")) {
                            tempArgTypes[n] = Types.FLOAT;
                        }
                    }
                    n++;
                }
                System.arraycopy(tempArgTypes, 1, argTypes, 0, tempArgTypes.length - 1); // 数组拷贝
                argTypes[argTypes.length - 1] = tempArgTypes[0];

            } catch (Exception e) {
                e.printStackTrace();
            }
            return argTypes;

        } else if (sqlFlag.equals(SQL_DELETE)) {
            int[] argTypes = new int[1]; // 长度是1
            try {
                fields[0].setAccessible(true); // 暴力反射
                if (fields[0].get(entity).getClass().getName().equals("java.lang.String")) {
                    argTypes[0] = Types.VARCHAR;
                } else if (fields[0].get(entity).getClass().getName().equals("java.lang.Integer")) {
                    argTypes[0] = Types.INTEGER;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return argTypes;
        }
        return null;
    }

    private List<T> find(int pageNo, int pageSize, Map<String, String> where, LinkedHashMap<String, String> orderby) {
        // where 与 order by 要写在select * from table 的后面，而不是where rownum<=? ) where rn>=?的后面
        StringBuffer sql = new StringBuffer(" select * from "
                + entityClass.getSimpleName());
        if (where != null && where.size() > 0) {
            sql.append(" where "); // 注意不是where
            for (Map.Entry<String, String> me : where.entrySet()) {
                String columnName = me.getKey();
                String columnValue = me.getValue();
                sql.append(columnName).append(" ").append(columnValue).append(" and "); // 没有考虑or的情况
            }
            int endIndex = sql.lastIndexOf("and");
            if (endIndex > 0) {
                sql = new StringBuffer(sql.substring(0, endIndex));
            }
        }
        if (orderby != null && orderby.size() > 0) {
            sql.append(" order by ");
            for (Map.Entry<String, String> me : orderby.entrySet()) {
                String columnName = me.getKey();
                String columnValue = me.getValue();
                //sql.append(columnName).append(" ").append(columnValue).append(",");
                if(columnName.contains("in")) {
                    String replace = columnValue.replace(",", "','");
                    sql.append(columnName).append(" ('").append(replace).append("') and "); // 没有考虑or的情况
                } else if(columnName.contains("like")) {
                    sql.append(columnName).append(" '%").append(columnValue).append("%' and "); // 模糊查询
                }else { //=
                    sql.append(columnName).append(" '").append(columnValue).append("' and "); // 没有考虑or的情况
                }
            }
            sql = sql.deleteCharAt(sql.length() - 1);
        }
        int sIndex = (pageNo-1)*pageSize;
        sql.append(" limit "+pageSize+" offset "+sIndex);

        RowMapper<T> rowMapper = BeanPropertyRowMapper.newInstance(entityClass);
        return jdbcTemplate.query(sql.toString(), rowMapper);
    }

    private List<T> find(Map<String, String> where, LinkedHashMap<String, String> orderby) {
        StringBuffer sql = new StringBuffer(" SELECT * FROM " + this.getTableNameByClassName(entityClass));
        if (where != null && where.size() > 0) {
            sql.append(" WHERE ");
            for (Map.Entry<String, String> me : where.entrySet()) {
                String columnName = me.getKey();
                String value = me.getValue();
                String link = " = '";
                if(value.contains("=")) {
                    value = value.replace("=","");
                } else if(value.contains("in")){
                    link = " in ('";
                    value = value.replace("in","").replace(",","','");
                } else if(value.contains("like")){
                    link = " like '";
                    value = value.replace("like","")+"%";
                }
                sql.append(columnName).append(link).append(value).append("' AND "); // 没有考虑or的情况
            }
            int endIndex = sql.lastIndexOf("AND");
            if (endIndex > 0) {
                sql = new StringBuffer(sql.substring(0, endIndex));
            }
        }
        if (orderby != null && orderby.size() > 0) {
            sql.append(" ORDER BY ");
            for (Map.Entry<String, String> me : orderby.entrySet()) {
                String columnName = me.getKey();
                String columnValue = me.getValue();
                sql.append(columnName).append(" ").append(columnValue).append(",");
            }
            sql = sql.deleteCharAt(sql.length() - 1);
        }
        System.out.println("SQL=" + sql);
        RowMapper<T> rowMapper = BeanPropertyRowMapper.newInstance(entityClass);
        return jdbcTemplate.query(sql.toString(), rowMapper);
    }

    private int count(Map<String, String> where) {
        StringBuffer sql = new StringBuffer(" SELECT COUNT(*) FROM " + this.getTableNameByClassName(entityClass));
        if (where != null && where.size() > 0) {
            sql.append(" WHERE ");
            for (Map.Entry<String, String> me : where.entrySet()) {
                String columnName = me.getKey();
                String columnValue = me.getValue();
                sql.append(columnName).append(" ").append(columnValue).append(" AND "); // 没有考虑or的情况
            }
            int endIndex = sql.lastIndexOf("AND");
            if (endIndex > 0) {
                sql = new StringBuffer(sql.substring(0, endIndex));
            }
        }
        System.out.println("SQL=" + sql);
        //return jdbcTemplate.queryForInt(sql.toString());
        return jdbcTemplate.queryForObject(sql.toString(), null, Integer.class);
    }

    //根据类获取表名，查询类注解
    private String getTableNameByClassName(Class entity) {
        String tablename = entity.getSimpleName();
        if(entity.isAnnotationPresent(MyTable.class)) {
            MyTable t = (MyTable) entity.getAnnotation(MyTable.class); //获取User类@Table注解的值value，该值我们定义为User表的表名称
            tablename = t.value();
        }
        return tablename;
    }


    /**
     * 驼峰写法转下划线写法
     * @param param
     * @return
     */
    private static String camelToUnderline(String param){
        if (param==null||"".equals(param.trim())){
            return "";
        }
        int len=param.length();
        StringBuilder sb=new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c=param.charAt(i);
            if (Character.isUpperCase(c)){
                if(i > 0){
                    sb.append('_');
                    sb.append(Character.toLowerCase(c));
                }else{
                    sb.append(Character.toLowerCase(c));
                }
            }else{
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
