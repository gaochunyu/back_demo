package com.cennavi.tp.service;

import com.cennavi.tp.beans.ComponentBean;
import com.cennavi.tp.beans.ComponentTypeBean;
import com.cennavi.tp.common.result.ResultModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ComponentService {
    // 添加组件
    boolean addComponent(Integer uid, String name, Integer type, String tags, String content, MultipartFile coverImg, List<MultipartFile> showImgList, String visitUrl, MultipartFile file);
    // 删除组件
    ResultModel delComponent(Integer id, Integer uid);
    // 更新组件
    ResultModel updateComponent(Integer id, Integer uid, String name, String tags, String cover, String content, String testUrl, String fileUrl);
    // 获取组件类型列表
    List<ComponentTypeBean> getComponentTypeList();
    // 获取组件分页列表
    List<Map<String,Object>> getComponentList(Integer pageNo, Integer pageSize, String tags, String status, String type);
    // 根据条件获取用户数量
    int getComponentCount(String tags, String status, String type);
}
