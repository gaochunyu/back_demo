package com.cennavi.tp.service;

import com.cennavi.tp.beans.ResourcesBean;
import com.cennavi.tp.common.result.ResultModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ResourcesService {

    ResourcesBean getResourcesById (Integer id);

    ResultModel addResourcesItem(Integer uid,String name,String tags,MultipartFile file,String description,String link, Integer type);

    boolean updateResourcesItem(Integer id,Integer uid,String name,String tags,MultipartFile file,String description,String link, Integer type);

    int getResourcesCount(String tags);

    List<ResourcesBean> getResourcesList(Integer page, Integer pageSize, String tags);

    Boolean deleteResource(Integer id);

    List<ResourcesBean> getTopFiveByCreateTime();

    List<ResourcesBean> getTopFiveByViews();
//    修改资料的状态
    Boolean updateResourcesStatus(Integer id,Integer status);
//    修改资料的views
    Boolean updateResourcesViews(Integer id);

}
