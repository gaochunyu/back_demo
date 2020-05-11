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

import javax.servlet.http.HttpServletResponse;
import java.io.*;
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
     * @param uid
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
    public ResultModel updateResources(Integer id,Integer uid,String name,String tags,MultipartFile file,String description,String link, Integer type){
        try {
            ResourcesBean resourcesBean = resourcesService.getResourcesById(id);
            if(resourcesBean == null){
                return Result.success("id无效");
            }
            boolean flag = resourcesService.updateResourcesItem( id, uid, name, tags, file, description, link, type);
            if(flag){
                return Result.success("更新成功");
            }else{
                return Result.fail("更新失败");

            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("更新异常");

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
    public ResultModel getResourcesList(Integer page,Integer pageSize,String tags,String status){
        try {
            Map<String,Object> map = new HashMap<>();
            List<ResourcesBean> list = resourcesService.getResourcesList( page, pageSize, tags ,status);
            int count = resourcesService.getResourcesCount(tags,status);
            map.put("list",list);
            map.put("total",count);
            return Result.success("查询成功",map);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("查询异常");
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
            return Result.fail("删除异常");
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
            resourcesService.updateResourcesViews(id);
            return Result.success("查询成功",resourcesService.getResourcesById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("查询异常");
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
            return Result.success("查询成功",resourcesService.getTopFiveByCreateTime());
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
            return Result.success("查询成功",resourcesService.getTopFiveByViews());
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("查询异常");
        }
    }

    /**
     * 下载文件
     * @param id
     * @param response
     * @return
     * @throws FileNotFoundException
     */
    @ResponseBody
    @RequestMapping("/downLoadFile")
    public void downLoadFile(Integer id,HttpServletResponse response) throws FileNotFoundException {
        try {
            ResourcesBean resourcesBean = resourcesService.getResourcesById(id);
            // 文件的存放路径
            String filePath = resourcesBean.getFile();
            File file = new File(filePath);
            String fileName = file.getName();
            System.out.println(fileName);
            // 设置输出的格式
            response.setContentType("application/octet-stream");
            response.setCharacterEncoding("UTF-8");
            response.addHeader("Content-Disposition", "attachment; filename=" + java.net.URLEncoder.encode(fileName,"UTF-8"));

            byte[] buff = new byte[1024];
            BufferedInputStream bis = null;
            OutputStream os = null;
            try {
                os = response.getOutputStream();
                bis = new BufferedInputStream(new FileInputStream(new File(filePath)));
                int i = bis.read(buff);
                while (i != -1) {
                    os.write(buff, 0, buff.length);
                    os.flush();
                    i = bis.read(buff);
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new Exception(e.getMessage());
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *审核资料
     * @param id
     * @param status
     * @return
     */
    @ResponseBody
    @RequestMapping("/verify")
    public ResultModel verify(Integer id,Integer status){
        try {
            boolean flag = resourcesService.updateResourcesStatus(id,status);
            if(flag){
                return Result.success("审核成功");
            }else{
                return Result.fail("审核失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("审核异常");
        }
    }
}
