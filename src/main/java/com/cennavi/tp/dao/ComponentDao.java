package com.cennavi.tp.dao;

import com.cennavi.tp.beans.ComponentBean;
import com.cennavi.tp.beans.ComponentImgBean;
import com.cennavi.tp.beans.ComponentTypeBean;

import java.util.List;

public interface ComponentDao {
    boolean addComponent(ComponentBean componentBean, List<ComponentImgBean> list, List<String> imgDataPathList);

    Integer delComponent(Integer id, Integer uid);

    List<ComponentTypeBean> getComponentTypeList();
}
