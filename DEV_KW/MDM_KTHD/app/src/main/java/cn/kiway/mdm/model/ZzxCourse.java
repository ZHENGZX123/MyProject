package cn.kiway.mdm.model;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/2/6.
 */

public class ZzxCourse {
    public String attachInfo;
    public String name;
    public String id;
    public String attach;
    public String userId;
    public int type;
    public String createDate;
    public int attendCourse;
    public String questions;
    public ArrayList<knowledgePoints> knowledgePoints;

    public String getAttachInfo() {
        return attachInfo;
    }

    public void setAttachInfo(String attachInfo) {
        this.attachInfo = attachInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getAttendCourse() {
        return attendCourse;
    }

    public void setAttendCourse(int attendCourse) {
        this.attendCourse = attendCourse;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public ArrayList<cn.kiway.mdm.model.knowledgePoints> getKnowledgePoints() {
        return knowledgePoints;
    }

    public void setKnowledgePoints(ArrayList<cn.kiway.mdm.model.knowledgePoints> knowledgePoints) {
        this.knowledgePoints = knowledgePoints;
    }

    public String getknowledgeContent() {
        String s = "";
        if (knowledgePoints != null) {
            for (int i = 0; i < knowledgePoints.size(); i++) {
                s = s + "知识点" + (i + 1) + ":  " + knowledgePoints.get(i).getContent() + "\n";
            }
        }
        return s;
    }
}
