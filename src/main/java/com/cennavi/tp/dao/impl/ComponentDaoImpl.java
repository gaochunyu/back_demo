package com.cennavi.tp.dao.impl;

import com.cennavi.tp.beans.ComponentBean;
import com.cennavi.tp.beans.ComponentImgBean;
import com.cennavi.tp.beans.ComponentTypeBean;
import com.cennavi.tp.dao.ComponentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: chenfeng
 * @date: 2020/4/30 10:42
 */
@Repository
public class ComponentDaoImpl implements ComponentDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 添加组件
    public boolean addComponent(ComponentBean componentBean, List<ComponentImgBean> list, List<String> imgDataPathList) {
        String sql = "insert into component(uid, name, type, tags, cover_img, content, create_time, visit_url, file_url, status) values ("
                + componentBean.getUid() + ",'" + componentBean.getName() + "',"+ componentBean.getType() + ",'" + componentBean.getTags() + "','" + componentBean.getCover_img() + "','" + componentBean.getContent() + "','" + componentBean.getCreate_time() + "','" + componentBean.getVisit_url() + "','" + componentBean.getFile_url() + "'," + componentBean.getStatus() + ")";
        int count = jdbcTemplate.update(sql);
        return count > 0;
    }

    @Override
    public Integer delComponent(Integer id, Integer uid) {
        String sql = "delete from component where id = "+ id +" and uid = "+ uid;
        Integer count = jdbcTemplate.update(sql);
        return count;
    }

    @Override
    public List<ComponentTypeBean> getComponentTypeList() {
        String sql = "select * from component_type";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(ComponentTypeBean.class));
    }

}
