package com.cennavi.tp.beans;

import com.cennavi.tp.common.IgnoreColumn;
import com.cennavi.tp.common.MyTable;

/**
 * Created by zhangxin on 2020/04/17.
 */
@MyTable("menu")
public class MenuSubtitleBean {
    @IgnoreColumn("id")
    private int id;
    //菜单名称
    private String name;
    //上级id
    private int parent;
    //排序
    private int sort;
    //用户id
    private int uid;
    //创建时间
    private String createTime;
    //状态0录入中 1待审核 2审核通过 3被拒绝
    private int status;
    @IgnoreColumn("username")
    private String username;//创建者


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "MenuSubtitleBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parent=" + parent +
                ", sort=" + sort +
                ", uid=" + uid +
                ", createTime='" + createTime + '\'' +
                ", status=" + status +
                '}';
    }
}
