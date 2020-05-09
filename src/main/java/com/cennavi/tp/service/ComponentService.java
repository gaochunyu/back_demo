package com.cennavi.tp.service;

import com.cennavi.tp.beans.ComponentBean;
import com.cennavi.tp.common.result.ResultModel;

public interface ComponentService {
    // 添加组件
    ResultModel addComponent(Integer uid, String name, String tags, String cover, String content, String testUrl, String fileUrl);
}
