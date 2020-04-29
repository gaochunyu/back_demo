package com.cennavi.tp.service.impl;

import com.cennavi.tp.beans.AssistBean;
import com.cennavi.tp.dao.AssistDao;
import com.cennavi.tp.dao.MenuDataDao;
import com.cennavi.tp.service.AssistService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 姚文帅 on 2020/4/29 14:31.
 */
@Service
public class AssistServiceImpl implements AssistService {

    @Autowired
    private AssistDao assistDao;

    @Override
    public long addAssistBean(AssistBean assistBean){
       return assistDao.addAssistItem(assistBean);
    }
}a
