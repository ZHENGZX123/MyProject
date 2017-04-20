package cn.kwim.mqttcilent.app_ui.home_school;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
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
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import org.json.JSONObject;

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

import cn.kiway.IConstant;
import cn.kiway.Yjpty.R;
import cn.kiway.utils.SharedPreferencesUtil;
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
import cn.kwim.mqttcilent.common.http.UploadUtil;
import cn.kwim.mqttcilent.common.utils.DateUtil;
import cn.kwim.mqttcilent.common.utils.Utils;
import cn.kwim.mqttcilent.common.views.DropdownListView;
import cn.kwim.mqttcilent.common.views.MyEditText;
import cn.kwim.mqttcilent.common.views.ProgressImageView;
import cn.kwim.mqttcilent.mqttclient.MqttInstance;
import cn.kwim.mqttcilent.mqttclient.mq.PushInterface;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import uk.co.senab.photoview.sample.ViewPagerActivity;

import static cn.kiway.Yjpty.R.id.resend;

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
    private CreateGroudDialog createGroudDialog;

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
        int i1 = v.getId();
        if (i1 == R.id.iv_personinfo) {
            Intent intent = new Intent(ChatActivity.this, GroupInformationActivity.class);
            intent.putExtra(GROUPID, groupId);
            intent.putExtra(QUNORTAO, getIntent().getStringExtra(QUNORTAO));
            intent.putExtra(GROUPNAME, ViewUtil.getContent(title1));
            startActivity(intent);
            overridePendingTransition(R.anim.im_slide_in_from_right, R.anim.im_slide_out_to_left);

        } else if (i1 == R.id.iv_top_more) {
            ll_btn_container.setVisibility(View.GONE);
            ll_btn_container1.setVisibility(View.GONE);
            ll_emoticon.setVisibility(View.GONE);

            if (ll_btn_top_container.getVisibility() == View.VISIBLE) {
                ll_btn_top_container.setVisibility(View.GONE);
            } else {
                ll_btn_top_container.setVisibility(View.VISIBLE);
            }
            Utils.hideSoftInput(ChatActivity.this);

        } else if (i1 == R.id.btn_send) {
            String contents = content.getText().toString();
            String uuid = UUID.randomUUID().toString();
            MessageDao.saveSendMessage(uuid, DaoType.TYPY.TEXT, content.getText().toString(), Global.getInstance
                            ().getNickName(),
                    "2", groupId, System.currentTimeMillis() + "", DaoType.STATUS.SENDING);
            sendTextMessage(contents, uuid, DaoType.TYPY.TEXT);
            content.setText("");
            setSkipLastItem();
        } else if (i1 == R.id.btn_more) {
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
        } else if (i1 == R.id.iv_emoticons_normal) {
            ll_btn_container.setVisibility(View.GONE);
            ll_btn_container1.setVisibility(View.GONE);
            ll_btn_top_container.setVisibility(View.GONE);
            if (ll_emoticon.getVisibility() == View.GONE) {
                ll_emoticon.setVisibility(View.VISIBLE);
            } else {
                ll_emoticon.setVisibility(View.GONE);
            }
            Utils.hideSoftInput(ChatActivity.this);
        } else if (i1 == R.id.iv_back) {
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
        } else if (i1 == R.id.ll_tv_pic) {
            Intent intentPic = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            //选择图片格式
            //intentPic.setType("image/*");
            //intentPic.putExtra("return-data", true);
            startActivityForResult(intentPic, CHOOSE_PHOTO);
        } else if (i1 == R.id.ll_tv_homework) {
            createGroudDialog.setTitle("发布作业", R.id.ll_tv_homework);
            createGroudDialog.show();
        } else if (i1 == R.id.ll_tv_photo) {
            Intent intentPhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//实例化Intent对象,
            // 使用MediaStore的ACTION_IMAGE_CAPTURE常量调用系统相机
            startActivityForResult(intentPhoto, TAKE_PHOTO);//开启相机，传入上面的Intent对象
        } else if (i1 == R.id.ll_tv_notification) {
            createGroudDialog.setTitle("发布通知", R.id.ll_tv_notification);
            createGroudDialog.show();
        } else if (i1 == R.id.ll_top_homework) {
            adapter.setLst(MessageDao.getFLData(groupId, chatType, DaoType.TYPY.HOMEWORK));
            update = false;
        } else if (i1 == R.id.ll_top_notification) {
            adapter.setLst(MessageDao.getFLData(groupId, chatType, DaoType.TYPY.NOTICE));
            update = false;
        } else if (i1 == R.id.ll_top_pic) {
            adapter.setLst(MessageDao.getFLData(groupId, chatType, DaoType.TYPY.IMAGE));
            update = false;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case CHOOSE_PHOTO: {
                    Uri uri = data.getData();
                    ContentResolver cr = this.getContentResolver();
                    Cursor cursor = cr.query(uri, null, null, null, null);
                    cursor.moveToFirst();
                    String msgId = UUID.randomUUID().toString();
                    MessageDao.saveSendMessage(msgId, DaoType.TYPY.IMAGE,
                            uri.toString(), Global.getInstance().getNickName(), chatType,
                            groupId, System.currentTimeMillis() + "", DaoType.STATUS.SENDING);
                    setPercent(uri, msgId, groupId);
                }
                break;
                case TAKE_PHOTO: {
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
                    String msgId = UUID.randomUUID().toString();
                    MessageDao.saveSendMessage(msgId, DaoType.TYPY.IMAGE,
                            filename, Global.getInstance().getNickName(), chatType,
                            groupId, System.currentTimeMillis() + "", DaoType.STATUS.SENDING);
                    setPercent(Uri.parse(filename), msgId, groupId);
                }
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
        if (viewId == R.id.ll_tv_homework) {
            MessageDao.saveSendMessage(uuid, DaoType.TYPY.HOMEWORK, message, Global.getInstance()
                            .getNickName(),
                    "2", groupId, System.currentTimeMillis() + "", DaoType.STATUS.SUCCESS);
            sendTextMessage(message, uuid, DaoType.TYPY.HOMEWORK);
        } else if (viewId == R.id.ll_tv_notification) {
            MessageDao.saveSendMessage(uuid, DaoType.TYPY.NOTICE, message, Global.getInstance()
                            .getNickName(),
                    "2", groupId, System.currentTimeMillis() + "", DaoType.STATUS.SUCCESS);
            sendTextMessage(message, uuid, DaoType.TYPY.NOTICE);
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

    private void sendTextMessage(String contents, String uuid, String type) {
        UpdateTask updateTask = new UpdateTask(ChatActivity.this, contents, uuid, type);
        updateTask.execute();
    }

    class UpdateTask extends AsyncTask<Void, Integer, Integer> {
        private String contents;
        private String uuid;

        UpdateTask(Context context, String contents, String uuid, String type) {
            this.contents = contents;
            this.uuid = uuid;
        }

        /**
         * 运行在UI线程中，在调用doInBackground()之前执行
         */
        @Override
        protected void onPreExecute() {
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
                Log.d("mqtt", "send text = " + key);
                Converse converse = new Gson().fromJson(key, Converse.class);
                if (converse.getStatusCode().equals("200")) {
                    //SUCCESS
                    Map map = (Map) converse.getData();
                    String msgid = map.get("msgid").toString().replace(".0", "");
                    MessageDao.sendSuccess(uuid, msgid, contents);
                } else {
                    //FAILURE
                    Log.d("mqtt", "send text failure");
                    MessageDao.sendFailure(uuid, contents);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("mqtt", "send text = e" + e.toString());
                MessageDao.sendFailure(uuid, contents);
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


    class ChatAdapter extends BaseAdapter {

        private List<Message> lst;
        private Context context;
        private LayoutInflater mInflater;
        private boolean isdelete;

        private static final int TEXTR = 1;
        private static final int TEXTL = 2;
        private static final int PUSHR = 3;
        private static final int PUSHL = 4;
        private static final int FILER = 5;
        private static final int FILEL = 6;
        private static final int IMAGER = 7;
        private static final int IMAGEL = 8;
        private static final int VOICER = 9;
        private static final int VOICEL = 10;
        private static final int HOMEWORKR = 11;
        private static final int HOMEWORKL = 12;
        private static final int NOTICER = 13;
        private static final int NOTICEL = 14;
        private static final int ACTIVITYR = 15;
        private static final int ACTIVITYL = 16;
        private static final int LOCATIONR = 17;
        private static final int LOCATIONL = 18;
        private static final int TOUSERR = 19;
        private static final int TOUSERL = 20;
        private static final int LINKR = 21;
        private static final int LINKL = 22;
        private static final int MUSICR = 23;
        private static final int MUSICL = 24;
        private static final int RECALL = 25;


        public ChatAdapter(Context context) {
            this.lst = new ArrayList<>();
            this.context = context;
            mInflater = LayoutInflater.from(context);
        }

        /**
         * 更新lst 实现
         */
        public void setLst(List<Message> list) {
            this.lst = list;
            notifyDataSetChanged();
        }

        public boolean getIsdelete() {
            return isdelete;

        }

        public void setIsdelete(boolean isdelete) {
            this.isdelete = isdelete;
        }


        @Override
        public int getCount() {
            return lst != null ? lst.size() : 0;
        }

        @Override
        public Object getItem(int position) {
            return lst.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            Message message = (Message) getItem(position);
            System.out.print("=============" + message.toString());
            if (message.getMessageType().equals(DaoType.TYPY.TEXT)) {
                if (message.getIsMy().equals(DaoType.ISMY.ISMY)) {
                    return TEXTR;
                } else {
                    return TEXTL;
                }
            } else if (message.getMessageType().equals(DaoType.TYPY.HOMEWORK)) {
                if (message.getIsMy().equals(DaoType.ISMY.ISMY)) {
                    return HOMEWORKR;
                } else {
                    return HOMEWORKL;
                }

            } else if (message.getMessageType().equals(DaoType.TYPY.NOTICE)) {
                if (message.getIsMy().equals(DaoType.ISMY.ISMY)) {
                    return NOTICER;
                } else {
                    return NOTICEL;
                }
            } else if (message.getMessageType().equals(DaoType.TYPY.IMAGE)) {
                if (message.getIsMy().equals(DaoType.ISMY.ISMY)) {
                    return IMAGER;
                } else {
                    return IMAGEL;
                }
            } else if (message.getMessageType().equals(DaoType.TYPY.RECALLMSG)) {
                return RECALL;
            }
            return TEXTL;
        }

        @Override
        public int getViewTypeCount() {

            return 26;
        }

        @SuppressWarnings("unused")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final Message message = lst.get(position);
            ViewHolder viewHolder = null;
            int type = getItemViewType(position);
            if (viewHolder == null) {
                viewHolder = new ViewHolder();
                switch (type) {
                    case TEXTR: {
                        convertView = mInflater.inflate(R.layout.im_a_chat_item_txt_right, null);
                        viewHolder.tv_chatcontent = (TextView) convertView.findViewById(R.id.tv_chatcontent);
                        viewHolder.resend = (TextView) convertView.findViewById(resend);
                        viewHolder.pb_sending = (ProgressBar) convertView.findViewById(R.id.pb_sending);
                        setViewHolder(convertView, viewHolder);
                        break;
                    }
                    case TEXTL: {
                        convertView = mInflater.inflate(R.layout.im_a_chat_item_txt_left, null);
                        viewHolder.tv_chatcontent = (TextView) convertView.findViewById(R.id.tv_chatcontent);
                        setViewHolder(convertView, viewHolder);
                        break;
                    }
                    case HOMEWORKR: {
                        convertView = mInflater.inflate(R.layout.im_a_chat_item_homework_right, null);
                        viewHolder.tv_chatcontent = (TextView) convertView.findViewById(R.id.tv_chatcontent);
                        viewHolder.resend = (TextView) convertView.findViewById(resend);
                        setViewHolder(convertView, viewHolder);
                        break;
                    }
                    case HOMEWORKL: {
                        convertView = mInflater.inflate(R.layout.im_a_chat_item_homework_left, null);
                        viewHolder.tv_chatcontent = (TextView) convertView.findViewById(R.id.tv_chatcontent);
                        setViewHolder(convertView, viewHolder);
                        break;
                    }
                    case NOTICER: {
                        convertView = mInflater.inflate(R.layout.im_a_chat_item_notice_right, null);
                        viewHolder.tv_chatcontent = (TextView) convertView.findViewById(R.id.tv_chatcontent);
                        viewHolder.resend = (TextView) convertView.findViewById(R.id.resend);
                        setViewHolder(convertView, viewHolder);
                        break;
                    }
                    case NOTICEL: {
                        convertView = mInflater.inflate(R.layout.im_a_chat_item_notice_left, null);
                        viewHolder.tv_chatcontent = (TextView) convertView.findViewById(R.id.tv_chatcontent);
                        setViewHolder(convertView, viewHolder);
                        break;
                    }
                    case IMAGER: {
                        convertView = mInflater.inflate(R.layout.im_a_chat_item_image_right, null);
                        setViewHolder(convertView, viewHolder);
                        viewHolder.pb_sending = (ProgressBar) convertView.findViewById(R.id.pb_sending);
                        viewHolder.resend = (TextView) convertView.findViewById(R.id.resend);
                        viewHolder.imageView = (ProgressImageView) convertView.findViewById(R.id.iv_chatcontent);
                        break;
                    }
                    case IMAGEL: {
                        convertView = mInflater.inflate(R.layout.im_a_chat_item_image_left, null);
                        setViewHolder(convertView, viewHolder);
                        viewHolder.iv_chatcontent = (SimpleDraweeView) convertView.findViewById(R.id.iv_chatcontent);
                        break;
                    }
                    case RECALL: {
                        convertView = mInflater.inflate(R.layout.im_a_chat_item_chexiao, null);
                        viewHolder.time = (TextView) convertView.findViewById(R.id.timestamp);
                    }
                }
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            Uri uri = Uri.parse(message.getSendLogo());
            switch (type) {
                case TEXTR: {
                    viewHolder.tv_chatcontent.setText(handler(viewHolder.tv_chatcontent, message.getMsg(), context));
                    if (message.getSendLogo().equals("null") || message.getSendLogo().equals(""))
                        uri = Uri.parse(SharedPreferencesUtil.getString(context, IConstant.USER_PIC));
                    setView(message, viewHolder, uri, position);
                    viewHolder.tv_chatcontent.setLongClickable(true);
                    viewHolder.tv_chatcontent.setTag(position + "$" + message.getKey() + "$" + message.getMessageType() +
                            "$" + message.getReadType() + "$" + "1");
                    viewHolder.tv_chatcontent.setOnCreateContextMenuListener(mListViewContextMenuListener);
                    if (message.getIsSendOk().equals(DaoType.STATUS.SENDING)) {
                        viewHolder.pb_sending.setVisibility(View.VISIBLE);
                        viewHolder.resend.setVisibility(View.GONE);
                    } else if (message.getIsSendOk().equals(DaoType.STATUS.SUCCESS)) {
                        viewHolder.pb_sending.setVisibility(View.GONE);
                        viewHolder.resend.setVisibility(View.GONE);
                    } else if (message.getIsSendOk().equals(DaoType.STATUS.FAILURE)) {
                        viewHolder.pb_sending.setVisibility(View.GONE);
                        viewHolder.resend.setVisibility(View.VISIBLE);
                    }
                    viewHolder.resend.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            resend(message);
                        }
                    });
                    break;
                }
                case TEXTL: {
                    viewHolder.tv_chatcontent.setText(handler(viewHolder.tv_chatcontent, message.getMsg(), context));
                    setView(message, viewHolder, uri, position);
                    viewHolder.tv_chatcontent.setLongClickable(true);
                    viewHolder.tv_chatcontent.setTag(position + "$" + message.getKey() + "$" + message.getMessageType() +
                            "$" + message.getReadType() + "$" + "3");
                    viewHolder.tv_chatcontent.setOnCreateContextMenuListener(mListViewContextMenuListener);
                    break;
                }
                case HOMEWORKR: {
                    viewHolder.tv_chatcontent.setText(handler(viewHolder.tv_chatcontent, message.getMsg(), context));
                    if (message.getSendLogo().equals("null") || message.getSendLogo().equals(""))
                        uri = Uri.parse(SharedPreferencesUtil.getString(context, IConstant.USER_PIC));
                    setView(message, viewHolder, uri, position);
                    viewHolder.tv_chatcontent.setLongClickable(true);
                    viewHolder.tv_chatcontent.setTag(position + "$" + message.getKey() + "$" + message.getMessageType() +
                            "$" + message.getReadType() + "$" + type);
                    viewHolder.tv_chatcontent.setOnCreateContextMenuListener(mListViewContextMenuListener);
                    break;
                }
                case HOMEWORKL: {
                    viewHolder.tv_chatcontent.setText(handler(viewHolder.tv_chatcontent, message.getMsg(), context));
                    setView(message, viewHolder, uri, position);
                    viewHolder.tv_chatcontent.setLongClickable(true);
                    viewHolder.tv_chatcontent.setTag(position + "$" + message.getKey() + "$" + message.getMessageType() +
                            "$" + message.getReadType() + "$" + "3");
                    viewHolder.tv_chatcontent.setOnCreateContextMenuListener(mListViewContextMenuListener);
                    break;
                }
                case NOTICER: {
                    viewHolder.tv_chatcontent.setText(handler(viewHolder.tv_chatcontent, message.getMsg(), context));
                    if (message.getSendLogo().equals("null") || message.getSendLogo().equals(""))
                        uri = Uri.parse(SharedPreferencesUtil.getString(context, IConstant.USER_PIC));
                    setView(message, viewHolder, uri, position);
                    viewHolder.tv_chatcontent.setLongClickable(true);
                    viewHolder.tv_chatcontent.setTag(position + "$" + message.getKey() + "$" + message.getMessageType() +
                            "$" + message.getReadType() + "$" + type);
                    viewHolder.tv_chatcontent.setOnCreateContextMenuListener(mListViewContextMenuListener);
                    break;
                }
                case NOTICEL: {
                    viewHolder.tv_chatcontent.setText(handler(viewHolder.tv_chatcontent, message.getMsg(), context));
                    setView(message, viewHolder, uri, position);
                    viewHolder.tv_chatcontent.setLongClickable(true);
                    viewHolder.tv_chatcontent.setTag(position + "$" + message.getKey() + "$" + message.getMessageType() +
                            "$" + message.getReadType() + "$" + "3");
                    viewHolder.tv_chatcontent.setOnCreateContextMenuListener(mListViewContextMenuListener);
                    break;
                }
                case IMAGER: {
                    String url = null;
                    if (message.getSendLogo().equals("null") || message.getSendLogo().equals(""))
                        uri = Uri.parse(SharedPreferencesUtil.getString(context, IConstant.USER_PIC));
                    setView(message, viewHolder, uri, position);
                    if (message.getIsSendOk().equals(DaoType.STATUS.SUCCESS)) {
                        viewHolder.resend.setVisibility(View.GONE);
                        viewHolder.imageView.setProgress(100);
                        url = /*OkhttpUtils.base_pic_url +*/ message.getMsg();
                        final Uri urii = Uri.parse(url);
                        viewHolder.imageView.setImageURI(urii);
                    } else if (message.getIsSendOk().equals(DaoType.STATUS.FAILURE)) {
                        viewHolder.resend.setVisibility(View.VISIBLE);
                    } else {
                        Uri urii;
                        if (message.getMsg().startsWith("content://")) {
                            urii = Uri.parse(message.getMsg());
                        } else {
                            urii = Uri.parse("file://" + message.getMsg());
                        }
                        viewHolder.imageView.setImageURI(urii);
                        url = /*OkhttpUtils.base_pic_url +*/ message.getMsg();
                    }
                    viewHolder.imageView.setLongClickable(true);
                    viewHolder.imageView.setTag(position + "$" + message.getKey() + "$" + message.getMessageType() + "$"
                            + message.getReadType() + "$" + type);
                    viewHolder.imageView.setOnCreateContextMenuListener(mListViewContextMenuListener);
                    setOnclickImage(viewHolder, url);

                    viewHolder.resend.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            resend2(message);
                        }
                    });

                    final String urlurl = url;
                    viewHolder.imageView.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showBigImage(urlurl);
                        }
                    });
                    break;
                }
                case IMAGEL: {
                    setView(message, viewHolder, uri, position);
                    final Uri urii = Uri.parse(/*OkhttpUtils.base_pic_url +*/ message.getMsg());
                    viewHolder.iv_chatcontent.setImageURI(urii);
                    viewHolder.iv_chatcontent.setLongClickable(true);
                    viewHolder.iv_chatcontent.setTag(position + "$" + message.getKey() + "$" + message.getMessageType() +
                            "$" + message.getReadType() + "$" + "2");
                    viewHolder.iv_chatcontent.setOnCreateContextMenuListener(mListViewContextMenuListener);
                    viewHolder.iv_chatcontent.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            showBigImage(message.getMsg());
                        }
                    });
                    break;
                }
                case RECALL: {
                    viewHolder.time.setText(message.getSendName() + "撤回了一条消息");
                    break;
                }

            }
            return convertView;
        }

        private void showBigImage(String url) {
            String[] urls = new String[1];
            urls[0] = url;
            ViewPagerActivity.sDrawables = urls;
            Intent intent = new Intent(ChatActivity.this, ViewPagerActivity.class);
            intent.putExtra("position", 0);
            startActivity(intent);
        }

        private void resend(final Message message) {
            final AlertDialog.Builder normalDialog = new AlertDialog.Builder(context);
            normalDialog.setTitle("提示");
            normalDialog.setMessage("重发该消息");
            normalDialog.setPositiveButton("确定",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            sendTextMessage(message.getMsg(), message.getMeassgeId(), message.getType());
                        }
                    });
            normalDialog.setNegativeButton("取消", null);
            normalDialog.show();
        }

        private void resend2(final Message message) {
            final AlertDialog.Builder normalDialog = new AlertDialog.Builder(context);
            normalDialog.setTitle("提示");
            normalDialog.setMessage("重发该消息");
            normalDialog.setPositiveButton("确定",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MessageDao.resend(message.getMeassgeId(), message.getMsg());
                        }
                    });
            normalDialog.setNegativeButton("取消", null);
            normalDialog.show();
        }


        private void setOnclickImage(ViewHolder viewHolder, final String url) {
            viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                Intent intent = new Intent(context, PicturePreviewActivity.class);
//                intent.putExtra("url", url);
//                intent.putExtra("indentify", context.getResources().getIdentifier("im_ic_picture_loadfailed",
//                        "drawable",
//                        context.getPackageName()));
//                ((Activity) context).startActivity(intent);
                    BaseActivity activity = (BaseActivity) context;
                    List<String> list = new ArrayList<String>();
                    list.add(url);
                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList(IConstant.BUNDLE_PARAMS, (ArrayList<String>) list);
                    bundle.putInt(IConstant.BUNDLE_PARAMS1, 1);
//                activity.startActivity(ViewPhotosActivity.class, bundle);
                }
            });
        }

        private void setView(Message message, ViewHolder viewHolder, Uri uri, int position) {
            if (position % 15 == 0) {
                viewHolder.time.setText(DateUtil.getTimeDisplay(DateUtil.getDate(Long.parseLong(message.getTime()))));
                viewHolder.time.setVisibility(View.VISIBLE);
            } else
                viewHolder.time.setVisibility(View.GONE);
            viewHolder.name.setText(message.getSendName());
            if (ViewUtil.getContent(viewHolder.name).equals("null"))
                viewHolder.name.setText("匿名");
            viewHolder.header.setImageURI(uri);
        }

        private void setViewHolder(View convertView, ViewHolder viewHolder) {
            viewHolder.time = (TextView) convertView.findViewById(R.id.timestamp);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.header = (SimpleDraweeView) convertView.findViewById(R.id.iv_userhead);
        }

        class ViewHolder {
            TextView tv_chatcontent;
            TextView time;
            TextView name;
            SimpleDraweeView header;
            ProgressImageView imageView;
            SimpleDraweeView iv_chatcontent;
            TextView resend;
            ProgressBar pb_sending;
        }

        /**
         * 过滤 显示GIF 动画
         *
         * @param gifTextView
         * @param content
         * @return
         */
        public SpannableString handler(final TextView gifTextView, String content, Context context) {

            SpannableString sb = new SpannableString(content);
            String regex = "(\\#\\[face/png/f_static_)\\d{3}(.png\\]\\#)";
            String[] s = content.split("\\d{3}(.png\\]\\#)");

            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(content);

            while (m.find()) {
                String tempText = m.group();
                try {
                    String png = tempText.substring("#[".length(), tempText.length() - "]#".length());
                    try {
                        sb.setSpan(new ImageSpan(context, BitmapFactory.decodeStream(context.getAssets().open(png))), m
                                .start(), m.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    } catch (IOException e1) {

                        e1.printStackTrace();
                    }
                 /*
                String num = tempText.substring("#[im_face/png/f_static_".length(), tempText.length() - ".png]#"
                .length());
                //if (s.length > 5) {
                    String png = "face/gif/f" + num + ".png";
                    sb.setSpan(
                            new ImageSpan(context, BitmapFactory
                                    .decodeStream(context.getAssets().open(png))), sb.length()
                                    - tempText.length(), sb.length(),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

              } else {
                    String gif = "face/gif/f" + num + ".gif";
                    *//**
                     * 如果open这里不抛异常说明存在gif，则显示对应的gif
                     * 否则说明gif找不到，则显示png
                     * *//*
                    InputStream is = context.getAssets().open(gif);

                    sb.setSpan(new AnimatedImageSpan(new AnimatedGifDrawable(is, new AnimatedGifDrawable
                    .UpdateListener() {
                                @Override
                                public void update() {
                                    gifTextView.postInvalidate();
                                }
                            })), m.start(), m.end(),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    is.close();
                }*/
                } catch (Exception e) {
                    String png = tempText.substring("#[".length(), tempText.length() - "]#".length());
                    try {
                        sb.setSpan(new ImageSpan(context, BitmapFactory.decodeStream(context.getAssets().open(png))), m
                                .start(), m.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    } catch (IOException e1) {

                        e1.printStackTrace();
                    }
                    e.printStackTrace();
                }
            }
            return sb;
        }

        // context menu
        private static final int menu_copy = 101;
        private static final int menu_delete = 102;
        private static final int menu_chehui = 103;
        public String selid = "-1";
        public View view = null;
        private View.OnCreateContextMenuListener mListViewContextMenuListener = new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                selid = v.getTag() + "";
                view = v;
                String s[] = selid.split("\\$");
                if (s[s.length - 1].equals("1")) {
                    addMenuItem(menu, menu_copy, 0, R.string.copy);
                    addMenuItem(menu, menu_delete, 0, R.string.delete);
                    addMenuItem(menu, menu_chehui, 0, R.string.chehui);

                } else if (s[s.length - 1].equals("2")) {
//                addMenuItem(menu, menu_copy, 0, R.string.copy); //添加菜单(菜单,id,排序序号,名字) 多个会出现滚动条
                    addMenuItem(menu, menu_delete, 0, R.string.delete);
                    //addMenuItem(menu, menu_copy, 0, R.string.copy);
                } else if (s[s.length - 1].equals("3")) {
                    addMenuItem(menu, menu_copy, 0, R.string.copy);
                    addMenuItem(menu, menu_delete, 0, R.string.delete);

                } else {
                    addMenuItem(menu, menu_delete, 0, R.string.delete);
                    addMenuItem(menu, menu_chehui, 0, R.string.chehui);
                }
            }
        };

        private void addMenuItem(Menu menu, int itemId, int order, int string) {
            addMenuItem(menu, itemId, order, string, -1);
        }

        private void addMenuItem(Menu menu, int itemId, int order, int string, int iconRes) {
            MenuItem item = menu.add(0, itemId, order, string).setOnMenuItemClickListener(menuItemClick);
            if (iconRes > 0) {
                item.setIcon(iconRes);
            }
        }

        private MenuItem.OnMenuItemClickListener menuItemClick = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                int itemId = item.getItemId();
                String obj[] = selid.split("\\$");
                switch (itemId) {
                    case menu_copy:
                        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                        cm.setText(lst.get(Integer.parseInt(obj[0])).getMsg());
                        break;
                    case menu_delete:
                        MessageDao.clearChatDataById(obj[1]);
                        lst.remove(Integer.parseInt(obj[0]));
                        setIsdelete(true);
                        notifyDataSetChanged();
                        break;
                    case menu_chehui:
                        String content = MqttInstance.getInstance().getPushInterface().recallMessage(obj[1]);
                        Converse converse = new Gson().fromJson(content, Converse.class);
                        if (converse.getStatusCode().equals("200")) {
                            Map map = (Map) converse.getData();
                            Object ob = map.get("status").toString();
                            if (ob != null && ob.toString().equals("fail")) {
                                String errinfo = map.get("errinfo").toString();
                                Toast.makeText(context, errinfo, Toast.LENGTH_SHORT).show();
                            } else {
                                MessageDao.recallMsg(obj[1]);
                            }
                        }
                        break;
                    default:
                        return false;
                }
                return true;
            }
        };
    }

    String path;

    private void setPercent(final Uri uri, final String messageId, final String
            groupId) {
        Log.i("Uri", uri.toString());
        if (uri.toString().startsWith("content://")) {
            ContentResolver cr = context.getContentResolver();
            Cursor cursor = cr.query(uri, null, null, null, null);
            cursor.moveToFirst();
            path = cursor.getString(1);
        } else {
            path = uri.toString().replace("file://", "");
        }
        if (Utils.getFileSize(path) > 100 * 1024) {
            Bitmap bitmap = ViewUtil.getSmallBitmap(path);
            String name = DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
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
            path = filename;
        }

        new Thread() {
            @Override
            public void run() {
                String jsessionid = getSharedPreferences("kiway", 0).getString("jsessionid", "");
                String ret = UploadUtil.uploadFile(new File(path), "http://www.yuertong.com/yjpts/course/file", "image", "JSESSIONID=" + jsessionid);
                Log.d("test", "upload ret = " + ret);
                try {
                    String serverUrl = new JSONObject(ret).getJSONObject("data").getString("url");
                    String key = MqttInstance.getInstance().getPushInterface().sendMessage(groupId,
                            "{\"msg\":\"" + serverUrl + "\",\"type\":\"image\"}", "2");
                    Converse converse1 = new Gson().fromJson(key, Converse.class);
                    Map map1 = (Map) converse1.getData();
                    MessageDao.sendSuccess(messageId, map1.get("msgid").toString().replace(".0", ""), serverUrl);
                } catch (Exception e) {
                    e.printStackTrace();
                    MessageDao.sendFailure(messageId, uri.toString());
                }
            }
        }.start();
//            OkhttpUtils.upLoadImg(ChatActivity.this, path, new PercentListener() {
//                @Override
//                public void onPercent(final String percent) {
//                    Log.i("onPercent", percent);
//                    viewHolder.imageView.setProgress(Integer.parseInt(percent));
//                    try {
//                        Thread.sleep(50);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    if (percent.equals("100")) {
//                        File file = new File(path);
//                        Log.i("获得文件大小Tag", file.length() + "");
//                        file.delete();
//                    }
//                }
//
//                @Override
//                public void onReponse(String response) {
//                    try {
//                        Converse converse = new Gson().fromJson(response, Converse.class);
//                        if (converse.getStatusCode().equals("200")) {
//                            List list = (List) converse.getData();
//                            Map map = (Map) list.get(0);
//                            String key = MqttInstance.getInstance().getPushInterface().sendMessage(groupId,
//                                    "{\"msg\":\"" + map.get("url").toString() + "\",\"type\":\"image\"}", "2");
//                            Converse converse1 = new Gson().fromJson(key, Converse.class);
//                            Map map1 = (Map) converse1.getData();
//                            MessageDao.sendSuccess(messageId, map1.get("msgid").toString().replace(".0", ""), map.get("url")
//                                    .toString());
//                        } else {
//                            MessageDao.sendFailure(messageId, uri.toString());
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        MessageDao.sendFailure(messageId, uri.toString());
//                    }
//                }
//            });
    }

}
