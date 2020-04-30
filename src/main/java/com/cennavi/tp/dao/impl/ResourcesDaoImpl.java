package com.cennavi.tp.dao.impl;

import com.cennavi.tp.beans.ResourcesBean;
import com.cennavi.tp.common.base.dao.impl.BaseDaoImpl;
import com.cennavi.tp.dao.ResourcesDao;
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
        String sql = "insert into resources (id,uid,tags,name,file,description,link,create_time,status,type) values ('" +
                resourcesBean.getId() + "','" + resourcesBean.getUid() + "','" + resourcesBean.getTags() + "','" + resourcesBean.getName()
                + "','" + resourcesBean.getFile() + "','" + resourcesBean.getDescription() + "','" + resourcesBean.getLink() + "','"
                + resourcesBean.getCreate_time() + "','" + 0 + "','" + resourcesBean.getType() + "','" + 0 + ")";
        int result = jdbcTemplate.update(sql);
        return result;
    }

    @Override
    public int updateResourcesItem(ResourcesBean resourcesBean) {
        String sql = "update resources set name ='" + resourcesBean.getName()
                + "', tags = '" + resourcesBean.getTags()
                + "', file = '" + resourcesBean.getFile()
                + "', description = '" + resourcesBean.getDescription()
                + "', link = '" + resourcesBean.getLink()
                + "', create_time = '" + resourcesBean.getCreate_time()
                + "', type = '" + resourcesBean.getType()
                +"'where id =" +resourcesBean.getId();
        int result = jdbcTemplate.update(sql);
        return result;
    }

    @Override
    public int getResourcesCount(){
        String sql = "select count(*) from resources";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    @Override
    public List<ResourcesBean> getResourcesList(Integer start, Integer pageSize, String tags){
        String sql = "select * from resources order by create_time desc limit " +pageSize+" offset "+start+"where status = 2 and tags like '%" +tags +"'%";
        return jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(ResourcesBean.class));
    }

}
