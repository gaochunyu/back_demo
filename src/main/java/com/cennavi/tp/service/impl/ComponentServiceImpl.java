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

    // 新增
    @Override
    public ResultModel addComponent(Integer uid, String name, String tags, String cover, String content, String testUrl, String fileUrl) {
        try {
            // 获取当前时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String createTime = df.format(new Date());

            //todo ??
            ComponentBean componentBean = new ComponentBean();
            componentBean.setUid(uid);
            componentBean.setName(name);
            componentBean.setTags(tags);
            componentBean.setCover(cover);
            componentBean.setContent(content);
            componentBean.setTest_url(testUrl);
            componentBean.setFile_url(fileUrl);
            componentBean.setCreate_time(createTime);

            Integer count = componentDao.addComponent(componentBean);
            if (count == 1) {
                return Result.success("成功添加组件");
            } else {
                return Result.fail("添加组件失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("添加组件失败");
        }
    }

    // 删除
    @Override
    public ResultModel delComponent(Integer id, Integer uid) {
        Integer count = componentDao.delComponent(id,uid);
        if (count == 0) {
            return Result.fail("输入的id无对应数据", count);
        }
        else {
            return Result.success("删除成功", count);
        }
    }

    @Override
    public ResultModel updateComponent(Integer id, Integer uid, String name, String tags, String cover, String content, String testUrl, String fileUrl) {
        return null;
    }

}
