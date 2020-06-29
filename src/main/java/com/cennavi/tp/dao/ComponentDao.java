package com.cennavi.tp.dao;

import com.cennavi.tp.beans.ComponentBean;
import com.cennavi.tp.beans.ComponentTypeBean;

import java.util.List;

public interface ComponentDao {
    // 新增模块
    int addComponent(ComponentBean componentBean);
    // 删除模块
    int deleteComponent(Integer id);
    // 获取模块类型列表
    List<ComponentTypeBean> getComponentTypeList();
    // 获取模块分页列表
    List<ComponentBean> getComponentList(Integer startNo, Integer pageSize, String tags, String status, String type, Integer uid);
    // 根据查询条件获取模块数量
    int getComponentCount(String tags, String status, String type, Integer uid);
    // 更新component信息
    int updateComponent(ComponentBean componentBean);
    // 根据id获取模块
    ComponentBean getComponentById(Integer id);
    // 更改模块状态
    int updateModuleStatus(Integer id, Boolean checkResult);
}
