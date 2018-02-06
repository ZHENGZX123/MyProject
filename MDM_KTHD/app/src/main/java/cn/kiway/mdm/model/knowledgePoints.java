package cn.kiway.mdm.model;

/**
 * Created by Administrator on 2018/2/6.
 */

public class knowledgePoints {
    String id;
    String courseId;
    String content;
    String operation;
    String createDate;
    String teachingContentVo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getTeachingContentVo() {
        return teachingContentVo;
    }

    public void setTeachingContentVo(String teachingContentVo) {
        this.teachingContentVo = teachingContentVo;
    }

}
