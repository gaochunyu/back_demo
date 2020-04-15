package com.cennavi.tp.controller;

import com.alibaba.fastjson.JSONObject;
import com.cennavi.tp.beans.BaseRtic;
import com.cennavi.tp.service.BaseDataService;
import com.cennavi.tp.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Created by sunpengyan on 2019/9/7.
 */
@Controller
@RequestMapping("/basedata")
public class BaseDataController {

    @Autowired
    private BaseDataService baseDataService;

    /**
     * 框选link
     * @param linkid linkid所在道路下的所有linkid 与geometry的交集
     * @param geometry geojson格式
     * @return
     */
    @ResponseBody
    @RequestMapping("/getLinkByGeometry")
    public String getLinkByGeometry(@RequestParam(value = "linkid",required = false) String linkid,
                                      @RequestParam(value = "geometry",required = false) String geometry,
                                    @RequestParam(value = "city",required = false,defaultValue = "xian") String city) {
        JSONObject json = new JSONObject();
        try {
            List<Map<String,Object>> list = baseDataService.getLinkByGeometry(linkid,geometry,city);
            json = JsonUtils.packJson(list);
        } catch (Exception e) {
            e.printStackTrace();
            json = JsonUtils.packJsonErr(e.getMessage());
        }finally {
            return JsonUtils.objectToJson(json,new String[]{});
        }
    }

    @ResponseBody
    @RequestMapping("/getLinkById")
    public String getLinkById(@RequestParam(value = "id") String id,
                              @RequestParam(value = "city",required = false,defaultValue = "xian") String city) {
        JSONObject json = new JSONObject();
        try {
            List<Map<String,Object>> list = baseDataService.getLinkById(id,city);
            json = JsonUtils.packJson(list);
        } catch (Exception e) {
            e.printStackTrace();
            json = JsonUtils.packJsonErr(e.getMessage());
        }finally {
            return JsonUtils.objectToJson(json,new String[]{});
        }
    }


    /**
     * 框选rtic
     * @param linkid linkid所在道路下的所有linkid 与geometry的交集
     * @param geometry geojson格式
     * @return
     */
    @ResponseBody
    @RequestMapping("/getRticByGeometry")
    public String getRticByGeometry(@RequestParam(value = "linkid",required = false) String linkid,
                                    @RequestParam(value = "geometry",required = false) String geometry,
                                    @RequestParam(value = "city",required = false,defaultValue = "xian") String city) {
        JSONObject json = new JSONObject();
        try {
            List<Map<String,Object>> list = baseDataService.getRticByGeometry(linkid,geometry,city);
            json = JsonUtils.packJson(list);
        } catch (Exception e) {
            e.printStackTrace();
            json = JsonUtils.packJsonErr(e.getMessage());
        }finally {
            return JsonUtils.objectToJson(json,new String[]{});
        }
    }

    @ResponseBody
    @RequestMapping("/getRticById")
    public String getRticById(@RequestParam(value = "id") String id,
                              @RequestParam(value = "city",required = false,defaultValue = "xian") String city) {
        JSONObject json = new JSONObject();
        try {
            List<BaseRtic> list = baseDataService.getRticById(id,city);
            json = JsonUtils.packJson(list);
        } catch (Exception e) {
            e.printStackTrace();
            json = JsonUtils.packJsonErr(e.getMessage());
        }finally {
            return JsonUtils.objectToJson(json,new String[]{});
        }
    }
}
