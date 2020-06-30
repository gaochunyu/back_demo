package com.cennavi.tp.beans;

import com.cennavi.tp.common.IgnoreColumn;
import com.cennavi.tp.common.MyTable;

@MyTable("notes")
public class NotesBean {

    @IgnoreColumn("id")
    private int id;

    private String title; //标题
    private String content;
    private int notes_type; // 类型
    private String name;  //类型名称
    private String file;
    private int notes_state;  //状态
    private String creat_time;//上传时间
    private int sort;  //排序
    private int view_num;  //访问量
    private int favour_num;  //点赞数

    private String username;//用户名
    private int uid;


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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getNotes_type() {
        return notes_type;
    }

    public void setNotes_type(int notes_type) {
        this.notes_type = notes_type;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public int getNotes_state() {
        return notes_state;
    }

    public void setNotes_state(int notes_state) {
        this.notes_state = notes_state;
    }

    public String getCreat_time() {
        return creat_time;
    }

    public void setCreat_time(String creat_time) {
        this.creat_time = creat_time;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getView_num() {
        return view_num;
    }

    public void setView_num(int view_num) {
        this.view_num = view_num;
    }

    public int getFavour_num() {
        return favour_num;
    }

    public void setFavour_num(int favour_num) {
        this.favour_num = favour_num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
