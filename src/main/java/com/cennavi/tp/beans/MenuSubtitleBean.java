package com.cennavi.tp.beans;

import com.cennavi.tp.common.MyTable;

/**
 * Created by zhangxin on 2020/04/17.
 */
@MyTable("menu")
public class MenuSubtitleBean {
    private int id;
    private String name;
    private int parent;
    private int sort;


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
}
