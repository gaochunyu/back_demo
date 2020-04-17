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

    }

    @Override
    public void batchSaveAreaRoadRtic(List<BaseAreaRoadRtic> list, String city) {

    }

    @Override
    public void batchSaveRticLink(List<BaseRticLink> list, String city) {

    }

    @Override
    public void batchSaveLink(List<BaseLink> list, String city) {

    }

    @Override
    public List<Map<String, Object>> getLinkByGeometry(String linkid, String geometry, String city) {
        return null;
    }

    @Override
    public List<Map<String, Object>> getLinkById(String id, String city) {
        return null;
    }

    @Override
    public List<Map<String, Object>> getRticByGeometry(String roadid, String geometry, String city) {
        return null;
    }

    @Override
    public List<BaseRtic> getRticById(String id, String city) {
        return null;
    }
}
