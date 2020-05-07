package com.cennavi.tp.service.impl;

import com.cennavi.tp.beans.ProjectBean;
import com.cennavi.tp.dao.ProjectDataDao;
import com.cennavi.tp.service.ProjectDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProjectDataServiceImpl implements ProjectDataService {

    @Autowired
    private ProjectDataDao projectDataDao;

    /**
     *
     * @param limitSize 每页展示几条
     * @param curPage 当前第几页
     * @param tradeType  所属行业类型
     * @param projectType  项目还是产品
     * @return
     */
    @Override
    public Map<String , Object> getProjectList(Integer limitSize,Integer curPage , String tradeType ,String projectType) {
        Integer offsetNum = limitSize * (curPage - 1); //每页开始的条数  从第 offsetNum+1条开始查询，总共查询limitSize条
        List<ProjectBean> list = projectDataDao.getProjectList(limitSize,offsetNum,tradeType,projectType);
        List<ProjectBean> newList = new ArrayList<>();
        Map<Integer,ProjectBean> map=new HashMap<>();

        if(list.size()>0){
            list.forEach(item -> {
                if(map.get(item.getId())==null){
                    List<String> arr = new ArrayList();
                    arr.add(item.getUrl());
                    item.setUrlList(arr);
                    map.put(item.getId(),item);
                }else {
                    ProjectBean projectBean=map.get(item.getId());
                    List<String> arr=projectBean.getUrlList();
                    arr.add(item.getUrl());
                }

            });
        }
        for(Map.Entry<Integer, ProjectBean> entry : map.entrySet()){
            newList.add(entry.getValue());
        }
        Integer num = projectDataDao.getProjectListNum(tradeType,projectType);
        Map<String , Object> newMap = new HashMap<>();
        newMap.put("list",newList);
        newMap.put("totalSize",num);
        return newMap;
    }
}
