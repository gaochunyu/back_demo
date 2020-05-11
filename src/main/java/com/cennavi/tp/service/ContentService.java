package com.cennavi.tp.service;

import com.cennavi.tp.beans.ContentBean;
import com.cennavi.tp.common.result.ResultModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * Created by 姚文帅 on 2020/4/17 14:34.
 */
public interface ContentService {
    // 新增一条数据
    ResultModel addANewItem(int id, String title, String subTitle, String content, String tags, String autoSave, MultipartFile file, int uid);
    // 删除一条数据
    void deleteItemById(int id);
    // 更新数据
    ResultModel updateItemById(int id, String title, String subTitle, String content, String tags, String autoSave,Boolean isHaveFile, MultipartFile file, int uid);

//    void updateItemById(int id, ContentBean contentBean);
    // 查找数据
    ContentBean getItemById(int id);
    //  检索数据
    List<ContentBean> getContentsByTags(String tags);

}
