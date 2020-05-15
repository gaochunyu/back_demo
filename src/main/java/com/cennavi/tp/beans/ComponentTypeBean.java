package com.cennavi.tp.beans;

import com.cennavi.tp.common.MyTable;

/**
 * @author: chenfeng
 * @date: 2020/5/14 15:17
 */
@MyTable("component_type")
public class ComponentTypeBean {
    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
