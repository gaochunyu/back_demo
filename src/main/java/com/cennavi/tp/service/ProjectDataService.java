package com.cennavi.tp.service;

import com.cennavi.tp.beans.ProjectBean;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ProjectDataService {
    Map<String , Object> getProjectList(Integer limitSize, Integer curPage , String tradeType , String projectType);

    boolean saveProjectInfo(int operation ,int id ,String name , int tradeTypeId, int proTypeId , String proContent , String proUrl , int proSort , MultipartFile mainImgFile ,MultipartFile[] proImgFileList ,int uid);

    List<ProjectBean> getProjectInfoById(int proId);
}
