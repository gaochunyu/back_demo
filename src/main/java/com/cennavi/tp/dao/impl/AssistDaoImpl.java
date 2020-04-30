package com.cennavi.tp.dao.impl;

import com.cennavi.tp.beans.AssistBean;
import com.cennavi.tp.common.base.dao.impl.BaseDaoImpl;
import com.cennavi.tp.dao.AssistDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 姚文帅 on 2020/4/29 14:33.
 */
@Repository
public class AssistDaoImpl extends BaseDaoImpl<AssistBean> implements AssistDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 插入一条新数据并返回插入数据的自增主键
    public long addAssistItem(AssistBean assistBean) {

        String sql = "insert into content(user_id,question,answer,create_time,category,weight) values (" +assistBean.getUser_id()+
                ",'"+assistBean.getQuestion()+
                "','"+assistBean.getAnswer()+
                "','"+assistBean.getCreateTime()+
                "','"+assistBean.getCategory()+
                "',"+assistBean.getWeight()+")";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql,new String [] {"id"});
            return ps;
        }, keyHolder);

        long insertId = keyHolder.getKey().longValue();

        return insertId;
    }


    @Override
    // 更新一条信息
    public Integer updateAssistItem(AssistBean assistBean)  {
        // 文本型字段值两边加引号，日期型两边加#，数字、逻辑两边什么都不用加。
        String sql = "UPDATE assist SET question='"+assistBean.getQuestion()+
                "',answer='"+assistBean.getAnswer()+
                "',create_time='"+assistBean.getQuestion()+
                "',category='"+assistBean.getCategory()+
                "',weight="+assistBean.getWeight()+
                " WHERE id="+assistBean.getId();
        int i = jdbcTemplate.update(sql);
        return i;
    }


    @Override
    // 删除一条信息
    public Integer deleteAssistItem(Integer id) {
        String sql = "delete from assist where id = " + id;
        int i = jdbcTemplate.update(sql);
        return i;
    }


    // 根据 id 查询某条数据
    public AssistBean getAssistItemById(Integer id){
        String sql = "select * from assist where id = "+id;
        List<AssistBean> list = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(AssistBean.class));
        return list.size()==0?null:list.get(0);
    }

    @Override
    // 分页查询
    public List<AssistBean> getAssistList(Integer page, Integer pageSize, Integer pageIndex)  {
//        SELECT * FROM
//                (
//                        SELECT A.*, ROWNUM RN
//                        FROM (SELECT * FROM TABLE_NAME) A  创建临时表
//                        WHERE ROWNUM <= 40
//                )
//        WHERE RN >= 21
//        查询记录 21至40的数据记录
//
//        页数page pagesize
//
//        开始记录值 RN>=(page-1)*pagesize+1
//
//        结束记录索引值ROWNUM<=page*pagesize
//        Integer start = (page-1)*pagesize+1;
//        Integer end = page*pagesize;

        String sql = "SELECT * FROM( SELECT ROWNUM NUM, * FROM assist) WHERE NUM >="
                    + pageIndex + "*" + pageSize + "+1 and num<=(" + pageIndex + "+1)*" + pageSize;

//        String sql="SELECT * FROM (SELECT A.*, ROWNUM RN FROM (SELECT * FROM assist) A WHERE ROWNUM <=" + end +
//                " ) WHERE RN >=" +start;

        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(AssistBean.class));
    }

    public Object queryOneColumnForSigetonRow(String sql,Object[] params,Class cla){
        Object result=null;
        try{
            if(params==null||params.length>0){
                result=jdbcTemplate.queryForObject(sql,params,cla);
            }else{
                result=jdbcTemplate.queryForObject(sql,cla);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return result;
    }


    /**
     * 查询分页
     */
    public List<AssistBean> getAssistList(int page, int pagerow) {

        String rowsql="select count(*) from assist";   //查询总行数sql
//
        int pages = 0;   //总页数
        int rows=(Integer)queryOneColumnForSigetonRow(rowsql, null, Integer.class);  //查询总行数
        // 判断页数,如果是页大小的整数倍就为rows/pageRow如果不是整数倍就为rows/pageRow+1
        if (rows % pagerow == 0) {
            pages = rows / pagerow;
        } else {
            pages = rows / pagerow + 1;
        }
        Integer start = 0;


        String sql = "select * from persons limit 10 offset" + start;
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(AssistBean.class));


    }

}
