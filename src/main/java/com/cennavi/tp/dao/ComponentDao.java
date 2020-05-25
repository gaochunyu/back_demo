package com.cennavi.tp.dao;

import com.cennavi.tp.beans.ComponentBean;
import com.cennavi.tp.beans.ComponentImgBean;
import com.cennavi.tp.beans.ComponentTypeBean;

import java.util.List;
import java.util.Map;

public interface ComponentDao {
    /**
     * 新增component表信息
     * @param componentBean
     * @return 新增组件的id
     */
    int addComponent(ComponentBean componentBean);

    // 删除组件
    int deleteComponent(Integer id);

    // 获取组件类型列表
    List<ComponentTypeBean> getComponentTypeList();

    // 根据component_id新增组件展示图
    boolean addComponentImg(ComponentImgBean componentImgBean);

    // 获取组件分页列表
    List<Map<String,Object>> getComponentList(Integer startNo, Integer pageSize, String tags, String status, String type, Integer uid);

    // 根据查询条件获取组件数量
    int getComponentCount(String tags, String status, String type, Integer uid);

    // 根据cid获取图片数量
    int getComponentImgCountByCid(Integer cid);

    // 根据cid删除组件图片表
    int deleteComponentImgByCid(Integer cid);
}
