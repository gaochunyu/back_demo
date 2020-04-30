package com.cennavi.tp.dao.impl;
import com.cennavi.tp.beans.ContentBean;
import com.cennavi.tp.common.base.dao.impl.BaseDaoImpl;
import com.cennavi.tp.dao.ContentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.PreparedStatementCreator;
//import org.springframework.jdbc.support.GeneratedKeyHolder;
//import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import java.sql.Statement;
import java.util.List;
import java.util.Map;
//import java.util.Objects;


/**
 * Created by 姚文帅 on 2020/4/17 15:07.
 */
@Repository
public class ContentDaoImpl extends BaseDaoImpl<ContentBean> implements ContentDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int insertItem(ContentBean contentBean){

        String sql = "insert into content(id,title,sub_title,content,tags,file,create_time,uid) values ('"+contentBean.getId()+"','"+contentBean.getTitle()+"','"+contentBean.getSub_title()+"','"+contentBean.getContent()+"','"+contentBean.getTags()+"','"+contentBean.getFile()+"','"+contentBean.getCreate_time()+"',"+contentBean.getUid()+")";
        int result = jdbcTemplate.update(sql);
        return result;
    };

    // 根据 id 查询某条数据
    public ContentBean getItemById(int id){
        String sql = "select * from content where id = "+id;
        List<ContentBean> list = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(ContentBean.class));
        return list.size()==0?null:list.get(0);
    };


    // 修改某条数据
    public void updateItemById(int id, ContentBean contentBean){
        String sql = "update content set sub_title='"+contentBean.getSub_title() +
                "', content='"+contentBean.getContent() +
                "', file='"+contentBean.getFile() +
                "', tags='"+contentBean.getTags() +
                "' where id = "+contentBean.getId();
            jdbcTemplate.update(sql);
    }

    @Override
    public List<ContentBean> getContentsByTags(String tags) {
        String sql = "select c.id, c.title,c.tags,c.create_time,c.sub_title,c.uid,u.username username from content c inner" +
                " join userinfo u on c.uid = u.id inner join menu m on c.id = m.id and m.status = 2 where c.tags like '%"+tags+"%'";
        return jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(ContentBean.class));

    };

}
