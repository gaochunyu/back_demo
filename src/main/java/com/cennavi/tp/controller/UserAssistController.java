package com.cennavi.tp.controller;

import com.cennavi.tp.beans.UserinfoBean;
import com.cennavi.tp.common.result.Result;
import com.cennavi.tp.common.result.ResultModel;
import com.cennavi.tp.service.UserAssistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by 姚文帅 on 2020/5/13 14:36.
 */
@Controller
@RequestMapping("/userAssist")
public class UserAssistController {

    @Autowired
    private UserAssistService userAssistService;

    /**
     *
     * @Param  点赞接口
     */
    @ResponseBody
    @RequestMapping(value = "/giveARedHeart", method = RequestMethod.GET)
    public ResultModel deleteUserAssistItem(int assistId, Boolean type, HttpServletRequest request){


        UserinfoBean user = (UserinfoBean) request.getSession().getAttribute("user");
        int userId = user.getId();

        try {
            if(userAssistService.giveARedHeart(userId, assistId,type) == 1) {
                return Result.success("成功进行操作", null);
            } else {
                return Result.success("操作异常", null);
            }


        } catch (Exception e){
            e.getStackTrace();
            return Result.build500("出现异常");
        }
    }


//    /**
//     *
//     * @Param  获取本条数据是否是被点赞的接口
//     */
//    @ResponseBody
//    @RequestMapping(value = "/getAssistWeightStatus", method = RequestMethod.GET)
//    public ResultModel getAssistWeightStatus(int assistId, HttpServletRequest request){
//
//
//        // 获取用户 id
//        UserinfoBean user = (UserinfoBean) request.getSession().getAttribute("user");
//        int userId = user.getId();
//
//        try {
//            if(userAssistService.getAssistWeightStatus(userId, assistId) == 1) {
//                return Result.buildResult(200, "成功获取数据,本条数据已经被赞过",null);
//            } else {
//                return Result.buildResult(201, "成功获取数据,本条数据没有被赞过",null);
//            }
//
//        } catch (Exception e){
//            e.getStackTrace();
//            return Result.build500("出现异常");
//        }
//    }






}
