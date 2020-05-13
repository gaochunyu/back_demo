package com.cennavi.tp.beans;

import com.cennavi.tp.common.IgnoreColumn;
import com.cennavi.tp.common.MyTable;

/**
 * Created by 姚文帅 on 2020/5/13 14:30.
 */
@MyTable("user_assist")
public class UserAssistBean {

    @IgnoreColumn("id")        // 自增型key值
    private int id;

    private int userId;       // 用户id
    private int assistId;     // 帮助列表的id

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAssistId() {
        return assistId;
    }

    public void setAssistId(int assistId) {
        this.assistId = assistId;
    }


}
