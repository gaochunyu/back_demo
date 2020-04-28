package com.cennavi.tp.beans;

import com.cennavi.tp.common.IgnoreColumn;
import com.cennavi.tp.common.MyTable;

import javax.swing.tree.RowMapper;
import javax.swing.tree.TreePath;

/**
 * Created by 姚文帅 on 2020/4/17 10:51.
 */
@MyTable("content")
public class ContentBean {
    // 和数据库 content 表字段一一对应
//    @IgnoreColumn("id")   // 自增型key值
    private int id;
    private String title; // 标题
    private String sub_title; // 副标题
    private String content; // 内容
    private String tags;    // 类型
    private String file;    //
    private String create_time;  // 创建时间
    private int uid;
    @IgnoreColumn("username")
    private String username;//创建者

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSub_title() {
        return sub_title;
    }

    public void setSub_title(String sub_title) {
        this.sub_title = sub_title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
