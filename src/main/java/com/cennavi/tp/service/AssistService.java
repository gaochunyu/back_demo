package com.cennavi.tp.service;

import com.cennavi.tp.beans.AssistBean;
import com.cennavi.tp.beans.MenuSubtitleBean;
import com.cennavi.tp.common.result.Result;
import com.cennavi.tp.common.result.ResultModel;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by 姚文帅 on 2020/4/29 14:31.
 */
public interface AssistService {

    /**
     * 新增或者是更新一条帮助信息
     * @return
     */

    ResultModel addOrUpdateAssiatantListItem(String question, String answer, String category, int id,Boolean saveType, HttpServletRequest request);

    // 删除数据
    ResultModel deleteAssistItemById(Integer id);

    // 获取一条数据
    AssistBean getAssistItemById(Integer id);

    /**
     * 帮助列表分页查询数据
     * @Param 分页
     * @return
     */
    Map<String,Object> getAssistList(Integer page, Integer pageSize,Integer contentType,Integer userId,String[] categoryList,String createTimeSortType,String weightSortType,int statusValue);

    /**
     * 根据id根据权重信息
     * @return
     */
    Integer updateAssistItemWeightById (Integer id,Boolean updateType);


    Integer checkAssistItem(Integer id,Boolean checkType);


    Integer questionIsCorrect(String question);



}
