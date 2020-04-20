package com.cennavi.tp.dao.impl;

import com.cennavi.tp.beans.UserinfoBean;
import com.cennavi.tp.common.base.dao.impl.BaseDaoImpl;
import com.cennavi.tp.dao.UserinfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 */
@Repository
public class UserinfoDaoImpl extends BaseDaoImpl<UserinfoBean> implements UserinfoDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public List<UserinfoBean> getUserList(Integer start, Integer pageSize) {
        String sql = "select id,username,password,create_time from userinfo limit "+pageSize+" offset "+start;
        return jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(UserinfoBean.class));
    }

    @Override
    public UserinfoBean getUserById(Integer id) {
        String sql = "select * from userinfo where id = "+id;
        List<UserinfoBean> list = jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(UserinfoBean.class));
        return list.size()==0?null:list.get(0);
    }
}
