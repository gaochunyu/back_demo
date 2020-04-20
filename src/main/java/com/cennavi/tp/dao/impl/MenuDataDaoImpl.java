package com.cennavi.tp.dao.impl;

import com.cennavi.tp.beans.MenuSubtitle;
import com.cennavi.tp.common.dao.impl.BaseDaoImpl;
import com.cennavi.tp.dao.MenuDataDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * Created by zhangxin  on 2020/4/17.
 */
public class MenuDataDaoImpl extends BaseDaoImpl<MenuSubtitle> implements MenuDataDao{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<MenuSubtitle> getMenuSubtitles() {
        String sql = "select id , name , parent ,sort from menu";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(MenuSubtitle.class));
    }
}
