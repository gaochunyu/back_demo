package com.cennavi.tp.dao.impl;

import com.cennavi.tp.beans.AssistBean;
import com.cennavi.tp.beans.MenuSubtitleBean;
import com.cennavi.tp.common.base.dao.impl.BaseDaoImpl;
import com.cennavi.tp.dao.AssistDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
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

//    @Override
//    public List<AssistBean> getAssistItemList(Integer page) {
//        String sql = "select m.id , m.name , m.parent ,m.sort, m.uid, m.create_time createTime, m.status,u.username " +
//                "from menu m inner join userinfo u on m.uid = u.id ";
////        if("visit".equals(model)){
////            sql+="and m.status = 2 ";
////        }else if("verify".equals(model)){
////            sql+="and m.status = 1 ";
////        }else if("mydata".equals(model)){
////            sql+="and m.uid = "+uid;
////        }
////        sql+=" order by sort asc ";
////
//        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(MenuSubtitleBean.class));
//    }



}
