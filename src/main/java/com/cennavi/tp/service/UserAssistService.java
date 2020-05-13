package com.cennavi.tp.service;

/**
 * Created by 姚文帅 on 2020/5/13 14:36.
 */
public interface UserAssistService {

    Integer giveARedHeart(Integer userId, Integer assistId, Boolean type);

    // 根据用户id和帮助列表的id获取本条数据的状态，是否已经点赞过
    Boolean getAssistWeightStatus(Integer userId, Integer assistId);
}
