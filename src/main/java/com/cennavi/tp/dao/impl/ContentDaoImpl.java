package com.cennavi.tp.dao.impl;

import com.cennavi.tp.beans.BaseRtic;
import com.cennavi.tp.beans.ContentBean;
import com.cennavi.tp.common.PageResult;
import com.cennavi.tp.common.dao.BaseDao;
import com.cennavi.tp.common.dao.impl.BaseDaoImpl;
import com.cennavi.tp.dao.BaseDataDao;
import com.cennavi.tp.dao.ContentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


/**
 * Created by 姚文帅 on 2020/4/17 15:07.
 */
@Repository
public class ContentDaoImpl extends BaseDaoImpl<ContentBean> implements ContentDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ContentBean getItemById(int id){
        String sql = "select id from content where id = ? ";
        ContentBean contentBean = jdbcTemplate.query(sql, new myRowMapper(), id);
        return contentBean;
    };

//    public void save(ContentBean contentBean){
//        String sql = "insert into content values (?,?,?,?,?,?)";
//        int count = jdbcTemplate.update(sql,1,contentBean.getTitle(),contentBean.getContent(),contentBean.getTags(),contentBean.getFile(),contentBean.getCreate_time());
//        System.out.println(count);
////        return count;
//
//    }

}
