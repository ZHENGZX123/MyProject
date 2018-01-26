package cn.kiway.mdm.zbus;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;

import cn.kiway.mdm.entity.KnowledgePoint;
import cn.kiway.mdm.entity.Question;
import cn.kiway.mdm.entity.Student;
import cn.kiway.web.kthd.zbus.utils.ZbusUtils;
import cn.kiway.web.kthd.zbus.vo.PushRecordVo;

import static cn.kiway.mdm.KWApplication.students;

/**
 * Created by Administrator on 2018/1/2.
 */

public class ZbusHost {
    public static String zbusHost = "192.168.8.161";
    public static String zbusPost = "15555";
    public static String topic = "";

    public static void tongji(Activity c, KnowledgePoint kp, OnListener onListener) {
        try {
            String title = "知识点统计";
            String userId = c.getSharedPreferences("kiway", 0).getString("userId", "");
            String msg = new JSONObject().put("data", new JSONObject().put("command", "tongji").put("topic", topic).put("knowledge", kp.content)).toString();
            for (Student s : students) {
                String studentTopic = "kiwayMDM_student_" + s.imei;
                doSendMsg(title, userId, msg, studentTopic);
            }
            if (onListener != null) {
                onListener.onSuccess();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (onListener != null) {
                onListener.onFailure();
            }
        }
    }

    public static void qiangdaResult(Activity c, Student s, int result, String qiangdaStudentName, OnListener onListener) {
        try {
            String title = "抢答结果";
            String userId = c.getSharedPreferences("kiway", 0).getString("userId", "");
            String msg = new JSONObject().put("data", new JSONObject().put("command", "qiangdaResult").put("topic", topic).put("result", result).put("qiangdaStudentName", qiangdaStudentName)).toString();
            String studentTopic = "kiwayMDM_student_" + s.imei;
            doSendMsg(title, userId, msg, studentTopic);
            if (onListener != null) {
                onListener.onSuccess();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (onListener != null) {
                onListener.onFailure();
            }
        }
    }

    public static void qiangda(Activity c, OnListener onListener) {
        try {
            String title = "抢答";
            String userId = c.getSharedPreferences("kiway", 0).getString("userId", "");
            String msg = new JSONObject().put("data", new JSONObject().put("command", "qiangda").put("topic", topic)).toString();
            for (Student s : students) {
                String studentTopic = "kiwayMDM_student_" + s.imei;
                doSendMsg(title, userId, msg, studentTopic);
            }
            if (onListener != null) {
                onListener.onSuccess();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (onListener != null) {
                onListener.onFailure();
            }
        }
    }

    public static void question(Activity c, Student s, Question q, int questionType, int questionTime, OnListener onListener) {
        try {
            String title = null;
            if (questionType == 1) {
                title = "点名答";
            } else if (questionType == 2) {
                title = "抽答";
            } else if (questionType == 3) {
                title = "抢答";
            }
            String userId = c.getSharedPreferences("kiway", 0).getString("userId", "");
            ArrayList<Question> questions = new ArrayList<>();
            questions.add(q);
            String questionStr = new Gson().toJson(questions);
            String msg = new JSONObject().put("data", new JSONObject().put("command", "question").put("topic", topic).put("questions", questionStr).put("questionType", questionType).put("questionTime", questionTime)).toString();
            String studentTopic = "kiwayMDM_student_" + s.imei;
            doSendMsg(title, userId, msg, studentTopic);
            if (onListener != null) {
                onListener.onSuccess();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (onListener != null) {
                onListener.onFailure();
            }
        }
    }

    //测评专用
    public static void questions(Activity c, ArrayList<Question> questions, int questionTime, OnListener onListener) {
        try {
            String title = "测评";
            String userId = c.getSharedPreferences("kiway", 0).getString("userId", "");
            String questionStr = new Gson().toJson(questions);
            String msg = new JSONObject().put("data", new JSONObject().put("command", "question").put("topic", topic).put("questions", questionStr).put("questionType", 4).put("questionTime", questionTime)).toString();
            for (Student s : students) {
                String studentTopic = "kiwayMDM_student_" + s.imei;
                doSendMsg(title, userId, msg, studentTopic);
            }
            if (onListener != null) {
                onListener.onSuccess();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (onListener != null) {
                onListener.onFailure();
            }
        }
    }


    public static void collection(Activity c, Student s, String collection, OnListener onListener) {
        try {
            String userId = c.getSharedPreferences("kiway", 0).getString("userId", "");
            String msg = new JSONObject().put("data", new JSONObject().put("command", "collection").put("topic", topic).put("collection", collection)).toString();
            String studentTopic = "kiwayMDM_student_" + s.imei;
            doSendMsg("批改结果", userId, msg, studentTopic);
            if (onListener != null) {
                onListener.onSuccess();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (onListener != null) {
                onListener.onFailure();
            }
        }
    }

    public static void shangke(Activity c, OnListener onListener) {
        try {
            String title = "上课";
            String userId = c.getSharedPreferences("kiway", 0).getString("userId", "");
            String msg = new JSONObject().put("data", new JSONObject().put("command", "shangke").put("ip", "").put("platform", "android").put("topic", topic)).toString();
            for (Student s : students) {
                String studentTopic = "kiwayMDM_student_" + s.imei;
                doSendMsg(title, userId, msg, studentTopic);
            }
            if (onListener != null) {
                onListener.onSuccess();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (onListener != null) {
                onListener.onFailure();
            }
        }
    }

    public static void xiake(Context c, OnListener onListener) {
        try {
            String title = "下课";
            String userId = c.getSharedPreferences("kiway", 0).getString("userId", "");
            String msg = new JSONObject().put("data", new JSONObject().put("command", "xiake").put("ip", "").put("platform", "android").put("topic", topic)).toString();
            for (Student s : students) {
                String studentTopic = "kiwayMDM_student_" + s.imei;
                doSendMsg(title, userId, msg, studentTopic);
            }
            if (onListener != null) {
                onListener.onSuccess();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (onListener != null) {
                onListener.onFailure();
            }
        }
    }

    public static void sign(Activity c, OnListener onListener) {
        try {
            String title = "点名";
            String userId = c.getSharedPreferences("kiway", 0).getString("userId", "");
            String msg = new JSONObject().put("data", new JSONObject().put("command", "sign").put("topic", topic)).toString();
            for (Student s : students) {
                String studentTopic = "kiwayMDM_student_" + s.imei;
                doSendMsg(title, userId, msg, studentTopic);
            }
            if (onListener != null) {
                onListener.onSuccess();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (onListener != null) {
                onListener.onFailure();
            }
        }
    }

    private static void doSendMsg(String title, String userId, String msg, String studentTopic) throws
            Exception {
        Log.d("test", "发送给学生title = " + title + " , msg = " + msg + ", studentTopic = " + studentTopic);
        PushRecordVo vo = new PushRecordVo();
        vo.setTitle(title);
        vo.setUserType(1);//发送方：1老师 2学生 3家长 4管理员
        vo.setSenderId(userId);
        vo.setMessage(msg);
        ZbusUtils.sendMsg(studentTopic, vo);
    }


    public static void wenjian(Activity c, ArrayList<Student> students, String url, OnListener onListener) {
        try {
            String title = "文件";
            String userId = c.getSharedPreferences("kiway", 0).getString("userId", "");
            String msg = new JSONObject().put("data", new JSONObject().put("command", "wenjian").put("topic", topic).put("url", url)).toString();
            for (Student s : students) {
                String studentTopic = "kiwayMDM_student_" + s.imei;
                doSendMsg(title, userId, msg, studentTopic);
            }
            if (onListener != null) {
                onListener.onSuccess();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (onListener != null) {
                onListener.onFailure();
            }
        }
    }
}
