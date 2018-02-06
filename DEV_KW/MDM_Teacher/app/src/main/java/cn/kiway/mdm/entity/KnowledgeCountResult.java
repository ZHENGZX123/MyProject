package cn.kiway.mdm.entity;

/**
 * Created by Administrator on 2018/2/6.
 */

public class KnowledgeCountResult {

    public String knowledgeId;
    public String knowledgeName;
    public String name;
    public int status;
    public String createDate;

    @Override
    public String toString() {
        return "KnowledgeCountResult{" +
                "knowledgeId='" + knowledgeId + '\'' +
                ", knowledgeName='" + knowledgeName + '\'' +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", createDate='" + createDate + '\'' +
                '}';
    }
}
