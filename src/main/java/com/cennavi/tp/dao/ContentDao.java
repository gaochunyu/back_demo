package com.cennavi.tp.dao;

import com.cennavi.tp.beans.ContentBean;
import com.cennavi.tp.common.base.dao.BaseDao;

import java.util.List;


/**
 * Created by 姚文帅 on 2020/4/17 15:06.
 */


public interface ContentDao extends BaseDao<ContentBean> {

      int insertItem(ContentBean contentBean);

      /**
       * 根据id获取用户
       *
       * @param id
       * @return
       */
      ContentBean getItemById(int id);

      void updateItemById(int id, ContentBean contentBean);

      List<ContentBean> getContentsByTags(String tags);

}
