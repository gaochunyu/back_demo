package com.cennavi.tp.service.impl;

import com.cennavi.tp.beans.ComponentBean;
import com.cennavi.tp.beans.ComponentImgBean;
import com.cennavi.tp.beans.ComponentTypeBean;
import com.cennavi.tp.common.result.Result;
import com.cennavi.tp.common.result.ResultModel;
import com.cennavi.tp.dao.ComponentDao;
import com.cennavi.tp.service.ComponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional
    public boolean addComponent(Integer uid, String name, Integer type, String tags, String content, MultipartFile coverImg, List<MultipartFile> showImgList, String visitUrl, MultipartFile file) {
        // 获取当前时间
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = df.format(new Date());

        // 组件状态 0-录入中 1-待审核(默认) 2-审核成功 3-审核失败
        int status = 1;

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

        // 处理file上传文件
        if (file != null && !file.isEmpty()) {
            String fileRelativePath = handleFileUpload("component/file/", file);
            componentBean.setFile_url(fileRelativePath);
        }

        // 新增component信息
        int insertId = componentDao.addComponent(componentBean);
        // component信息新增成功
        if (insertId > 0) {
            // 如果有展示图则新增
            if (showImgList != null && showImgList.size() > 0) {
                for (MultipartFile showImgFile : showImgList) {
                    // 如果当前图片文件存在
                    if (showImgFile != null && !showImgFile.isEmpty()) {
                        String showImgRelativePath = handleFileUpload("component/img/imgList/", showImgFile);
                        // 生成ComponentImgBean
                        ComponentImgBean componentImgBean = new ComponentImgBean();
                        componentImgBean.setCid(insertId);
                        componentImgBean.setImg_url(showImgRelativePath);
                        boolean flag = componentDao.addComponentImg(componentImgBean);
                        if (!flag) {
                            throw new RuntimeException("组件图片新增失败");
                        }
                    }
                }
            }
            return true;
        } else {
            // component信息新增失败
            return false;
        }
    }

    @Override
    @Transactional
    public boolean delComponent(Integer id) {
        int imgCount = componentDao.getComponentImgCountByCid(id);
        if(imgCount > 0) {
            componentDao.deleteComponentImgByCid(id);
        }
        int count = componentDao.deleteComponent(id);
        if(count > 0) {
            return true;
        } else {
            throw new RuntimeException("组件删除失败");
        }
    }

    @Override
    public ResultModel updateComponent(Integer id, Integer uid, String name, String tags, String cover, String content, String testUrl, String fileUrl) {
        return null;
    }

    @Override
    public List<ComponentTypeBean> getComponentTypeList() {
        return componentDao.getComponentTypeList();
    }

    @Override
    public List<Map<String, Object>> getComponentList(Integer pageNo, Integer pageSize, String tags, String status, String type, Integer uid) {
        int startNo = (pageNo - 1) * pageSize;
        if (type == null || type.length() == 0) {
            return new ArrayList<>();
        }
        if (status == null || status.length() == 0) {
            return new ArrayList<>();
        }
        List<Map<String, Object>> list = componentDao.getComponentList(startNo, pageSize, tags, status, type, uid);
        for (Map<String,Object> map : list) {
            Object imgList = map.get("img_list");
            if (imgList == null) {
                map.put("img_list", new ArrayList<>());
            } else {
                String imgList_s = (String)imgList;
                map.put("img_list", Arrays.asList(imgList_s.split(",")));
            }
        }
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

    /**
     * 组件模块处理文件上传
     * @param relativePath 文件要保存的相对路径
     * @param file 要处理的由前端上传来的文件
     * @return 存入数据库的相对路径
     */
    private String handleFileUpload(String relativePath, MultipartFile file) {
        String absolutePath = fileSaveRootPath + relativePath;
        String fileName = file.getOriginalFilename();
        String relativeFullPath = relativePath + fileName;
        String absoluteFullPath = absolutePath + fileName;
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

}
