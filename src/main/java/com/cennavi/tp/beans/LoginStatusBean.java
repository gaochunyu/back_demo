package com.cennavi.tp.beans;

import com.cennavi.tp.common.IgnoreColumn;
import com.cennavi.tp.common.MyTable;

import java.util.Date;

/**
 * <p>
 * 用户登录状态
 * </p>
 *
 * @author MaLiWei
 * @since 2020-02-07
 */
@MyTable("login_status")
public class LoginStatusBean {

    /**
     * 主键
     */
    private int id;
    /**
     * 用户表主键
     */
    private int uid;
    /**
     * token鉴权
     */
    private String token;
    /**
     * 0失效1登录正常
     */
    private int flag;
    /**
     * 登录时间
     */
    private String login_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getLogin_time() {
        return login_time;
    }

    public void setLogin_time(String login_time) {
        this.login_time = login_time;
    }
}
