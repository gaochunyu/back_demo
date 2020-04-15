package com.cennavi.tp.service.impl;

import com.cennavi.tp.beans.BaseAreaRoadRtic;
import com.cennavi.tp.beans.BaseLink;
import com.cennavi.tp.beans.BaseRtic;
import com.cennavi.tp.beans.BaseRticLink;
import com.cennavi.tp.dao.BaseDataDao;
import com.cennavi.tp.service.BaseDataService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by sunpengyan on 2019/9/7.
 */
@Service
public class BaseDataServiceImpl implements BaseDataService {

    @Autowired
    private BaseDataDao baseDataDao;

    @Override
    public void batchSaveRtic(List<BaseRtic> baseRtics, String city) {
        String tablename = city+"_base_rtic";
        if(!baseDataDao.isExist(tablename)) {
            baseDataDao.createTable(tablename,"rtic");
        }
        baseDataDao.batchSave(baseRtics,BaseRtic.class, city+"_base_rtic");
    }

    @Override
    public void batchSaveAreaRoadRtic(List<BaseAreaRoadRtic> list, String city) {
        String tablename = city+"_base_area_road_rtic";
        if(!baseDataDao.isExist(tablename)) {
            baseDataDao.createTable(tablename,"area_road_rtic");
        }
        baseDataDao.batchSave(list,BaseAreaRoadRtic.class, tablename);
    }

    @Override
    public void batchSaveRticLink(List<BaseRticLink> list, String city){
        String tablename = city+"_base_rtic_link";
        if(!baseDataDao.isExist(tablename)) {
            baseDataDao.createTable(tablename,"rtic_link");
        }
        baseDataDao.batchSave(list,BaseRticLink.class, tablename);
    }

    @Override
    public void batchSaveLink(List<BaseLink> list, String city) {
        String tablename = city+"_base_link";
        if(!baseDataDao.isExist(tablename)) {
            baseDataDao.createTable(tablename,"link");
        }
        baseDataDao.batchSave(list,BaseLink.class, tablename);
    }

    @Override
    public List<Map<String,Object>> getLinkByGeometry(String linkid,String geometry, String city){
        String roadid = "";
        if(StringUtils.isNotBlank(linkid)) {
            roadid = baseDataDao.getRoadIdByLinkid(linkid,city);
        }
        return baseDataDao.listLinkByRoadid(roadid,geometry,city);
    }

    @Override
    public List<Map<String, Object>> getLinkById(String id, String city) {
        return baseDataDao.getLinkById(id,city);
    }

    @Override
    public List<Map<String,Object>> getRticByGeometry(String linkid,String geometry, String city){
        String roadid = "";
        if(StringUtils.isNotBlank(linkid)) {
            roadid = baseDataDao.getRoadIdByLinkid(linkid,city);
        }
        return baseDataDao.listRticByRoadid(roadid,geometry,city);
    }

    @Override
    public List<BaseRtic> getRticById(String id, String city) {
        return baseDataDao.getRticById(id,city);
    }
}
