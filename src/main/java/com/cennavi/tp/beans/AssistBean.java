
package com.cennavi.tp.beans;

import com.cennavi.tp.common.IgnoreColumn;
import com.cennavi.tp.common.MyTable;


/**
 * Created by 姚文帅 on 2020/4/29 15:01.
 */
@MyTable("assist")
public class AssistBean {


    @IgnoreColumn("id")        // 自增型key值
    private int id;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    private int userId;       // 用户id
    private String question;   // 问题
    private String answer;     // 答案
    private String createTime; // 创建时间
    private String category;   // 问题分类
    private int weight;        // 问题权重
    private int status;        // 问题的状态  状态0录入中 1待审核 2审核通过 3被拒绝


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
