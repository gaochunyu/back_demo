package com.cennavi.tp.controller;

import com.cennavi.tp.beans.MenuSubtitleBean;
import com.cennavi.tp.beans.UserinfoBean;
import com.cennavi.tp.common.result.Result;
import com.cennavi.tp.common.result.ResultModel;
import com.cennavi.tp.service.AssistService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by 姚文帅 on 2020/4/29 14:30. 帮助接口
 */
@Controller
@RequestMapping("assist")
public class AssistController {

    @Resource
    private AssistService assistService;


    /**
     * 获取帮助列表,采用分页的方式,默认分页为1
     * @Param uid 用户id
     * @Param model all获取所有 visit 普通访问，verify审核,mydata 我的内容
     * @return
     */
    @ResponseBody
    @RequestMapping("/getAssiatantList")
    // 请求帮助页的列表数据
    public ResultModel getAssiatantList(int page, HttpServletRequest request){
        try {
            return Result.success("成功获取数据",null);

        } catch (Exception e){
            e.getStackTrace();
            return Result.build500("出现异常");
        }
    }


    /**
     * 增加一个item的信息
     * @Param id 本条信息的id
     * @return
     */
    @ResponseBody
    @RequestMapping("/addAssiatantListItem")
    // 请求帮助页的列表数据
    public ResultModel addAssiatantListItem(int id, HttpServletRequest request){
        try {
            return Result.success("成功获取数据",null);
        } catch (Exception e){
            e.getStackTrace();
            return Result.build500("出现异常");
        }

    }


    /**
     * 删除单个item的信息
     * @Param id 本条信息的id
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleteAssiatantListItemById")
    // 请求帮助页的列表数据
    public ResultModel deleteAssiatantListItemById(int id, HttpServletRequest request){
        try {

            return Result.success("成功获取数据",null);


        } catch (Exception e){
            e.getStackTrace();
            return Result.build500("出现异常");
        }

    }

    /**
     * 更新单个item的信息
     * @Param id 本条信息的id
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateAssiatantListItemById")
    // 请求帮助页的列表数据
    public ResultModel updateAssiatantListItemById(int id, HttpServletRequest request){
        try {

            return Result.success("成功获取数据",null);


        } catch (Exception e){
            e.getStackTrace();
            return Result.build500("出现异常");
        }

    }



    /**
     * 获取列表单个item的信息
     * @Param id 本条信息的id
     * @return
     */
    @ResponseBody
    @RequestMapping("/getAssiatantListItem")
    // 请求帮助页的列表数据
    public ResultModel getAssiatantListItem(int id, HttpServletRequest request){
        try {

            return Result.success("成功获取数据",null);


        } catch (Exception e){
            e.getStackTrace();
            return Result.build500("出现异常");
        }

    }







}
