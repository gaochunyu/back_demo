package com.cennavi.tp.controller;

import com.alibaba.fastjson.JSONObject;
import com.cennavi.tp.beans.ContentBean;
import com.cennavi.tp.common.result.Result;
import com.cennavi.tp.common.result.ResultModel;
import com.cennavi.tp.service.ContentService;
import com.cennavi.tp.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by 姚文帅 on 2020/4/17 14:23.
 */
@Controller
@RequestMapping("/content")
public class ContentController {

    @Autowired
    private ContentService contentService;

    // 1. 新增一条数据
    @ResponseBody
    @RequestMapping("/addAContentItem")
    public ResultModel addAContentItem(@RequestParam(value = "title") String title,
                                       @RequestParam(value = "content") String content,
                                       @RequestParam(value = "tags") String tags,
                                       @RequestParam(value = "file") String file,
                                       @RequestParam(value = "create_time") String create_time) {



        JSONObject json = new JSONObject();
        try {
            ContentBean contentBean = new ContentBean();
            contentBean.setTitle(title);
            contentBean.setContent(content);
            contentBean.setTags(tags);
            contentBean.setFile(file);
            contentBean.setCreate_time(create_time);
            contentService.addANewItem(contentBean);
            return Result.success("成功获取数据", "成功插入一条数据");

        } catch (Exception e) {
            e.printStackTrace();
            json = JsonUtils.packJsonErr(e.getMessage());
        } finally {
            return Result.fail("插入数据失败", JsonUtils.objectToJson(json,new String[]{}));

        }
    }

    // 2. 删除一条数据(根据id删除一条数据)
    @ResponseBody
    @RequestMapping("/deleteItemById")
    public ResultModel deleteItemById(@RequestParam(value = "id") int id) {
        contentService.deleteItemById(id);

        return Result.success("成功删除一条数据", "成功删除一条数据");
    }


    // 3. 更新某条数据内容

    // 4. 查找数据：根据id获取一条表数据
    @ResponseBody
    @RequestMapping("/getItemById")
    public ResultModel getItemById(@RequestParam(value = "id") int id) {
        contentService.getItemById(id);
        return Result.success("成功找到一条数据", "成功找到一条数据");
    }
}
