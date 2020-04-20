package com.cennavi.tp.service;

import com.cennavi.tp.beans.ContentBean;

import java.util.List;

/**
 * Created by 姚文帅 on 2020/4/17 14:34.
 */
public interface ContentService {
    // 新增一条数据
    void addANewItem(ContentBean item);
    // 删除一条数据
    void deleteItemById(int id);
    // 更新数据
    void updateItem(ContentBean item);
    // 查找数据
    ContentBean getItemById(int id);

}
