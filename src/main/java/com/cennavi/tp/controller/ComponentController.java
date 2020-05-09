package com.cennavi.tp.controller;

import com.cennavi.tp.beans.ComponentBean;
import com.cennavi.tp.common.result.Result;
import com.cennavi.tp.common.result.ResultModel;
import com.cennavi.tp.service.ComponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: chenfeng
 * @date: 2020/4/29 16:47
 */
@Controller
@RequestMapping("/component")
public class ComponentController {

    @Autowired
    private ComponentService componentService;

    // 接口3.修改组件及图片
    // 接口4.分页搜索查询组件列表
    // 接口5.根据id查询组件及图片

    // 添加组件
    @ResponseBody
    @RequestMapping("/addComponent")
    public ResultModel addComponent(Integer uid, String name, String tags, String cover, String content, String testUrl, String fileUrl) {
        try {
            return componentService.addComponent(uid, name, tags, cover, content, testUrl, fileUrl);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("查找数据失败", null);
        }
    }

    // 删除组件
    @ResponseBody
    @RequestMapping("/delComponent")
    public ResultModel delComponent(Integer id, Integer uid) {
        return componentService.delComponent(id,uid);
    }

    // 更新组件
    @ResponseBody
    @RequestMapping("/updateComponent")
    public ResultModel updateComponent(Integer id, Integer uid, String name, String tags, String cover, String content, String testUrl, String fileUrl) {
        return componentService.updateComponent(id,uid,name,tags,cover,content,testUrl,fileUrl);
    }

    // 接口6.获取组件列表
}
