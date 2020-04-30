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
     * @Param uid 用户id
     * @Param model all获取所有 visit 普通访问，verify审核,mydata 我的内容
     * @return
     */
    @ResponseBody
    @RequestMapping("/getAssistTableList")
    // 请求帮助页的列表数据
    public ResultModel getAssistTableList(int page, int pageSize,HttpServletRequest request){
        try {

            Map<String,Object> list =  assistService.getAssistList(page,pageSize);
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
                                            int id,
                                            HttpServletRequest request)
    {

        return assistService.addOrUpdateAssiatantListItem(question, answer,category,id,request);

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


}
