package com.cennavi.tp.service.impl;

import com.cennavi.tp.beans.AssistBean;
import com.cennavi.tp.common.result.Result;
import com.cennavi.tp.common.result.ResultModel;
import com.cennavi.tp.dao.AssistDao;
import com.cennavi.tp.service.AssistService;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 姚文帅 on 2020/4/29 14:31.
 */
@Service
public class AssistServiceImpl implements AssistService {

    @Resource
    AssistDao assistDao;

    @Override
    public ResultModel addOrUpdateAssiatantListItem(String question, String answer, String category, int id) {
        try{
            AssistBean assistBean = new AssistBean();

            assistBean.setQuestion(question);
            assistBean.setAnswer(answer);
            assistBean.setCategory(category);

            if(id==-1) {
                // 获取时间戳并转化格式
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
                Date date = new Date(System.currentTimeMillis());
                String createTime = formatter.format(date);
                assistBean.setCreateTime(createTime);

                // 如果新增成功，返回新增的id
                Map<String,Object> map = new HashMap<>();
                map.put("id",assistDao.addAssistItem(assistBean));
                return Result.success("成功新增一条数据",map);

            } else {
                assistBean.setId(id);
                if(assistDao.updateAssistItem(assistBean) == 1) {
                    // 如果更新成功，返回更新成功的id
                    // 如果新增成功，返回新增的id
                    Map<String,Object> map = new HashMap<>();
                    map.put("id",assistDao.addAssistItem(assistBean));
                    return Result.success("成功更新一条数据",id);
                } else {
                    return Result.success("更新失败",null);
                }
            }

        }catch (Exception e){
            e.getStackTrace();
            return Result.build500("出现异常");
        }


    }

    @Override
    public Integer deleteAssistItem(Integer id) {
        return assistDao.deleteAssistItem(id);
    }

    @Override
    public AssistBean getAssistItemById(Integer id) {
        return assistDao.getAssistItemById(id);
    }
}
