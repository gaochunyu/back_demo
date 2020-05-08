package com.cennavi.tp.service;

import com.cennavi.tp.beans.ProjectBean;

import java.util.List;
import java.util.Map;

public interface ProjectDataService {
    Map<String , Object> getProjectList(Integer limitSize, Integer curPage , String tradeType , String projectType);
}
