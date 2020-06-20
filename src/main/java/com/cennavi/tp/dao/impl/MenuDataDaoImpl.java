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
    public List<MenuSubtitleBean> getMenuSubtitles(Integer uid,String model) {
        String sql = "select m.id , m.name , m.parent ,m.sort, m.uid, m.create_time createTime, m.status,m.level,u.username " +
                "from menu m inner join userinfo u on m.uid = u.id ";
        if("visit".equals(model)){
            sql+="and (m.status = 2  or level =1 or level =2)";
        }else if("verify".equals(model)){
            sql+="and (m.status = 1  or level =1 or level =2)";
        }else if("mydata".equals(model)){
            sql+="and (m.uid = "+uid+" and level !=1 and level !=2) ";
        }
        sql+=" order by sort asc ";

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

    @Override
    public Integer updateMenu(MenuSubtitleBean menu) {
        String sql = " UPDATE menu SET name='"+menu.getName()+"',parent="+menu.getParent()+",sort="+menu.getSort()+"," +
                "uid="+menu.getUid()+",create_time='"+menu.getCreateTime()+"',status="+menu.getStatus()+",level="+menu.getLevel()+
                " WHERE id="+menu.getId();
        int i = jdbcTemplate.update(sql);
        return i;
    }

    @Override
    public Integer updateMenuStatus(int id, int status){

        String sql = " UPDATE menu SET status="+status+" WHERE id="+id;
        int i = jdbcTemplate.update(sql);
        return i;
    }

}
