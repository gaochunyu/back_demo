package com.cennavi.tp.service;

import com.cennavi.tp.beans.ComponentBean;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ComponentService {
    // 添加模块
    boolean addComponent(Integer uid, String name, String type, String tags, String content, MultipartFile coverImg, List<MultipartFile> showImgList, String visitUrl, MultipartFile file);
    // 删除模块
    boolean deleteComponent(Integer id);
    // 获取模块类型列表
    List<Map<String, Object>> getComponentTypeList();
    // 获取模块分页列表
    List<ComponentBean> getComponentList(Integer pageNo, Integer pageSize, String tags, String status, String type, Integer uid);
    // 根据条件获取用户数量
    int getComponentCount(String tags, String status, String type, Integer uid);
    // 更新模块
    boolean updateComponent(Integer id, String name, String type, String tags, String content,
                            MultipartFile coverImg, String  coverUploadedPath, List<MultipartFile> showImgList, String showImgUploadedPathList,
                            String visitUrl, MultipartFile file, String fileUploadedPath);
    // 根据id获取模块
    ComponentBean getComponentById(Integer id);
    // 更改模块状态
    boolean updateModuleStatus(Integer id, Boolean checkResult);
}
