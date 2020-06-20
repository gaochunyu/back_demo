package com.cennavi.tp.dao.impl;

import com.cennavi.tp.beans.ProjectBean;
import com.cennavi.tp.beans.ProjectImgBean;
import com.cennavi.tp.beans.UserinfoBean;
import com.cennavi.tp.common.base.dao.impl.BaseDaoImpl;
import com.cennavi.tp.dao.ProjectDataDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ProjectDataDaoImpl extends BaseDaoImpl<ProjectBean> implements ProjectDataDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<ProjectBean> getProjectList(Integer limitSize,Integer offsetNum ,String tradeType ,String projectType ,String status , Integer userId) {
        String sql = "";
        sql = "select info.id , info.name , info.trade_type_id , info.project_type , info.status, info.main_img ,string_agg ( imgs.url,',') AS urlList , trades.name as tradeName  FROM project_info as info LEFT JOIN project_imgs as imgs on info.id = imgs.project_id LEFT JOIN trade_type as trades on info.trade_type_id = trades.id" +
                " where trade_type_id in ("+ tradeType +") and project_type in ("+ projectType + ") and status in (" + status + ") GROUP BY info.id , trades.name order by info.sort desc LIMIT "+ limitSize +" OFFSET " + offsetNum;

        if(userId != 0){
            sql = "select info.id , info.name , info.trade_type_id , info.project_type , info.status, info.main_img ,string_agg ( imgs.url,',') AS urlList , trades.name as tradeName  FROM project_info as info LEFT JOIN project_imgs as imgs on info.id = imgs.project_id LEFT JOIN trade_type as trades on info.trade_type_id = trades.id" +
                    " where trade_type_id in ("+ tradeType +") and project_type in ("+ projectType + ") and status in (" + status + ") and uid = "+ userId +" GROUP BY info.id , trades.name order by info.sort desc LIMIT "+ limitSize +" OFFSET " + offsetNum;
        }
        //System.out.println("查询"+sql);
        return jdbcTemplate.query(sql , BeanPropertyRowMapper.newInstance(ProjectBean.class));
    }

    @Override
    public Integer getProjectListNum(String tradeType , String projectType,String status) {
        String sql = "select  count(DISTINCT info.id)  FROM project_info as info LEFT JOIN project_imgs as imgs on info.id = imgs.project_id where trade_type_id in ("+ tradeType +") and project_type in ("+ projectType +") and status in (" + status + ") ";
        return jdbcTemplate.queryForObject(sql,Integer.class);
    }

    @Override
    public boolean saveProjectInfo(int id , boolean mainImgIsUpdate ,ProjectBean projectBean , List<ProjectImgBean> list ,String[] nameList) {
        boolean flag = false;
        if(id == 0){
            String sql = "insert into project_info (name, trade_type_id, project_type, content, visit_url, sort, main_img, status, creat_time, uid) values ('"
                    +projectBean.getName()+"','"+projectBean.getTrade_type_id()+"','"+projectBean.getProject_type()+"','"+projectBean.getContent()+"','"+projectBean.getVisit_url()+"','"+projectBean.getSort()+"','"+projectBean.getMain_img()+"','"
                    +projectBean.getStatus()+"','"+projectBean.getCreateTime()+"','"+projectBean.getuId()+"')";

            //int result = jdbcTemplate.update(sql);
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql,new String [] {"id"});
                return ps;
            }, keyHolder);

            long insertId = keyHolder.getKey().longValue();
            if(insertId > 0){
                String imgSql = "insert into project_imgs (url , project_id)  values ";
                for(ProjectImgBean projectImgBean : list){
                    imgSql += "('"+projectImgBean.getUrl() + "','"+ insertId +"'),";
                }
                if(imgSql.contains(",")){
                    imgSql = imgSql.substring(0,imgSql.length() - 1);
                    imgSql += ";";
                    int result = jdbcTemplate.update(imgSql);
                    if(result > 0){
                        flag = true;
                    }
                }
            }
        }
        if(id > 0){
            //System.out.println("修改");
            String sql = "";
            if(mainImgIsUpdate){  //修改了 封面图片
                sql = "update project_info set  name = '" +projectBean.getName() + "', trade_type_id = '" + projectBean.getTrade_type_id()
                        + "', project_type = '" + projectBean.getProject_type() + "', content = '" + projectBean.getContent() + "', visit_url = '" + projectBean.getVisit_url()
                        + "', sort = '" + projectBean.getSort() + "', main_img = '" + projectBean.getMain_img() + "', status = '" + projectBean.getStatus()
                        + "', creat_time = '" + projectBean.getCreateTime() + "', uid = '" + projectBean.getuId() + "' where id = " + id;
            }else{  //未修改封面图片
                sql = "update project_info set  name = '" +projectBean.getName() + "', trade_type_id = '" + projectBean.getTrade_type_id()
                        + "', project_type = '" + projectBean.getProject_type() + "', content = '" + projectBean.getContent() + "', visit_url = '" + projectBean.getVisit_url()
                        + "', sort = '" + projectBean.getSort() + "', status = '" + projectBean.getStatus()
                        + "', creat_time = '" + projectBean.getCreateTime() + "', uid = '" + projectBean.getuId() + "' where id = " + id;
            }
            int result = jdbcTemplate.update(sql);

            String sql2 = "DELETE FROM project_imgs where project_id = " + id;
            int result2 = jdbcTemplate.update(sql2);

            String sql3 = "insert into project_imgs (url , project_id)  values ";
            for(String imgName : nameList){
                sql3 += "('"+ imgName + "','"+ id +"'),";
            }
            int result3 = 0;
            if(sql3.contains(",")){
                sql3 = sql3.substring(0,sql3.length() - 1);
                sql3 += ";";
                result3 = jdbcTemplate.update(sql3);
            }

            //System.out.println("修改:===" + result3);
            if(result > 0 && result2 > 0 && result3 > 0){
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public List<ProjectBean> getProjectInfoById(Integer proId) {
        String sql = "select info.id , info.name , info.trade_type_id , info.project_type , info.status , info.content , info.visit_url , info.sort , info.main_img , info.creat_time , string_agg ( imgs.url,',') AS urlList , trades.name as tradeName FROM project_info as info LEFT JOIN project_imgs as imgs on info.id = imgs.project_id LEFT JOIN trade_type as trades on info.trade_type_id = trades.id where info.id = " + proId + " GROUP BY info.id , trades.name ";
        return jdbcTemplate.query(sql , BeanPropertyRowMapper.newInstance(ProjectBean.class));
    }

    @Override
    public boolean updateStatus(int id, int status) {
        boolean flag = false;
        String sql = "update project_info set status = "+ status +" where id = " + id;
        int result = jdbcTemplate.update(sql);
        if(result > 0){
            flag = true;
        }
        return flag;
    }

    @Override
    public boolean deleteProjectInfo(int id) {
        boolean flag = false;
        String sql = "delete  from project_info where id = " + id;
        int result = jdbcTemplate.update(sql);

        String sql2 = "delete  from project_imgs where project_id = " + id;
        int result2 = jdbcTemplate.update(sql2);

        if(result > 0 && result2 >0){
            flag = true;
        }
        return flag;
    }

    @Override
    public List<ProjectImgBean> getProjectImgs(Integer pid) {
        String sql = "select * from project_imgs where project_id = "+pid;
        return jdbcTemplate.query(sql,BeanPropertyRowMapper.newInstance(ProjectImgBean.class));
    }

}
