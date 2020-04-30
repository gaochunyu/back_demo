package com.cennavi.tp.controller;

import com.cennavi.tp.beans.ResourcesBean;
import com.cennavi.tp.common.result.Result;
import com.cennavi.tp.common.result.ResultModel;
import com.cennavi.tp.service.ResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/resources")
public class ResourcesController {
    @Autowired
    private ResourcesService resourcesService;

    /**
     * 新增资料
     * @param uid
     * @param description
     * @param file
     * @param name
     * @param link
     * @param tags
     * @param type
     */
    @ResponseBody
    @RequestMapping(value = "/addResources",method = RequestMethod.POST)
    public ResultModel addResources(Integer uid, String name, String tags, MultipartFile file, String description, String link, Integer type){
        try {
            return resourcesService.addResourcesItem( uid, name, tags, file, description, link, type);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("添加失败");
        }
    }

    /**
     * 更细数据
     * @param id
     * @param name
     * @param tags
     * @param file
     * @param description
     * @param link
     * @param type
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateResources",method = RequestMethod.POST)
    public ResultModel updateResources(Integer id,String name,String tags,MultipartFile file,String description,String link, Integer type){
        try {
            resourcesService.updateResourcesItem( id, name, tags, file, description, link, type);

            return Result.success("查询成功");

        } catch (Exception e) {
            e.printStackTrace();
            return Result.success("更新异常");

        }
    }

    /**
     * 获取资料分页列表
     * @param page
     * @param pageSize
     * @param tags
     * @return
     */
    @ResponseBody
    @RequestMapping("/getResourcesList")
    public ResultModel getResourcesList(Integer page,Integer pageSize,String tags){
        try {
            Map<String,Object> map = new HashMap<>();
            List<ResourcesBean> list = resourcesService.getResourcesList( page, pageSize, tags);
            int count = resourcesService.getResourcesCount();
            map.put("list",list);
            map.put("total",count);
            return Result.success("查询成功",map);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.success("查询异常");
        }
    }

    /**
     * 根据id删除资料
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleteResource")
    public ResultModel deleteResource(Integer id){
        try {
             Boolean flag =  resourcesService.deleteResource(id);
             if(flag){
                 return Result.success("删除成功");
             }else{
                 return Result.fail("删除失败");
             }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.success("删除异常");
        }
    }
    /**
     * 根据id获取资料
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/getResourceById")
    public ResultModel getResourceById(Integer id){
        try {
            return Result.success("查询成功",resourcesService.getResourcesById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.success("查询异常");
        }
    }

    /**
     * 根据创建时间获取top5
     * @return
     */
    @ResponseBody
    @RequestMapping("/getTopFiveByCreateTime")
    public ResultModel getTopFiveByCreateTime(){
        try {
            return Result.fail("查询成功",resourcesService.getTopFiveByCreateTime());
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("查询异常");
        }
    }
    /**
     * 根据点击量获取top5
     * @return
     */
    @ResponseBody
    @RequestMapping("/getTopFiveByViews")
    public ResultModel getTopFiveByViews(){
        try {
            return Result.fail("查询成功",resourcesService.getTopFiveByViews());
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("查询异常");
        }
    }

    @ResponseBody
    @RequestMapping("/downLoadFile")
    public ResultModel downLoadFile(){
        try {
            return Result.success("下载成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.success("下载失败");
        }
    }


}
