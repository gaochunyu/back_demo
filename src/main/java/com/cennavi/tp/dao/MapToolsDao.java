package com.cennavi.tp.dao;

import com.cennavi.tp.beans.MapToolsBean;
import com.cennavi.tp.common.base.dao.BaseDao;

import java.util.Map;

/**
 * Created by 马立伟 on 2020/6/3 22:49.
 */

public interface MapToolsDao extends BaseDao<MapToolsBean> {
    // 新增数据,并返回新增数据的id的值
    long addMapTools(MapToolsBean mapTools);

    // 更新数据
    Integer updateMapTools(MapToolsBean mapTools);

    // 删除数据
    Integer deleteById(Integer id);

    // 获取一条数据
    MapToolsBean getMapToolsById(Integer id);

    // 获取列表数据
    Map<String,Object> getMapToolsList(Integer page, Integer pageSize,Integer model, String type, Integer uid, String searchKey, int statusValue);


}
