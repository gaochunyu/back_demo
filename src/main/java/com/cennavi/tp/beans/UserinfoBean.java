package com.cennavi.tp.beans;

import com.cennavi.tp.common.IgnoreColumn;
import com.cennavi.tp.common.MyTable;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

@MyTable("userinfo")
public class UserinfoBean {
    @IgnoreColumn("id")
    private int id;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    private String createTime;
    private int enable;
    private int role;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    @JsonProperty(value = "username")
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    @JsonProperty(value = "password")
    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "{username=" + username  + ", password= ******}";
    }
}