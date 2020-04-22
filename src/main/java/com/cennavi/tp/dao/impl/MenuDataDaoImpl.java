package com.cennavi.tp.dao.impl;

import com.cennavi.tp.beans.MenuSubtitleBean;
import com.cennavi.tp.common.base.dao.impl.BaseDaoImpl;
import com.cennavi.tp.dao.MenuDataDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhangxin  on 2020/4/17.
 */
@Repository
public class MenuDataDaoImpl extends BaseDaoImpl<MenuSubtitleBean> implements MenuDataDao{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<MenuSubtitleBean> getMenuSubtitles() {
        String sql = "select id , name , parent ,sort, uid, create_time createTime, status from menu ";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(MenuSubtitleBean.class));
    }

    @Override
    public List<MenuSubtitleBean> getParentMenuList(Integer rootValue) {
        String sql = "select * from menu where parent = " + rootValue;
        return jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(MenuSubtitleBean.class));
    }

    @Override
    public MenuSubtitleBean getMenuSubtitleBeanById(Integer id) {
        String sql = "select * from menu where id = " + id;
        List<MenuSubtitleBean> list = jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(MenuSubtitleBean.class));
        return list.size()==0?null:list.get(0);
    }

    @Override
    public Integer deleteMenuSubtitleBeanById(Integer id) {
        String sql = "delete from menu where id = " + id;
        int i = jdbcTemplate.update(sql);
        return i;
    }

}
