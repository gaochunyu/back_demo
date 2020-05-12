package com.cennavi.tp.service.impl;

import com.cennavi.tp.beans.ResourcesBean;
import com.cennavi.tp.common.result.Result;
import com.cennavi.tp.common.result.ResultModel;
import com.cennavi.tp.dao.ResourcesDao;
import com.cennavi.tp.service.ResourcesService;
import com.cennavi.tp.utils.MyDateUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ResourcesServiceImpl implements ResourcesService {
    @Autowired
    private ResourcesDao resourcesDao;

    @Value("${file_path}")
    private String fileSavePath;

    @Override
    public ResourcesBean getResourcesById(Integer id) {
        ResourcesBean resourcesBean = resourcesDao.getResourcesById(id);

        return resourcesBean;
    }

    @Override
    public ResultModel addResourcesItem(Integer uid, String name, String tags, MultipartFile file, String description, String link, Integer type) {
        try {
            ResourcesBean resourcesBean = new ResourcesBean();
            String format = "yyyy-MM-dd HH:mm:ss";
            String time = MyDateUtils.format(new Date(), format);
            resourcesBean.setCreate_time(time);
            resourcesBean.setDescription(description);
            resourcesBean.setLink(link);
            resourcesBean.setName(name);
            resourcesBean.setTags(tags);
            resourcesBean.setType(type);
            resourcesBean.setUid(uid);
            if (file != null) {
                if (!file.isEmpty()) {
                    String fileName = file.getOriginalFilename();
                    String format2 = "yyyy-MM-dd";
                    String time2 = MyDateUtils.format(new Date(), format2);
                    String path = fileSavePath + time2;
                    File test = new File(path + "/" + fileName);
                    System.out.println(test.getParentFile());
                    if (!test.getParentFile().exists()) {
                        // 判断 test 文件夹是否存在，如果不存在就新建
                        test.getParentFile().mkdir();
                    }
                    try {
                        file.transferTo(test);
                        resourcesBean.setFile(path + "/" + fileName);

                    } catch (IOException e) {
                        e.printStackTrace();
                        return Result.fail("文件保存失败");
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                        return Result.fail("文件保存失败");
                    }

                }
            }
            int id = resourcesDao.addResourcesItem(resourcesBean);
            Map<String, Object> map = new HashMap<>();
            map.put("id", id);
            return Result.success("添加成功", map);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("添加异常");
        }


    }

    @Override
    public boolean updateResourcesItem(Integer id, Integer uid, String name, String tags, MultipartFile file, String description, String link, Integer type) {
        ResourcesBean resourcesBean = new ResourcesBean();
        String format = "yyyy-MM-dd HH:mm:ss";
        String time = MyDateUtils.format(new Date(), format);
        resourcesBean.setCreate_time(time);
        resourcesBean.setDescription(description);
        resourcesBean.setId(id);
        resourcesBean.setUid(uid);
        resourcesBean.setLink(link);
        resourcesBean.setName(name);
        resourcesBean.setTags(tags);
        resourcesBean.setType(type);
        // 获取之前上传的文件并删除
        ResourcesBean oldResourcesBean = resourcesDao.getResourcesById(id);

        File unique = new File(oldResourcesBean.getFile());
        if (unique != null && unique.exists()) {//判断文件是否存在
            deleteDir(oldResourcesBean.getFile());
            resourcesBean.setFile("");
        }

        if (file != null) {
            if (!file.isEmpty()) {
                String fileName = file.getOriginalFilename();
                String format2 = "yyyy-MM-dd";
                String time2 = MyDateUtils.format(new Date(), format2);
                String path = fileSavePath + time2;
                File test = new File(path + "/" + fileName);
                if (!test.getParentFile().exists()) {
                    // 判断 test 文件夹是否存在，如果不存在就新建
                    test.getParentFile().mkdir();
                }

                try {
                    // 保存文件
                    file.transferTo(test);

                    resourcesBean.setFile(path + "/" + fileName);
                } catch (IOException e) {
                    e.printStackTrace();

                } catch (IllegalStateException e) {
                    e.printStackTrace();

                }

            }
        }

        return resourcesDao.updateResourcesItem(resourcesBean);


    }

    @Override
    public int getResourcesCount(String tags,String status,String type) {
        int count = resourcesDao.getResourcesCount(tags, status, type);
        return count;
    }

    @Override
    public List<ResourcesBean> getResourcesList(Integer page, Integer pageSize, String tags, String status,String type) {
        int start = (page - 1) * pageSize;
        return resourcesDao.getResourcesList(start, pageSize, tags, status, type);

    }

    @Override
    public Boolean deleteResource(Integer id) {
        try {
            ResourcesBean resourcesBean = resourcesDao.getResourcesById(id);
            File unique = new File(resourcesBean.getFile());
            if (unique != null && unique.exists()) {//判断文件是否存在
                deleteDir(resourcesBean.getFile());
            }
            resourcesDao.delete(id);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<ResourcesBean> getTopFiveByCreateTime() {
        return resourcesDao.getTopFiveByCreateTime();
    }

    @Override
    public List<ResourcesBean> getTopFiveByViews() {
        return resourcesDao.getTopFiveByViews();
    }

    @Override
    public Boolean updateResourcesStatus(Integer id, Integer status) {

        return resourcesDao.updateResourcesStatus(id, status);
    }

    @Override
    public Boolean updateResourcesViews(Integer id) {
        ResourcesBean resourcesBean = resourcesDao.getResourcesById(id);
        int views = resourcesBean.getViews() + 1;
        return resourcesDao.updateResourcesViews(id, views);
    }

    public void deleteDir(String dirPath) {
        File file = new File(dirPath);// 读取
        if (file.isFile()) { // 判断是否是文件夹
            file.delete();// 删除
        } else {
            File[] files = file.listFiles(); // 获取文件
            if (files == null) {
                file.delete();// 删除
            } else {
                for (int i = 0; i < files.length; i++) {// 循环
                    deleteDir(files[i].getAbsolutePath());
                }
                file.delete();// 删除
            }
        }
    }


}
