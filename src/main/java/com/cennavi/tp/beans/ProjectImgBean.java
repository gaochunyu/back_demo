package com.cennavi.tp.beans;

import com.cennavi.tp.common.MyTable;

@MyTable
public class ProjectImgBean {
    private int id;
    private String url;
    private int project_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    @Override
    public String toString() {
        return "ProjectImgBean{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", project_id=" + project_id +
                '}';
    }
}
