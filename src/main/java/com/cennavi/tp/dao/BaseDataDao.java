package com.cennavi.tp.dao;


import com.cennavi.tp.common.base.dao.BaseDao;

import java.util.List;
import java.util.Map;


public interface BaseDataDao extends BaseDao<Object> {

    List<Map<String,Object>> getContentCardInfo(Integer uid);

    List<Map<String, Object>> getAssistCardInfo(Integer uid);

    List<Map<String, Object>> getComponentCardInfo(Integer uid);

    List<Map<String, Object>> getProjectCardInfo(Integer uid);

    List<Map<String, Object>> getResourcesCardInfo(Integer uid);

    List<Map<String,Object>> getContentVerify(Integer uid);

    List<Map<String, Object>> getAssistVerify(Integer uid);

    List<Map<String, Object>> getComponentVerify(Integer uid);

    List<Map<String, Object>> getProjectVerify(Integer uid);

    List<Map<String, Object>> getResourcesVerify(Integer uid);
}
