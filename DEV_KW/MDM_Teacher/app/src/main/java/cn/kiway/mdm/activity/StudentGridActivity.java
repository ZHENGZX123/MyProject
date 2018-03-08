package cn.kiway.mdm.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.leon.lfilepickerlibrary.LFilePicker;
import com.leon.lfilepickerlibrary.utils.Constant;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.kiway.mdm.KWApplication;
import cn.kiway.mdm.entity.FileModel;
import cn.kiway.mdm.entity.KnowledgePoint;
import cn.kiway.mdm.entity.Question;
import cn.kiway.mdm.entity.Student;
import cn.kiway.mdm.teacher.R;
import cn.kiway.mdm.util.FileUtils;
import cn.kiway.mdm.util.Logger;
import cn.kiway.mdm.util.UploadUtil;
import cn.kiway.mdm.util.Utils;
import cn.kiway.mdm.view.RoundedImageView;
import cn.kiway.mdm.zbus.OnListener;
import cn.kiway.mdm.zbus.ZbusHost;

import static cn.kiway.mdm.activity.Course0Activity.TYPE_QUESTION_DIANMINGDA;
import static cn.kiway.mdm.teacher.R.id.count;
import static cn.kiway.mdm.teacher.R.id.send;
import static cn.kiway.mdm.util.Constant.clientUrl;
import static cn.kiway.mdm.util.Constant.lockAll;
import static cn.kiway.mdm.util.Constant.muteAll;
import static cn.kiway.mdm.util.Utils.check301;
import static cn.kiway.mdm.web.JsAndroidInterface.requsetFile2;

//该页面做多个地方使用
//首页:TYPE_DIANMING
//点名答:TYPE_DIANMINGDA
//统计:TYPE_TONGJI
//分发文件:TYPE_WENJIAN
//查屏:TYPE_CHAPING
//锁屏:TYPE_SUOPING
public class StudentGridActivity extends BaseActivity implements View.OnClickListener {

    public static final int TYPE_DIANMING = 1;
    public static final int TYPE_DIANMINGDA = 2;
    public static final int TYPE_TONGJI = 3;
    public static final int TYPE_WENJIAN = 4;
    public static final int TYPE_CHAPING = 5;
    public static final int TYPE_SUOPING = 6;
    public static final int TYPE_JINGYIN = 7;

    private Button ok;
    private Button all;
    private ImageButton lock;
    private View leftView;

    private int type;
    private GridView gv;
    private MyAdapter adapter;

    private ArrayList<Student> students = new ArrayList<>();
    private Student chapingStudent;

    private boolean dianmingAlready;
    private ArrayList<KnowledgePoint> selectKPs;
    private String selectCourseId;

