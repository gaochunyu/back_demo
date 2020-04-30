package com.cennavi.tp.dao;

import com.cennavi.tp.beans.ResourcesBean;
import com.cennavi.tp.common.base.dao.BaseDao;

import java.util.List;

public interface ResourcesDao  extends BaseDao<ResourcesBean> {
    ResourcesBean getResourcesById(Integer id);

    int addResourcesItem(ResourcesBean resourcesBean);

    int updateResourcesItem(ResourcesBean resourcesBean);

    int getResourcesCount();
    
    List<ResourcesBean> getResourcesList(Integer start, Integer pageSize);

    List<ResourcesBean> getResourcesListByTags(int start, Integer pageSize, String tags);

    List<ResourcesBean> getTopFiveByCreateTime();
    
    List<ResourcesBean> getTopFiveByViews();

    Boolean updateResourcesStatus(Integer id,Integer status);

    Boolean updateResourcesViews(Integer id);

}
