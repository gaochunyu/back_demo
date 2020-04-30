package com.cennavi.tp.service;

import com.cennavi.tp.beans.ComponentBean;
import com.cennavi.tp.common.result.ResultModel;

public interface ComponentService {
    // 添加
    ResultModel addComponent(ComponentBean componentBean);
}
