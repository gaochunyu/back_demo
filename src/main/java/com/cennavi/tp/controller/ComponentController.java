package com.cennavi.tp.controller;

import com.cennavi.tp.common.result.Result;
import com.cennavi.tp.common.result.ResultModel;
import com.cennavi.tp.service.ComponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

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
    @RequestMapping(value = "/addComponent", method = RequestMethod.POST)
    public ResultModel addComponent(Integer uid, String name, Integer type, String tags, String content, MultipartFile coverImg, List<MultipartFile> imgList, String visitUrl, MultipartFile file) {
        try {
            boolean flag = componentService.addComponent(uid, name, type, tags, content, coverImg, imgList, visitUrl, file);
            if (flag) {
                return Result.success("保存成功",null);
            } else {
                return Result.fail("保存失败", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.build500("出现异常", null);
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

    // 获取组件类型列表
    @ResponseBody
    @RequestMapping("/getComponentTypeList")
    public ResultModel getComponentTypeList() {
        try {
            List list = componentService.getComponentTypeList();
            if (list == null && list.size() == 0) {
                return Result.fail("暂无数据", new ArrayList<>());
            } else {
                return Result.success("查询成功", list);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.build500("出现异常");
        }
    }

    // 接口6.获取组件列表
}
