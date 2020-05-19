package com.cennavi.tp.dao;

import com.cennavi.tp.beans.ComponentBean;
import com.cennavi.tp.beans.ComponentImgBean;
import com.cennavi.tp.beans.ComponentTypeBean;

import java.util.List;

public interface ComponentDao {
    /**
     * 新增component表信息
     * @param componentBean
     * @return 新增组件的id
     */
    int addComponent(ComponentBean componentBean);

    Integer delComponent(Integer id, Integer uid);

    List<ComponentTypeBean> getComponentTypeList();

    // 根据component_id新增组件展示图
    boolean addComponentImg(ComponentImgBean componentImgBean);

    // 获取组件分页列表
    List<ComponentBean> getComponentList(Integer startNo, Integer pageSize, String tags, String status, String type);

    // 根据查询条件获取组件数量
    int getComponentCount(String tags, String status, String type);
}
