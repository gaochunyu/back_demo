package com.cennavi.tp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cennavi.tp.beans.ProjectBean;
import com.cennavi.tp.common.result.Result;
import com.cennavi.tp.dao.ProjectDataDao;
import com.cennavi.tp.service.ProjectDataService;
import com.cennavi.tp.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ProjectDataServiceImpl implements ProjectDataService {

    @Autowired
    private ProjectDataDao projectDataDao;

    @Value("${file_path}")
    private String fileSavePath;

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

    @Override
    public boolean saveProjectInfo(String name, int tradeTypeId, int proTypeId, String proContent, String proUrl, int proSort, MultipartFile file , int uid) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        String createTime = formatter.format(date);

        Integer status = 1;  //是否展示  0-待审核   1-审核成功   2-审核失败

        ProjectBean projectBean = new ProjectBean();
        projectBean.setName(name);
        projectBean.setTrade_type_id(tradeTypeId);
        projectBean.setProject_type(proTypeId);
        projectBean.setContent(proContent);
        projectBean.setVisit_url(proUrl);
        projectBean.setSort(proSort);
        projectBean.setStatus(status);
        projectBean.setCreateTime(createTime);
        projectBean.setuId(uid);

        if(file != null && !file.isEmpty()){
            String fileName = file.getOriginalFilename();

            // 创建保存图片的文件夹    D://test
            String rootPath = fileSavePath + "project/img";
            File test = new File(rootPath + "/");
            if (!test.getParentFile().exists()) {
                // 判断 文件夹是否存在，如果不存在就新建
                test.getParentFile().mkdir();
            }

            // 拼接文件从存放的路径，创建project/img文件夹，保存对应的文件进去
            String path = fileSavePath + "project/img";
            File f = new File(path + "/" + fileName);

            if (!f.getParentFile().exists()) {
                //判断文件父目录是否存在
                f.getParentFile().mkdir();
            }

            try{
                file.transferTo(f); //保存文件
                projectBean.setMain_img("project/img/" + fileName);
            }catch (IllegalStateException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        boolean flag = projectDataDao.saveProjectInfo(projectBean);
        return flag;
    }
}
