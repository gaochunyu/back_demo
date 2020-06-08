package com.cennavi.tp.beans;

import com.cennavi.tp.common.MyTable;

/**
 * @author: chenfeng
 * @date: 2020/5/15 15:57
 */
@MyTable("component_img")
public class ComponentImgBean {
    private int id;
    private int cid;
    private String img_url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
