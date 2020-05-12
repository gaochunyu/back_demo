package com.cennavi.tp.controller;

import com.cennavi.tp.beans.AssistBean;
import com.cennavi.tp.beans.UserinfoBean;
import com.cennavi.tp.common.result.Result;
import com.cennavi.tp.common.result.ResultModel;
import com.cennavi.tp.service.AssistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Created by 姚文帅 on 2020/4/29 14:30. 帮助接口
 */


@Controller
@RequestMapping("/assist")
public class AssistController {

    @Autowired
    private AssistService assistService;


    /**
     * 获取帮助列表,采用分页的方式,默认分页为1
     * @Param    page页数，pageSize每页的大小
     * @Param    数据请求的模式（浏览,我的发布，我的审核）当前模式id 2-浏览模式  3-审核模式   4-我的发布
     * @Param    数据筛选条件 categoryList  1 默认分类  2 地图知识 3 基础知识 4 图层
     * @Param    数据排序条件 createTimeSortType 根据日期排序 weightSortType 根据权重排序
     * @Param    状态筛选，只有管理员有 statusValue
     */
    @ResponseBody
    @RequestMapping("/getAssistTableList")
    // 请求帮助页的列表数据
    public ResultModel getAssistTableList(int page, int pageSize, int contentType,
                                         String[] categoryList,String createTimeSortType,String weightSortType,int statusValue, HttpServletRequest request){

        UserinfoBean user = (UserinfoBean) request.getSession().getAttribute("user");
        int userId = user.getId();
        try {
            Map<String,Object> list =  assistService.getAssistList(page,pageSize,contentType,userId,categoryList,createTimeSortType,weightSortType,statusValue);
            return Result.success("成功获取数据",list);

        } catch (Exception e){
            e.getStackTrace();
            return Result.build500("出现异常");
        }
    }


    /**
     * 增加/更新一个item的信息
     * @Param id 本条信息的id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addOrUpdateAssistItem", method = RequestMethod.POST)
    // 请求帮助页的列表数据
    public ResultModel addOrUpdateAssistItem(@RequestParam(value = "question") String question,
                                             @RequestParam(value = "answer") String answer,
                                             @RequestParam(value = "category") String category,
                                             @RequestParam(value = "saveType") Boolean saveType,
                                            int id,
                                            HttpServletRequest request)
    {

        return assistService.addOrUpdateAssiatantListItem(question, answer,category,id,saveType,request);

    }


    /**
     * 删除单个item的信息
     * @Param id 本条信息的id
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleteAssistItemById")
    // 请求帮助页的列表数据
    public ResultModel deleteAssistItemById(int id, HttpServletRequest request){
        return assistService.deleteAssistItemById(id);
    }



    /**
     * 获取列表单个item的信息
     * @Param id 本条信息的id
     * @return
     */
    @ResponseBody
    @RequestMapping("/getAssistItemById")
    // 请求帮助页的列表数据
    public ResultModel getAssistItemById(int id, HttpServletRequest request){
        try {
            AssistBean assistBean = assistService.getAssistItemById(id);
            if(assistBean!= null) {
                return Result.success("成功获取数据",assistBean);
            } else {
                return Result.success("没有获取到数据",null);
            }
        } catch (Exception e){
            e.getStackTrace();
            return Result.build500("出现异常");
        }
    }

    /**
     * 修改每条记录的权重信息
     * @Param id 本条信息的id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateAssistItemWeightById",method = RequestMethod.GET )
    public ResultModel updateAssistItemWeightById(int id, Boolean type, HttpServletRequest request) {
        try {
            Integer result =  assistService.updateAssistItemWeightById(id, type);

            if(result == 1) {
                return Result.success("成功修改数据",null);
            } else {
                return Result.success("修改数据失败",null);
            }
        } catch (Exception e){
            e.getStackTrace();
            return Result.build500("出现异常");
        }

    }

    /**
     *  超级用户审核通过 && 审核拒绝
     * @Param id 本条信息的id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/checkAssistItem",method = RequestMethod.GET )
    public ResultModel checkAssistItem(int id, Boolean checkType, HttpServletRequest request) {
        Object obj = request.getSession().getAttribute("user");
        try {
            Integer result =  assistService.checkAssistItem(id, checkType);

            if(result == 1) {
                return Result.success("审核成功",null);
            } else {
                return Result.success("审核失败",null);
            }
        } catch (Exception e){
            e.getStackTrace();
            return Result.build500("出现异常");
        }
    }


}