    static String uploadurl;
    static String uploadname;
    static String uploadsize;
    ArrayList<Student> selectStudents;
    FileAdapter fileAdapter;
    ListView fileListView;
    TextView remove;
    private ImageButton dm;
    private ImageButton sk;
    private boolean chaxun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_grid);

        type = getIntent().getIntExtra("type", 0);

        initView();
        initData();
        initListener();
    }

    public void initView() {
        super.initView();
        String className = getSharedPreferences("kiway", 0).getString("className", "");
        titleName.setText(className);
        gv = (GridView) findViewById(R.id.studentGV);
        adapter = new MyAdapter();
        gv.setAdapter(adapter);

        dm = (ImageButton) findViewById(R.id.dm);
        sk = (ImageButton) findViewById(R.id.sk);

        ok = (Button) findViewById(R.id.ok);
        all = (Button) findViewById(R.id.all);
        lock = (ImageButton) findViewById(R.id.lock);
        leftView = findViewById(R.id.leftView);
        remove = (TextView) findViewById(R.id.remove);

        fileAdapter = new FileAdapter();
        fileListView = (ListView) findViewById(R.id.listView);
        fileListView.setAdapter(fileAdapter);
        fileListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FileUtils.openFile(StudentGridActivity.this, fileModels.get(i - 1).filePath);
            }
        });
        findViewById(R.id.f_back).setOnClickListener(this);
        findViewById(R.id.add).setOnClickListener(this);
        findViewById(R.id.remove).setOnClickListener(this);
        findViewById(send).setOnClickListener(this);

        if (type == TYPE_DIANMING) {

        } else if (type == TYPE_DIANMINGDA) {
            ok.setVisibility(View.GONE);
            toolsRL.setVisibility(View.GONE);
            leftView.setVisibility(View.GONE);
        } else if (type == TYPE_TONGJI) {
            toolsRL.setVisibility(View.GONE);
            leftView.setVisibility(View.GONE);
        } else if (type == TYPE_WENJIAN) {
            ok.setVisibility(View.VISIBLE);
            all.setVisibility(View.VISIBLE);
            toolsRL.setVisibility(View.GONE);
            leftView.setVisibility(View.GONE);
        } else if (type == TYPE_SUOPING) {
            lock.setVisibility(View.VISIBLE);
            lock.setBackgroundResource(R.drawable.lock);
            toolsRL.setVisibility(View.GONE);
            leftView.setVisibility(View.GONE);
        } else if (type == TYPE_JINGYIN) {
            lock.setVisibility(View.VISIBLE);
            lock.setBackgroundResource(R.drawable.mute1);
            toolsRL.setVisibility(View.GONE);
            leftView.setVisibility(View.GONE);
        } else if (type == TYPE_CHAPING) {
            ok.setVisibility(View.GONE);
            toolsRL.setVisibility(View.GONE);
            leftView.setVisibility(View.GONE);
        }

        //如果是suoping和jingyin，先当前查询一下状态
        if (type == TYPE_SUOPING || type == TYPE_JINGYIN) {
            chaxun = true;
            pd.setMessage("正在查询学生平板状态，请稍候");
            showPD();
            sendChaxunCommand(type);
            new Thread() {
                @Override
                public void run() {
                    try {
                        sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    chaxun = false;
                    hidePD();
                }
            }.start();
        }
    }

    private void sendChaxunCommand(int type) {
        ZbusHost.chaxun(StudentGridActivity.this, type, null);
    }

    public void gj(View view) {
        if (dm.getVisibility() == View.VISIBLE) {
            dm.setVisibility(View.GONE);
            sk.setVisibility(View.GONE);
        } else {
            dm.setVisibility(View.VISIBLE);
            sk.setVisibility(View.VISIBLE);
        }
    }

    private boolean selectAll = false;

    public void clickALL(View v) {
        selectAll = !selectAll;
        if (selectAll) {
            all.setText("取消");
        } else {
            all.setText("全选");
        }
        for (Student s : students) {
            if (s.online) {
                s.selected = selectAll;
            }
        }
        adapter.notifyDataSetChanged();
    }


    public void clickLock(View v) {
        String message = "";
        if (type == TYPE_SUOPING) {
            if (lockAll) {
                message = "是否解锁全班学生的屏幕";
            } else {
                message = "是否锁定全班学生的屏幕";
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
            AlertDialog dialog = builder.setTitle("提示").setMessage(message)
                    .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            //发送命令
                            if (lockAll) {
                                sendSuopingCommand2(0);
                            } else {
                                sendSuopingCommand2(1);
                            }
                        }
                    }).setPositiveButton(android.R.string.cancel, null).create();
            dialog.show();
        } else if (type == TYPE_JINGYIN) {
            if (muteAll) {
                message = "是否解除全班学生的静音";
            } else {
                message = "是否静音全班学生的平板";
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
            AlertDialog dialog = builder.setTitle("提示").setMessage(message)
                    .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            //发送命令
                            if (muteAll) {
                                sendJingyinCommand2(0);
                            } else {
                                sendJingyinCommand2(1);
                            }
                        }
                    }).setPositiveButton(android.R.string.cancel, null).create();
            dialog.show();
        }
    }

    public void clickOK(View v) {
        selectStudents = new ArrayList<>();
        for (Student s : students) {
            if (s.selected) {
                selectStudents.add(s);
            }
        }
        if (selectStudents.size() == 0) {
            toast("请选择至少一个学生");
            return;
        }
        if (type == TYPE_WENJIAN) {
            findViewById(R.id.sendFile).setVisibility(View.VISIBLE);
            findViewById(R.id.studentGV).setVisibility(View.GONE);
            isFileDel = false;
        }
    }


    public void uploadUserfile() {
        final ArrayList<String> selectImei = new ArrayList<String>();
        for (int i = 0; i < selectStudents.size(); i++) {
            selectImei.add(selectStudents.get(i).imei);
        }
        Logger.log(":::::" + selectImei.toString());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AsyncHttpClient client = new AsyncHttpClient();
                client.addHeader("x-auth-token", getSharedPreferences("kiway", 0).getString("x-auth-token", ""));
                client.setTimeout(10000);
                RequestParams param = new RequestParams();
                param.put("url", uploadurl);
                param.put("name", uploadname);
                param.put("size", uploadsize);
                param.put("imeis", selectImei.toString());
                param.put("type", 2 + "");
                client.post(StudentGridActivity.this, clientUrl + "/device/teacher/teacher/file/push", param, new
                        TextHttpResponseHandler() {
                            @Override
                            public void onSuccess(int code, Header[] headers, String ret) {
                                Logger.log("course onSuccess = " + ret);
                            }

                            @Override
                            public void onFailure(int i, Header[] headers, String ret, Throwable throwable) {
                                Logger.log("::::::::::::onFailure" + ret);
                                if (!Utils.check301(StudentGridActivity.this, ret, "filepush")) {
                                    toast("请求失败，请稍后再试");
                                }
                            }
                        });
            }
        });
    }

    @Override
    public void clickRetry(View view) {
        super.clickRetry(view);
        initData();
    }

    public void initData() {
        //zhengkang add 0202, 如果已经有学生，就不用重复获取了，节省流量，不过状态可能会有点其他问题
        for (Student s : KWApplication.students) {
            s.known = 2;
            s.selected = false;
        }
        if (KWApplication.students.size() != 0) {
            students = KWApplication.students;
            adapter.notifyDataSetChanged();
            onSuccess();
            return;
        }
        //-----------0202-----------------

        try {
            showPD();
            hideRetry();
            String url = clientUrl + "/device/teacher/class/students";
            AsyncHttpClient client = new AsyncHttpClient();
            client.addHeader("x-auth-token", getSharedPreferences("kiway", 0).getString("x-auth-token", ""));
            client.setTimeout(10000);
            client.get(this, url, null, new TextHttpResponseHandler() {
                @Override
                public void onSuccess(int code, Header[] headers, String ret) {
                    Log.e("test", " onSuccess = " + ret);
                    hideRetry();
                    hidePD();
                    try {
                        JSONArray data = new JSONObject(ret).optJSONArray("data");
                        students = new GsonBuilder().create().fromJson(data.toString(), new TypeToken<List<Student>>() {
                        }.getType());
                        //1.刷新界面
                        adapter.notifyDataSetChanged();
                        //2.发送上课命令
                        KWApplication.students = students;
                        //sendTestCommand();
                        StudentGridActivity.this.onSuccess();
                    } catch (Exception e) {
                        toast("请求失败，请稍后再试");
                        hidePD();
                        showRetry();
                    }
                }

                @Override
                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                    Log.e("test", " onFailure = " + s);
                    if (!Utils.check301(StudentGridActivity.this, s, "students")) {
                        toast("请求失败，请稍后再试");
                        hidePD();
                        showRetry();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            toast("请求失败，请稍后再试");
            hidePD();
        }
    }

    private void onSuccess() {
        if (type == TYPE_DIANMING) {
            sendShangkeCommand();
        } else if (type == TYPE_TONGJI) {
            sendTongjiCommand();
        }
    }

    int id = 0;

    private void sendTestCommand() {
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    ZbusHost.test(StudentGridActivity.this, id, null);
                    id++;
                    if (id == 100) {
                        break;
                    }
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    private void sendTongjiCommand() {
        selectCourseId = getIntent().getStringExtra("courseId");
        selectKPs = (ArrayList<KnowledgePoint>) getIntent().getSerializableExtra("kps");
        ZbusHost.tongji(StudentGridActivity.this, selectKPs.get(0), new OnListener() {

            @Override
            public void onSuccess() {
                showTongjidialog();
            }

            @Override
            public void onFailure() {
                toast("发送统计命令失败");
                finish();
            }
        });
    }

    private void sendChapingCommand(final Student s, final int chaping) {
        ZbusHost.chaping(StudentGridActivity.this, s, chaping, new OnListener() {

            @Override
            public void onSuccess() {
                if (chaping == 1) {
                    String roomName = s.imei;
                    Log.d("test", "roomName = " + roomName);
                    startPlayer(roomName);
                }
            }

            @Override
            public void onFailure() {
                if (chaping == 1) {
                    toast("发送查屏命令失败");
                }
            }
        });
    }

    private void sendSuopingCommand1(final Student s, int suoping) {
        ArrayList<Student> selectStudents = new ArrayList<>();
        selectStudents.add(s);
        ZbusHost.suoping(StudentGridActivity.this, selectStudents, suoping, new OnListener() {

            @Override
            public void onSuccess() {
                //2.刷新界面
                s.locked = !s.locked;
                adapter.notifyDataSetChanged();
                //3.修改lockAll变量
                lockAll = true;
                for (Student temp : students) {
                    if (temp.online) {
                        lockAll = lockAll & temp.locked;
                    }
                }
            }

            @Override
            public void onFailure() {
                toast("发送锁屏命令失败");
            }
        });
    }

    private void sendSuopingCommand2(int suoping) {
        ZbusHost.suoping(StudentGridActivity.this, students, suoping, new OnListener() {

            @Override
            public void onSuccess() {
                //2.刷新界面
                lockAll = !lockAll;
                for (Student s : students) {
                    if (s.online) {
                        s.locked = lockAll;
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure() {
                toast("发送锁屏命令失败");
            }
        });
    }


    private void sendJingyinCommand1(final Student s, int suoping) {
        ArrayList<Student> selectStudents = new ArrayList<>();
        selectStudents.add(s);
        ZbusHost.jingyin(StudentGridActivity.this, selectStudents, suoping, new OnListener() {

            @Override
            public void onSuccess() {
                //2.刷新界面
                s.muted = !s.muted;
                adapter.notifyDataSetChanged();
                //3.修改lockAll变量
                muteAll = true;
                for (Student temp : students) {
                    if (temp.online) {
                        muteAll = muteAll & temp.muted;
                    }
                }
            }

            @Override
            public void onFailure() {
                toast("发送静音命令失败");
            }
        });
    }

    private void sendJingyinCommand2(int suoping) {
        ZbusHost.jingyin(StudentGridActivity.this, students, suoping, new OnListener() {

            @Override
            public void onSuccess() {
                //2.刷新界面
                muteAll = !muteAll;
                for (Student s : students) {
                    if (s.online) {
                        s.muted = muteAll;
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure() {
                toast("发送静音命令失败");
            }
        });
    }

    private void sendShangkeCommand() {
        ZbusHost.shangke(StudentGridActivity.this, new OnListener() {

            @Override
            public void onSuccess() {
                //toast("发送上课命令成功");
            }

            @Override
            public void onFailure() {
                toast("发送上课命令失败");
            }
        });
    }

    private void sendDianmingdaCommand(final Student s) {
        showPD();
        ArrayList<Question> questions = (ArrayList<Question>) getIntent().getSerializableExtra("questions");
        Question q = questions.get(0);
        ZbusHost.question(StudentGridActivity.this, s, q, 1, getIntent().getIntExtra("questionTime", 0), new
                OnListener() {

                    @Override
                    public void onSuccess() {
                        hidePD();
                        //toast("发送上课命令成功");
                        //点名答
                        ArrayList<Student> selectStudents = new ArrayList<>();
                        selectStudents.add(s);
                        startActivity(new Intent(StudentGridActivity.this, ResultActivity.class).putExtra("type",
                                TYPE_QUESTION_DIANMINGDA).putExtra("students", selectStudents).putExtra("questionTime",
                                getIntent().getIntExtra("questionTime", 0)).putExtra("questions", getIntent()
                                .getSerializableExtra("questions")));
                        finish();
                    }

                    @Override
                    public void onFailure() {
                        hidePD();
                        toast("发送点名答命令失败");
                    }
                });
    }


    private void initListener() {
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Student s = students.get(position);
                if (type == TYPE_DIANMING) {
                    if (dianmingAlready) {
                        toast(s.name + (s.come ? "到了" : "没到"));
                    } else {
                        toast(s.name + (s.online ? "在线" : "不在线"));
                    }
                } else if (type == TYPE_DIANMINGDA) {
                    if (!s.online) {
                        toast("该学生不在线，暂无法操作该功能");
                        return;
                    }
                    sendDianmingdaCommand(s);
                } else if (type == TYPE_WENJIAN) {
                    //0305 注意：老游说，不在线也可以发文件
                    //if (!s.online) {
                    //    toast("该学生不在线，暂无法操作该功能");
                    //    return;
                    //}
                    //选中的
                    s.selected = !s.selected;
                    adapter.notifyDataSetChanged();
                    //3.修改lockAll变量
                    selectAll = true;
                    for (Student temp : students) {
                        selectAll = selectAll & temp.selected;
                    }
                    if (selectAll) {
                        all.setText("取消");
                    } else {
                        all.setText("全选");
                    }
                } else if (type == TYPE_CHAPING) {
                    if (!s.online) {
                        toast("该学生不在线，暂无法操作该功能");
                        return;
                    }
                    toast("查看" + s.name + "的屏幕");
                    chapingStudent = s;
                    sendChapingCommand(s, 1);
                } else if (type == TYPE_SUOPING) {
                    if (!s.online) {
                        toast("该学生不在线，暂无法操作该功能");
                        return;
                    }
                    String message;
                    if (s.locked) {
                        message = "是否解锁" + s.name + "的屏幕";
                    } else {
                        message = "是否锁定" + s.name + "的屏幕";
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(StudentGridActivity.this, AlertDialog
                            .THEME_HOLO_LIGHT);
                    AlertDialog dialog = builder.setTitle("提示").setMessage(message)
                            .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    //1.发送命令
                                    if (s.locked) {
                                        sendSuopingCommand1(s, 0);
                                    } else {
                                        sendSuopingCommand1(s, 1);
                                    }
                                }
                            }).setPositiveButton(android.R.string.cancel, null).create();
                    dialog.show();
                } else if (type == TYPE_JINGYIN) {
                    if (!s.online) {
                        toast("该学生不在线，暂无法操作该功能");
                        return;
                    }
                    String message;
                    if (s.muted) {
                        message = "是否解除" + s.name + "的静音";
                    } else {
                        message = "是否静音" + s.name + "的平板";
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(StudentGridActivity.this, AlertDialog
                            .THEME_HOLO_LIGHT);
                    AlertDialog dialog = builder.setTitle("提示").setMessage(message)
                            .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    //1.发送命令
                                    if (s.muted) {
                                        sendJingyinCommand1(s, 0);
                                    } else {
                                        sendJingyinCommand1(s, 1);
                                    }
                                }
                            }).setPositiveButton(android.R.string.cancel, null).create();
                    dialog.show();
                }
            }
        });
    }

    boolean isFileDel = false;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.f_back:
                findViewById(R.id.sendFile).setVisibility(View.GONE);
                findViewById(R.id.studentGV).setVisibility(View.VISIBLE);
                break;
            case R.id.add:
                isFileDel = false;
                fileAdapter.notifyDataSetChanged();
                new LFilePicker()
                        .withActivity(this)
                        .withTitle(getString(R.string.filepath3))
                        .withRequestCode(requsetFile2)
                        .withMutilyMode(true)
                        .start();
                break;
            case R.id.remove:
                // fileModels.clear();
                isFileDel = !isFileDel;
                if (isFileDel) {
                    remove.setText("取消移除");
                } else {
                    remove.setText("移除文件");
                }
                fileAdapter.notifyDataSetChanged();
                break;
            case send:
                //1.上传文件
                //  showPD();
                isFileDel = false;
                fileAdapter.notifyDataSetChanged();
                if (fileModels.size() > 0) {
                    sendFile(0);
                } else {
                    toast("");
                }
                break;
        }
    }


    private void sendFile(int i) {

        if (fileModels.get(i).staute == 3) {
            if (fileModels.size() <= i + 1) {
                toast("发送文件成功");
            } else {
                sendFile(i + 1);
            }
            return;
        }
        fileModels.get(i).staute = 2;
        fileAdapter.notifyDataSetChanged();
        new Thread() {
            @Override
            public void run() {
                try {
                    File file = new File(String.valueOf(fileModels.get(i).filePath));
                    Logger.log("::::::::" + file.getName());
                    String accessToken = getSharedPreferences("kiway", 0).getString("x-auth-token", "");
                    final String ret = UploadUtil.uploadFile(file, clientUrl + "/common/file?x-auth-token=" +
                            accessToken, file.getName());
                    Log.d("test", "upload ret = " + ret);
                    //2.发送文件url给学生
                    JSONObject obj = new JSONObject(ret);
                    String url = obj.optJSONObject("data").optString("url");
                    //zzx add 上传文件记录
                    StudentGridActivity.uploadurl = url;
                    StudentGridActivity.uploadname = file.getName();
                    StudentGridActivity.uploadsize = FileUtils.GetFileSize(file);
                    uploadUserfile();
                    ZbusHost.wenjian(StudentGridActivity.this, selectStudents, url, file.getName(), FileUtils
                            .GetFileSize(file), new OnListener() {
                        @Override
                        public void onSuccess() {
                            if (i + 1 >= fileModels.size()) {
                                // hidePD();
                                fileModels.get(i).staute = 3;
                                fileAdapter.notifyDataSetChanged();
                                toast("发送文件成功");
                            } else {
                                fileModels.get(i).staute = 3;
                                fileAdapter.notifyDataSetChanged();
                                sendFile(i + 1);
                            }
                        }

                        @Override
                        public void onFailure() {
//                            hidePD();
//                            toast("发送文件失败");
                            if (i + 1 >= fileModels.size()) {
                                // hidePD();
                            } else {
                                fileModels.get(i).staute = 4;
                                fileAdapter.notifyDataSetChanged();
                                sendFile(i + 1);
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    hidePD();
                    toast("上传失败，请重试");
                }
            }
        }.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;
        if (requestCode == requsetFile2) {
            List<String> list = data.getStringArrayListExtra(Constant.RESULT_INFO);
            for (int i = 0; i < list.size(); i++) {
                if (!fileModels.toString().contains(list.get(i))) {
                    File file = new File(list.get(i));
                    FileModel fileModel = new FileModel();
                    fileModel.name = file.getName();
                    fileModel.filePath = file.getPath();
                    fileModel.size = FileUtils.GetFileSize(file);
                    fileModel.time = System.currentTimeMillis();
                    fileModel.staute = 1;
                    fileModels.add(fileModel);
                }
            }
            fileAdapter.notifyDataSetChanged();
        }

    }

    ArrayList<FileModel> fileModels = new ArrayList<>();


    private class FileAdapter extends BaseAdapter {

        private final LayoutInflater inflater;
        FileHolder holder;

        public FileAdapter() {
            inflater = LayoutInflater.from(StudentGridActivity.this);
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = inflater.inflate(R.layout.item_file, null);
                holder = new FileHolder();
                holder.name = (TextView) view.findViewById(R.id.name);
                holder.size = (TextView) view.findViewById(R.id.size);
                holder.time = (TextView) view.findViewById(R.id.time);
                holder.staute = (TextView) view.findViewById(R.id.staute);
                holder.remove = (ImageView) view.findViewById(R.id.item_remove);
                view.setTag(holder);
            } else {
                holder = (FileHolder) view.getTag();
            }
            FileModel fileModel = fileModels.get(i);
            holder.name.setText(fileModel.name);
            holder.size.setText(fileModel.size);
            holder.time.setText(Utils.longToDate(fileModel.time));
            if (isFileDel) {
                holder.remove.setVisibility(View.VISIBLE);
            } else {
                holder.remove.setVisibility(View.GONE);
            }
            holder.remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fileModels.remove(i);
                    notifyDataSetChanged();
                }
            });
            switch (fileModel.staute) {
                case 1:
                    holder.staute.setText("准备发送");
                    holder.staute.setTextColor(getResources().getColor(R.color._333333));
                    break;
                case 2:
                    holder.staute.setText("正在发送");
                    holder.staute.setTextColor(getResources().getColor(R.color._f35c3e));
                    break;
                case 3:
                    holder.staute.setText("发送完成");
                    holder.staute.setTextColor(getResources().getColor(R.color._13a0f9));
                    break;
                case 4:
                    holder.staute.setText("发送失败");
                    holder.staute.setTextColor(getResources().getColor(R.color._f42a2a));
                    break;
            }
            return view;
        }

        @Override
        public int getCount() {
            return fileModels.size();
        }

        @Override
        public Object getItem(int i) {
            return fileModels.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        class FileHolder {
            TextView name, size, time, staute;
            ImageView remove;
        }

    }


    private class MyAdapter extends BaseAdapter {

        private final LayoutInflater inflater;

        public MyAdapter() {
            inflater = LayoutInflater.from(StudentGridActivity.this);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            ViewHolder holder;
            if (rowView == null) {
                rowView = inflater.inflate(R.layout.item_student, null);
                holder = new ViewHolder();

                holder.name = (TextView) rowView.findViewById(R.id.name);
                holder.icon = (RoundedImageView) rowView.findViewById(R.id.icon);
                holder.lock = (ImageView) rowView.findViewById(R.id.lock);
                holder.cover = (ImageView) rowView.findViewById(R.id.cover);

                rowView.setTag(holder);
            } else {
                holder = (ViewHolder) rowView.getTag();
            }

            final Student s = students.get(position);
            holder.name.setText(s.name);
            if (TextUtils.isEmpty(s.avatar)) {
                s.avatar = "drawable://" + R.drawable.icon;
            }
            ImageLoader.getInstance().displayImage(s.avatar, holder.icon, KWApplication.getLoaderOptions());

            if (type != TYPE_TONGJI) {
                if (s.online) {
                    holder.cover.setVisibility(View.VISIBLE);
                    holder.cover.setImageResource(R.drawable.icon2);
                } else {
                    holder.cover.setVisibility(View.GONE);
                }
            }

            if (type == TYPE_DIANMING) {
                if (s.come) {
                    holder.cover.setVisibility(View.VISIBLE);
                    holder.cover.setImageResource(R.drawable.icon4);
                }
            } else if (type == TYPE_DIANMINGDA || type == TYPE_WENJIAN) {
                if (s.selected) {
                    holder.cover.setVisibility(View.VISIBLE);
                    holder.cover.setImageResource(R.drawable.icon4);
                }
            } else if (type == TYPE_TONGJI) {
                //0没明白，1是明白，未回复是2
                if (s.known == 2) {
                    holder.cover.setVisibility(View.GONE);
                } else if (s.known == 1) {
                    holder.cover.setVisibility(View.VISIBLE);
                    holder.cover.setImageResource(R.drawable.icon2);
                } else if (s.known == 0) {
                    holder.cover.setVisibility(View.VISIBLE);
                    holder.cover.setImageResource(R.drawable.icon3);
                }
            } else if (type == TYPE_SUOPING) {
                if (s.locked) {
                    holder.lock.setVisibility(View.VISIBLE);
                    holder.lock.setImageResource(R.drawable.lock);
                } else {
                    holder.lock.setVisibility(View.GONE);
                }
            } else if (type == TYPE_JINGYIN) {
                if (s.muted) {
                    holder.lock.setVisibility(View.VISIBLE);
                    holder.lock.setImageResource(R.drawable.mute1);
                } else {
                    holder.lock.setVisibility(View.GONE);
                }
            }

            return rowView;
        }

        public class ViewHolder {
            public TextView name;
            public RoundedImageView icon;
            public ImageView lock;
            public ImageView cover;
        }

        @Override
        public int getCount() {
            return students.size();
        }

        @Override
        public Student getItem(int arg0) {
            return students.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

    }

    //点名对话框
    private Dialog dialog_dianming;
    private Button dianmingBtn;
    private TextView count_dianming;
    private TextView time_dianming;
    private int totalcount_dianming = 300;

    public void dm(View view) {
        //reset
        for (Student s : students) {
            s.come = false;
        }
        adapter.notifyDataSetChanged();

        dialog_dianming = new Dialog(this, R.style.popupDialog);
        dialog_dianming.setContentView(R.layout.dialog_dianming);
        dialog_dianming.show();
        dianmingBtn = (Button) dialog_dianming.findViewById(R.id.dianming);
        count_dianming = (TextView) dialog_dianming.findViewById(count);
        count_dianming.setText(0 + "/" + students.size());
        time_dianming = (TextView) dialog_dianming.findViewById(R.id.time);

        dianmingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dianmingBtn.getText().toString().equals("开始点名")) {
                    //1.发送点名请求并实时刷新界面
                    doStartSign();
                } else {
                    //点名结束，跳到上课
                    doEndSign();
                }
            }
        });
    }

    public void sk(View view) {
        //0307游志华说的
//        if (!dianmingAlready) {
//            toast("请先点名");
//            return;
//        }
        startActivity(new Intent(StudentGridActivity.this, CourseListActivity.class));
        finish();
    }

    private void doEndSign() {
        mHandler.removeMessages(TYPE_DIANMING);
        dialog_dianming.dismiss();
    }

    public void doStartSign() {
        showPD();
        ZbusHost.sign(this, new OnListener() {
            @Override
            public void onSuccess() {
                dianmingAlready = true;

                Log.d("test", "dianmingBtn onSuccess");
                hidePD();
                dianmingBtn.setBackgroundResource(R.drawable.dianmingbutton2);
                dianmingBtn.setText("结束点名");
                //开始点名、对话框不可关闭
                dialog_dianming.setCancelable(false);
                dialog_dianming.setCanceledOnTouchOutside(false);
                //开始倒计时
                mHandler.sendEmptyMessageDelayed(TYPE_DIANMING, 1000);
            }

            @Override
            public void onFailure() {
                Log.d("test", "dianmingBtn onFailure");
                if (!check301(StudentGridActivity.this, "", "dianmingBtn")) {
                    toast("请求失败，请稍后再试");
                    hidePD();
                }
            }
        });
    }

    //统计对话框
    private Dialog dialog_tongji;
    private TextView count_tongji;
    private TextView time_tongji;
    private int totalcount_tongji = 120;

    private void showTongjidialog() {
        dialog_tongji = new Dialog(this, R.style.popupDialog);
        dialog_tongji.setContentView(R.layout.dialog_tongji2);
        dialog_tongji.setCancelable(false);
        dialog_tongji.setCanceledOnTouchOutside(false);
        dialog_tongji.show();
        Button dianmingBtn = (Button) dialog_tongji.findViewById(R.id.dianming);
        count_tongji = (TextView) dialog_tongji.findViewById(count);
        count_tongji.setText(0 + "/" + students.size());
        time_tongji = (TextView) dialog_tongji.findViewById(R.id.time);

        mHandler.sendEmptyMessageDelayed(TYPE_TONGJI, 1000);
        dianmingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //结束统计
                doEndTongji();
            }
        });
    }

    public void doEndTongji() {
        toast("统计结束");
        //1.上报统计结果
        mHandler.removeMessages(TYPE_TONGJI);
        dialog_tongji.dismiss();
        uploadResult();
        Utils.courseOperation(this, selectCourseId, 2, selectKPs.get(0).id);
    }

    public void uploadResult() {
        try {
            String url = clientUrl + "/device/teacher/course/student/knowledge/result";
            Log.d("test", "url = " + url);
            AsyncHttpClient client = new AsyncHttpClient();
            client.addHeader("x-auth-token", getSharedPreferences("kiway", 0).getString("x-auth-token", ""));
            client.setTimeout(10000);
            JSONArray array = new JSONArray();
            for (Student s : students) {
                JSONObject o1 = new JSONObject();
                o1.put("imei", s.imei);
                o1.put("status", s.known);
                o1.put("knowledgeId", selectKPs.get(0).id);
                array.put(o1);
            }
            Log.d("test", "knowledge array = " + array.toString());
            StringEntity stringEntity = new StringEntity(array.toString(), "utf-8");
            client.post(this, url, stringEntity, "application/json", new TextHttpResponseHandler() {
                @Override
                public void onSuccess(int code, Header[] headers, String ret) {
                    Log.d("test", " onSuccess = " + ret);
                }

                @Override
                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                    Log.d("test", " onFailure = " + s);
                    Utils.check301(StudentGridActivity.this, s, "knowledgeCountResult");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            toast("请求失败，请稍后再试");
        }
    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            if (what == TYPE_DIANMING) {
                totalcount_dianming--;
                time_dianming.setText("倒计时 " + Utils.secToTime(totalcount_dianming));
                if (totalcount_dianming > 0) {
                    mHandler.sendEmptyMessageDelayed(TYPE_DIANMING, 1000);
                } else {
                    toast("点名结束");
                    //这里可以弹出点名结果
                    doEndSign();
                }
            } else if (what == TYPE_TONGJI) {
                totalcount_tongji--;
                time_tongji.setText("倒计时 " + Utils.secToTime(totalcount_tongji));
                if (totalcount_tongji > 0) {
                    mHandler.sendEmptyMessageDelayed(TYPE_TONGJI, 1000);
                } else {
                    doEndTongji();
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    public void onelineOneStudent(final String studentIMEI) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (Student s : students) {
                    if (s.imei.equals(studentIMEI)) {
                        s.online = true;
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void chaxunOneSubmit(String studentIMEI, int type, int status) {
        //3秒之内有效
        if (!chaxun) {
            Log.d("test", "3秒查询时间已经过了");
            return;
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (Student s : students) {
                    if (s.imei.equals(studentIMEI)) {
                        if (type == TYPE_SUOPING) {
                            s.locked = (status == 1);
                        } else if (type == TYPE_JINGYIN) {
                            s.muted = (status == 1);
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
    }


    public void signOneStudent(final String studentIMEI) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (Student s : students) {
                    if (s.imei.equals(studentIMEI)) {
                        s.come = true;
                    }
                }
                adapter.notifyDataSetChanged();
                int count = 0;
                for (Student s : students) {
                    if (s.come) {
                        count++;
                    }
                }
                if (count_dianming != null) {
                    count_dianming.setText(count + "/" + students.size());
                }
            }
        });
    }


    public void knowOneStudent(final String student, final int known) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (Student s : students) {
                    if (s.imei.equals(student)) {
                        s.known = known;
                    }
                }
                adapter.notifyDataSetChanged();

                int count = 0;
                for (Student s : students) {
                    if (s.known == 1) {
                        count++;
                    }
                }
                if (count_tongji != null) {
                    count_tongji.setText(count + "/" + students.size());
                }
            }
        });
    }

    //暂时把结束查屏放这里
    @Override
    protected void onRestart() {
        super.onRestart();
        sendChapingCommand(chapingStudent, 0);
    }

    @Override
    public void onBackPressed() {
        if (type == TYPE_DIANMING) {
            //发送下课命令
            ZbusHost.xiake(this, null);
        }
        super.onBackPressed();
    }
}
