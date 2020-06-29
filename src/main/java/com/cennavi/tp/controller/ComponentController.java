package com.cennavi.tp.controller;

import com.cennavi.tp.beans.ComponentBean;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: chenfeng
 * @date: 2020/4/29 16:47
 */
@Controller
@RequestMapping("/component")
public class ComponentController {

    @Autowired
    private ComponentService componentService;

    // 添加模块
    @ResponseBody
    @RequestMapping(value = "/addComponent", method = RequestMethod.POST)
    public ResultModel addComponent(Integer uid, String name, String type, String tags, String content,
                                    MultipartFile coverImg, List<MultipartFile> imgList, String visitUrl, MultipartFile file) {
        try {
            boolean flag = componentService.addComponent(uid, name, type, tags, content, coverImg, imgList, visitUrl, file);
            if (flag) {
                return Result.success("保存成功");
            } else {
                return Result.fail("保存失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.build500("出现异常");
        }
    }

    // 删除模块
    @ResponseBody
    @RequestMapping("/deleteComponent")
    public ResultModel deleteComponent(Integer id) {
        try {
            boolean flag = componentService.deleteComponent(id);
            if (flag) {
                return Result.success("删除成功");
            } else {
                return Result.fail("删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.build500(e.getMessage(),null);
        }

    }

    // 更新模块
    @ResponseBody
    @RequestMapping("/updateComponent")
    public ResultModel updateComponent(Integer id, String name, String type, String tags, String content,
                                       MultipartFile coverImg, String coverUploadedPath, List<MultipartFile> imgList, String imgUploadedPathList,
                                       String visitUrl, MultipartFile file, String fileUploadedPath) {
        try {
            boolean flag = componentService.updateComponent(id, name, type, tags, content, coverImg, coverUploadedPath, imgList, imgUploadedPathList, visitUrl, file, fileUploadedPath);
            if (flag) {
                return Result.success("更新成功");
            } else {
                return  Result.fail("更新失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.build500("出现异常");
        }
    }

    // 获取模块类型列表
    @ResponseBody
    @RequestMapping("/getComponentTypeList")
    public ResultModel getComponentTypeList() {
        try {
            List<Map<String, Object>> list = componentService.getComponentTypeList();
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

    // 获取模块分页列表
    @ResponseBody
    @RequestMapping("/getComponentList")
    public ResultModel getComponentList(Integer pageNo, Integer pageSize, String tags, String status, String type, Integer uid) {
        try {
            Map<String, Object> map = new HashMap<>();
            List<ComponentBean> list = componentService.getComponentList(pageNo, pageSize, tags, status, type, uid);
            int count = componentService.getComponentCount(tags, status, type, uid);
            map.put("list", list);
            map.put("total",count);
            return Result.success("查询成功",map);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.build500("出现异常");
        }
    }

    // 根据id获取模块
    @ResponseBody
    @RequestMapping("/getComponentById")
    public ResultModel getComponentById(Integer id) {
        try {
            ComponentBean componentBean = componentService.getComponentById(id);
            if (componentBean == null) {
                return Result.fail("获取模块失败");
            } else {
                return Result.success("获取模块成功", componentBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.build500("出现异常");
        }
    }

    // 更改模块状态
    @ResponseBody
    @RequestMapping("/updateModuleStatus")
    public ResultModel updateModuleStatus(Integer id, Boolean checkResult) {
        try {
            boolean flag = componentService.updateModuleStatus(id, checkResult);
            if (flag) {
                return Result.success("更新成功");
            } else {
                return  Result.fail("更新失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.build500("出现异常");
        }
    }
}
