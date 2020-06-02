package com.cennavi.tp.service.impl;

import com.cennavi.tp.beans.FeedbackBean;
import com.cennavi.tp.dao.FeedbackDao;
import com.cennavi.tp.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackDao feedbackDao;
    @Override
    public boolean addFeedback(FeedbackBean feedback) {
        try{
            feedbackDao.save(feedback);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
