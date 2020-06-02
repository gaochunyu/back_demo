package com.cennavi.tp.dao;

import com.cennavi.tp.beans.ComponentBean;
import com.cennavi.tp.beans.ComponentTypeBean;

import java.util.List;

public interface ComponentDao {
    // 新增组件
    int addComponent(ComponentBean componentBean);
    // 删除组件
    int deleteComponent(Integer id);
    // 获取组件类型列表
    List<ComponentTypeBean> getComponentTypeList();
    // 获取组件分页列表
    List<ComponentBean> getComponentList(Integer startNo, Integer pageSize, String tags, String status, String type, Integer uid);
    // 根据查询条件获取组件数量
    int getComponentCount(String tags, String status, String type, Integer uid);
    // 更新component信息
    int updateComponent(ComponentBean componentBean);
}
