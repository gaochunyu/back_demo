package com.cennavi.tp.service;

import com.cennavi.tp.beans.MapToolsBean;
import com.cennavi.tp.common.result.ResultModel;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by 马立伟 on 2020/6/3 22:49.
 */
public interface MapToolsService {

    // 新增数据,并返回新增数据的id的值
    ResultModel addMapTools(int uid,String name,String icon,String type,String img,String help_info,String tags,Integer self,String url, HttpServletRequest request);
//    ResultModel addMapTools(int uid,String name,String icon,String type,String img,String help_info,String tags,Integer self,String url, HttpServletRequest request);

    // 更新数据
    ResultModel updateMapTools(int id,String name,String icon,String type,String img,String help_info,Integer status,String tags,Integer self,String url, HttpServletRequest request);

    // 删除数据
    ResultModel deleteById(Integer id);

    // 获取一条数据
    MapToolsBean getMapToolsById(Integer id);

    // 获取列表数据
    ResultModel getMapToolsList(Integer page, Integer pageSize, Integer model,String type,String status,HttpServletRequest request);




}
