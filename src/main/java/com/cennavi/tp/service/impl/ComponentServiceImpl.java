package com.cennavi.tp.service.impl;

import com.cennavi.tp.beans.ComponentBean;
import com.cennavi.tp.common.result.Result;
import com.cennavi.tp.common.result.ResultModel;
import com.cennavi.tp.dao.ComponentDao;
import com.cennavi.tp.service.ComponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: chenfeng
 * @date: 2020/4/29 17:33
 */
@Service
public class ComponentServiceImpl implements ComponentService {

    @Autowired
    private ComponentDao componentDao;

    /**
     * 新增
     */
    @Override
    public ResultModel addComponent(ComponentBean componentBean) {
        try {
            // 获取当前时间
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
            Date date = new Date(System.currentTimeMillis());
            String createTime = formatter.format(date);

            //todo ??
            componentBean.setUid(componentBean.getUid());
            componentBean.setName(componentBean.getName());
            componentBean.setTags(componentBean.getTags());
            componentBean.setCover(componentBean.getCover());
            componentBean.setContent(componentBean.getContent());
            componentBean.setTest_url(componentBean.getTest_url());
            componentBean.setFile_url(componentBean.getFile_url());
            componentBean.setCreate_time(createTime);
            
            if (componentDao.addComponent(componentBean)) {
                return Result.success("成功添加组件");
            } else {
                return Result.fail("添加组件失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("添加组件失败");
        }
    }
}
