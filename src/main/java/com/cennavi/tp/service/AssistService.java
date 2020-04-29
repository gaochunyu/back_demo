package com.cennavi.tp.service;

import com.cennavi.tp.beans.AssistBean;
import com.cennavi.tp.beans.MenuSubtitleBean;
import com.cennavi.tp.common.result.ResultModel;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by 姚文帅 on 2020/4/29 14:31.
 */
public interface AssistService {

    /**
     * 新增或者是更新一条帮助信息
     * @return
     */

    ResultModel addOrUpdateAssiatantListItem(String question, String answer, String category, int id);

    // 删除数据
    Integer deleteAssistItem(Integer id);

    // 获取一条数据
    AssistBean getAssistItemById(Integer id);


}
