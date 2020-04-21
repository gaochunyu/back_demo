package com.cennavi.tp.service.impl;

import com.cennavi.tp.beans.ContentBean;
import com.cennavi.tp.dao.ContentDao;
import com.cennavi.tp.service.ContentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by 姚文帅 on 2020/4/17 14:50.
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Resource
    ContentDao contentDao;

    //新增一条数据
    @Override
    public long addANewItem(ContentBean contentBean){
//        contentDao.save(contentBean);
        return contentDao.insertItemReturnId(contentBean);

    }

    // 删除一条数据
    public void deleteItemById(int id){
        contentDao.delete(id);
    }

    // 更新数据
    public void updateItemById(int id, ContentBean contentBean){
        contentDao.updateItemById(id, contentBean);
    }

    // 查找数据
    public ContentBean getItemById(int id){
        ContentBean contentBean = contentDao.getItemById(id);
        return contentBean;
    }

}
