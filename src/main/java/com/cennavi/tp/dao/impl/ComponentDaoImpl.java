package com.cennavi.tp.dao.impl;

import com.cennavi.tp.beans.ComponentBean;
import com.cennavi.tp.beans.ComponentTypeBean;
import com.cennavi.tp.dao.ComponentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: chenfeng
 * @date: 2020/4/30 10:42
 */
@Repository
public class ComponentDaoImpl implements ComponentDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int addComponent(ComponentBean componentBean) {
        String sql = "insert into component(uid, name, type, tags, cover_img, img_list, content, create_time, visit_url, file_url, status) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, componentBean.getUid());
            ps.setString(2, componentBean.getName());
            ps.setString(3, componentBean.getType());
            ps.setString(4, componentBean.getTags());
            ps.setString(5, componentBean.getCover_img());
            ps.setString(6, componentBean.getImg_list());
            ps.setString(7, componentBean.getContent());
            ps.setString(8, componentBean.getCreate_time());
            ps.setString(9, componentBean.getVisit_url());
            ps.setString(10, componentBean.getFile_url());
            ps.setString(11, componentBean.getStatus());
            return ps;
        });
    }

    @Override
    public int deleteComponent(Integer id) {
        String sql = "delete from component where id = ?";
        return jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            return ps;
        });
    }

    @Override
    public List<ComponentTypeBean> getComponentTypeList() {
        String sql = "select * from component_type";
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(ComponentTypeBean.class));
    }

    @Override
    public List<ComponentBean> getComponentList(Integer startNo, Integer pageSize, String tags, String status, String type, Integer uid) {
        List<Object> paramList = new ArrayList<>();
        String sql = "select * from component where ";

        if (type != null && type.length() > 0) {
            String[] typeArr = type.split(",");
            sql += "type in (";
            for(String typeItem : typeArr) {
                sql += "?,";
                paramList.add(typeItem);
            }
            sql = sql.substring(0, sql.length() - 1) + ") ";
        }

        if (status != null && status.length() > 0) {
            String[] statusArr = status.split(",");
            sql += "and status in (";
            for(String statusItem : statusArr) {
                sql += "?,";
                paramList.add(statusItem);
            }
            sql = sql.substring(0, sql.length() - 1) + ") ";
        }

        // 关联查询用户发布组件
        if(uid != 0) {
            paramList.add(uid);
            sql += "and uid = ? ";
        }

        if(tags != null && tags.length() > 0) {
            paramList.add(tags);
            sql += "and tags like concat('%',?,'%') ";
        }
        paramList.add(pageSize);
        paramList.add(startNo);
        sql +=  "order by create_time desc limit ? offset ?";
        return jdbcTemplate.query(sql, paramList.toArray(), BeanPropertyRowMapper.newInstance(ComponentBean.class));
    }

    @Override
    public int getComponentCount(String tags, String status, String type, Integer uid) {
        List<Object> paramList = new ArrayList<>();
        String sql = "select count(*) from component where ";

        if (type != null && type.length() > 0) {
            String[] typeArr = type.split(",");
            sql += "type in (";
            for(String typeItem : typeArr) {
                sql += "?,";
                paramList.add(typeItem);
            }
            sql = sql.substring(0, sql.length() - 1) + ") ";
        }

        if (status != null && status.length() > 0) {
            String[] statusArr = status.split(",");
            sql += "and status in (";
            for(String statusItem : statusArr) {
                sql += "?,";
                paramList.add(statusItem);
            }
            sql = sql.substring(0, sql.length() - 1) + ") ";
        }

        // 关联查询用户发布组件
        if(uid != 0) {
            paramList.add(uid);
            sql += "and uid = ? ";
        }

        if(tags != null && tags.length() > 0) {
            paramList.add(tags);
            sql += "and tags like concat('%',?,'%')";
        }
        return jdbcTemplate.queryForObject(sql, paramList.toArray(), Integer.class);
    }

    @Override
    public int updateComponent(ComponentBean componentBean) {
        String sql = "update component set name=?, type=?, tags=?, content=?, cover_img=?, img_list=?, visit_url=?, file_url=? where id = ?";
        return jdbcTemplate.update( conn -> {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, componentBean.getName());
            ps.setString(2, componentBean.getType());
            ps.setString(3, componentBean.getTags());
            ps.setString(4, componentBean.getContent());
            ps.setString(5, componentBean.getCover_img());
            ps.setString(6, componentBean.getImg_list());
            ps.setString(7, componentBean.getVisit_url());
            ps.setString(8, componentBean.getFile_url());
            ps.setInt(9, componentBean.getId());
            return ps;
        });
    }

    @Override
    public ComponentBean getComponentById(Integer id) {
        String sql = "select * from component where id = ?";
        Integer[] arr = {id};
        List<ComponentBean> list = jdbcTemplate.query(sql, arr, BeanPropertyRowMapper.newInstance(ComponentBean.class));
        return list.size() == 0 ? null : list.get(0);
    }

    @Override
    public int updateModuleStatus(Integer id, Boolean checkResult) {
        // 2-已审核 3-拒绝
        String status = checkResult ? "2" : "3";
        String sql = "UPDATE component SET status=? WHERE id=?";
        return jdbcTemplate.update( conn -> {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ps.setInt(2, id);
            return ps;
        });
    }

    private Map<String, Object> formatFilterParam(String param, List<Object> paramList) {
        Map<String, Object> map = new HashMap<>();
        String[] paramArr = param.split(",");
        String sql = "";
        List<Object> list = paramList;
        for (String paramItem : paramArr) {
            sql += "?,";
            paramList.add(paramItem);
        }
        sql = sql.substring(0, sql.length() - 1) + ")";
        map.put("sql", sql);
        map.put("list", list);
        return map;
    }
}
