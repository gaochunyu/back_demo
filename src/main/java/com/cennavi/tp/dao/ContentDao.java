package com.cennavi.tp.dao;

import com.cennavi.tp.beans.ContentBean;
import com.cennavi.tp.common.dao.BaseDao;

/**
 * Created by 姚文帅 on 2020/4/17 15:06.
 */
public interface ContentDao extends BaseDao<ContentBean> {

//    void save(ContentBean contentBean);
      public ContentBean getItemById(int id);
}
