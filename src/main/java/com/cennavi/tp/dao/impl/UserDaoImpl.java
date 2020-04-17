package com.cennavi.tp.dao.impl;

import com.cennavi.tp.beans.User;
import com.cennavi.tp.common.base.dao.impl.BaseDaoImpl;
import com.cennavi.tp.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 */
@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public List<User> getUserList(Integer start, Integer pageSize) {
        String sql = "select id,username,password,create_time from \"public\".\"user\" limit "+pageSize+" offset "+start;
        return jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(User.class));
    }

    @Override
    public User getUserById(Integer id) {
        String sql = "select * from \"public\".\"user\" where id = "+id;
        List<User> list = jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(User.class));
        return list.size()==0?null:list.get(0);
    }
}
