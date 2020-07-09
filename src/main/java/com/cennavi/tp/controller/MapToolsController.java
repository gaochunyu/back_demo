package com.cennavi.tp.controller;

import com.cennavi.tp.beans.MapToolsBean;
import com.cennavi.tp.common.result.Result;
import com.cennavi.tp.common.result.ResultModel;
import com.cennavi.tp.dao.MapToolsDao;
import com.cennavi.tp.service.MapToolsService;
import com.cennavi.tp.utils.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 马立伟 on 2020/6/3 22:49.
 */


@Controller
@RequestMapping("/tools")
public class MapToolsController {

    @Autowired
    private MapToolsService mapToolsService;
    @Autowired
    private MapToolsDao mapToolsDao;

    @Value("${file_path}")
    private String fileSavePath;


    /**
     * 获取列表
     *
     * @param offset
     * @param limit
     * @param model
     * @param type
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getMapToolsList")
    public ResultModel getMapToolsList(Integer offset, Integer limit, Integer model, String type, String status,HttpServletRequest request) {
//    public ResultModel getMapToolsList(Integer offset, Integer limit,Integer model, HttpServletRequest request) {
        try {
            ResultModel resultModel = mapToolsService.getMapToolsList(offset, limit, model,type,status,request);
            return resultModel;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.build500("出现异常");
        }
    }


    /**
     * 请求帮助页的列表数据
     *
     * @return
     * @Param id 本条信息的id
     */
    @ResponseBody
    @RequestMapping(value = "/addOrUpdate")
//    public ResultModel addOrUpdate(Integer id, String name, String icon, String type, String img, String help_info, String tags, int self, String url, Integer status, HttpServletRequest request) {
    public ResultModel addOrUpdate(Integer id, String name, String icon, String type, String img, String help_info, String tags,String url,
                                   int self,Integer status,Integer uid,MultipartFile file,HttpServletRequest request) {
        if (tags !=null){
            tags = tags.replace("，",",");
        }
        if (id == null) {//增加
//            return mapToolsService.addMapTools(mapToolsBean, request);
            return mapToolsService.addMapTools(uid,name, icon, type, img, help_info, tags, self, url, request);
        } else {//修改
            return mapToolsService.updateMapTools(id, name, icon, type, img, help_info,status,tags, self, url, request);
//            return mapToolsService.updateMapTools(id, name, icon, type, img, help_info,status,tags, self, url, request);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/uploadToolsImg")
    public String dealuUploadFile(MultipartFile file) {
        StringBuffer pathString = new StringBuffer();
        String finalPath = UploadUtil.handleFileUpload(fileSavePath, "test/", file);
        pathString.append(finalPath);
        return pathString.toString();
    }


    /**
     * 请求帮助页的列表数据
     *
     * @return
     * @Param id 本条信息的id
     */
    @ResponseBody
    @RequestMapping("/deleteMapToolsById")
    public ResultModel deleteMapToolsById(int id, HttpServletRequest request) {
        return mapToolsService.deleteById(id);
    }


    /**
     * 请求帮助页的列表数据
     *
     * @return
     * @Param id 本条信息的id
     */
    @ResponseBody
    @RequestMapping("/getMapToolsById")
    public ResultModel getMapToolsById(int id, HttpServletRequest request) {
        MapToolsBean mapToolsBean = mapToolsService.getMapToolsById(id);
        return Result.success("查询成功", mapToolsBean);
    }


    /**
     * 超级用户审核通过 && 审核拒绝
     *
     * @return
     * @Param id 信息id
     * @Param status 状态 true通过，false拒绝
     */
    @ResponseBody
    @RequestMapping(value = "/verify")
    public ResultModel verify(int id, Boolean status, HttpServletRequest request) {
        Object obj = request.getSession().getAttribute("user");
        try {
            MapToolsBean mapToolsBean = mapToolsService.getMapToolsById(id);
            if (status) {
                mapToolsBean.setStatus(2);
            } else {
                mapToolsBean.setStatus(3);
            }
            int count = mapToolsDao.updateMapTools(mapToolsBean);
            if (count > 0) {
                return Result.success("操作成功");
            } else {
                return Result.fail("操作失败");
            }
        } catch (Exception e) {
            e.getStackTrace();
            return Result.build500("出现异常");
        }
    }


}
