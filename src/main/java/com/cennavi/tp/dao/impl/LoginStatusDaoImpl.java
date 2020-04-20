package com.cennavi.tp.dao.impl;

import com.cennavi.tp.beans.LoginStatusBean;
import com.cennavi.tp.beans.UserinfoBean;
import com.cennavi.tp.common.base.dao.impl.BaseDaoImpl;
import com.cennavi.tp.dao.LoginStatusDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 */
@Repository
public class LoginStatusDaoImpl extends BaseDaoImpl<LoginStatusBean> implements LoginStatusDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public LoginStatusBean checkUserLoginByToken(String token) {
        String sql = "select * from login_status where token = '"+token+"' and flag =1 ";
        List<LoginStatusBean> list = jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(LoginStatusBean.class));
        return list.size()==0?null:list.get(0);
    }

    @Override
    public void updateLastVisitTime(String token,String time) {
        String sql = "update login_status set login_time = '"+time+"' where token = '"+token+"'";
        jdbcTemplate.execute(sql);
    }
}
