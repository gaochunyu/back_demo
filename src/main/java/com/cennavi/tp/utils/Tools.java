package com.cennavi.tp.utils;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Tools {

    private static String[][] models = {{"6","指南"},{"7","工具"},{"8","组件模块"},{"9","案例集锦模块"},{"10","帮助"},{"11","笔记"}};

    public static String getModelsMap(String ms){
        Map<Integer,String> map = new HashMap<>();
        String[] msTmp = ms.split(",");
        for(String info : msTmp){
            for(String[] info2 : models){
                if(info.equals(info2[0])){
                    map.put(Integer.parseInt(info),info2[1]);
                }
            }
        }
        JSONObject jsonObject = JSONObject.parseObject(map.toString());
        return jsonObject.toString();
    }
}
