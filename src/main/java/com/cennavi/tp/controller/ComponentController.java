package com.cennavi.tp.controller;

import com.cennavi.tp.beans.ComponentBean;
import com.cennavi.tp.common.result.Result;
import com.cennavi.tp.common.result.ResultModel;
import com.cennavi.tp.service.ComponentService;
import com.cennavi.tp.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
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

    // 接口1.增加组件及图片
    // 接口2.删除组件及图片
    // 接口3.修改组件及图片
    // 接口4.分页搜索查询组件列表
    // 接口5.根据id查询组件及图片

    // 添加
    @ResponseBody
    @RequestMapping("/addComponent")
    public ResultModel addComponent(@RequestBody ComponentBean componentBean) {
        try {
            return componentService.addComponent(componentBean);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("查找数据失败", null);
        }
    }

    // 接口6.获取组件列表
}
