package com.cennavi.tp.beans;

import com.cennavi.tp.common.IgnoreColumn;
import com.cennavi.tp.common.MyTable;

@MyTable("trade_type")
public class TradeTypeBean {
    @IgnoreColumn("id")
    private int id;  //项目所属行业类型id
    private String name; //项目所属行业类型id

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

    @Override
    public String toString() {
        return "TradeTypeBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
