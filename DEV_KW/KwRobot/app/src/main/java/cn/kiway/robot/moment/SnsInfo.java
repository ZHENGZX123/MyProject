package cn.kiway.robot.moment;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by chiontang on 2/8/16.
 */
public class SnsInfo {
    public String id = "";
    public String authorId = "";
    public String authorName = "";
    public String content = "";
    public ArrayList<Like> likes = new ArrayList<Like>();
    public ArrayList<Comment> comments = new ArrayList<Comment>();
    public ArrayList<String> mediaList = new ArrayList<String>();
    public String rawXML = "";
    public long timestamp = 0;
    public boolean ready = false;
    public boolean isCurrentUser = false;

    public void print() {
        Log.d("wechatmomentstat", "================================");
        Log.d("wechatmomentstat", "id: " + this.id);
        Log.d("wechatmomentstat", "authorId: " + this.authorId);
        Log.d("wechatmomentstat", "authorName: " + this.authorName);
        Log.d("wechatmomentstat", "Content: " + this.content);
        Log.d("wechatmomentstat", "Likes:");
        for (int i = 0; i < likes.size(); i++) {
            Log.d("wechatmomentstat", likes.get(i).userName);
        }
        Log.d("wechatmomentstat", "Comments:");
        for (int i = 0; i < comments.size(); i++) {
            Comment comment = comments.get(i);
            Log.d("wechatmomentstat", "CommentAuthor: " + comment.authorName + "; CommentContent: " + comment.content + "; ToUser: " + comment.toUser + "; Time：" + comment.time);
        }
        Log.d("wechatmomentstat", "Media List:");
        for (int i = 0; i < mediaList.size(); i++) {
            Log.d("wechatmomentstat", mediaList.get(i));
        }
    }

    public SnsInfo clone() {
        SnsInfo newSns = new SnsInfo();
        newSns.id = this.id;
        newSns.authorName = this.authorName;
        newSns.content = this.content;
        newSns.authorId = this.authorId;
        newSns.likes = new ArrayList<Like>(this.likes);
        newSns.comments = new ArrayList<Comment>(this.comments);
        newSns.mediaList = new ArrayList<String>(this.mediaList);
        newSns.rawXML = this.rawXML;
        newSns.timestamp = this.timestamp;
        return newSns;
    }

    static public class Like {
        public String userName;
        public String userId;
        public boolean isCurrentUser = false;
    }

    static public class Comment {
        public String momentID;
        public String authorName;
        public String content;
        public String toUser;
        public String authorId;
        public String toUserId;
        public boolean isCurrentUser = false;
        public long time;
        public int uploaded;

        public Comment() {

        }

        //authorName+content+time做唯一
        public Comment(String momentID, String authorName, String content, String toUser, long time, int uploaded) {
            this.momentID = momentID;
            this.authorName = authorName;
            this.content = content;
            this.toUser = toUser;
            this.time = time;
            this.uploaded = uploaded;
        }

        @Override
        public String toString() {
            return "Comment{" +
                    "momentID='" + momentID + '\'' +
                    ", authorName='" + authorName + '\'' +
                    ", content='" + content + '\'' +
                    ", toUser='" + toUser + '\'' +
                    ", time=" + time +
                    ", uploaded=" + uploaded +
                    '}';
        }
    }
}
