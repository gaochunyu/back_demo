package com.cennavi.tp.service;

import com.cennavi.tp.beans.ResourcesBean;
import com.cennavi.tp.common.result.ResultModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ResourcesService {

    ResourcesBean getResourcesById (Integer id);

    ResultModel addResourcesItem(Integer uid,String name,String tags,MultipartFile file,String description,String link, Integer type);

    ResultModel updateResourcesItem(Integer id,String name,String tags,MultipartFile file,String description,String link, Integer type);

    int getResourcesCount();

    List<ResourcesBean> getResourcesList(Integer page, Integer pageSize, String tags);

    public  Boolean deleteResource(Integer id);
}
