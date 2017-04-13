package yjpty.teaching.acitivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.zk.myweex.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import yjpty.teaching.adpater.OnSessionAdapter;
import yjpty.teaching.dialog.ClearDataDialog;
import yjpty.teaching.dialog.LoginDialog;
import yjpty.teaching.fragment.onsession.OnSessionFragment;
import yjpty.teaching.http.IUrContant;
import yjpty.teaching.model.OnClassModel;
import yjpty.teaching.model.VideoModel;
import yjpty.teaching.tcpudp.HandlerClient;
import yjpty.teaching.util.AppUtil;
import yjpty.teaching.util.GlobeVariable;
import yjpty.teaching.util.IConstant;
import yjpty.teaching.util.StringUtil;
import yjpty.teaching.util.ViewUtil;
import yjpty.teaching.wifi.WifiAdmin;

public class OnSessionActivity extends BaseActivity implements
        OnCheckedChangeListener, ClearDataDialog.ClearDataCallBack, Animation.AnimationListener {
    RadioGroup rg;
    OnSessionFragment sessionZhuKeFragment, sessionWeiKeFragment;
    int page = 0;
    VideoModel videoCateMode;
    boolean hasMeasured = false;// 只获取一次键盘的宽高
    LinearLayout layout;// 键盘
    public static int height;// 键盘的高度
    public static int width;// 键盘的宽度
    TextView open_txt;// 点击键盘按钮
    List<OnClassModel> Zhukelist = new ArrayList<>();
    List<OnClassModel> Weikelist1 = new ArrayList<>();
    ClearDataDialog dialog;// 退出弹框
    private int ZhukePosition, WeiKePosition;
    long timeOut = 0;
    public static OnSessionActivity onSessionActivity;
    LoginDialog exitdialog;
    String playData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yjpty_activity_onsession);
        videoCateMode = (VideoModel) bundle
                .getSerializable(IConstant.BUNDLE_PARAMS);
        onSessionActivity = this;
        try {
            getKeyBoradHW();
            loadData();
            initView();
            setSessionMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void setSessionMessage() {
        if (bundle.getBoolean(IConstant.BUNDLE_PARAMS1)) {
            if (videoCateMode.isKiectSession()) {//判断是否为体感课程
                playData = GlobeVariable.STRAT_APK + videoCateMode.getKinectPackageName()+":::"+videoCateMode.getKiectApkDownLoadUrl();
            } else {
                if (Zhukelist.size() <= 0)
                    return;
                playData = GlobeVariable.PLAY_VIDEO
                        + Zhukelist.get(ZhukePosition).getUuid() + ":::" +
                        Zhukelist.get(ZhukePosition).getVideoUrl();// 进来播放第一个视频
            }
            if (app.client == null)
                app.client = new HandlerClient();
            if (app.isHotSesson()) {
                if (app.client != null) {
                    app.client.close();
                    app.client.destory();
                }
                app.client = new HandlerClient();
                app.client.connectTCP(resources.getString(R.string.ip));
                handler.sendEmptyMessageDelayed(4, 1000);
            } else {
                sendHeziMessage(playData);
            }
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 4:
                    if (app.client != null) {
                        app.client.close();
                        app.client.destory();
                    }
                    app.client = new HandlerClient();
                    if (app.isHotSesson) {
                        app.client.connectTCP(resources.getString(R.string.ip));
                    } else {
                        app.client.connectTCP(app.getSessionIp());
                    }
                    handler.sendEmptyMessageDelayed(5, 1000);
                    break;
                case 5:
                    if (!app.client.isConnect()) {
                        handler.sendEmptyMessageDelayed(4, 1000);
                    } else {
                        app.client.sendTCP(playData.getBytes());
                        handler.removeMessages(4);
                        handler.removeMessages(5);
                    }
                    break;
            }
        }
    };

    /**
     * 获取控制键盘的宽高
     *
     * @author Administrator 直接layout.getMesaguse获取出来是0
     */
    void getKeyBoradHW() throws Exception {
        layout = ViewUtil.findViewById(this, R.id.layout);
        ViewTreeObserver vto = layout.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                if (hasMeasured == false) {
                    height = layout.getMeasuredHeight();
                    width = layout.getMeasuredWidth();
                    hasMeasured = true;
                }
                return true;
            }
        });
    }


    @Override
    public void loadData() throws Exception {
        super.loadData();
        Zhukelist.clear();
        Weikelist1.clear();
        if (videoCateMode.isKiectSession()) {
            OnClassModel model = new OnClassModel();
            model.setContent(videoCateMode.getKiectSessionContent());
            model.setBool(true);
            Zhukelist.add(model);
        } else {
            JSONObject data = mCache.getAsJSONObject(IUrContant.GET_ONE_COURSE + videoCateMode.getId());
            if (data != null) {
                JSONArray ZhukeJs = data.optJSONObject("data").optJSONArray("processList");
                JSONArray WeikeJs = data.optJSONObject("data").optJSONArray("courseTinyList");
                if (ZhukeJs != null) {
                    for (int i = 0; i < ZhukeJs.length(); i++) {
                        JSONObject item = ZhukeJs.optJSONObject(i);
                        OnClassModel model = new OnClassModel();
                        model.setUuid(item.optJSONObject("resource").optString("uuid") + "." + item.optJSONObject
                                ("resource").optString("suffix"));
                        model.setContent(item.optString("content"));
                        model.setVideoUrl(item.optJSONObject("resource").optString("file_path"));
                        if (i == 0)
                            model.setBool(true);
                        else
                            model.setBool(false);
                        Zhukelist.add(model);
                    }
                }
                if (WeikeJs != null) {
                    for (int i = 0; i < WeikeJs.length(); i++) {
                        JSONObject item = WeikeJs.optJSONObject(i);
                        OnClassModel model = new OnClassModel();
                        model.setUuid(item.optJSONObject("resource").optString("uuid") + "." + item.optJSONObject
                                ("resource").optString("suffix"));
                        model.setContent(item.optJSONObject("resource").optString("name"));
                        model.setVideoUrl(item.optJSONObject("resource").optString("file_path"));
                        model.setBool(false);
                        Weikelist1.add(model);
                    }
                }
            }
        }
        if (Weikelist1.size() <= 0) {
            findViewById(R.id.rg).setVisibility(View.GONE);
            findViewById(R.id.views).setVisibility(View.GONE);
        }
    }

    @Override
    public void initView() throws Exception {
        rg = ViewUtil.findViewById(this, R.id.rg);
        rg.setOnCheckedChangeListener(this);
        // 进来默认课程表
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragment(transaction, sessionZhuKeFragment, sessionWeiKeFragment);
        if (sessionZhuKeFragment == null) {
            sessionZhuKeFragment = OnSessionFragment.newInstance(Zhukelist, videoCateMode, bundle.getBoolean(IConstant
                    .BUNDLE_PARAMS1));
            transaction.add(R.id.fragment, sessionZhuKeFragment,
                    "sessionZhuKeFragment");
        } else if (sessionZhuKeFragment.isAdded()
                && sessionZhuKeFragment.isHidden()) {
            transaction.show(sessionZhuKeFragment);
        }
        transaction.commit();
        findViewById(R.id.previos_class).setOnClickListener(this);
        ViewUtil.setContent(this, R.id.title, videoCateMode.getName());
        ViewUtil.setContent(this, R.id.rb1, R.string.zhuke);
        ViewUtil.setContent(this, R.id.rb2, R.string.weike);
        findViewById(R.id.last).setOnClickListener(this);
        findViewById(R.id.play).setOnClickListener(this);
        findViewById(R.id.next).setOnClickListener(this);
        findViewById(R.id.pause).setOnClickListener(this);
        findViewById(R.id.resume).setOnClickListener(this);
        findViewById(R.id.video_add).setOnClickListener(this);
        findViewById(R.id.video_last).setOnClickListener(this);
        findViewById(R.id.open).setOnClickListener(this);
        findViewById(R.id.previos_class).setOnClickListener(this);
        findViewById(R.id.darkscreen).setOnClickListener(this);
        findViewById(R.id.brightenscreen).setOnClickListener(this);
        open_txt = ViewUtil.findViewById(this, R.id.open);
        View v = ViewUtil.findViewById(this, R.id.previos_class);// 初始化退出的dialog
        dialog = new ClearDataDialog(this, this,
                resources.getString(R.string.exit_this_session), v);
        if (!bundle.getBoolean(IConstant.BUNDLE_PARAMS1))
            findViewById(R.id.layout).setVisibility(View.GONE);
        if (videoCateMode.isKiectSession()) {//如果是体感课程则隐藏掉一下按钮
            findViewById(R.id.layout2).setVisibility(View.GONE);
            findViewById(R.id.layout3).setVisibility(View.GONE);
            findViewById(R.id.pause).setVisibility(View.GONE);
            findViewById(R.id.resume).setVisibility(View.GONE);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        int pg = 0;
        if (checkedId == R.id.rb1) {
            pg = 0;

        } else if (checkedId == R.id.rb2) {
            pg = 1;

        }
        if (page > pg) {// 切换动画
            transaction.setCustomAnimations(R.anim.yjpty_silde_in_left,
                    R.anim.yjpty_silde_out_right);
        } else {
            transaction.setCustomAnimations(R.anim.yjpty_silde_in_right,
                    R.anim.yjpty_silde_out_left);
        }
        page = pg;
        hideFragment(transaction, sessionZhuKeFragment, sessionWeiKeFragment);
        if (checkedId == R.id.rb1) {
            if (sessionZhuKeFragment == null) {
                sessionZhuKeFragment = OnSessionFragment.newInstance(Zhukelist, videoCateMode, bundle.getBoolean
                        (IConstant.BUNDLE_PARAMS1));
                transaction.add(R.id.fragment, sessionZhuKeFragment,
                        "sessionZhuKeFragment");
            } else if (sessionZhuKeFragment.isAdded()
                    && sessionZhuKeFragment.isHidden()) {
                transaction.show(sessionZhuKeFragment);
            }
            ViewUtil.moveFrontBg(findViewById(R.id.view),
                    displayMetrics.widthPixels / 2, 0, 0, 0);

        } else if (checkedId == R.id.rb2) {
            if (sessionWeiKeFragment == null) {
                sessionWeiKeFragment = OnSessionFragment.newInstance(Weikelist1, null, bundle.getBoolean(IConstant
                        .BUNDLE_PARAMS1));
                transaction.add(R.id.fragment, sessionWeiKeFragment,
                        "sessionWeiKeFragment");
            } else if (sessionWeiKeFragment.isAdded()
                    && sessionWeiKeFragment.isHidden()) {
                transaction.show(sessionWeiKeFragment);
            }
            ViewUtil.moveFrontBg(findViewById(R.id.view), 0,
                    displayMetrics.widthPixels / 2, 0, 0);

        }
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int i = v.getId();
        if (i == R.id.last) {
            if (page == 0) {
                if (sessionZhuKeFragment.adapter.list.size() <= 0)
                    return;
                if (ZhukePosition - 1 < 0) {// 如果已经是第一个视频了，则提示
                    ViewUtil.showMessage(context, R.string.first_video);
                    AppUtil.Vibrate(this, 100);
                } else {
                    ZhukePosition = ZhukePosition - 1;
                    sendHeziMessage(GlobeVariable.PLAY_VIDEO
                            + sessionZhuKeFragment.adapter.list.get(ZhukePosition).getUuid() + ":::" +
                            sessionZhuKeFragment.adapter.list.get(ZhukePosition).getVideoUrl());// 上个视频
                    sessionZhuKeFragment.adapter.list.get(ZhukePosition).setBool(true);
                    sessionZhuKeFragment.adapter.list.get(ZhukePosition + 1).setBool(false);
                    sessionZhuKeFragment.adapter.notifyDataSetChanged();
                    sessionZhuKeFragment.listView.setSelection(ZhukePosition + 1);
                }
            } else {
                if (sessionWeiKeFragment.adapter.list.size() <= 0)
                    return;
                if (WeiKePosition - 1 < 0) {// 如果已经是第一个视频了，则提示
                    ViewUtil.showMessage(context, R.string.first_video);
                    AppUtil.Vibrate(this, 100);
                } else {
                    WeiKePosition = WeiKePosition - 1;
                    sendHeziMessage(GlobeVariable.PLAY_VIDEO
                            + sessionWeiKeFragment.adapter.list.get(WeiKePosition).getUuid() + ":::" +
                            sessionWeiKeFragment.adapter.list.get(WeiKePosition).getVideoUrl());// 上个视频
                    sessionWeiKeFragment.adapter.list.get(WeiKePosition).setBool(true);
                    sessionWeiKeFragment.adapter.list.get(WeiKePosition + 1).setBool(false);
                    sessionWeiKeFragment.adapter.notifyDataSetChanged();
                }
                sessionWeiKeFragment.listView.setSelection(WeiKePosition + 1);
            }

        } else if (i == R.id.next) {
            if (page == 0) {
                if (sessionZhuKeFragment.adapter.list.size() <= 0)
                    return;
                if (ZhukePosition + 2 > sessionZhuKeFragment.adapter.list.size()) {// 如果已经是最后视频了，则提示
                    AppUtil.Vibrate(this, 100);
                    ViewUtil.showMessage(context, R.string.last_video);
                } else {
                    ZhukePosition = ZhukePosition + 1;
                    sendHeziMessage(GlobeVariable.PLAY_VIDEO
                            + sessionZhuKeFragment.adapter.list.get(ZhukePosition).getUuid() + ":::" +
                            sessionZhuKeFragment.adapter.list.get(ZhukePosition).getVideoUrl());// 下个视频
                    sessionZhuKeFragment.adapter.list.get(ZhukePosition).setBool(true);
                    sessionZhuKeFragment.adapter.list.get(ZhukePosition - 1).setBool(false);
                    sessionZhuKeFragment.adapter.notifyDataSetChanged();
                }
                sessionZhuKeFragment.listView.setSelection(ZhukePosition + 1);
            } else {
                if (sessionWeiKeFragment.adapter.list.size() <= 0)
                    return;
                if (WeiKePosition + 2 > sessionWeiKeFragment.adapter.list.size()) {// 如果已经是最后视频了，则提示
                    AppUtil.Vibrate(this, 100);
                    ViewUtil.showMessage(context, R.string.last_video);
                } else {
                    WeiKePosition = WeiKePosition + 1;
                    sendHeziMessage(GlobeVariable.PLAY_VIDEO
                            + sessionWeiKeFragment.adapter.list.get(WeiKePosition).getUuid() + ":::" +
                            sessionWeiKeFragment.adapter.list.get(WeiKePosition).getVideoUrl());// 下个视频
                    sessionWeiKeFragment.adapter.list.get(WeiKePosition).setBool(true);
                    sessionWeiKeFragment.adapter.list.get(WeiKePosition - 1).setBool(false);
                    sessionWeiKeFragment.adapter.notifyDataSetChanged();
                }
                sessionWeiKeFragment.listView.setSelection(WeiKePosition + 1);
            }

        } else if (i == R.id.play) {
            if (page == 0) {
                if (sessionZhuKeFragment.adapter.list.size() <= 0)
                    return;
                sendHeziMessage(GlobeVariable.PLAY_VIDEO
                        + sessionZhuKeFragment.adapter.list.get(ZhukePosition).getUuid() + ":::" +
                        sessionZhuKeFragment.adapter.list.get(ZhukePosition).getVideoUrl());// 重新播放视频
            } else {
                if (sessionWeiKeFragment.adapter.list.size() <= 0)
                    return;
                sessionWeiKeFragment.adapter.list.get(WeiKePosition).setBool(true);
                sessionWeiKeFragment.adapter.notifyDataSetChanged();
                sendHeziMessage(GlobeVariable.PLAY_VIDEO
                        + sessionWeiKeFragment.adapter.list.get(WeiKePosition).getUuid() + ":::" +
                        sessionWeiKeFragment.adapter.list.get(WeiKePosition).getVideoUrl());// 重新播放视
            }

        } else if (i == R.id.video_add) {
            sendHeziMessage(GlobeVariable.ADD_VOLUME);// 增加声音

        } else if (i == R.id.video_last) {
            sendHeziMessage(GlobeVariable.DECREASE_VOLUME);// 减少声音

        } else if (i == R.id.open) {
            if (findViewById(R.id.layout1).getVisibility() == View.VISIBLE) {
                Animation animation1 = AnimationUtils.loadAnimation(this, R.anim.yjpty_dialog_bottom_exit_anim);
                layout.setAnimation(animation1);
                animation1.setAnimationListener(this);
                ViewUtil.setContent(this, R.id.open, R.string.open);
                ViewUtil.setArroundDrawable(open_txt, -1, -1,// 更改按钮文字，与图形
                        R.drawable.yjpty_ic_hardware_keyboard_arrow_up, -1);
                if (OnSessionAdapter.viewSpace != null) {// 隐藏空白
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            OnSessionActivity.width, (int) resources.getDimension(R.dimen._50dp));
                    OnSessionAdapter.viewSpace.setLayoutParams(layoutParams);
                }
            } else {
                findViewById(R.id.layout1).setVisibility(View.VISIBLE);
                Animation animation1 = AnimationUtils.loadAnimation(this, R.anim.yjpty_dialog_bottom_enter_anim);
                layout.setAnimation(animation1);
                ViewUtil.setContent(this, R.id.open, R.string.close);
                ViewUtil.setArroundDrawable(open_txt, -1, -1,
                        R.drawable.yjpty_ic_hardware_keyboard_arrow_down, -1);
                if (OnSessionAdapter.viewSpace != null) {// 打开的时候给下方留空白，不留的话，播放到最后的时候，文字会被键盘挡住
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            OnSessionActivity.width, height);
                    OnSessionAdapter.viewSpace.setLayoutParams(layoutParams);
                }
            }

        } else if (i == R.id.pause) {
            if (isPlayPPtOrVideo) {
                sendHeziMessage(GlobeVariable.PAUSE);
            } else {
                sendHeziMessage(GlobeVariable.PPT_LASR);
            }

        } else if (i == R.id.resume) {
            if (isPlayPPtOrVideo) {
                sendHeziMessage(GlobeVariable.NEXT);
            } else {
                sendHeziMessage(GlobeVariable.PPT_NEXT);
            }

        } else if (i == R.id.darkscreen) {
            sendHeziMessage(GlobeVariable.DARK);// 变黑

        } else if (i == R.id.brightenscreen) {
            sendHeziMessage(GlobeVariable.BRIGHT);// 变亮

        } else if (i == R.id.previos_class) {
            if (!bundle.getBoolean(IConstant.BUNDLE_PARAMS1))// 如果不是在上课则不发送请求
            {
                finish();
            } else {// 确定退出的dialog
                if (dialog != null && !dialog.isShowing()) {
                    dialog.show();
                }
            }

        }
    }


    // 想盒子发送请求
    void sendHeziMessage(String data) {
        if (!bundle.getBoolean(IConstant.BUNDLE_PARAMS1))// 如果不是在上课则不发送请求
            return;
        if (Integer.parseInt(data.split(":::")[0]) == 1) {// 如果是播放请求，先判断类型
            if (data.contains("ppt")) {
                playData = GlobeVariable.PLAY_PPT;
                setPauseAndNext(false);
            } else if (data.contains("jpeg") || data.contains("png") || data.contains("jpg")) {
                playData = GlobeVariable.PLAY_IMG;
                setPauseAndNext(true);
            } else {
                playData = GlobeVariable.PLAY_VIDEO;
                setPauseAndNext(true);
            }
            playData = playData + data.split(":::")[1]
                    + ":::" + GradId() + ":::" + data.split(":::")[2];
        } else {
            playData = data;
        }
        //ViewUtil.showMessage(this, "发送命令：：：" + playData);
        if (app.client != null) {
            if (!app.client.isConnect()) {
                handler.sendEmptyMessage(4);
                ViewUtil.showMessage(this, R.string.lzs);
                return;
            }
            app.client.sendTCP(playData.getBytes());
            handler.removeMessages(4);
        }
    }

    boolean isPlayPPtOrVideo;

    void setPauseAndNext(boolean b) {
        this.isPlayPPtOrVideo = b;
        if (b) {
            ViewUtil.setContent(this, R.id.pause, R.string.pause);
            ViewUtil.setContent(this, R.id.resume, R.string.resume);
        } else {
            ViewUtil.setContent(this, R.id.pause, R.string.last);
            ViewUtil.setContent(this, R.id.resume, R.string.next);
        }
    }

    int GradId() {
        String s = StringUtil.getResourse(this, videoCateMode.getGrader());
        if (s.contains(resources.getString(R.string.s_class)))
            return 4;
        else if (s.contains(resources.getString(R.string.m_class)))
            return 3;
        else if (s.contains(resources.getString(R.string.h_class)) && !s.contains(resources.getString(R.string
                .l_class)))
            return 2;
        else if (s.contains(resources.getString(R.string.l_class)))
            return 1;
        else
            return 0;
    }

    @Override
    public void clearDataCallBack(View vx) throws Exception {
        if (videoCateMode.isKiectSession())
            sendHeziMessage(GlobeVariable.KILL_APK + videoCateMode.getKinectPackageName());
        else
            sendHeziMessage(GlobeVariable.EXIT);
        if (timeOut == 1) {
            finish();
            return;
        }
        if (app.getNowWifi().equals("noWifi")) {// 如果之前不是wifi网咯则断开wifi不启动wifi连接
            WifiAdmin wa = new WifiAdmin(this);// 退出上课自动连保存的用户的网络,因为盒子的wifi不能连接互联网，所以得切换回去
            finish();
            wa.closeWifi();
        } else if (app.getNowWifi().equals(app.classModels.get(app.getClassPosition()).getHezi_code())
                || app.getNowWifi().equals(AppUtil.getConnectWifiSsid(this))) {
            finish();
        } else {// 连接保存的网咯
            WifiAdmin wa = new WifiAdmin(this);// 退出上课自动连保存的用户的网络,因为盒子的wifi不能连接互联网，所以得切换回去
            wa.connectConfiguratedWifi(app.getNowWifi());
            exitdialog = new LoginDialog(this);
            exitdialog.setTitle(resources.getString(R.string.now_exit));
            exitdialog.show();
            if (app.client != null) {
                app.client.close();
                app.client.destory();
                app.client = null;
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeMessages(4);
        if (exitdialog != null)
            exitdialog.close();
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!bundle.getBoolean(IConstant.BUNDLE_PARAMS1))// 如果不是在上课则不发送请求
            {
                finish();
            } else {// 确定退出的dialog
                if (dialog != null && !dialog.isShowing()) {
                    dialog.show();
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        findViewById(R.id.layout1).setVisibility(View.GONE);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
