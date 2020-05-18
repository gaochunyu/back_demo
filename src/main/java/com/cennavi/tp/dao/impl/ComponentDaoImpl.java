package com.cennavi.tp.dao.impl;

import com.cennavi.tp.beans.ComponentBean;
import com.cennavi.tp.beans.ComponentImgBean;
import com.cennavi.tp.beans.ComponentTypeBean;
import com.cennavi.tp.dao.ComponentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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

    public int addComponent(ComponentBean componentBean) {
        String sql = "insert into component(uid, name, type, tags, cover_img, content, create_time, visit_url, file_url, status) values ("
                + componentBean.getUid() + ",'" + componentBean.getName() + "',"+ componentBean.getType() + ",'" + componentBean.getTags() + "','" + componentBean.getCover_img() + "','" + componentBean.getContent() + "','" + componentBean.getCreate_time() + "','" + componentBean.getVisit_url() + "','" + componentBean.getFile_url() + "'," + componentBean.getStatus() + ")";
        // 获取component_id
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update( connection -> connection.prepareStatement(sql, new String [] {"id"}), keyHolder);
        return keyHolder.getKey().intValue();
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


    @Override
    public boolean addComponentImg(ComponentImgBean componentImgBean) {
        String sql = "insert into component_img (cid, img_url) values (" + componentImgBean.getCid() + ",'" + componentImgBean.getImg_url() + "')";
        int count = jdbcTemplate.update(sql);
        return count > 0;
    }

}
