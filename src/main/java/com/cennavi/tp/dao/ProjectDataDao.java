package com.cennavi.tp.dao;

import com.cennavi.tp.beans.ProjectBean;
import com.cennavi.tp.beans.ProjectImgBean;
import com.cennavi.tp.common.base.dao.BaseDao;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.List;

public interface ProjectDataDao extends BaseDao<ProjectBean> {
    List<ProjectBean> getProjectList(Integer limitSize,Integer offsetNum ,String tradeType ,String projectType);
    Integer getProjectListNum(String tradeType , String projectType);
    boolean saveProjectInfo(int id ,boolean mainImgIsUpdate , ProjectBean projectBean ,  List<ProjectImgBean> list ,String[] nameList);
    List<ProjectBean> getProjectInfoById(Integer proId);
}
