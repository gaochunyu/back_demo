package com.cennavi.tp.controller;

import com.cennavi.tp.beans.MenuSubtitleBean;
import com.cennavi.tp.beans.UserinfoBean;
import com.cennavi.tp.common.result.Result;
import com.cennavi.tp.common.result.ResultModel;
import com.cennavi.tp.service.ProjectDataService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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

    @ResponseBody
    @RequestMapping("/saveProjectInfo")
    public ResultModel saveProjectInfo(int operation ,int id ,String name , int tradeTypeId, int proTypeId , String proContent , String proUrl , int proSort , MultipartFile mainImgFile , MultipartFile[] proImgFileList, String[] imgNameList,HttpServletRequest request){
        try{
            Object obj = request.getSession().getAttribute("user");
            if(obj==null){
                return Result.buildUnLogin();
            }
            UserinfoBean user = (UserinfoBean) obj;
            int uid = user.getId();
            boolean flag = projectDataService.saveProjectInfo(operation,id ,name , tradeTypeId, proTypeId , proContent , proUrl , proSort , mainImgFile ,proImgFileList ,imgNameList ,uid);
            if(flag){
                return Result.success("项目信息保存成功",flag);
            }else{
                return Result.fail("项目信息保存失败",new Object());
            }
        }catch (Exception e){
            e.getStackTrace();
            System.out.println(e.getMessage() + e);
            return Result.build500("出现异常");
        }
    }

    @ResponseBody
    @RequestMapping("/getProjectInfoById")
    public ResultModel getProjectInfoById(@RequestParam(value = "proId") Integer proId){
        try{
            List list = projectDataService.getProjectInfoById(proId);
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
