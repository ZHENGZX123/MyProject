package cn.kwim.mqttcilent.app_ui.home_school.group;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import cn.kiway.Yjpty.R;
import cn.kiway.utils.ViewUtil;
import cn.kwim.mqttcilent.BaseActivity;
import cn.kwim.mqttcilent.app_ui.dailog.CreateGroudDialog;
import cn.kwim.mqttcilent.app_ui.home_school.ChatActivity;
import cn.kwim.mqttcilent.common.cache.dao.DaoType;
import cn.kwim.mqttcilent.common.cache.dao.GroupMemberListDao;
import cn.kwim.mqttcilent.common.cache.dao.MainListDao;
import cn.kwim.mqttcilent.mqttclient.MqttInstance;
import cn.kwim.mqttcilent.mqttclient.MqttUtils;

/**
 * 群资料
 *
 * @author hmg
 */
public class GroupInformationActivity extends BaseActivity implements OnClickListener, CreateGroudDialog.CreateGroup {

    private RelativeLayout relative;
    private ImageView iv_back;
    private String groupId;
    private int sum;
    private TextView tv_groupnumber;
    private RelativeLayout rl_chatdata;
    private Button deleteGroup;
    CreateGroudDialog createGroudDialog;

    //    LoginDialog loginDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.im_activity_group_infomation);
        groupId = getIntent().getStringExtra(ChatActivity.GROUPID);
        if (getIntent().getStringExtra(ChatActivity.QUNORTAO).equals("1"))
            findViewById(R.id.deleteGroup).setVisibility(View.GONE);
        else {
            findViewById(R.id.deleteGroup).setVisibility(View.VISIBLE);
            findViewById(R.id.rl_groupName).setOnClickListener(this);
        }
        initView();
        sum = GroupMemberListDao.getGroupListMember(groupId).size();
        setBindView();
    }

    private void setBindView() {
        tv_groupnumber.setText(sum + "人");
    }

    public void initView() {
        relative = (RelativeLayout) findViewById(R.id.relative);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_groupnumber = (TextView) findViewById(R.id.tv_groupnumber);
        rl_chatdata = (RelativeLayout) findViewById(R.id.rl_chatdata);
        deleteGroup = (Button) findViewById(R.id.deleteGroup);
        relative.setOnClickListener(this);
        rl_chatdata.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        deleteGroup.setOnClickListener(this);
//        loginDialog=new LoginDialog(this);
        createGroudDialog = new CreateGroudDialog(this, this);
        ViewUtil.setContent(this, R.id.tv_groupName, getIntent().getStringExtra(ChatActivity.GROUPNAME));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    String s;

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.iv_back) {
            finish();
            overridePendingTransition(R.anim.im_slide_in_from_left, R.anim.im_slide_out_to_right);

        } else if (i == R.id.rl_chatdata) {
            Intent intentr = new Intent(GroupInformationActivity.this, ChatActivity.class);
            intentr.putExtra(ChatActivity.GROUPID, groupId);
            intentr.putExtra(ChatActivity.VTYPE, ChatActivity.CHATDATA);
            intentr.putExtra(ChatActivity.CHATTYPE, DaoType.SESSTIONTYPE.GROUP);
            startActivity(intentr);
            overridePendingTransition(R.anim.im_slide_in_from_right, R.anim.im_slide_out_to_left);

        } else if (i == R.id.relative) {
            Intent intent = new Intent(GroupInformationActivity.this, GroupMemberActivity.class);
            intent.putExtra(ChatActivity.GROUPID, groupId);
            startActivity(intent);
            overridePendingTransition(R.anim.im_slide_in_from_right, R.anim.im_slide_out_to_left);

        } else if (i == R.id.deleteGroup) {
            new AsyncTask<Object, Object, Object>() {
                @Override
                protected Object doInBackground(Object... objects) {
                    if (MqttInstance.getInstance() != null && MqttInstance.getInstance().getPushInterface() !=
                            null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                    loginDialog.setTitle("退出中...");
//                                    loginDialog.show();
                            }
                        });
                        s = MqttInstance.getInstance().getPushInterface().delGroup(groupId);
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Object o) {
                    if (s != null) {
                        try {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
//                                        loginDialog.dismiss();
                                }
                            });
                            JSONObject data = new JSONObject(s);
                            Log.e("JOSN", s);
                            if (data.optJSONObject("data").optString("status").equals("success")) {
                                MqttUtils.deleteGroup(groupId);
                                ViewUtil.showMessage(GroupInformationActivity.this, R.string.exit_success);
                                finishAllAct();
                                finish();
//                                    startActivity(MainActivity.class);
                            } else {
                                ViewUtil.showMessage(GroupInformationActivity.this, data.optJSONObject("data")
                                        .optString("errinfo"));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    super.onPostExecute(o);
                }
            }.execute(new Object());

        } else if (i == R.id.rl_groupName) {
            if (createGroudDialog != null && !createGroudDialog.isShowing())
                createGroudDialog.show();

        } else {
        }

    }

    @Override
    protected void onDestroy() {
        overridePendingTransition(R.anim.im_slide_in_from_left, R.anim.im_slide_out_to_right);
        super.onDestroy();
    }

    @Override
    public void createGroupCallBack(final String message, int viewId) throws Exception {
        new AsyncTask<Object, Object, Object>() {
            @Override
            protected Object doInBackground(Object... objects) {
                if (MqttInstance.getInstance() != null && MqttInstance.getInstance().getPushInterface() !=
                        null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            loginDialog.setTitle("修改中...");
//                            loginDialog.show();

                        }
                    });
                    JSONObject data = new JSONObject();
                    try {
                        data.put("groupid", groupId);
                        data.put("name", message);
                        data.put("icon", "");
                        data.put("notice", "");
                        data.put("intro", "");
                        data.put("ispublic", "");
                        data.put("isvalidate", "");
                        data.put("maxnum", "1000");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    s = MqttInstance.getInstance().getPushInterface().updateGroupData(data.toString());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                if (s != null) {
                    try {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                loginDialog.close();
                                createGroudDialog.dismiss();
                            }
                        });
                        JSONObject data = new JSONObject(s);
                        Log.e("JOSN", s);
                        if (data.optJSONObject("data").optString("status").equals("success")) {
                            ViewUtil.showMessage(GroupInformationActivity.this, data.optJSONObject("data")
                                    .optString("errinfo"));
                            new Thread() {
                                @Override
                                public void run() {
                                    MainListDao.saveGroupList(MqttInstance.getInstance().getPushInterface().getGroupList(), DaoType.SESSTIONTYPE.GROUP);
                                }
                            }.start();
                            finishAllAct();
                            finish();
//                            startActivity(MainActivity.class);
                        } else {
                            ViewUtil.showMessage(GroupInformationActivity.this, data.optJSONObject("data")
                                    .optString("errinfo"));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                super.onPostExecute(o);
            }
        }.execute(new Object());
    }
}
