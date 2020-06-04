package com.cennavi.tp.service.impl;

import com.cennavi.tp.beans.MapToolsBean;
import com.cennavi.tp.beans.UserinfoBean;
import com.cennavi.tp.common.result.Result;
import com.cennavi.tp.common.result.ResultModel;
import com.cennavi.tp.dao.MapToolsDao;
import com.cennavi.tp.service.MapToolsService;
import com.cennavi.tp.utils.MyDateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * Created by 马立伟 on 2020/6/3 22:49.
 */
@Service
public class MapToolsServiceImpl implements MapToolsService {

    @Resource
    private MapToolsDao mapToolsDao;


    @Override
    public ResultModel addMapTools(String name,String icon,String type,String img,String help_info,String tags,Integer self,String url, HttpServletRequest request) {
        //获取当前登录账号
        UserinfoBean user = (UserinfoBean) request.getSession().getAttribute("user");
        if(user==null) Result.buildUnLogin();
        MapToolsBean maptools = new MapToolsBean();
        maptools.setName(name);
        maptools.setIcon(icon);
        maptools.setType(type);
        maptools.setImg(img);
        maptools.setHelp_info(help_info);
        maptools.setTags(tags);
        maptools.setStatus(1);//刚创建为待审核状态
        maptools.setSelf(self);
        maptools.setUrl(url);
        maptools.setUid(user.getId());
        String format = "yyyy-MM-dd HH:mm:ss";
        String time = MyDateUtils.format(new Date(),format);
        maptools.setCreate_time(time);
        long count = mapToolsDao.addMapTools(maptools);
        return Result.success("插入成功");
    }

    @Override
    public ResultModel updateMapTools(int id,String name,String icon,String type,String img,String help_info,String tags,Integer self,String url, HttpServletRequest request) {
        //获取当前登录账号
        UserinfoBean user = (UserinfoBean) request.getSession().getAttribute("user");
        if(user==null) Result.buildUnLogin();
        MapToolsBean maptools = mapToolsDao.getMapToolsById(id);
        if(maptools==null){
            return Result.fail("无id对应数据");
        }
        maptools.setName(name);
        maptools.setIcon(icon);
        maptools.setType(type);
        maptools.setImg(img);
        maptools.setHelp_info(help_info);
        maptools.setTags(tags);
        maptools.setSelf(self);
        maptools.setUrl(url);
        maptools.setUid(user.getId());
        int count = mapToolsDao.updateMapTools(maptools);
        return Result.success("修改成功");
    }

    @Override
    public ResultModel deleteById(Integer id) {
        int count = mapToolsDao.deleteById(id);
        if(count>0){
            return Result.success("删除成功");
        }else{
            return Result.fail("删除失败");
        }
    }

    @Override
    public MapToolsBean getMapToolsById(Integer id) {
        MapToolsBean mapToolsBean = mapToolsDao.getMapToolsById(id);
        return mapToolsBean;
    }

    @Override
    public ResultModel getMapToolsList(Integer page, Integer pageSize, Integer model, String type, String searchKey, Integer statusValue, HttpServletRequest request) {
        Map<String,Object> map=null;
        if(model==null || model==1){//浏览模式  审核模式  我的发布
            model=1;
            map = mapToolsDao.getMapToolsList(page,pageSize,model,"",0,"",0);
        }else if(model==2 || model==3){
            UserinfoBean user = (UserinfoBean) request.getSession().getAttribute("user");
            if(user==null)return Result.buildUnLogin();
            map = mapToolsDao.getMapToolsList(page,pageSize,model,type,user.getId(),searchKey,statusValue);
        }
        return Result.success("查询成功",map);
    }
}
