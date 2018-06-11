package cn.kiway.mdm.zbus;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.rabbitmq.client.Channel;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.kiway.mdm.activity.ResultActivity;
import cn.kiway.mdm.entity.KnowledgePoint;
import cn.kiway.mdm.entity.Question;
import cn.kiway.mdm.entity.Student;
import cn.kiway.mdm.util.Constant;
import cn.kiway.mdm.util.Utils;
import cn.kiway.wx.reply.utils.RabbitMQUtils;
import cn.kiway.wx.reply.vo.PushMessageVo;
import ly.count.android.api.Countly;

import static cn.kiway.mdm.KWApplication.students;

/**
 * Created by Administrator on 2018/1/2.
 */

public class ZbusHost {
    public static RabbitMQUtils consumeUtil;
    public static List<Channel> channels = new ArrayList<>();
    public static Channel channel;
    public static void closeMQ() {
        if (consumeUtil != null) {
            consumeUtil.close();
        }
        for (Channel channel : channels) {
            try {
                channel.abort();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void tongji(final Activity c, final KnowledgePoint kp, final OnListener onListener) {
        new Thread() {
            @Override
            public void run() {
                try {
                    String title = "知识点统计";
                    String userId = Utils.getIMEI(c);
                    String msg = new JSONObject().put("data", new JSONObject().put("command", "tongji").put("teacherUserId", userId).put("knowledge", kp.content).put("currentTime", Utils.longToDate(System.currentTimeMillis()))).toString();
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

    public static void tongji_timeout(final Activity c, final OnListener onListener) {
        new Thread() {
            @Override
            public void run() {
                try {
                    String title = "知识点统计失效";
                    String userId = Utils.getIMEI(c);
                    String msg = new JSONObject().put("data", new JSONObject().put("command", "tongji_timeout").put("teacherUserId", userId).put("currentTime", Utils.longToDate(System.currentTimeMillis()))).toString();
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
                    String userId = Utils.getIMEI(c);
                    String msg = new JSONObject().put("data", new JSONObject().put("command", "tuiping").put("teacherUserId", userId).put("status", status).put("currentTime", Utils.longToDate(System.currentTimeMillis()))).toString();
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
            String userId = Utils.getIMEI(c);
            String msg = new JSONObject().put("data", new JSONObject().put("command", "qiangdaResult").put("teacherUserId", userId).put("result", result).put("qiangdaStudentName", qiangdaStudentName).put("currentTime", Utils.longToDate(System.currentTimeMillis()))).toString();
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

    public static void qiangda(Activity c, ArrayList<Student> students, final OnListener onListener) {
        try {
            String title = "抢答";
            String userId = Utils.getIMEI(c);
            String msg = new JSONObject().put("data", new JSONObject().put("command", "qiangda").put("teacherUserId", userId).put("currentTime", Utils.longToDate(System.currentTimeMillis()))).toString();

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

    //其他问题专用
    public static void question(Activity c, Student s, Question q, int questionType, int questionTime, final OnListener onListener) {
        try {
            String title = null;
            if (questionType == 1) {
                title = "点名答";
            } else if (questionType == 2) {
                title = "抢答";
            } else if (questionType == 3) {
                title = "抽答";
            }
            String userId = Utils.getIMEI(c);
            ArrayList<Question> questions = new ArrayList<>();
            questions.add(q);
            String questionStr = new Gson().toJson(questions);
            String msg = new JSONObject().put("data", new JSONObject().put("command", "question").put("teacherUserId", userId).put("questions", questionStr).put("questionType", questionType).put("questionTime", questionTime).put("currentTime", Utils.longToDate(System.currentTimeMillis()))).toString();
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
            String userId = Utils.getIMEI(c);
            String questionStr = new Gson().toJson(questions);
            String msg = new JSONObject().put("data", new JSONObject().put("command", "question").put("teacherUserId", userId).put("questions", questionStr).put("questionType", 4).put("questionTime", questionTime).put("currentTime", Utils.longToDate(System.currentTimeMillis()))).toString();
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
            String userId = Utils.getIMEI(c);
            String msg = new JSONObject().put("data", new JSONObject().put("command", "collection").put("teacherUserId", userId).put("collection", collection).put("currentTime", Utils.longToDate(System.currentTimeMillis()))).toString();
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
            String userId = Utils.getIMEI(c);
            String msg = new JSONObject().put("data", new JSONObject().put("command", "chaping").put("teacherUserId", userId).put("chaping", chaping).put("currentTime", Utils.longToDate(System.currentTimeMillis()))).toString();
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
            String userId = Utils.getIMEI(c);
            // 这个要按照易敏的格式
            String commeand = "";
            if (suoping == 1) {
                commeand = "temporary_lockScreen";
            } else {
                commeand = "temporary_unlockScreen";
            }
            String msg = new JSONObject().put("data", new JSONObject().put("command", commeand).put("currentTime", Utils.longToDate(System.currentTimeMillis()))).toString();
//            String msg = new JSONObject().put("data", new JSONObject().put("command", commeand).put("currentTime", Utils.longToDate("" + System.currentTimeMillis())).put("currentTime", Utils.longToDate(System.currentTimeMillis()))).toString();
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

    public static void jingyin(Activity c, ArrayList<Student> students, int suoping, final OnListener onListener) {
        try {
            String title = "静音";
            String userId = Utils.getIMEI(c);
            // 这个要按照易敏的格式
            String commeand = "";
            if (suoping == 1) {
                commeand = "temporary_mute";
            } else {
                commeand = "temporary_unMute";
            }
            String msg = new JSONObject().put("data", new JSONObject().put("command", commeand).put("currentTime", Utils.longToDate("" + System.currentTimeMillis())).put("currentTime", Utils.longToDate(System.currentTimeMillis()))).toString();
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

    public static void shangke(final Activity c, String command, ArrayList<Student> students, final OnListener onListener) {
        Countly.sharedInstance().recordEvent("上课");
        new Thread() {
            @Override
            public void run() {
                try {
                    String title = "上课";
                    String userId = Utils.getIMEI(c);
                    String msg = new JSONObject().put("data", new JSONObject().put("command", command).put("ip", "").put("platform", "android").put("teacherUserId", userId).put("currentTime", Utils.longToDate(System.currentTimeMillis()))).toString();
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
        Countly.sharedInstance().recordEvent("下课");
        try {
            String title = "下课";
            String userId = Utils.getIMEI(c);
            String msg = new JSONObject().put("data", new JSONObject().put("command", "xiake").put("ip", "").put("platform", "android").put("teacherUserId", userId).put("currentTime", Utils.longToDate(System.currentTimeMillis()))).toString();
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
            String userId = Utils.getIMEI(c);
            String msg = new JSONObject().put("data", new JSONObject().put("command", "sign").put("teacherUserId", userId).put("currentTime", Utils.longToDate(System.currentTimeMillis()))).toString();
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

    public static void sign_timeout(Activity c, final OnListener onListener) {
        try {
            String title = "点名失效";
            String userId = Utils.getIMEI(c);
            String msg = new JSONObject().put("data", new JSONObject().put("command", "sign_timeout").put("teacherUserId", userId).put("currentTime", Utils.longToDate(System.currentTimeMillis()))).toString();
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
        String url = Constant.zbusHost + ":" + Constant.zbusPost;
        PushMessageVo pushMessageVo = new PushMessageVo();
        pushMessageVo.setDescription(title);
        pushMessageVo.setTitle(title);
        pushMessageVo.setMessage(msg);
        pushMessageVo.setAppId(Constant.APPID);
        pushMessageVo.setModule("student");
        Set<String> userIds = new HashSet<>();
        for (Student s : students) {
            userIds.add(s.token);
        }
        pushMessageVo.setUserId(userIds);//学生token
        pushMessageVo.setSenderId(userId);//老师的userId
        pushMessageVo.setPushType("zbus");


        Log.e("test", "发送给学生topic = " + topic + " , msg = " + msg + ", url = " + url);
       // ZbusUtils.sendMsg(topic, pushMessageVo);
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    Channel channel = consumeUtil.createChannel(topic, topic);
                    consumeUtil.sendMsgs(msg, channel);
                    channel.abort();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Countly.sharedInstance().recordEvent(title);
            }
        }.start();

    }


    public static void wenjian(Activity c, ArrayList<Student> students, String url, String fileName, String fileSize, final OnListener onListener) {
        try {
            String title = "文件";
            String userId = Utils.getIMEI(c);
            String msg = new JSONObject().put("data", new JSONObject().put("command", "wenjian").put("teacherUserId", userId).put("url", url).put("fileName", fileName).put("size", fileSize).put("currentTime", Utils.longToDate(System.currentTimeMillis()))).toString();
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
            String userId = Utils.getIMEI(c);
            String msg = new JSONObject().put("data", new JSONObject().put("command", "questionTimeup").put("teacherUserId", userId).put("currentTime", Utils.longToDate(System.currentTimeMillis()))).toString();
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
            String userId = Utils.getIMEI(c);
            String msg = new JSONObject().put("data", new JSONObject().put("command", "questionEnd").put("teacherUserId", userId).put("currentTime", Utils.longToDate(System.currentTimeMillis()))).toString();
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

    //心跳
    public static void heartbeat(Context c, String command) {
        try {
            String title = "心跳";
            String userId = Utils.getIMEI(c);
            String msg = new JSONObject().put("data", new JSONObject().put("command", command).put("teacherUserId", userId).put("currentTime", Utils.longToDate(System.currentTimeMillis()))).toString();
            doSendMsg(c, title, userId, msg, students);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //测试用
    public static void test(Activity c, int id, final OnListener onListener) {
        try {
            String title = "测试用";
            String userId = Utils.getIMEI(c);
            long time = System.currentTimeMillis();
            String randomString = "" + time + time + time + time + time + time + time;
            String msg = new JSONObject().put("data", new JSONObject().put("command", "test").put("teacherUserId", userId).put("time", time).put("id", id).put("randomString", randomString).put("currentTime", Utils.longToDate(System.currentTimeMillis()))).toString();
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

    public static void chaxun(Activity c, int type, final OnListener onListener) {
        try {
            String title = "查询";
            String userId = Utils.getIMEI(c);
            String msg = new JSONObject().put("data", new JSONObject().put("command", "chaxun").put("type", type).put("teacherUserId", userId).put("currentTime", Utils.longToDate(System.currentTimeMillis()))).toString();
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
