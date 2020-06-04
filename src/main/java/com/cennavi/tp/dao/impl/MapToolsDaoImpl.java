package com.cennavi.tp.dao.impl;

import com.cennavi.tp.beans.MapToolsBean;
import com.cennavi.tp.beans.MapToolsBean;
import com.cennavi.tp.common.base.dao.impl.BaseDaoImpl;
import com.cennavi.tp.dao.AssistDao;
import com.cennavi.tp.dao.MapToolsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 马立伟 on 2020/6/3 22:49.
 */
@Repository
public class MapToolsDaoImpl extends BaseDaoImpl<MapToolsBean> implements MapToolsDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 插入一条新数据并返回插入数据的自增主键
    public long addMapTools(MapToolsBean mapTools) {
            String sql = "insert into mine_map_tools(name,icon,type,img,help_info,tags,status,create_time,url,self,uid) values (?,?,?,?,?,?,?,?,?,?,?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql,new String [] {"id"});
                ps.setString(1,mapTools.getName());
                ps.setString(2,mapTools.getIcon());
                ps.setString(3,mapTools.getType());
                ps.setString(4,mapTools.getImg());
                ps.setString(5, mapTools.getHelp_info());
                ps.setString(6, mapTools.getTags());
                ps.setInt(7, mapTools.getStatus());
                ps.setString(8, mapTools.getCreate_time());
                ps.setString(9, mapTools.getUrl());
                ps.setInt(10, mapTools.getSelf());
                ps.setInt(11, mapTools.getUid());
                return ps;
            }, keyHolder);
            long insertId = keyHolder.getKey().longValue();
            return insertId;
    }


    @Override
    // 更新一条信息
    public Integer updateMapTools(MapToolsBean mapTools)  {
        try {
        // 文本型字段值两边加引号，日期型两边加#，数字、逻辑两边什么都不用加。
        String sql = "UPDATE mine_map_tools SET name=?,icon=?,type=?,img=?,help_info=?,tags=?,status=?,url=?,self=?,uid=? WHERE id=?";


        KeyHolder keyHolder = new GeneratedKeyHolder();

       int i =  jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
           ps.setString(1,mapTools.getName());
           ps.setString(2,mapTools.getIcon());
           ps.setString(3,mapTools.getType());
           ps.setString(4,mapTools.getImg());
           ps.setString(5, mapTools.getHelp_info());
           ps.setString(6, mapTools.getTags());
           ps.setInt(7, mapTools.getStatus());
           ps.setString(8, mapTools.getUrl());
           ps.setInt(9, mapTools.getSelf());
           ps.setInt(10, mapTools.getUid());
           ps.setInt(11, mapTools.getId());
            return ps;
        }, keyHolder);

        return i;

        }catch (Exception e){
            e.getStackTrace();
            return -1;
        }
    }



    @Override
    // 删除一条信息
    public Integer deleteById(Integer id) {
        String sql = "delete from mine_map_tools where id = ?" ;
        int i =  jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            return ps;
        });
        return i;
    }


    // 根据 id 查询某条数据
    @Override
    public MapToolsBean getMapToolsById(Integer id){
        String sql = "select * from mine_map_tools where id = ?";
        List<MapToolsBean> list = jdbcTemplate.query(sql, new Object[]{id}, BeanPropertyRowMapper.newInstance(MapToolsBean.class));
        return list.size()==0?null:list.get(0);
    }

    @Override
    public Map<String, Object> getMapToolsList(Integer offset, Integer limit,Integer model, String type, Integer uid, String searchKey, int statusValue) {
        // 状态0录入中 1待审核 2审核通过 3被拒绝
        String selectSql = "";
        int rows = 0;
        List<MapToolsBean> list = null;
        List<Object> params = new ArrayList<>();
        params.add(limit);
        params.add(offset);
        try{
            //model: 1-浏览模式  2-审核模式   3-我的发布
            if(model == 1) {
                // 查询所有审核通过的数据总行数
                String scanSql = "select count(*) from mine_map_tools where status=2";
                rows = jdbcTemplate.queryForObject(scanSql, Integer.class);
                String scanPageSql = "SELECT * FROM mine_map_tools where status=2" + " " + selectSql +" LIMIT ? OFFSET ?";
                list = jdbcTemplate.query(scanPageSql, params.toArray(), new BeanPropertyRowMapper<>(MapToolsBean.class));
            } else if(model == 2) {
                String checkSql = "select count(*) from mine_map_tools where status=1" + " " +selectSql;
                // 查询所有待审核的数据的总行数
                rows = jdbcTemplate.queryForObject(checkSql, params.toArray(), Integer.class);
                String checkPageSql = "SELECT * FROM mine_map_tools where status=1" + " " + selectSql +" LIMIT ? OFFSET ?";
                list = jdbcTemplate.query(checkPageSql, params.toArray(), new BeanPropertyRowMapper<>(MapToolsBean.class));
            } else {
                // 查询当前用户下面的数据
                String statusSql = statusValue == 100 ? "": "and status=?";
                String mangerSql = "select count(*) from mine_map_tools where uid=?"+ " " + selectSql + " " + statusSql;
                params.add(0,uid);
                if(statusValue != 100)    params.add(statusValue);
                rows = jdbcTemplate.queryForObject(mangerSql, params.toArray(), Integer.class);
                String mangerPageSql = "select * from mine_map_tools where user_id=?"+ " " + selectSql + " " + statusSql + "LIMIT ? OFFSET ?";
                list = jdbcTemplate.query(mangerPageSql, params.toArray(), new BeanPropertyRowMapper<>(MapToolsBean.class));
            }
            Map<String,Object> map = new HashMap<>();
            map.put("sum", rows);
            map.put("dataList", list);
            map.put("rowCount", list.size());
            return map;

        } catch (Exception e){
            e.getStackTrace();
            return null;
        }
    }
}
