package com.cennavi.tp.service;


import com.cennavi.tp.beans.BaseAreaRoadRtic;
import com.cennavi.tp.beans.BaseLink;
import com.cennavi.tp.beans.BaseRtic;
import com.cennavi.tp.beans.BaseRticLink;

import java.util.List;
import java.util.Map;

/**
 * Created by sunpengyan on 2019/9/7.
 */
public interface BaseDataService {
    void batchSaveRtic(List<BaseRtic> baseRtics, String city);
    void batchSaveAreaRoadRtic(List<BaseAreaRoadRtic> list, String city);
    void batchSaveRticLink(List<BaseRticLink> list, String city);
    void batchSaveLink(List<BaseLink> list, String city);

    List<Map<String,Object>> getLinkByGeometry(String linkid,String geometry, String city);

    List<Map<String,Object>> getLinkById(String id, String city);

    List<Map<String,Object>> getRticByGeometry(String roadid,String geometry, String city);

    List<BaseRtic> getRticById(String id, String city);
}
