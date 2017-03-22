package cn.kwim.mqttcilent.app_ui.home_school;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.text.style.ImageSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zk.myweex.R;
import cn.kiway.utils.ViewUtil;
import cn.kwim.mqttcilent.BaseActivity;
import cn.kwim.mqttcilent.app_ui.dailog.CreateGroudDialog;
import cn.kwim.mqttcilent.app_ui.home_school.group.GroupInformationActivity;
import cn.kwim.mqttcilent.common.Global;
import cn.kwim.mqttcilent.common.cache.dao.Dao;
import cn.kwim.mqttcilent.common.cache.dao.DaoType;
import cn.kwim.mqttcilent.common.cache.dao.GroupMemberListDao;
import cn.kwim.mqttcilent.common.cache.dao.MainListDao;
import cn.kwim.mqttcilent.common.cache.dao.MessageDao;
import cn.kwim.mqttcilent.common.cache.javabean.Converse;
import cn.kwim.mqttcilent.common.cache.javabean.GroupContent;
import cn.kwim.mqttcilent.common.cache.javabean.Message;
import cn.kwim.mqttcilent.common.utils.L;
import cn.kwim.mqttcilent.common.utils.Utils;
import cn.kwim.mqttcilent.common.views.DropdownListView;
import cn.kwim.mqttcilent.common.views.MyEditText;
import cn.kwim.mqttcilent.mqttclient.MqttInstance;
import cn.kwim.mqttcilent.mqttclient.mq.PushInterface;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * 聊天chat
 *
 * @author hmg
 */
