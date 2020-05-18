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

import java.sql.PreparedStatement;
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
    public boolean addComponent(ComponentBean componentBean, List<ComponentImgBean> list) {
        boolean flag = false;
        // 新增component信息
        String sql = "insert into component(uid, name, type, tags, cover_img, content, create_time, visit_url, file_url, status) values ("
                + componentBean.getUid() + ",'" + componentBean.getName() + "',"+ componentBean.getType() + ",'" + componentBean.getTags() + "','" + componentBean.getCover_img() + "','" + componentBean.getContent() + "','" + componentBean.getCreate_time() + "','" + componentBean.getVisit_url() + "','" + componentBean.getFile_url() + "'," + componentBean.getStatus() + ")";

        // 获取component_id
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update( connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String [] {"id"});
            return ps;
        }, keyHolder);
        int insertId = keyHolder.getKey().intValue();
        // 组件信息插入成功
        if (insertId > 0) {
            flag = true;
            // 如果有展示图则新增展示图
            if (list.size() > 0) {
                // 新增component_img信息
                String imgSql = "insert into component_img (cid, img_url) values ";
                for (ComponentImgBean componentImgBean : list) {
                    imgSql += "(" + insertId + ",'" + componentImgBean.getImg_url() + "'),";
                }
                imgSql = imgSql.substring(0, imgSql.length() - 1);
                int count = jdbcTemplate.update(imgSql);
                if (count <= 0) {
                    flag = false;
                }
            }
        }
        return flag;
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
