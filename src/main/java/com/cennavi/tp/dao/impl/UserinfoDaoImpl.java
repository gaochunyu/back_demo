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
    public List<UserinfoBean> getUsers(Integer start, Integer pageSize) {
        String sql = "select id,username,password,create_time,enable,role from userinfo order by create_time desc limit "+pageSize+" offset "+start;
        return jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(UserinfoBean.class));
    }

    @Override
    public int getUsersCount(Integer start, Integer pageSize) {
        String sql = "select count(*) from userinfo";
        return jdbcTemplate.queryForObject(sql,Integer.class);
    }


    @Override
    public UserinfoBean getUserById(Integer id) {
        String sql = "select * from userinfo where id = "+id;
        List<UserinfoBean> list = jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(UserinfoBean.class));
        return list.size()==0?null:list.get(0);
    }

    @Override
    public List<UserinfoBean> login(String username) {
        String sql = "select * from userinfo where username = '"+username+"' and enable = 0 ";
        return jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(UserinfoBean.class));
    }

    @Override
    public int getUsersCountByUserName(String username) {
        String sql = "select count(*) from userinfo where username = '"+username+"'";
        return jdbcTemplate.queryForObject(sql,Integer.class);
    }

    @Override
    public int getUsersCountByIdAndUserName(Integer id, String username) {
        String sql = "select count(*) from userinfo where id != "+id+" and username = '"+username+"'";
        return jdbcTemplate.queryForObject(sql,Integer.class);
    }
}
