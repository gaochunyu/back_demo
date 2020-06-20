package com.cennavi.tp.service.impl;

import com.cennavi.tp.beans.FeedbackBean;
import com.cennavi.tp.dao.FeedbackDao;
import com.cennavi.tp.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackDao feedbackDao;

    @Override
    public boolean addFeedback(FeedbackBean feedback) {
        try {
            feedbackDao.save(feedback);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public int getFeedbackCount(Integer page, Integer pageSize, String keyword) {
        return feedbackDao.getFeedbackCount(page, pageSize, keyword);
    }

    @Override
    public List<FeedbackBean> getFeedbackList(Integer page, Integer pageSize, String keyword) {
        return feedbackDao.getFeedbackList(page, pageSize, keyword);
    }

    @Override
    public boolean deleteFeedback(Integer id) {
        try {
            feedbackDao.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean updateFeedbackState(Integer id,Integer state) {
        try {
            int i = feedbackDao.updateFeedbackState(id, state);
            if (i>0){
               return true;
            }else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
