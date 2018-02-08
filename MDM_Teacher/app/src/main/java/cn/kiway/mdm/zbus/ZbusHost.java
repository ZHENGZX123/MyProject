package cn.kiway.mdm.zbus;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import cn.kiway.mdm.activity.ResultActivity;
import cn.kiway.mdm.entity.KnowledgePoint;
import cn.kiway.mdm.entity.Question;
import cn.kiway.mdm.entity.Student;
import cn.kiway.mdm.util.Utils;
import cn.kiway.zbus.utils.ZbusUtils;
import cn.kiway.zbus.vo.PushMessageVo;

import static cn.kiway.mdm.KWApplication.students;

/**
 * Created by Administrator on 2018/1/2.
 */

public class ZbusHost {
    public static String zbusHost = "192.168.8.161";
    public static String zbusPost = "15556";//15555

    public static final String APPID = "f2ec1fb69b27c7ab5260d2eb7cd95dea";
    public static final String APPKEY = "9a9b01f8ab910e12422bcc0e88d95dff2f95f582";

    public static void tongji(final Activity c, final KnowledgePoint kp, final OnListener onListener) {
        new Thread() {
            @Override
            public void run() {
                try {
                    String title = "知识点统计";
                    String userId = c.getSharedPreferences("kiway", 0).getString("userId", "");
                    String msg = new JSONObject().put("data", new JSONObject().put("command", "tongji").put("teacherUserId", userId).put("knowledge", kp.content)).toString();
                    doSendMsg(c, title, userId, msg, students);
                    if (onListener != null) {
                        c.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                c.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        onListener.onSuccess();
                                    }
                                });
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (onListener != null) {
                        c.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                c.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        onListener.onFailure();
                                    }
                                });
                            }
                        });
                    }
                }
            }
        }.start();
    }

    public static void tuiping(final Activity c, final int status, final OnListener onListener) {
        new Thread() {
            @Override
            public void run() {
                try {
                    String title = "推屏";
                    String userId = c.getSharedPreferences("kiway", 0).getString("userId", "");
                    String msg = new JSONObject().put("data", new JSONObject().put("command", "tuiping").put("teacherUserId", userId).put("status", status)).toString();
                    doSendMsg(c, title, userId, msg, students);
                    if (onListener != null) {
                        c.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                onListener.onSuccess();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (onListener != null) {
                        c.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                onListener.onFailure();
                            }
                        });
                    }
                }
            }
        }.start();
    }

    public static void qiangdaResult(Activity c, Student s, int result, String qiangdaStudentName, final OnListener onListener) {
        try {
            String title = "抢答结果";
            String userId = c.getSharedPreferences("kiway", 0).getString("userId", "");
            String msg = new JSONObject().put("data", new JSONObject().put("command", "qiangdaResult").put("teacherUserId", userId).put("result", result).put("qiangdaStudentName", qiangdaStudentName)).toString();
            ArrayList<Student> students = new ArrayList<>();
            students.add(s);
            doSendMsg(c, title, userId, msg, students);
            if (onListener != null) {
                c.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onListener.onSuccess();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (onListener != null) {
                c.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onListener.onFailure();
                    }
                });
            }
        }
    }

    public static void qiangda(Activity c, final OnListener onListener) {
        try {
            String title = "抢答";
            String userId = c.getSharedPreferences("kiway", 0).getString("userId", "");
            String msg = new JSONObject().put("data", new JSONObject().put("command", "qiangda").put("teacherUserId", userId)).toString();
            doSendMsg(c, title, userId, msg, students);
            if (onListener != null) {
                c.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onListener.onSuccess();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (onListener != null) {
                c.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onListener.onFailure();
                    }
                });
            }
        }
    }

    public static void question(Activity c, Student s, Question q, int questionType, int questionTime, final OnListener onListener) {
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
            String msg = new JSONObject().put("data", new JSONObject().put("command", "question").put("teacherUserId", userId).put("questions", questionStr).put("questionType", questionType).put("questionTime", questionTime)).toString();
            ArrayList<Student> students = new ArrayList<>();
            students.add(s);
            doSendMsg(c, title, userId, msg, students);
            if (onListener != null) {
                c.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onListener.onSuccess();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (onListener != null) {
                c.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onListener.onFailure();
                    }
                });
            }
        }
    }

    //测评专用
    public static void questions(Activity c, ArrayList<Question> questions, int questionTime, final OnListener onListener) {
        try {
            String title = "测评";
            String userId = c.getSharedPreferences("kiway", 0).getString("userId", "");
            String questionStr = new Gson().toJson(questions);
            String msg = new JSONObject().put("data", new JSONObject().put("command", "question").put("teacherUserId", userId).put("questions", questionStr).put("questionType", 4).put("questionTime", questionTime)).toString();
            doSendMsg(c, title, userId, msg, students);
            if (onListener != null) {
                c.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onListener.onSuccess();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (onListener != null) {
                c.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onListener.onFailure();
                    }
                });
            }
        }
    }


    public static void collection(Activity c, Student s, String collection, final OnListener onListener) {
        try {
            String title = "批改结果";
            String userId = c.getSharedPreferences("kiway", 0).getString("userId", "");
            String msg = new JSONObject().put("data", new JSONObject().put("command", "collection").put("teacherUserId", userId).put("collection", collection)).toString();
            ArrayList<Student> students = new ArrayList<>();
            students.add(s);
            doSendMsg(c, title, userId, msg, students);
            if (onListener != null) {
                c.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onListener.onSuccess();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (onListener != null) {
                c.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onListener.onFailure();
                    }
                });
            }
        }
    }

    public static void chaping(Activity c, Student s, int chaping, final OnListener onListener) {
        try {
            String title = "查屏";
            String userId = c.getSharedPreferences("kiway", 0).getString("userId", "");
            String msg = new JSONObject().put("data", new JSONObject().put("command", "chaping").put("teacherUserId", userId).put("chaping", chaping)).toString();
            ArrayList<Student> students = new ArrayList<>();
            students.add(s);
            doSendMsg(c, title, userId, msg, students);
            if (onListener != null) {
                c.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onListener.onSuccess();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (onListener != null) {
                c.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onListener.onFailure();
                    }
                });
            }
        }
    }


    public static void suoping(Activity c, ArrayList<Student> students, int suoping, final OnListener onListener) {
        try {
            String title = "锁屏";
            String userId = c.getSharedPreferences("kiway", 0).getString("userId", "");
            // 这个要按照易敏的格式
            String commeand = "";
            if (suoping == 1) {
                commeand = "temporary_lockScreen";
            } else {
                commeand = "temporary_unlockScreen";
            }
            String msg = new JSONObject().put("data", new JSONObject().put("command", commeand).put("currentTime", Utils.longToDate("" + System.currentTimeMillis()))).toString();
            doSendMsg(c, title, userId, msg, students);
            if (onListener != null) {
                c.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onListener.onSuccess();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (onListener != null) {
                c.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onListener.onFailure();
                    }
                });
            }
        }
    }

    public static void shangke(final Activity c, final OnListener onListener) {
        new Thread() {
            @Override
            public void run() {
                try {
                    String title = "上课";
                    String userId = c.getSharedPreferences("kiway", 0).getString("userId", "");
                    String msg = new JSONObject().put("data", new JSONObject().put("command", "shangke").put("ip", "").put("platform", "android").put("teacherUserId", userId)).toString();
                    doSendMsg(c, title, userId, msg, students);
                    if (onListener != null) {
                        c.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                onListener.onSuccess();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (onListener != null) {
                        c.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                onListener.onFailure();
                            }
                        });
                    }
                }
            }
        }.start();
    }

    public static void xiake(Activity c, final OnListener onListener) {
        try {
            String title = "下课";
            String userId = c.getSharedPreferences("kiway", 0).getString("userId", "");
            String msg = new JSONObject().put("data", new JSONObject().put("command", "xiake").put("ip", "").put("platform", "android").put("teacherUserId", userId)).toString();
            doSendMsg(c, title, userId, msg, students);
            if (onListener != null) {
                c.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onListener.onSuccess();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (onListener != null) {
                c.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onListener.onFailure();
                    }
                });
            }
        }
    }

    public static void sign(Activity c, final OnListener onListener) {
        try {
            String title = "点名";
            String userId = c.getSharedPreferences("kiway", 0).getString("userId", "");
            String msg = new JSONObject().put("data", new JSONObject().put("command", "sign").put("teacherUserId", userId)).toString();
            doSendMsg(c, title, userId, msg, students);
            if (onListener != null) {
                c.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onListener.onSuccess();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (onListener != null) {
                c.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onListener.onFailure();
                    }
                });
            }
        }
    }

    private static void doSendMsg(Context c, String title, String userId, String msg, ArrayList<Student> students) throws
            Exception {
        //topic : 老师的deviceId#userId
        String topic = Utils.getIMEI(c) + "#" + userId;
        String url = zbusHost + ":" + zbusPost;
        PushMessageVo pushMessageVo = new PushMessageVo();
        pushMessageVo.setDescription(title);
        pushMessageVo.setTitle(title);
        pushMessageVo.setMessage(msg);
        pushMessageVo.setAppId(APPID);
        pushMessageVo.setModule("student");
        Set<String> userIds = new HashSet<>();
        for (Student s : students) {
            userIds.add(s.token);
        }
        pushMessageVo.setUserId(userIds);//学生token
        pushMessageVo.setSenderId(userId);//老师的userId
        pushMessageVo.setPushType("zbus");

        Log.d("test", "发送给学生topic = " + topic + " , msg = " + msg + ", url = " + url);
        ZbusUtils.sendMsg(topic, pushMessageVo);
    }


    public static void wenjian(Activity c, ArrayList<Student> students, String url, String fileName, String fileSize, final OnListener onListener) {
        try {
            String title = "文件";
            String userId = c.getSharedPreferences("kiway", 0).getString("userId", "");
            String msg = new JSONObject().put("data", new JSONObject().put("command", "wenjian").put("teacherUserId", userId).put("url", url)).put("fileName", fileName).put("size", fileSize).toString();
            doSendMsg(c, title, userId, msg, students);
            if (onListener != null) {
                c.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onListener.onSuccess();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (onListener != null) {
                c.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onListener.onFailure();
                    }
                });
            }
        }
    }

    public static void questionTimeup(ResultActivity c, ArrayList<Student> students, final OnListener onListener) {
        try {
            String title = "答题时间到";
            String userId = c.getSharedPreferences("kiway", 0).getString("userId", "");
            String msg = new JSONObject().put("data", new JSONObject().put("command", "questionTimeup").put("teacherUserId", userId)).toString();
            doSendMsg(c, title, userId, msg, students);
            if (onListener != null) {
                c.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onListener.onSuccess();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (onListener != null) {
                c.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onListener.onFailure();
                    }
                });
            }
        }
    }

    public static void questionEnd(ResultActivity c, ArrayList<Student> students, final OnListener onListener) {
        try {
            String title = "答题终止";
            String userId = c.getSharedPreferences("kiway", 0).getString("userId", "");
            String msg = new JSONObject().put("data", new JSONObject().put("command", "questionEnd").put("teacherUserId", userId)).toString();
            doSendMsg(c, title, userId, msg, students);
            if (onListener != null) {
                c.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onListener.onSuccess();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (onListener != null) {
                c.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onListener.onFailure();
                    }
                });
            }
        }
    }

    //测试用
    public static void test(Activity c, int id, final OnListener onListener) {
        try {
            String title = "测试用";
            String userId = c.getSharedPreferences("kiway", 0).getString("userId", "");
            long time = System.currentTimeMillis();
            String randomString = "" + time + time + time + time + time + time + time;
            String msg = new JSONObject().put("data", new JSONObject().put("command", "test").put("teacherUserId", userId).put("time", time).put("id", id).put("randomString", randomString)).toString();
            doSendMsg(c, title, userId, msg, students);
            if (onListener != null) {
                c.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onListener.onSuccess();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (onListener != null) {
                c.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onListener.onFailure();
                    }
                });
            }
        }
    }
}
