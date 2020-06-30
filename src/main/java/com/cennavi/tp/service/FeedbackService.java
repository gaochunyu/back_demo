package com.cennavi.tp.service;

import com.cennavi.tp.beans.FeedbackBean;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface FeedbackService {

    boolean addFeedback(FeedbackBean feedback);

    int getFeedbackCount(Integer page, Integer pageSize, String keyword,Integer userId);

    List<FeedbackBean> getFeedbackList(Integer page, Integer pageSize, String keyword, Integer userId);

    boolean deleteFeedback(Integer id);

    boolean updateFeedbackState(FeedbackBean feedbackBean);

    boolean updateFeedback(FeedbackBean feedbackBean);

    FeedbackBean getFeedbackDetail(Integer id);
}