public class ChatActivity extends BaseActivity implements OnClickListener, DropdownListView.OnRefreshListenerHeader,
        CreateGroudDialog.CreateGroup {

    public static final String GROUPID = "groupId";
    //群聊还是私聊
    public static final String CHATTYPE = "chattype";
    public static final String QUNORTAO = "qunortao";//班级群还是讨论组
    public static final String GROUPNAME = "groupName";//群名字
    //显示类型
    public static final String VTYPE = "vtype";
    public static final String CHATDATA = "chatdata";
    public static final String CHAT = "chat";
    public static final int CHOOSE_PHOTO = 0;
    public static final int TAKE_PHOTO = 1;

    private ImageView iv_personinfo;
    private MyEditText content;
    private ImageView iv_emoticons_normal;
    private Button btn_more;
    private Button btn_send;
    private LinearLayout ll_emoticon;
    private LinearLayout ll_btn_container1;
    private LinearLayout ll_btn_container;
    private ImageView iv_more;
    private ImageView iv_top_more;
    private LinearLayout ll_btn_top_container;
    private DropdownListView list;
    private ChatAdapter adapter;
    private ViewPager mViewPager;
    private LinearLayout mDotsLayout;
    private ArrayList<String> staticFacesList;
    private List<View> views = new ArrayList<>();
    private int columns = 6;
    private int rows = 4;
    private ImageView iv_back;
    private Realm realm;
    private RealmResults<GroupContent> result;
    private RealmChangeListener<Realm> realmListener;
    private String groupId;
    private LinearLayout ll_tv_pic;
    private LinearLayout ll_tv_photo;
    private LinearLayout ll_tv_notification;
    private LinearLayout ll_tv_homework;
    private LinearLayout ll_top_homework;
    private LinearLayout ll_top_notification;
    private LinearLayout ll_top_pic;

    private boolean update = true;
    private String chat;
    private String chatType;
    private LinearLayout rl_bottom;
    private TextView title1;
    CreateGroudDialog createGroudDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.im_activity_chat);
        groupId = getIntent().getStringExtra(GROUPID);
        chat = getIntent().getStringExtra(VTYPE);
        chatType = getIntent().getStringExtra(CHATTYPE);
        initView();
        adapter = new ChatAdapter(ChatActivity.this);
        list.setAdapter(adapter);
        adapter.setLst(MessageDao.getLimitMessage(20, 1, groupId, chatType));
        realm = Realm.getDefaultInstance();
        realmListener = new RealmChangeListener<Realm>() {
            @Override
            public void onChange(Realm element) {
                if (update) {
                    if (!adapter.getIsdelete()) {
                        adapter.setLst(MessageDao.getLimitMessage(20, 1, groupId, chatType));
                        setSkipLastItem();
                    }
                    adapter.setIsdelete(false);
                }
            }
        };
        realm.addChangeListener(realmListener);
        list.setOnRefreshListenerHead(this);
        setSkipLastItem();
    }

    @Override
    protected void onResume() {
        super.onResume();
        title1.setText(MainListDao.getTitleName(groupId, chatType));
    }

    public void initView() {
        iv_personinfo = (ImageView) findViewById(R.id.iv_personinfo);
        content = (MyEditText) findViewById(R.id.content);
        iv_emoticons_normal = (ImageView) findViewById(R.id.iv_emoticons_normal);
        btn_more = (Button) findViewById(R.id.btn_more);
        btn_send = (Button) findViewById(R.id.btn_send);
        mViewPager = (ViewPager) findViewById(R.id.face_viewpager);
        mDotsLayout = (LinearLayout) findViewById(R.id.face_dots_container);
        ll_btn_container1 = (LinearLayout) findViewById(R.id.ll_btn_container1);
        ll_btn_container = (LinearLayout) findViewById(R.id.ll_btn_container);
        iv_more = (ImageView) findViewById(R.id.iv_more);
        iv_top_more = (ImageView) findViewById(R.id.iv_top_more);
        ll_btn_top_container = (LinearLayout) findViewById(R.id.ll_btn_top_container);
        list = (DropdownListView) findViewById(R.id.list);
        iv_emoticons_normal = (ImageView) findViewById(R.id.iv_emoticons_normal);
        ll_emoticon = (LinearLayout) findViewById(R.id.ll_emoticon);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        ll_tv_pic = (LinearLayout) findViewById(R.id.ll_tv_pic);
        ll_tv_photo = (LinearLayout) findViewById(R.id.ll_tv_photo);
        ll_tv_homework = (LinearLayout) findViewById(R.id.ll_tv_homework);
        ll_tv_notification = (LinearLayout) findViewById(R.id.ll_tv_notification);
        rl_bottom = (LinearLayout) findViewById(R.id.rl_bottom);
        title1 = (TextView) findViewById(R.id.title1);

        ll_top_homework = (LinearLayout) findViewById(R.id.ll_top_homework);
        ll_top_notification = (LinearLayout) findViewById(R.id.ll_top_notification);
        ll_top_pic = (LinearLayout) findViewById(R.id.ll_top_pic);

        //顶部
        ll_top_homework.setOnClickListener(this);
        ll_top_notification.setOnClickListener(this);
        ll_top_pic.setOnClickListener(this);

        //底部添加的多个
        ll_tv_pic.setOnClickListener(this);
        ll_tv_photo.setOnClickListener(this);
        ll_tv_homework.setOnClickListener(this);
        ll_tv_notification.setOnClickListener(this);

        iv_emoticons_normal.setOnClickListener(this);
        iv_personinfo.setOnClickListener(this);
        iv_top_more.setOnClickListener(this);
        btn_send.setOnClickListener(this);
        btn_more.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        content.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    if (btn_more.getVisibility() == View.VISIBLE) {
                        btn_more.setVisibility(View.GONE);
                        btn_send.setVisibility(View.VISIBLE);
                    }
                } else {
                    btn_more.setVisibility(View.VISIBLE);
                    btn_send.setVisibility(View.GONE);
                }
            }
        });
        content.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ll_btn_container.setVisibility(View.GONE);
                ll_btn_container1.setVisibility(View.GONE);
                ll_emoticon.setVisibility(View.GONE);
                ll_btn_top_container.setVisibility(View.GONE);
                return false;
            }
        });

        list.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ll_btn_container.setVisibility(View.GONE);
                ll_btn_container1.setVisibility(View.GONE);
                ll_emoticon.setVisibility(View.GONE);
                ll_btn_top_container.setVisibility(View.GONE);
                return false;
            }
        });

        mViewPager.setOnPageChangeListener(new PageChange());
        initStaticFaces();
        InitViewPager();
        if (chat.equals(CHATDATA)) {
            rl_bottom.setVisibility(View.GONE);
            iv_personinfo.setVisibility(View.GONE);
        } else {
            rl_bottom.setVisibility(View.VISIBLE);
            iv_personinfo.setVisibility(View.VISIBLE);
        }
        createGroudDialog = new CreateGroudDialog(this, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_personinfo:
                Intent intent = new Intent(ChatActivity.this, GroupInformationActivity.class);
                intent.putExtra(GROUPID, groupId);
                intent.putExtra(QUNORTAO, getIntent().getStringExtra(QUNORTAO));
                intent.putExtra(GROUPNAME, ViewUtil.getContent(title1));
                startActivity(intent);
                overridePendingTransition(R.anim.im_slide_in_from_right, R.anim.im_slide_out_to_left);
                break;
            case R.id.iv_top_more:
                ll_btn_container.setVisibility(View.GONE);
                ll_btn_container1.setVisibility(View.GONE);
                ll_emoticon.setVisibility(View.GONE);

                if (ll_btn_top_container.getVisibility() == View.VISIBLE) {
                    ll_btn_top_container.setVisibility(View.GONE);
                } else {
                    ll_btn_top_container.setVisibility(View.VISIBLE);
                }
                Utils.hideSoftInput(ChatActivity.this);
                break;

            case R.id.btn_send:
                String contents = content.getText().toString();
                String uuid = UUID.randomUUID().toString();
                MessageDao.saveSendMessage(uuid, DaoType.TYPY.TEXT, content.getText().toString(), Global.getInstance
                        ().getNickName(),
                        "2", groupId, System.currentTimeMillis() + "", DaoType.ISSENDOK.OK);
                saveMessage(contents, uuid, DaoType.TYPY.TEXT);
                content.setText("");
                setSkipLastItem();
                break;
            case R.id.btn_more:
                ll_emoticon.setVisibility(View.GONE);
                ll_btn_top_container.setVisibility(View.GONE);
                if (ll_btn_container1.getVisibility() == View.VISIBLE || ll_btn_container.getVisibility() == View
                        .VISIBLE) {
                    ll_btn_container.setVisibility(View.GONE);
                    ll_btn_container1.setVisibility(View.GONE);
                } else {
                    ll_btn_container.setVisibility(View.GONE);
                    ll_btn_container1.setVisibility(View.VISIBLE);

                }
                Utils.hideSoftInput(ChatActivity.this);
                break;
            case R.id.iv_emoticons_normal:

                ll_btn_container.setVisibility(View.GONE);
                ll_btn_container1.setVisibility(View.GONE);
                ll_btn_top_container.setVisibility(View.GONE);
                if (ll_emoticon.getVisibility() == View.GONE) {
                    ll_emoticon.setVisibility(View.VISIBLE);
                } else {
                    ll_emoticon.setVisibility(View.GONE);
                }
                Utils.hideSoftInput(ChatActivity.this);
                break;
            case R.id.iv_back:
                if (update == false) {
                    update = true;
                    ll_btn_top_container.setVisibility(View.GONE);
                    adapter.setLst(MessageDao.getLimitMessage(20, 1, groupId, chatType));
                    setSkipLastItem();
                } else {
                    //更新数据库信息
                    Message message = MessageDao.getLastContent(groupId, chatType);
                    if (message != null) {
                        MainListDao.updateGroupList("0", Dao.getKey(groupId), message.getMsg(), message
                                .getMessageType(), message.getSendName());

                        MessageDao.updateUnreadMessage(groupId, "2");
                    }
                    setResult(RESULT_OK);
                    finish();
                    overridePendingTransition(R.anim.im_slide_in_from_left, R.anim.im_slide_out_to_right);
                }
                break;
            case R.id.ll_tv_pic:
                Intent intentPic = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //选择图片格式
                //intentPic.setType("image/*");
                //intentPic.putExtra("return-data", true);

                startActivityForResult(intentPic, CHOOSE_PHOTO);
                break;
            case R.id.ll_tv_homework:
                createGroudDialog.setTitle("发布作业", R.id.ll_tv_homework);
                createGroudDialog.show();
                break;

            case R.id.ll_tv_photo:
                Intent intentPhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//实例化Intent对象,
                // 使用MediaStore的ACTION_IMAGE_CAPTURE常量调用系统相机
                startActivityForResult(intentPhoto, TAKE_PHOTO);//开启相机，传入上面的Intent对象

                break;
            case R.id.ll_tv_notification:
                createGroudDialog.setTitle("发布通知", R.id.ll_tv_notification);
                createGroudDialog.show();
                break;
            case R.id.ll_top_homework: {
                adapter.setLst(MessageDao.getFLData(groupId, chatType, DaoType.TYPY.HOMEWORK));
                update = false;
                break;
            }
            case R.id.ll_top_notification: {
                adapter.setLst(MessageDao.getFLData(groupId, chatType, DaoType.TYPY.NOTICE));
                update = false;
                break;
            }
            case R.id.ll_top_pic: {
                adapter.setLst(MessageDao.getFLData(groupId, chatType, DaoType.TYPY.IMAGE));
                update = false;
                break;
            }
            default:
                break;
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case CHOOSE_PHOTO:
                    Uri uri = data.getData();
//                    L.i("图片124564513", data.getType());
                    // System.out.print("图片);
                    ContentResolver cr = this.getContentResolver();
                    Cursor cursor = cr.query(uri, null, null, null, null);
                    cursor.moveToFirst();
                    MessageDao.saveSendMessage(UUID.randomUUID().toString(), DaoType.TYPY.IMAGE,
                            uri.toString(), Global.getInstance().getNickName(), chatType,
                            groupId, System.currentTimeMillis() + "", DaoType.ISSENDOK.NO);
                    break;
                case TAKE_PHOTO:
                    L.i("相机", data.toString());

                    String sdState = Environment.getExternalStorageState();
                    if (!sdState.equals(Environment.MEDIA_MOUNTED)) {
                        //  GameLog.log(Tag, "sd card unmount");
                        return;
                    }
                    new DateFormat();
                    String name = DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
                    Bundle bundle = data.getExtras();
                    //获取相机返回的数据，并转换为图片格式
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    FileOutputStream fout = null;
                    File file = new File("/sdcard/pintu/");
                    file.mkdirs();
                    String filename = file.getPath() + name;
                    try {
                        fout = new FileOutputStream(filename);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fout);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            fout.flush();
                            fout.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    MessageDao.saveSendMessage(UUID.randomUUID().toString(), DaoType.TYPY.IMAGE,
                            filename, Global.getInstance().getNickName(), chatType,
                            groupId, System.currentTimeMillis() + "", DaoType.ISSENDOK.NO);
                    break;
            }

        }
    }

    @Override
    protected void onStart() {

//        new Thread() {
//            @Override
//            public void run() {
//                try {
//
//                    PushInterface interfaces = MqttInstance.getInstance().getPushInterface();
//                    if (interfaces.groupMemberList(groupId) != null) {
//                        //GroupMemberListDao.saveGroupListMember(interfaces.groupMemberList(groupId), groupId);
//                    }
//
//                } catch (Exception e) {
//
//                }
//            }
//        }.start();
        new AsyncTask<Object, Object, Object>() {
            private String member;

            @Override
            protected Object doInBackground(Object... objects) {
                if (MqttInstance.getInstance() != null && MqttInstance.getInstance().getPushInterface() != null) {
                    PushInterface interfaces = MqttInstance.getInstance().getPushInterface();
                    member = interfaces.groupMemberList(groupId);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                if (member != null) {
                    GroupMemberListDao.saveGroupListMember(member, groupId);
                }
                super.onPostExecute(o);
            }
        }.execute(new Object());

        super.onStart();
    }

    private void setSkipLastItem() {
        list.setSelection(adapter.getCount() - 1);
        list.setSelected(true);
    }

    @Override
    protected void onDestroy() {
        realm.removeChangeListener(realmListener);
        realm.close();
        overridePendingTransition(R.anim.im_slide_in_from_left, R.anim.im_slide_out_to_right);
        super.onDestroy();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Message message = MessageDao.getLastContent(groupId, chatType);
            if (message != null) {
                MainListDao.updateGroupList("0", Dao.getKey(groupId), message.getMsg(), message.getMessageType(),
                        message.getSendName());
                MessageDao.updateUnreadMessage(groupId, "2");
            }
            setResult(RESULT_OK);
            finish();
        }
        return true;
    }

    /**
     * 表情排列
     */
    private void InitViewPager() {
        // 获取页数
        for (int i = 0; i < getPagerCount(); i++) {
            views.add(viewPagerItem(i));
            LayoutParams params = new LayoutParams(16, 16);
            mDotsLayout.addView(dotsItem(i), params);
        }
        FaceVPAdapter mVpAdapter = new FaceVPAdapter(views);
        mViewPager.setAdapter(mVpAdapter);
        mDotsLayout.getChildAt(0).setSelected(true);
    }

    private View viewPagerItem(int position) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.im_face_gridview, null);//表情布局
        GridView gridview = (GridView) layout.findViewById(R.id.chart_face_gv);
        /**
         * 注：因为每一页末尾都有一个删除图标，所以每一页的实际表情columns*rows－1; 空出最后一个位置给删除图标
         * */
        List<String> subList = new ArrayList<String>();
        subList.addAll(staticFacesList
                .subList(position * (columns * rows - 1),
                        (columns * rows - 1) * (position + 1) > staticFacesList
                                .size() ? staticFacesList.size() : (columns
                                * rows - 1)
                                * (position + 1)));
        /**
         * 末尾添加删除图标
         * */
        subList.add("emotion_del_normal.png");
        FaceGVAdapter mGvAdapter = new FaceGVAdapter(subList, this);
        gridview.setAdapter(mGvAdapter);
        gridview.setNumColumns(columns);
        // 单击表情执行的操作
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    String png = ((TextView) ((LinearLayout) view).getChildAt(1)).getText().toString();
                    if (!png.contains("emotion_del_normal")) {// 如果不是删除图标
                        insert(getFace(png));
                    } else {
                        delete();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return gridview;
    }

    private SpannableStringBuilder getFace(String png) {
        SpannableStringBuilder sb = new SpannableStringBuilder();
        try {
            /**
             * 经过测试，虽然这里tempText被替换为png显示，但是但我单击发送按钮时，获取到輸入框的内容是tempText的值而不是png
             * 所以这里对这个tempText值做特殊处理
             * 格式：#[im_face/png/f_static_000.png]#，
             * */
            String tempText = "#[" + png + "]#";
            sb.append(tempText);
            sb.setSpan(
                    new ImageSpan(ChatActivity.this, BitmapFactory
                            .decodeStream(getAssets().open(png))), sb.length()
                            - tempText.length(), sb.length(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb;
    }

    /**
     * 向输入框里添加表情
     */
    private void insert(CharSequence text) {
        int iCursorStart = Selection.getSelectionStart((content.getText()));
        int iCursorEnd = Selection.getSelectionEnd((content.getText()));
        if (iCursorStart != iCursorEnd) {
            ((Editable) content.getText()).replace(iCursorStart, iCursorEnd, "");
        }
        int iCursor = Selection.getSelectionEnd((content.getText()));
        ((Editable) content.getText()).insert(iCursor, text);
    }

    /**
     * 删除图标执行事件
     * 注：如果删除的是表情，在删除时实际删除的是tempText即图片占位的字符串，所以必需一次性删除掉tempText，才能将图片删除
     */
    private void delete() {
        if (content.getText().length() != 0) {
            int iCursorEnd = Selection.getSelectionEnd(content.getText());
            int iCursorStart = Selection.getSelectionStart(content.getText());
            if (iCursorEnd > 0) {
                if (iCursorEnd == iCursorStart) {
                    if (isDeletePng(iCursorEnd)) {
                        String st = "#[im_face/png/f_static_000.png]#";
                        ((Editable) content.getText()).delete(
                                iCursorEnd - st.length(), iCursorEnd);
                    } else {
                        ((Editable) content.getText()).delete(iCursorEnd - 1,
                                iCursorEnd);
                    }
                } else {
                    ((Editable) content.getText()).delete(iCursorStart,
                            iCursorEnd);
                }
            }
        }
    }

    /**
     * 判断即将删除的字符串是否是图片占位字符串tempText 如果是：则讲删除整个tempText
     **/
    private boolean isDeletePng(int cursor) {
        String st = "#[im_face/png/f_static_000.png]#";
        String contents = content.getText().toString().substring(0, cursor);
        if (contents.length() >= st.length()) {
            String checkStr = contents.substring(contents.length() - st.length(),
                    contents.length());
            String regex = "(\\#\\[face/png/f_static_)\\d{3}(.png\\]\\#)";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(checkStr);
            return m.matches();
        }
        return false;
    }

    private ImageView dotsItem(int position) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.im_dot_image, null);
        ImageView iv = (ImageView) layout.findViewById(R.id.face_dot);
        iv.setId(position);
        return iv;
    }

    /**
     * 根据表情数量以及GridView设置的行数和列数计算Pager数量
     *
     * @return
     */
    private int getPagerCount() {
        int count = staticFacesList.size();
        return count % (columns * rows - 1) == 0 ? count / (columns * rows - 1)
                : count / (columns * rows - 1) + 1;
    }

    /**
     * 初始化表情列表staticFacesList
     */
    private void initStaticFaces() {
        try {
            staticFacesList = new ArrayList<String>();
            String[] faces = getAssets().list("face/png");
            //将Assets中的表情名称转为字符串一一添加进staticFacesList
            for (int i = 0; i < faces.length; i++) {
                staticFacesList.add(faces[i]);
            }
            //去掉删除图片
            staticFacesList.remove("emotion_del_normal.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    int i = 1;
    boolean type = true;

    @Override
    public void onRefresh() {
        // Toast.makeText(this,"下路刷新操作",Toast.LENGTH_LONG).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                list.onRefreshCompleteHeader();
                if (type) {
                    i = i + 1;
                }
                int ordersum = adapter.getCount();
                List<Message> lst = MessageDao.getLimitMessage(20, i, groupId, chatType);
                if (lst == null) {
                    type = false;
                    Toast.makeText(ChatActivity.this, "不要拉了,已经到顶了", Toast.LENGTH_LONG).show();
                } else {
                    type = true;
                    adapter.setLst(lst);

                }
                if (lst != null && i != 1) {
                    list.setSelection(lst.size() - ordersum + 1);
                    list.setSelected(true);

                }

            }
        }, 500);
    }

    @Override
    public void createGroupCallBack(String message, int viewId) throws Exception {
        String uuid = UUID.randomUUID().toString();
        switch (viewId) {
            case R.id.ll_tv_homework:
                MessageDao.saveSendMessage(uuid, DaoType.TYPY.HOMEWORK, message, Global.getInstance()
                                .getNickName(),
                        "2", groupId, System.currentTimeMillis() + "", DaoType.ISSENDOK.OK);
                saveMessage(message, uuid, DaoType.TYPY.HOMEWORK);
                break;
            case R.id.ll_tv_notification:
                MessageDao.saveSendMessage(uuid, DaoType.TYPY.NOTICE, message, Global.getInstance()
                                .getNickName(),
                        "2", groupId, System.currentTimeMillis() + "", DaoType.ISSENDOK.OK);
                saveMessage(message, uuid, DaoType.TYPY.NOTICE);
                break;
        }
        createGroudDialog.dismiss();
    }


    /**
     * 表情页改变时，dots效果也要跟着改变
     */
    class PageChange implements OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int arg0) {
            for (int i = 0; i < mDotsLayout.getChildCount(); i++) {
                mDotsLayout.getChildAt(i).setSelected(false);
            }
            mDotsLayout.getChildAt(arg0).setSelected(true);
        }

    }

    /**
     * 更新数据库
     *
     * @param contents
     * @param uuid
     * @param type
     */
    private void saveMessage(String contents, String uuid, String type) {

//        try{
//            //异步进行
//            String key = MqttInstance.getInstance().getPushInterface().sendMessage(groupId, "{\"msg\":\"" +
// contents + "\",\"type\":\"text\"}", "2");
//            Converse converse = new Gson().fromJson(key, Converse.class);
//            if (converse.getStatusCode().equals("200")) {
//                Map map = (Map) converse.getData();
//                String msgid = map.get("msgid").toString().replace(".0", "");
//                MessageDao.sendOk(uuid, msgid, contents);
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        UpdateTask updateTask = new UpdateTask(ChatActivity.this, contents, uuid, type);
        updateTask.execute();


    }

    class UpdateTask extends AsyncTask<Void, Integer, Integer> {
        private Context context;
        private String contents;
        private String uuid;
        private String type;

        UpdateTask(Context context, String contents, String uuid, String type) {
            this.context = context;
            this.contents = contents;
            this.uuid = uuid;
            this.type = type;
        }

        /**
         * 运行在UI线程中，在调用doInBackground()之前执行
         */
        @Override
        protected void onPreExecute() {
            // Toast.makeText(context,"开始执行",Toast.LENGTH_SHORT).show();
        }

        /**
         * 后台运行的方法，可以运行非UI线程，可以执行耗时的方法
         */
        @Override
        protected Integer doInBackground(Void... params) {
            try {
                //异步进行
                String key = MqttInstance.getInstance().getPushInterface().sendMessage(groupId, "{\"msg\":\"" +
                        contents + "\",\"type\":\"text\"}", "2");
                Converse converse = new Gson().fromJson(key, Converse.class);
                if (converse.getStatusCode().equals("200")) {
                    Map map = (Map) converse.getData();
                    if (map.get("msgid") != null) {
                        String msgid = map.get("msgid").toString().replace(".0", "");
                        MessageDao.sendOk(uuid, msgid, contents);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * 运行在ui线程中，在doInBackground()执行完毕后执行
         */
        @Override
        protected void onPostExecute(Integer integer) {

        }

        /**
         * 在publishProgress()被调用以后执行，publishProgress()用于更新进度
         */
        @Override
        protected void onProgressUpdate(Integer... values) {

        }
    }


}
