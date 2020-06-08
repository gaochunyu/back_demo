package com.cennavi.tp.service.impl;

import com.cennavi.tp.beans.DataCard;
import com.cennavi.tp.dao.BaseDataDao;
import com.cennavi.tp.service.BaseDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class BaseDataServiceImpl implements BaseDataService {

    @Autowired
    private BaseDataDao baseDataDao;
    private String[] category = new String[]{"编辑中","待审核","已通过","已拒绝"};
    private String[] category2 = new String[]{"待审核","已通过","已拒绝"};
    private String[] category3 = new String[]{"待审核"};

    @Override
    public List getDataCard(Integer uid){
        List<Map<String,Object>> contentCard = baseDataDao.getContentCardInfo(uid);
        List<Map<String,Object>> assistCard = baseDataDao.getAssistCardInfo(uid);
        List<Map<String,Object>> componentCard = baseDataDao.getComponentCardInfo(uid);
        List<Map<String,Object>> projectCard = baseDataDao.getProjectCardInfo(uid);
        List<Map<String,Object>> resourcesCard = baseDataDao.getResourcesCardInfo(uid);
        Map<String,Object> contentMap = getCardInfo("指南",contentCard,category);
        Map<String,Object> componentMap = getCardInfo("组件",componentCard,category2);
        Map<String,Object> assistMap = getCardInfo("帮助",assistCard,category2);
        Map<String,Object> projectMap = getCardInfo("资源->项目",projectCard,category2);
        Map<String,Object> resourcesMap = getCardInfo("资源->资料",resourcesCard,category2);
        List<Map<String,Object>> list = new ArrayList<>();
        list.add(contentMap);
        list.add(componentMap);
        list.add(assistMap);
        list.add(projectMap);
        list.add(resourcesMap);
        return list;
    }

    @Override
    public List getVerifyDataCard(Integer uid){
        List<Map<String,Object>> contentCard = baseDataDao.getContentVerify(uid);
        List<Map<String,Object>> assistCard = baseDataDao.getAssistVerify(uid);
        List<Map<String,Object>> componentCard = baseDataDao.getComponentVerify(uid);
        List<Map<String,Object>> projectCard = baseDataDao.getProjectVerify(uid);
        List<Map<String,Object>> resourcesCard = baseDataDao.getResourcesVerify(uid);
        Map<String,Object> contentMap = getCardInfo("指南",contentCard,category3);
        Map<String,Object> componentMap = getCardInfo("组件",componentCard,category3);
        Map<String,Object> assistMap = getCardInfo("帮助",assistCard,category3);
        Map<String,Object> projectMap = getCardInfo("资源->项目",projectCard,category3);
        Map<String,Object> resourcesMap = getCardInfo("资源->资料",resourcesCard,category3);
        List<Map<String,Object>> list = new ArrayList<>();
        list.add(contentMap);
        list.add(componentMap);
        list.add(assistMap);
        list.add(projectMap);
        list.add(resourcesMap);
        return list;
    }


    private Map<String,Object> getCardInfo(String name,List<Map<String,Object>> contentCard,String[] categorys){
        List<DataCard> contentList = new ArrayList<>();
        for(String info :categorys){
            DataCard dc = null;
            for(Map<String,Object> infos : contentCard){
                if(info.equals(infos.get("status").toString())){
                    dc = new DataCard();
                    dc.setStatus(infos.get("status").toString());
                    dc.setCount(infos.get("count"));
                    contentList.add(dc);
                }
            }
            if(dc==null){
                dc = new DataCard();
                dc.setCount(0);
                dc.setStatus(info);
                contentList.add(dc);
            }
        }
        Map<String,Object> contentMap = new HashMap<>();
        contentMap.put("name",name);
        contentMap.put("list",contentList);
        return contentMap;
    }
}
