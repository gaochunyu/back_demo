package com.cennavi.tp.service;

import com.cennavi.tp.beans.FeedbackBean;

import java.util.List;

public interface FeedbackService {

    boolean addFeedback(FeedbackBean feedback);

    int getFeedbackCount(Integer page, Integer pageSize, String keyword);

    List<FeedbackBean> getFeedbackList(Integer page, Integer pageSize, String keyword);

    boolean deleteFeedback(Integer id);

    boolean updateFeedbackState(Integer id,Integer state);
}
