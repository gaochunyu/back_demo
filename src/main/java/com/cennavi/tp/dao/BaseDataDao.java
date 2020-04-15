package com.cennavi.tp.dao;


import com.cennavi.tp.beans.BaseRtic;
import com.cennavi.tp.common.dao.BaseDao;

import java.util.List;
import java.util.Map;

/**
 * Created by sunpengyan on 2019/9/7.
 */
public interface BaseDataDao extends BaseDao<Object> {

    String getRoadIdByLinkid(String linkid, String city);

    List<Map<String,Object>> listLinkByRoadid(String roadid,String geometry, String city);

    List<Map<String,Object>> getLinkById(String id, String city);

    List<Map<String,Object>> listRticByRoadid(String roadid,String geometry, String city);

    List<BaseRtic> getRticById(String id, String city);

    /**
     * 创建表
     * @param tablename 表名
     * @param type 区别建表语句，area_road_rtic，rtic，link，rtic_link
     */
    void createTable(String tablename,String type);
}
