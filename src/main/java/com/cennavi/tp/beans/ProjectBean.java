package com.cennavi.tp.beans;

import com.cennavi.tp.common.IgnoreColumn;
import com.cennavi.tp.common.MyTable;

import java.util.List;

@MyTable("project_info")
public class ProjectBean {
    @IgnoreColumn("id")
    private int id;

    private String name;
    private int trade_type_id; //项目所属行业id
    private String tradeName;

    private int project_type; //项目类型  1-项目  2-产品
    private String content;//项目描述
    private String visit_url;  //项目访问链接
    private int sort; //项目展示序号

    private String main_img;  //封面图片
    private String file;

    private int status;
    private String createTime;
    private int uId;

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    @IgnoreColumn("url")
    private String url;
    private List<String> urlList;

    public List<String> getUrlList() {
        return urlList;
    }

    public void setUrlList(List<String> urlList) {
        this.urlList = urlList;
    }

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

    public int getTrade_type_id() {
        return trade_type_id;
    }

    public void setTrade_type_id(int trade_type_id) {
        this.trade_type_id = trade_type_id;
    }

    public int getProject_type() {
        return project_type;
    }

    public void setProject_type(int project_type) {
        this.project_type = project_type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getVisit_url() {
        return visit_url;
    }

    public void setVisit_url(String visit_url) {
        this.visit_url = visit_url;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getMain_img() {
        return main_img;
    }

    public void setMain_img(String main_img) {
        this.main_img = main_img;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    @Override
    public String toString() {
        return "ProjectBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", trade_type_id=" + trade_type_id +
                ", project_type=" + project_type +
                ", content='" + content + '\'' +
                ", visit_url='" + visit_url + '\'' +
                ", sort=" + sort +
                ", main_img='" + main_img + '\'' +
                ", file='" + file + '\'' +
                ", status=" + status +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
