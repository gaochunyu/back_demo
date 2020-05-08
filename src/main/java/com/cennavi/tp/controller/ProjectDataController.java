package com.cennavi.tp.controller;

import com.cennavi.tp.common.result.Result;
import com.cennavi.tp.common.result.ResultModel;
import com.cennavi.tp.service.ProjectDataService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/projectData")
public class ProjectDataController {

    @Resource
    private ProjectDataService projectDataService;

    @ResponseBody
    @RequestMapping("/getProjectList")
    public ResultModel getProjectList(@RequestParam(value = "limitSize") Integer limitSize ,@RequestParam(value = "curPage") Integer curPage , @RequestParam(value = "tradeType") String tradeType ,@RequestParam(value = "projectType") String projectType ){
        try{
            Map list = projectDataService.getProjectList(limitSize,curPage ,tradeType ,projectType);
            if(list == null && list.size() == 0){
                return Result.fail("暂无数据",new ArrayList<>()); //1000
            }else{
                return Result.success("成功获取数据",list);  //200
            }
        }catch (Exception e){
            e.getStackTrace();
            return Result.build500("出现异常");
        }
    }

}
