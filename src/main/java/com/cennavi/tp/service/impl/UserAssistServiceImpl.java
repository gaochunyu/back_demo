package com.cennavi.tp.service.impl;
import com.cennavi.tp.dao.UserAssistDao;
import com.cennavi.tp.service.UserAssistService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by 姚文帅 on 2020/5/13 14:36.
 */
@Service
public class UserAssistServiceImpl implements UserAssistService {

    @Resource
    UserAssistDao userAssistDao;


    @Override
    public Integer giveARedHeart(Integer userId, Integer assistId, Boolean type) {
        return userAssistDao.giveARedHeart(userId, assistId,type);
    }

    @Override
    public Boolean getAssistWeightStatus(Integer userId, Integer assistId) {
        return userAssistDao.getAssistWeightStatus(userId, assistId);
    }
}
