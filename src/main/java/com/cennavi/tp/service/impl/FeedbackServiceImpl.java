package com.cennavi.tp.service.impl;

import com.cennavi.tp.beans.FeedbackBean;
import com.cennavi.tp.dao.FeedbackDao;
import com.cennavi.tp.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
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
    public int getFeedbackCount(Integer page, Integer pageSize, String keyword, Integer userId) {
        return feedbackDao.getFeedbackCount(page, pageSize, keyword,userId);
    }

    @Override
    public List<FeedbackBean> getFeedbackList(Integer page, Integer pageSize, String keyword, Integer userId) {
        return feedbackDao.getFeedbackList(page, pageSize, keyword,userId);
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
    public boolean updateFeedbackState(FeedbackBean feedbackBean) {
        try {
            int i = feedbackDao.updateFeedbackState(feedbackBean);
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

    @Override
    public boolean updateFeedback(FeedbackBean feedbackBean) {

        return false;
    }

    @Override
    public FeedbackBean getFeedbackDetail(Integer id) {
        return feedbackDao.getFeedbackDetail(id);
    }
}
