package com.cennavi.tp.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tools {

    private static String[][] models = {{"6","指南"},{"7","工具"},{"8","组件模块"},{"9","案例集锦模块"},{"10","帮助"},{"11","笔记"}};

    public static String getModelsMap(String ms){
        List<Map<String,String>> list = new ArrayList<>();
        String[] msTmp = ms.split(",");
        for(String info : msTmp){
            for(String[] info2 : models){
                if(info.equals(info2[0])){
                    Map<String,String> chid = new HashMap<>();
                    chid.put("id",info2[0]);
                    chid.put("name",info2[1]);
                    list.add(chid);
                }
            }
        }
        String jsonString= JSON.toJSONString(list);
        return jsonString;
    }
}
