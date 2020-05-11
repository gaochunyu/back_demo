package com.cennavi.tp.dao.impl;

import com.cennavi.tp.beans.ResourcesBean;
import com.cennavi.tp.common.base.dao.impl.BaseDaoImpl;
import com.cennavi.tp.dao.ResourcesDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ResourcesDaoImpl extends BaseDaoImpl<ResourcesBean> implements ResourcesDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public ResourcesBean getResourcesById(Integer id) {
        String sql = "select * from resources where id = " + id;
        List<ResourcesBean> list = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(ResourcesBean.class));
        return list.size() == 0 ? null : list.get(0);
    }

    @Override
    public int addResourcesItem(ResourcesBean resourcesBean) {
        String sql = "insert into resources (uid,tags,name,file,description,link,create_time,status,type,views) values ("
                + resourcesBean.getUid() + ",'" + resourcesBean.getTags() + "','" + resourcesBean.getName()
                + "','" + resourcesBean.getFile() + "','" + resourcesBean.getDescription() + "','" + resourcesBean.getLink() + "','"
                + resourcesBean.getCreate_time() + "'," + 1 + "," + resourcesBean.getType() + "," + 0 + ") RETURNING id";
        int result = jdbcTemplate.queryForObject(sql,Integer.class);
        return result;

    }

    @Override
    public boolean updateResourcesItem(ResourcesBean resourcesBean) {
        String sql = "update resources set name ='" + resourcesBean.getName()
                + "', uid = " + resourcesBean.getUid()
                + ", tags = '" + resourcesBean.getTags()
                + "', file = '" + resourcesBean.getFile()
                + "', description = '" + resourcesBean.getDescription()
                + "', link = '" + resourcesBean.getLink()
                + "', create_time = '" + resourcesBean.getCreate_time()
                + "', type = " + resourcesBean.getType()
                + ", status = 1"
                +" where id = " +resourcesBean.getId();
        int result = jdbcTemplate.update(sql);
        return result == 1?true:false;
    }

    @Override
    public int getResourcesCount(String tags,String status){
        String sql = "";
        String  statusCondition = "";
        if(status.length() == 1){
            statusCondition = "where status = " + Integer.parseInt(status);
        }else if(status.length() == 2){
            char[]  arr = status.toCharArray();
            String s1 = String.valueOf(arr[0]);
            String s2 = String.valueOf(arr[1]);
            statusCondition = "where status = " + Integer.parseInt(s1) +" or status = "+ Integer.parseInt(s2);
        }else {
            statusCondition = "";
        }
        if (StringUtils.isNotBlank(tags)&&tags != null) {//判断检索条件是否为空
            if(StringUtils.isBlank(statusCondition)){
                sql = "select count(*) from resources where tags like '%" +tags +"%'";

            }else {
                sql = "select count(*) from resources "+statusCondition+" and tags like '%" +tags +"%'";
            }
        }else{
            sql = "select count(*) from resources "+statusCondition;
        }
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    @Override
    public List<ResourcesBean> getResourcesList(Integer start, Integer pageSize, String tags, String status){
        String sql = "";
        String  statusCondition = "";
        if(status.length() == 1){
            statusCondition = "where status = " + Integer.parseInt(status);
        }else if(status.length() == 2){
            char[]  arr = status.toCharArray();
            String s1 = String.valueOf(arr[0]);
            String s2 = String.valueOf(arr[1]);
            statusCondition = "where status = " + Integer.parseInt(s1) +" or status = "+ Integer.parseInt(s2);
        }else {
            statusCondition = "";
        }
        if (StringUtils.isNotBlank(tags)&&tags != null) {//判断检索条件是否为空
            if(StringUtils.isBlank(statusCondition)){
                sql = "select * from resources where tags like '%" +tags +"%' order by create_time desc limit " +pageSize+" offset "+start;
            }else{
                sql = "select * from resources "+statusCondition+" and tags like '%" +tags +"%' order by create_time desc limit " +pageSize+" offset "+start;
            }
        }else{
            sql = "select * from resources "+statusCondition+" order by create_time desc limit " +pageSize+" offset "+start;
        }
        return jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(ResourcesBean.class));
    }

    @Override
    public List<ResourcesBean> getTopFiveByCreateTime(){
        String sql = "select * from resources where status = 2 order by create_time desc limit 5";
        return jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(ResourcesBean.class));

    }

    @Override
    public List<ResourcesBean> getTopFiveByViews(){
        String sql = "select * from resources where status = 2 order by views desc limit 5";
        return jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(ResourcesBean.class));

    }

    @Override
    public Boolean updateResourcesStatus(Integer id,Integer status) {
        String sql = "update resources set status = "+status +" where id = "+id;
        int result = jdbcTemplate.update(sql);
        return result == 1?true:false;
    }

    @Override
    public Boolean updateResourcesViews(Integer id,Integer views) {

        String sql = "update resources set views = "+ views +" where id = "+id;
        int result = jdbcTemplate.update(sql);
        return result == 1?true:false;
    }

}
