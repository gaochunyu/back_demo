
package com.cennavi.tp.beans;

import com.cennavi.tp.common.IgnoreColumn;
import com.cennavi.tp.common.MyTable;


/**
 * Created by 马立伟 on 2020/6/3 22:49.
 */
@MyTable("mine_map_tools")
public class MapToolsBean {


    @IgnoreColumn("id")
    private int id;             // 自增id
    private String name;        //工具名称
    private String icon;        //工具图标
    private String type;        //工具类型
    private String img;         //工具预览图
    private String help_info;   //工具帮助说明
    private String tags;        //标签
    private int status;         // 问题的状态  状态0录入中 1待审核 2审核通过 3被拒绝
    private String create_time; //创建时间
    private String url;         //工具访问地址
    private int self;           //是否站内工具
    private int uid;            //用户id


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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getHelp_info() {
        return help_info;
    }

    public void setHelp_info(String help_info) {
        this.help_info = help_info;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getSelf() {
        return self;
    }

    public void setSelf(int self) {
        this.self = self;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
