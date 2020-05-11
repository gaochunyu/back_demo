package com.cennavi.tp.dao.impl;

import com.cennavi.tp.beans.AssistBean;
import com.cennavi.tp.beans.ContentBean;
import com.cennavi.tp.common.base.dao.impl.BaseDaoImpl;
import com.cennavi.tp.common.result.Result;
import com.cennavi.tp.dao.AssistDao;
import com.cennavi.tp.dao.ContentDao;
//import jdk.internal.org.objectweb.asm.tree.TryCatchBlockNode;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 姚文帅 on 2020/4/29 14:33.
 */
@Repository
public class AssistDaoImpl extends BaseDaoImpl<AssistBean> implements AssistDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 插入一条新数据并返回插入数据的自增主键
    public long addAssistItem(AssistBean assistBean) {
//        try {
            String sql = "insert into assist(user_id,question,answer,create_time,category,weight,status) values (" +assistBean.getUserId()+
                    ",'"+assistBean.getQuestion()+
                    "','"+assistBean.getAnswer()+
                    "','"+assistBean.getCreateTime()+
                    "','"+assistBean.getCategory()+
                    "',"+assistBean.getWeight()+
                    ","+assistBean.getStatus()+")";

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
        try {
        // 文本型字段值两边加引号，日期型两边加#，数字、逻辑两边什么都不用加。
        String sql = "UPDATE assist SET question='"+assistBean.getQuestion()+
                "',answer='"+assistBean.getAnswer()+
                "',category='"+assistBean.getCategory()+
                "',status="+assistBean.getStatus()+
                " WHERE id="+assistBean.getId();

        int i = jdbcTemplate.update(sql);
        return i;
        }catch (Exception e){
            e.getStackTrace();
            return -1;
        }
    }


    @Override
    // 删除一条信息
    public Integer deleteAssistItem(Integer id) {
        String sql = "delete from assist where id = " + id;
        int i = jdbcTemplate.update(sql);
        return i;
    }


    // 根据 id 查询某条数据
    @Override
    public AssistBean getAssistItemById(Integer id){
        String sql = "select * from assist where id = "+id;
        List<AssistBean> list = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(AssistBean.class));
        return list.size()==0?null:list.get(0);
    }

    /**
     * 查询分页
     */
    @Override
    public Map<String,Object> getAssistList(Integer page, Integer pageSize,Integer contentType, Integer userId) {

        Integer offset = pageSize;


        int rows = 0;
        List<AssistBean> list = null;

        // 2-浏览模式  3-审核模式   4-我的发布
        if(contentType == 2) {
            //查询总行数sql
            rows = jdbcTemplate.queryForObject("select count(*) from assist", null, Integer.class);
            if (page * pageSize > rows) {
                // 超出总值的时候，需要计算offse的值
                offset = rows - page * pageSize + pageSize;
            }

            int startIndex = (page-1) * pageSize;//提取分页开始索引
            list = jdbcTemplate.query("SELECT * FROM assist  ORDER BY id ASC LIMIT ? OFFSET ?", new Object[]{offset,startIndex}, new BeanPropertyRowMapper<>(AssistBean.class));


        } else if(contentType == 3) {


            rows = jdbcTemplate.queryForObject("select count(*) from assist where status=1", null, Integer.class);
            if (page * pageSize > rows) {
                // 超出总值的时候，需要计算offse的值
                offset = rows - page * pageSize + pageSize;
            }

            int startIndex = (page-1) * pageSize;//提取分页开始索引
            list = jdbcTemplate.query("SELECT * FROM assist where status=1 ORDER BY id ASC LIMIT ? OFFSET ?", new Object[]{offset,startIndex}, new BeanPropertyRowMapper<>(AssistBean.class));


        } else {
            String sql = "select count(*) from assist where user_id=" + userId;
            rows = jdbcTemplate.queryForObject(sql, null, Integer.class);
            if (page * pageSize > rows) {
                // 超出总值的时候，需要计算offse的值
                offset = rows - page * pageSize + pageSize;
            }

            int startIndex = (page-1) * pageSize;//提取分页开始索引
            String sqlList = "SELECT * FROM assist WHERE user_id=? ORDER BY id ASC LIMIT ? OFFSET ?";

            list = jdbcTemplate.query(sqlList, new Object[]{userId,offset,startIndex}, new BeanPropertyRowMapper<>(AssistBean.class));

        }

        Map<String,Object> map = new HashMap<>();
        map.put("totalNumber", rows);
        map.put("dataList", list);
        map.put("pageNumber", page);
        map.put("pageSize", pageSize);

        return map;

    }

    @Override
    public Integer updateAssistItemWeightById(Integer id, Boolean updateType) {
        // 首先根据id去获取数据库中保存的id

        String querySql =  "select weight from assist where id = "+id;
        int weight = jdbcTemplate.queryForObject(querySql, null, Integer.class);
        if(updateType) {
            weight=weight+1;
        } else {
            weight=weight-1;
        }

        String sql = "UPDATE assist SET weight="+ weight +
                " WHERE id="+id;

        int i = jdbcTemplate.update(sql);
        return i;

    }

    // 审核修改assist的状态
    @Override
    public Integer checkAssistItem(Integer id, Boolean checkType) {
        try {
            // 2审核通过 3被拒绝
            int status = checkType? 2 : 3;
            String sql = "UPDATE assist SET status="+status+
                    " WHERE id="+id;

            int i = jdbcTemplate.update(sql);
            return i;


        } catch (Exception e){
            e.getStackTrace();
            return -1;
        }
    }
}
