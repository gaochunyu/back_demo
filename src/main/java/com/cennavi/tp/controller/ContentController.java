package com.cennavi.tp.controller;

import com.alibaba.fastjson.JSONObject;
import com.cennavi.tp.beans.ContentBean;
import com.cennavi.tp.common.result.Result;
import com.cennavi.tp.common.result.ResultModel;
import com.cennavi.tp.service.ContentService;
import com.cennavi.tp.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 姚文帅 on 2020/4/17 14:23.
 */
@Controller
@RequestMapping("/content")
public class ContentController {

    @Autowired
    private ContentService contentService;

    // 1. 新增一条数据, title为必须参数，其他的默认值为 ""
    @ResponseBody
    @PostMapping("/addAContentItem")
    public ResultModel addAContentItem(@RequestParam(value = "title",required = true) String title,
                                       @RequestParam(value = "content",required = false, defaultValue = "") String content,
                                       @RequestParam(value = "tags",required = false, defaultValue = "") String tags,
                                       @RequestParam(value = "file",required = false, defaultValue = "") String file) {



        // 获取时间戳并转化格式
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        String createTime = formatter.format(date);

        JSONObject json = new JSONObject();
        try {
            ContentBean contentBean = new ContentBean();
            contentBean.setTitle(title);
            contentBean.setContent(content);
            contentBean.setTags(tags);
            contentBean.setFile(file);
            contentBean.setCreate_time(createTime);

            // 获得刚刚插入的key值
            long insertId = contentService.addANewItem(contentBean);
            return Result.success("成功插入数据", contentService.getItemById((int)insertId));

        } catch (Exception e) {
            e.printStackTrace();
            json = JsonUtils.packJsonErr(e.getMessage());
            return Result.fail("插入数据失败", JsonUtils.objectToJson(json,new String[]{}));
        } finally {

        }
    }

    // 2. 删除一条数据(根据id删除一条数据)
    @ResponseBody
    @PostMapping("/deleteItemById")
    public ResultModel deleteItemById(@RequestParam(value = "id") int id) {
        JSONObject json = new JSONObject();
        try {
            ContentBean contentBean =  contentService.getItemById(id);
            contentService.deleteItemById(id);
            return Result.success("成功删除一条数据,删除的数据是", contentBean);
        } catch (Exception e) {
            e.printStackTrace();
            json = JsonUtils.packJsonErr(e.getMessage());
            return Result.fail("删除数据失败", JsonUtils.objectToJson(json,new String[]{}));
        }
    }


    // 3. 更新某条数据内容
    @ResponseBody
    @PostMapping("/updateItemById")
    public ResultModel updateItemById(@RequestParam(value = "id") int id,
                                      @RequestParam(value = "title",required = false) String title,
                                      @RequestParam(value = "content", required = false) String content,
                                      @RequestParam(value = "tags", required = false) String tags,
                                      @RequestParam(value = "file", required = false) String file){
        JSONObject json = new JSONObject();
        try {
            ContentBean contentBean = new ContentBean();
            contentBean.setTitle(title);
            contentBean.setContent(content);
            contentBean.setTags(tags);
            contentBean.setFile(file);
            contentService.updateItemById(id, contentBean);
            return Result.success("成功更新一条数据", contentBean);
        } catch (Exception e) {
            e.printStackTrace();
            json = JsonUtils.packJsonErr(e.getMessage());
            return Result.fail("更新数据失败", JsonUtils.objectToJson(json,new String[]{}));
        }
    }

    // 4. 查找数据：根据id获取一条表数据  RequestParam 默认参数是必填
    @ResponseBody
    @PostMapping("/getItemById")
    public ResultModel getItemById(@RequestParam(value = "id") int id) {

        JSONObject json = new JSONObject();
        try {
            ContentBean contentBean =  contentService.getItemById(id);
            if(contentBean != null) {
                return Result.success("成功找到一条数据", contentBean);
            } else {
                return Result.success("没有找到对应id的数据", null);
            }

        } catch (Exception e) {
            e.printStackTrace();
            json = JsonUtils.packJsonErr(e.getMessage());
            return Result.fail("查找数据失败", JsonUtils.objectToJson(json,new String[]{}));
        }
    }
}
