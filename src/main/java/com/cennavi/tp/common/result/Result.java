package com.cennavi.tp.common.result;


public class Result {
    public static ResultModel buildResult(Integer code, String msg, Object data) {
        return new ResultModel(code, msg, data);
    }

    public static ResultModel build(Integer code, String msg) {
        return buildResult(code, msg, null);
    }

    public static ResultModel success(String msg) {
        return success(msg, null);
    }

    public static ResultModel success(String msg, Object data) {
        return buildResult(200, msg, data);
    }

    //代码逻辑异常500
    public static ResultModel build500(String msg) {
        return buildResult(500, msg, null);
    }

    public static ResultModel build500(String msg, Object data) {
        return buildResult(500, msg, data);
    }
    //处理逻辑否 1000
    public static ResultModel fail(String msg) {
        return buildResult(1000, msg, null);
    }

    public static ResultModel fail(String msg, Object data) {
        return buildResult(1000, msg, data);
    }
    //未登录 401
    public static ResultModel buildUnLogin() {
        return build(401, "未登录，请登录");
    }
}
