package com.cennavi.tp.controller;

import com.cennavi.tp.beans.DataCard;
import com.cennavi.tp.beans.UserinfoBean;
import com.cennavi.tp.common.result.Result;
import com.cennavi.tp.common.result.ResultModel;
import com.cennavi.tp.service.BaseDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 基础数据处理
 */
@Controller
@RequestMapping("/base")
public class BaseDataController {

    @Autowired
    private BaseDataService baseDataService;

    @ResponseBody
    @RequestMapping("/getDataCard")
    public ResultModel getDataCard(HttpServletRequest request){
        try{
            UserinfoBean user = (UserinfoBean)request.getSession().getAttribute("user");
            if(user!=null){
//                Map<>
                if(user.getRole()==0){//超级管理员
                    List<DataCard> list = baseDataService.getVerifyDataCard(user.getId());
                    return Result.success("success",list);
                }else if(user.getRole()==1){//管理员
                    List<DataCard> list = baseDataService.getDataCard(user.getId());
                    return Result.success("success",list);
                }else{
                    return Result.fail("该权限用户无信息卡片");
                }
            }else{
                return Result.fail("获取用户信息失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            return Result.build500("出现异常");
        }
    }

}
