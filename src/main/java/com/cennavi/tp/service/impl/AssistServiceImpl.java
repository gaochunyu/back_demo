package com.cennavi.tp.service.impl;

import com.cennavi.tp.beans.AssistBean;
import com.cennavi.tp.beans.UserinfoBean;
import com.cennavi.tp.common.result.Result;
import com.cennavi.tp.common.result.ResultModel;
import com.cennavi.tp.dao.AssistDao;
import com.cennavi.tp.service.AssistService;

import com.cennavi.tp.service.UserAssistService;
import com.cennavi.tp.utils.MyDateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 姚文帅 on 2020/4/29 14:31.
 */
@Service
public class AssistServiceImpl implements AssistService {

    @Resource
    private AssistDao assistDao;

    @Autowired
    private UserAssistService userAssistService;

    @Override
    public ResultModel addOrUpdateAssiatantListItem(String question, String answer, String category, int id, Boolean saveType, HttpServletRequest request) {
        try{
            UserinfoBean user = (UserinfoBean) request.getSession().getAttribute("user");
            AssistBean assistBean = new AssistBean();

            assistBean.setQuestion(question);
            assistBean.setAnswer(answer);
            assistBean.setCategory(category);
            assistBean.setUserId(user.getId());

            if(saveType) {
                // 如果是自动保存，那麽这条数据的状态是 0 录入中
                assistBean.setStatus(0);
            } else {
                // 如果是点击提交保存的，那么这条数据的状态 1 待审核
                assistBean.setStatus(1);
            }


            // 新增一条数据
            if(id==-1) {
                // 获取时间戳并转化格式
                String format = "yyyy-MM-dd";
                String time = MyDateUtils.format(new Date(),format);
                assistBean.setCreateTime(time);

                // 如果新增成功，返回新增的id
                Map<String,Object> map = new HashMap<>();
                map.put("id",assistDao.addAssistItem(assistBean));
                return Result.success("成功新增一条数据",map);

            } else {
                assistBean.setId(id);

                if(assistDao.updateAssistItem(assistBean) == 1) {
                    // 如果更新成功，返回更新成功的id
                    Map<String,Object> map = new HashMap<>();
                    map.put("id", id);
                    return Result.success("成功更新一条数据",map);
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
    public ResultModel deleteAssistItemById(Integer id) {
        try{
            if(assistDao.deleteAssistItem(id) == 1) {
                return Result.success("成功删除数据",null);
            } else {
                return Result.fail("删除数据异常",null);
            }
        }catch (Exception e){
            e.getStackTrace();
            return Result.build500("出现异常");
        }

    }

    @Override
    public ResultModel getAssistItemById(Integer userID, Integer id) {

        try {
            AssistBean assistBean = assistDao.getAssistItemById(id);
            if(assistBean!= null) {

                Map<String,Object> map = new HashMap<>();
                map.put("assistData", assistBean);
                // 获取本条信息是否被赞过的状态
                if(userAssistService.getAssistWeightStatus(userID, id)) {
                    map.put("status", true);
                } else {
                   map.put("status", false);
                }
                return Result.success("成功获取数据",map);
            } else {
                return Result.success("没有获取到数据",null);
            }
        } catch (Exception e){
            e.getStackTrace();
            return Result.build500("出现异常");
        }
    }


    @Override
    public Map<String,Object> getAssistList(Integer page, Integer pageSize,Integer contentType,Integer userId,String[] categoryList,String createTimeSortType,String weightSortType,int statusValue){
        return assistDao.getAssistList(page, pageSize,contentType,userId,categoryList,createTimeSortType,weightSortType,statusValue);
    }

    @Override
    public ResultModel updateAssistItemWeightById(Integer userID, Integer id, Boolean updateType) {

        try {
            Integer result =  assistDao.updateAssistItemWeightById(id, updateType);
            Integer result2 =  userAssistService.giveARedHeart(userID, id, updateType);

            if(result == 1 && result2 == 1) {
                return Result.success("成功修改数据",null);
            } else {
                return Result.success("修改数据失败",null);
            }
        } catch (Exception e){
            e.getStackTrace();
            return Result.build500("出现异常");
        }
    }

    @Override
    public Integer checkAssistItem(Integer id, Boolean checkType) {
        return assistDao.checkAssistItem(id, checkType);
    }

    @Override
    public Integer questionIsCorrect(String question) {
        return assistDao.questionIsCorrect(question);
    }


}
