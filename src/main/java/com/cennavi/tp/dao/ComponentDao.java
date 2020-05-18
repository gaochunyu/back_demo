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
}
