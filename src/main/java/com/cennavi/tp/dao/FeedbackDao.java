package com.cennavi.tp.dao;

import com.cennavi.tp.beans.FeedbackBean;
import com.cennavi.tp.common.base.dao.BaseDao;

import java.util.List;

public interface FeedbackDao extends BaseDao<FeedbackBean> {

    int getFeedbackCount(Integer page, Integer pageSize, String keyword,Integer userId);

    List<FeedbackBean> getFeedbackList(Integer page, Integer pageSize, String keyword,Integer userId);

    int updateFeedbackState(FeedbackBean feedbackBean);

    FeedbackBean getFeedbackDetail(Integer id);
}
