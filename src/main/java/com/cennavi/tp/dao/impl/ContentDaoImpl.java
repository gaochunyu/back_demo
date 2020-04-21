package com.cennavi.tp.dao.impl;
import com.cennavi.tp.beans.ContentBean;
import com.cennavi.tp.common.base.dao.impl.BaseDaoImpl;
import com.cennavi.tp.dao.ContentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;


/**
 * Created by 姚文帅 on 2020/4/17 15:07.
 */
@Repository
public class ContentDaoImpl extends BaseDaoImpl<ContentBean> implements ContentDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public long insertItemReturnId(ContentBean contentBean){

        String sql = "insert into content(title,content,tags,file,create_time) values ('"+contentBean.getTitle()+"','"+contentBean.getContent()+"','"+contentBean.getTags()+"','"+contentBean.getFile()+"','"+contentBean.getCreate_time()+"')";

        //获取插入数据的自增主键
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql,new String [] {"id"});
            return ps;
        }, keyHolder);
        long insertId = keyHolder.getKey().longValue();
        return insertId;
    };

    // 根据 id 查询某条数据
    public ContentBean getItemById(int id){
        String sql = "select * from content where id = "+id;
        List<ContentBean> list = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(ContentBean.class));
        return list.size()==0?null:list.get(0);
    };


    // 修改某条数据
    public void updateItemById(int id, ContentBean contentBean){
        if(contentBean.getTitle() != null) {
            String sql = "update content set title='"+contentBean.getTitle() + "' where id = " + id;
            jdbcTemplate.update(sql);

        }
        if(contentBean.getContent() != null) {
            String sql = "update content set content='"+contentBean.getContent() + "' where id = " + id;
            jdbcTemplate.update(sql);

        }
        if(contentBean.getTags() != null) {
            String sql = "update content set tags='"+contentBean.getTags() + "' where id = " + id;
            jdbcTemplate.update(sql);

        }
        if(contentBean.getFile() != null) {
            String sql = "update content set file='"+contentBean.getFile() + "' where id = " + id;
            jdbcTemplate.update(sql);

        }

    };



}
