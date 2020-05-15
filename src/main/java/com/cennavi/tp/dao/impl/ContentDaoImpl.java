package com.cennavi.tp.dao.impl;
import com.cennavi.tp.beans.ContentBean;
import com.cennavi.tp.common.base.dao.impl.BaseDaoImpl;
import com.cennavi.tp.dao.ContentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;


/**
 * Created by 姚文帅 on 2020/4/17 15:07.
 */
@Repository
public class ContentDaoImpl extends BaseDaoImpl<ContentBean> implements ContentDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int insertItem(ContentBean contentBean){

        String sql = "insert into content(id,title,sub_title,content,tags,file,create_time,uid) values (?,?,?,?,?,?,?,?)";
        int result =  jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,contentBean.getId());
            ps.setString(2,contentBean.getTitle());
            ps.setString(3, contentBean.getSub_title());
            ps.setString(4, contentBean.getContent());
            ps.setString(5, contentBean.getTags());
            ps.setString(6, contentBean.getFile());
            ps.setString(7, contentBean.getCreate_time());
            ps.setInt(8, contentBean.getUid());
            return ps;
        });
//        int result = jdbcTemplate.update(sql, new Object[]{,,,,,,,}, Integer.class);

        return result;
    };

    // 根据 id 查询某条数据
    public ContentBean getItemById(int id){
        String sql = "select * from content where id = ?";
        List<ContentBean> list = jdbcTemplate.query(sql, new Object[]{id},BeanPropertyRowMapper.newInstance(ContentBean.class));
        return list.size()==0?null:list.get(0);
    };


    // 修改某条数据
    public void updateItemById(int id, ContentBean contentBean){
        String sql = "update content set sub_title=?, content= ?,file=?,tags=? where id = ?";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,contentBean.getSub_title());
            ps.setString(2,contentBean.getContent());
            ps.setString(3, contentBean.getFile());
            ps.setString(4, contentBean.getTags());
            ps.setInt(5, contentBean.getId());
            return ps;
        });
    }

    @Override
    public List<ContentBean> getContentsByTags(String tags) {
        String sql = "select c.id, c.title,c.tags,c.create_time,c.sub_title,c.uid,u.username username from content c inner" +
                " join userinfo u on c.uid = u.id inner join menu m on c.id = m.id and m.status = 2 where c.tags like '%?%'";
        return jdbcTemplate.query(sql,new Object[]{tags},BeanPropertyRowMapper.newInstance(ContentBean.class));

    };

}
