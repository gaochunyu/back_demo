package com.cennavi.tp.service.impl;

import com.cennavi.tp.beans.ComponentBean;
import com.cennavi.tp.beans.ComponentTypeBean;
import com.cennavi.tp.dao.ComponentDao;
import com.cennavi.tp.service.ComponentService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author: chenfeng
 * @date: 2020/4/29 17:33
 */
@Service
public class ComponentServiceImpl implements ComponentService {

    @Autowired
    private ComponentDao componentDao;

    @Value("${file_path}")
    private String fileSaveRootPath;

    @Override
    public boolean addComponent(Integer uid, String name, String type, String tags, String content, MultipartFile coverImg, List<MultipartFile> showImgList, String visitUrl, MultipartFile file) {
        // 获取当前时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = df.format(new Date());

        // 组件状态 0-录入中 1-待审核(默认) 2-审核成功 3-审核失败
        String status = "1";

        // coverImg/imgList/file 另行处理
        ComponentBean componentBean = new ComponentBean();
        componentBean.setUid(uid);
        componentBean.setName(name);
        componentBean.setType(type);
        componentBean.setTags(tags);
        componentBean.setContent(content);
        componentBean.setVisit_url(visitUrl);
        componentBean.setCreate_time(createTime);
        componentBean.setStatus(status);

        // 处理coverImg封面图
        if (coverImg != null && !coverImg.isEmpty()) {
            String coverImgRelativePath = handleFileUpload("component/img/cover/", coverImg);
            componentBean.setCover_img(coverImgRelativePath);
        }

        // 处理展示图列表
        String imgListData = "";
        if (showImgList != null && showImgList.size() > 0) {
            imgListData = handleShowImgFileList(showImgList);
        }
        componentBean.setImg_list(imgListData);

        // 处理file上传文件
        String fileRelativePath = "";
        if (file != null && !file.isEmpty()) {
            fileRelativePath = handleFileUpload("component/file/", file);
        }
        componentBean.setFile_url(fileRelativePath);

        int count = componentDao.addComponent(componentBean);
        return count > 0;
    }

    @Override
    public boolean deleteComponent(Integer id) {
        int count = componentDao.deleteComponent(id);
        return count > 0;
    }

    @Override
    public List<Map<String, Object>> getComponentTypeList() {
        List<Map<String, Object>> mapList = new ArrayList<>();
        // type id转成string
        List<ComponentTypeBean> list = componentDao.getComponentTypeList();
        for (ComponentTypeBean componentTypeBean : list) {
            Map<String, Object> map = new HashMap<>();
            int typeId = componentTypeBean.getId();
            map.put("id", typeId + "");
            map.put("name", componentTypeBean.getName());
            mapList.add(map);
        }
        return mapList;
    }

    @Override
    public List<ComponentBean> getComponentList(Integer pageNo, Integer pageSize, String tags, String status, String type, Integer uid) {
        int startNo = (pageNo - 1) * pageSize;
        if (type == null || type.length() == 0) {
            return new ArrayList<>();
        }
        if (status == null || status.length() == 0) {
            return new ArrayList<>();
        }
        List<ComponentBean> list = componentDao.getComponentList(startNo, pageSize, tags, status, type, uid);
        return list;
    }

    @Override
    public int getComponentCount(String tags, String status, String type, Integer uid) {
        if (type == null || type.length() == 0) {
            return 0;
        }
        if (status == null || status.length() == 0) {
            return 0;
        }
        return componentDao.getComponentCount(tags, status, type, uid);
    }

    @Override
    public boolean updateComponent(Integer id, String name, String type, String tags, String content,
                                   MultipartFile coverImg, String coverUploadedPath, List<MultipartFile> showImgList, String showImgUploadedPathList,
                                   String visitUrl, MultipartFile file, String fileUploadedPath) {
        ComponentBean componentBean = new ComponentBean();
        componentBean.setId(id);
        componentBean.setName(name);
        componentBean.setType(type);
        componentBean.setTags(tags);
        componentBean.setContent(content);
        componentBean.setVisit_url(visitUrl);

        // 处理coverImg
        if (coverImg == null) {// 若coverImg未新上传
            componentBean.setCover_img(coverUploadedPath);
        } else {
            String coverUrlData = handleFileUpload("component/img/cover/", coverImg);
            componentBean.setCover_img(coverUrlData);
        }

        // 处理showImg是否有新增
        if (showImgList == null) {
            componentBean.setImg_list(showImgUploadedPathList);
        } else {
            String imgListPath = "";
            String imgData = "";
            if (showImgList.size() > 0) {
                imgListPath += handleShowImgFileList(showImgList);
            }
            if (imgListPath.length() > 0 && showImgUploadedPathList.length() > 0) {
                imgData = imgListPath + "," + showImgUploadedPathList;
            }
            if (imgListPath.length() > 0 && showImgUploadedPathList.length() == 0) {
                imgData = imgListPath;
            }
            if (imgListPath.length() == 0 && showImgUploadedPathList.length() > 0) {
                imgData = showImgUploadedPathList;
            }
            componentBean.setImg_list(imgData);
        }

        // 处理file
        if (file == null) {
            componentBean.setFile_url(fileUploadedPath);
        } else {
            String fileData = handleFileUpload("component/file/", file);
            componentBean.setFile_url(fileData);
        }

        int count = componentDao.updateComponent(componentBean);
        return count > 0;
    }

    @Override
    public ComponentBean getComponentById(Integer id) {
        return componentDao.getComponentById(id);
    }

    @Override
    public boolean updateModuleStatus(Integer id, Boolean checkResult) {
        int count = componentDao.updateModuleStatus(id, checkResult);
        return count > 0;
    }

    /**
     * 组件模块处理文件上传
     * @param relativePath 文件要保存的相对路径
     * @param file 要处理的由前端上传来的文件
     * @return 存入数据库的相对路径
     */
    private String handleFileUpload(String relativePath, MultipartFile file) {
        String absolutePath = fileSaveRootPath + relativePath;
        String fileName = file.getOriginalFilename();
        long stamp = new Date().getTime();
        String relativeFullPath = relativePath + stamp + "-" + fileName;
        String absoluteFullPath = absolutePath + stamp + "-" + fileName;
        // 判断文件夹是否存在
        File dirTest = new File(absolutePath);
        if (!dirTest.exists()) {
            dirTest.mkdirs();
        }
        // 获取文件
        File f = new File(absoluteFullPath);
        try {
            // 保存文件
            file.transferTo(f);
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }
        return relativeFullPath;
    }

    // 处理展示图上传文件列表
    private String handleShowImgFileList(List<MultipartFile> showImgFileList) {
        List<String> imgDataList = new ArrayList<>();
        // 获取每个展示图路径
        for (MultipartFile showImgFile : showImgFileList) {
            // 如果当前图片文件参数存在
            if (showImgFile != null && !showImgFile.isEmpty()) {
                String showImgRelativePath = handleFileUpload("component/img/imgList/", showImgFile);
                imgDataList.add(showImgRelativePath);
            }
        }
        // imglist存入数据库
        String imgData = StringUtils.join(imgDataList.toArray(), ",");
        return imgData;
    }
}
