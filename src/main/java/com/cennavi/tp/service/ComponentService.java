package com.cennavi.tp.service;

import com.cennavi.tp.beans.ComponentBean;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ComponentService {
    // 添加组件
    boolean addComponent(Integer uid, String name, String type, String tags, String content, MultipartFile coverImg, List<MultipartFile> showImgList, String visitUrl, MultipartFile file);
    // 删除组件
    boolean deleteComponent(Integer id);
    // 获取组件类型列表
    List<Map<String, Object>> getComponentTypeList();
    // 获取组件分页列表
    List<ComponentBean> getComponentList(Integer pageNo, Integer pageSize, String tags, String status, String type, Integer uid);
    // 根据条件获取用户数量
    int getComponentCount(String tags, String status, String type, Integer uid);
    // 更新组件
    boolean updateComponent(Integer id, String name, String type, String tags, String content,
                            MultipartFile coverImg, String  coverUploadedPath, List<MultipartFile> showImgList, String showImgUploadedPathList,
                            String visitUrl, MultipartFile file, String fileUploadedPath);
    // 根据id获取组件
    ComponentBean getComponentById(Integer id);
}
