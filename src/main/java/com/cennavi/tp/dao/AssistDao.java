package com.cennavi.tp.dao;

import com.cennavi.tp.beans.AssistBean;
import com.cennavi.tp.common.base.dao.BaseDao;

import java.util.List;

/**
 * Created by 姚文帅 on 2020/4/29 14:32.
 */

public interface AssistDao extends BaseDao<AssistBean> {
    // 新增数据,并返回新增数据的id的值
    long addAssistItem(AssistBean assistBean);

    // 更新数据
    Integer updateAssistItem(AssistBean assistBean);

    // 删除数据
    Integer deleteAssistItem(Integer id);

    // 获取一条数据
    AssistBean getAssistItemById(Integer id);

    // 获取列表数据
//    List<AssistBean> getAssistItemList(Integer page);

}
