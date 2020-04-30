package com.cennavi.tp.service.impl;

import com.cennavi.tp.beans.ResourcesBean;
import com.cennavi.tp.common.result.Result;
import com.cennavi.tp.common.result.ResultModel;
import com.cennavi.tp.dao.ResourcesDao;
import com.cennavi.tp.service.ResourcesService;
import com.cennavi.tp.utils.MyDateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class ResourcesServiceImpl implements ResourcesService {
    @Autowired
    private ResourcesDao resourcesDao;

    @Autowired
    private ContentServiceImpl contentServiceImpl;

    @Value("${file_path}")
    private String fileSavePath;

    @Override
    public ResourcesBean getResourcesById(Integer id){
        ResourcesBean resourcesBean = resourcesDao.getResourcesById(id);
        return resourcesBean;
    }

    @Override
    public ResultModel addResourcesItem( Integer uid, String name, String tags, MultipartFile file, String description, String link, Integer type) {
        try {
            ResourcesBean resourcesBean = new ResourcesBean();
            String format = "yyyy-MM-dd HH:mm:ss";
            String time = MyDateUtils.format(new Date(),format);
            resourcesBean.setCreate_time(time);
            resourcesBean.setDescription(description);
            resourcesBean.setLink(link);
            resourcesBean.setName(name);
            resourcesBean.setTags(tags);
            resourcesBean.setType(type);
            resourcesBean.setUid(uid);
            if(file != null){
                if(!file.isEmpty()){
                    String fileName = file.getOriginalFilename();

                    File test = new File(fileSavePath+resourcesBean.getId()+"/"+fileName);
                    if(test.getParentFile().exists()){
                        // 判断 test 文件夹是否存在，如果不存在就新建
                       test.getParentFile().mkdir();
                    }
                    try {
                        file.transferTo(test);
                        String format2 = "yyyy-MM-dd";
                        String time2 = MyDateUtils.format(new Date(),format2);
                        String path = fileSavePath+time2;
                        resourcesBean.setFile(path+"/"+fileName);
                        resourcesDao.addResourcesItem(resourcesBean);

                    } catch (IOException e) {
                        e.printStackTrace();
                        return Result.fail("文件保存失败");
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                        return Result.fail("文件保存失败");
                    }

                }
            }
            return Result.fail("添加成功");


//            resourcesDao.save(resourcesBean);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("添加异常");
        }


    }

    @Override
    public ResultModel updateResourcesItem(Integer id, String name, String tags, MultipartFile file, String description, String link, Integer type) {
        try {
            ResourcesBean resourcesBean = new ResourcesBean();
            String format = "yyyy-MM-dd HH:mm:ss";
            String time = MyDateUtils.format(new Date(),format);
            resourcesBean.setCreate_time(time);
            resourcesBean.setDescription(description);
            resourcesBean.setLink(link);
            resourcesBean.setName(name);
            resourcesBean.setTags(tags);
            resourcesBean.setType(type);
            if(file != null){
                if(!file.isEmpty()){
                   // 获取之前上传的文件并删除
                    ResourcesBean oldResourcesBean = resourcesDao.getResourcesById(id);
                    File unique = new File(oldResourcesBean.getFile());
                    try{
                        contentServiceImpl.deleteFile(unique);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    String fileName = file.getOriginalFilename();
                    File test = new File(fileSavePath+"/"+fileName);
                    if(test.getParentFile().exists()){
                        // 判断 test 文件夹是否存在，如果不存在就新建
                        test.getParentFile().mkdir();
                    }

                    try {
                       // 保存文件
                        file.transferTo(test);
                        resourcesBean.setFile(fileSavePath+id+"/"+fileName);
                        resourcesDao.updateResourcesItem(resourcesBean);

                    } catch (IOException e) {
                        e.printStackTrace();
                        return Result.fail("文件保存失败");
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                        return Result.fail("文件保存失败");
                    }

                }
            }
            return Result.success("文件更新成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("文件更新失败");
        }

    }

    @Override
    public int getResourcesCount() {
        int count = resourcesDao.getResourcesCount();
        return count;
    }

    @Override
    public List<ResourcesBean> getResourcesList(Integer page, Integer pageSize, String tags) {
        int start = (page-1)*pageSize;

        return resourcesDao.getResourcesList(start,pageSize,tags);
    }

    @Override
    public Boolean deleteResource(Integer id) {
        try{
            resourcesDao.delete(id);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }


}
