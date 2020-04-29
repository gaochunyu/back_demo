package com.cennavi.tp.service;

import com.cennavi.tp.beans.AssistBean;
import com.cennavi.tp.beans.MenuSubtitleBean;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;

/**
 * Created by 姚文帅 on 2020/4/29 14:31.
 */
public interface AssistService {

    /**
     * 新增一条帮助信息
     * @return
     */
    long addAssistBean(AssistBean assistBean);


}
