package com.cennavi.tp.controller;

import com.cennavi.tp.beans.MapToolsBean;
import com.cennavi.tp.common.result.Result;
import com.cennavi.tp.common.result.ResultModel;
import com.cennavi.tp.dao.MapToolsDao;
import com.cennavi.tp.service.MapToolsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 *  Created by 马立伟 on 2020/6/3 22:49.
 */


@Controller
@RequestMapping("/tools")
public class MapToolsController {

    @Autowired
    private MapToolsService mapToolsService;
    @Autowired
    private MapToolsDao mapToolsDao;


    /**
     * 获取列表
     * @param offset
     * @param limit
     * @param model
     * @param type
     * @param searchKey
     * @param statusValue
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getMapToolsList")
    public ResultModel getMapToolsList(Integer offset, Integer limit, Integer model, String type,String searchKey, Integer statusValue, HttpServletRequest request){
        try {
            ResultModel resultModel =  mapToolsService.getMapToolsList(offset, limit, model, type, searchKey, statusValue,request);
            return resultModel;
        } catch (Exception e){
            e.printStackTrace();
            return Result.build500("出现异常");
        }
    }


    /**
     * 请求帮助页的列表数据
     * @Param id 本条信息的id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addOrUpdate", method = RequestMethod.POST)
    public ResultModel addOrUpdate(Integer id,String name,String icon,String type,String img,String help_info,String tags,int self,String url,HttpServletRequest request){
        if(id==null){//增加
            return mapToolsService.addMapTools(name,icon,type,img,help_info,tags,self,url,request);
        }else{//修改
            return mapToolsService.updateMapTools(id,name,icon,type,img,help_info,tags,self,url,request);
        }
    }


    /**
     * 请求帮助页的列表数据
     * @Param id 本条信息的id
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleteMapToolsById")
    public ResultModel deleteMapToolsById(int id, HttpServletRequest request){
        return mapToolsService.deleteById(id);
    }



    /**
     * 请求帮助页的列表数据
     * @Param id 本条信息的id
     * @return
     */
    @ResponseBody
    @RequestMapping("/getMapToolsById")
    public ResultModel getMapToolsById(int id, HttpServletRequest request){
        MapToolsBean mapToolsBean =  mapToolsService.getMapToolsById(id);
        return Result.success("查询成功",mapToolsBean);
    }


    /**
     *  超级用户审核通过 && 审核拒绝
     * @Param id 信息id
     * @Param status 状态 true通过，false拒绝
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/verify")
    public ResultModel verify(int id, Boolean status, HttpServletRequest request) {
        Object obj = request.getSession().getAttribute("user");
        try {
            MapToolsBean mapToolsBean = mapToolsService.getMapToolsById(id);
            if(status){
                mapToolsBean.setStatus(2);
            }else{
                mapToolsBean.setStatus(3);
            }
            int count = mapToolsDao.updateMapTools(mapToolsBean);
            if(count>0){
                return Result.success("操作成功");
            }else{
                return Result.fail("操作失败");
            }
        } catch (Exception e){
            e.getStackTrace();
            return Result.build500("出现异常");
        }
    }


}
