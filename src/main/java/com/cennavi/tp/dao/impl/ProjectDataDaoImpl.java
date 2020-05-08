package com.cennavi.tp.dao.impl;

import com.cennavi.tp.beans.ProjectBean;
import com.cennavi.tp.common.base.dao.impl.BaseDaoImpl;
import com.cennavi.tp.dao.ProjectDataDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProjectDataDaoImpl extends BaseDaoImpl<ProjectBean> implements ProjectDataDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<ProjectBean> getProjectList(Integer limitSize,Integer offsetNum ,String tradeType ,String projectType) {
        String sql = "select info.id , info.name , info.trade_type_id , info.project_type , info.content , info.visit_url , info.sort , info.main_img , info.creat_time , imgs.url FROM project_info as info LEFT JOIN project_imgs as imgs on info.id = imgs.project_id where trade_type_id in ("+ tradeType +") and project_type in ("+ projectType +") LIMIT "+ limitSize +" OFFSET " + offsetNum;
        return jdbcTemplate.query(sql , BeanPropertyRowMapper.newInstance(ProjectBean.class));
    }

    @Override
    public Integer getProjectListNum(String tradeType , String projectType) {
        String sql = "select  count(DISTINCT info.id)  FROM project_info as info LEFT JOIN project_imgs as imgs on info.id = imgs.project_id where trade_type_id in ("+ tradeType +") and project_type in ("+ projectType +") ";
        return jdbcTemplate.queryForObject(sql,Integer.class);
    }

    @Override
    public boolean saveProjectInfo(ProjectBean projectBean) {
        String sql = "insert into project_info (name, trade_type_id, project_type, content, visit_url, sort, main_img, status, creat_time, uid) values ('"
                +projectBean.getName()+"','"+projectBean.getTrade_type_id()+"','"+projectBean.getProject_type()+"','"+projectBean.getContent()+"','"+projectBean.getVisit_url()+"','"+projectBean.getSort()+"',"+projectBean.getMain_img()
                +projectBean.getStatus()+"','"+projectBean.getCreateTime()+"','"+projectBean.getuId()+")";
        int result = jdbcTemplate.update(sql);
        boolean flag = false;
        if(result == 1){
            flag = true;
        }
        return flag;
    }
}
