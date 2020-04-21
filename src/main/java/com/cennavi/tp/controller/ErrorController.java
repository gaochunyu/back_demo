package com.cennavi.tp.controller;

import com.cennavi.tp.common.result.Result;
import com.cennavi.tp.common.result.ResultModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 错误处理控制
 * @Author maliwei
 **/
@RestController
@RequestMapping("/error")
public class ErrorController {

    /**
     * 获取首页信息
     * @return
     */
    @RequestMapping("/unlogin")
    public ResultModel unlogin(){
        return Result.buildUnLogin();
    }
}
