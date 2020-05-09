package com.cennavi.tp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cennavi.tp.beans.ProjectBean;
import com.cennavi.tp.beans.ProjectImgBean;
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
import java.util.stream.Collectors;

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
        newMap.put("list",newList.stream().sorted((i, j) -> j.getSort() - i.getSort()).collect(Collectors.toList()));
        newMap.put("totalSize",num);
        return newMap;
    }

    @Override
    public boolean saveProjectInfo(int operation , int id , String name, int tradeTypeId, int proTypeId, String proContent, String proUrl, int proSort, MultipartFile mainImgFile , MultipartFile[] proImgFileList ,int uid) {
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

        if(mainImgFile != null && !mainImgFile.isEmpty()){
            // 创建保存图片的文件夹    D://test
            String rootPath = fileSavePath + "project/mainImg";
            File test = new File(rootPath + "/");
            if (!test.getParentFile().exists()) {
                // 判断 文件夹是否存在，如果不存在就新建
                test.getParentFile().mkdir();
            }

            String fileName = mainImgFile.getOriginalFilename();
            // 拼接文件从存放的路径，创建project/img文件夹，保存对应的文件进去
            String path = fileSavePath + "project/mainImg";
            File f = new File(path + "/" + fileName);

            if (!f.getParentFile().exists()) {
                //判断文件父目录是否存在
                f.getParentFile().mkdir();
            }

            try{
                mainImgFile.transferTo(f); //保存文件
                projectBean.setMain_img("project/mainImg/" + fileName);
            }catch (IllegalStateException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        List<ProjectImgBean> list = new ArrayList<>();

        if(proImgFileList!=null && proImgFileList.length>0){
            for(MultipartFile proImgFile : proImgFileList){
                if(proImgFile != null && !proImgFile.isEmpty()){
                    // 创建保存图片的文件夹    D://test
                    String rootPath = fileSavePath + "project/img";
                    File test = new File(rootPath + "/");
                    if (!test.getParentFile().exists()) {
                        // 判断 文件夹是否存在，如果不存在就新建
                        test.getParentFile().mkdir();
                    }

                    String fileName = proImgFile.getOriginalFilename();
                    // 拼接文件从存放的路径，创建project/img文件夹，保存对应的文件进去
                    String path = fileSavePath + "project/img";
                    File f = new File(path + "/" + fileName);

                    if (!f.getParentFile().exists()) {
                        //判断文件父目录是否存在
                        f.getParentFile().mkdir();
                    }

                    try{
                        proImgFile.transferTo(f); //保存文件
                        ProjectImgBean projectImgBean = new ProjectImgBean();
                        projectImgBean.setUrl("project/img/" + fileName);
                        list.add(projectImgBean);
                    }catch (IllegalStateException | IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
        boolean mainImgIsUpdate = true;
        if(id > 0 && mainImgFile == null){  //修改操作
            mainImgIsUpdate = false;// 不去修改 项目 图片地址
        }

        boolean flag = projectDataDao.saveProjectInfo(id, mainImgIsUpdate , projectBean,list);
        return flag;
    }

    @Override
    public List<ProjectBean> getProjectInfoById(int proId) {
        List<ProjectBean> list = projectDataDao.getProjectInfoById(proId);
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
        return newList;
    }

}
