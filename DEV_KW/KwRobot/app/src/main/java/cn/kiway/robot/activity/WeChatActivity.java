package cn.kiway.robot.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.easy.wtool.sdk.MessageEvent;
import com.easy.wtool.sdk.OnMessageListener;
import com.easy.wtool.sdk.OnTaskEndListener;
import com.easy.wtool.sdk.TaskEndEvent;
import com.easy.wtool.sdk.WToolSDK;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import cn.kiway.robot.R;
import cn.kiway.robot.db.MyDBHelper;
import cn.kiway.robot.moment.SnsStat;
import cn.kiway.robot.moment.Task;
import cn.kiway.robot.moment.common.Share;
import cn.kiway.robot.util.WxUtils;

import static cn.kiway.robot.hook.TencentLocationManagerHook.XSPlatitude;
import static cn.kiway.robot.hook.TencentLocationManagerHook.XSPlongitude;
import static com.loopj.android.http.AsyncHttpClient.LOG_TAG;

/**
 * Created by Administrator on 2018/4/3.
 */

public class WeChatActivity extends BaseActivity {
    public WToolSDK wToolSDK = new WToolSDK();
    JSONArray wxPeopleList = new JSONArray();
    JSONArray wxRoomList = new JSONArray();
    SnsStat snsStat = null;
    Task task = null;
    SharedPreferences sharedPreferences;
    EditText latitude, longitude;
    public static final String WX_ROOT_PATH = "/data/data/com.tencent.mm/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences(getPackageName() + "_preferences", Activity.MODE_WORLD_READABLE);
        task = new Task(this.getApplicationContext());
        setContentView(R.layout.activity_wechat);
        latitude = (EditText) findViewById(R.id.latitude);
        longitude = (EditText) findViewById(R.id.longitude);
        task.testRoot();
        wToolSDK.encodeValue("1");
        //String s = wToolSDK.init("9999", "757533D0860F8CC0590B510BE2374F48C5750673");临时
        String s = wToolSDK.init("19552766", "0505CD626951D9D63B72541B75BF82CF7E9F7673");//可用 有消息监听
        // String s = wToolSDK.init("17810096", "010112143ECFD10AC82DE363C837A7CBB45E0302");
        Log.e("zzx", s);

        wToolSDK.setOnMessageListener(new OnMessageListener() {
            @Override
            public void messageEvent(MessageEvent event) {
                JSONObject jsonTask = new JSONObject();
                try {//开发消息监听后这里做转发回去
                    jsonTask.put("type", 12);
                    jsonTask.put("taskid", System.currentTimeMillis());
                    jsonTask.put("content", new JSONObject());
                    jsonTask.getJSONObject("content").put("talker", event.getTalker());
                    jsonTask.getJSONObject("content").put("msgtype", event.getMsgType());
                    jsonTask.getJSONObject("content").put("msgid", new JSONObject(event.getContent()).getString
                            ("msgid"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //jsonTask.getJSONObject("content").put("timeout",-1);
                String content = wToolSDK.sendTask(jsonTask.toString());
                Log.e("---", content);
                Log.e(LOG_TAG, "messageEvent on message: " + event.getTalker() + "," + event.getContent());
            }
        });
        wToolSDK.setOnTaskEndListener(new OnTaskEndListener() {
            @Override
            public void taskEndEvent(TaskEndEvent taskEndEvent) {
                Log.e(LOG_TAG, taskEndEvent.getType() + "," + taskEndEvent.getContent() + "," + taskEndEvent
                        .getTaskId());
            }
        });
    }


    public void chageJinWei(View view) {
        sharedPreferences.edit().putString(XSPlatitude, latitude.getText().toString()).commit();
        sharedPreferences.edit().putString(XSPlongitude, longitude.getText().toString()).commit();
    }

    public void getFriend(View view) {
        //1.获取所有的好友
        getWxPeople();
    }

    public void message(View view) {
        try {
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(1);//个人
            jsonArray.put(2);//群聊
            jsonObject.put("talkertypes", jsonArray);//设置监听的消息来源
            jsonObject.put("froms", new JSONArray());//可以设置监听某个人或者群聊的消息  wxid roomid
            jsonArray = new JSONArray();
            jsonArray.put(1);//文字 设置监听的消息类型
            jsonArray.put(3);//图片
            jsonArray.put(34);//语音
            jsonArray.put(43);//视频
            jsonArray.put(49);//图文链接
            jsonArray.put(62);//视频
            jsonObject.put("msgtypes", jsonArray);
            jsonObject.put("msgfilters", new JSONArray());
            String result = wToolSDK.startMessageListener(jsonObject.toString());
            Log.e("----", result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void groupSendText(View view) {
        //群发文字、群发卡片给所有好友
        ArrayList<String> list = WxUtils.getCeWxLists();
        for (int i = 0; i < list.size(); i++) {
            sendWxMsg(1, list.get(i), "文字测试内容");
        }
    }

    public void groupSendImage(View view) {
        ArrayList<String> list = WxUtils.getCeWxLists();
        for (int i = 0; i < list.size(); i++) {
            sendWxMsg(2, list.get(i), "http://img2.imgtn.bdimg.com/it/u=2239146502,165013516&fm=27&gp=0.jpg");
        }
    }

    public void getFriendCircle(View view) {
        //1.获取所有好友的朋友圈
        //2.上报给易敏
        execRootCmd("chmod -R 777 " + WX_ROOT_PATH);
        ((Button) findViewById(R.id.wx)).setText(R.string.exporting_sns);
        ((Button) findViewById(R.id.wx)).setEnabled(false);
        new RunningTask().execute();
    }

    /**
     * 执行linux指令
     *
     * @param paramString
     */
    public void execRootCmd(String paramString) {
        try {
            Process localProcess = Runtime.getRuntime().exec("su");
            Object localObject = localProcess.getOutputStream();
            DataOutputStream localDataOutputStream = new DataOutputStream((OutputStream) localObject);
            String str = String.valueOf(paramString);
            localObject = str + "\n";
            localDataOutputStream.writeBytes((String) localObject);
            localDataOutputStream.flush();
            localDataOutputStream.writeBytes("exit\n");
            localDataOutputStream.flush();
            localProcess.waitFor();
            localObject = localProcess.exitValue();
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    class RunningTask extends AsyncTask<Void, Void, Void> {

        Throwable error = null;

        @Override
        protected Void doInBackground(Void... params) {
            try {
                task.copySnsDB();
                task.initSnsReader();
                task.snsReader.run();
                snsStat = new SnsStat(task.snsReader.getSnsList());
            } catch (Throwable e) {
                this.error = e;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void voidParam) {
            super.onPostExecute(voidParam);
            ((Button) findViewById(R.id.wx)).setText(R.string.launch);
            ((Button) findViewById(R.id.wx)).setEnabled(true);
            if (this.error != null) {
                Toast.makeText(WeChatActivity.this, R.string.not_rooted, Toast.LENGTH_LONG).show();
                Log.e("wechatmomentstat", "exception", this.error);
                return;
            }
            Share.snsData = snsStat;
            Intent intent = new Intent(WeChatActivity.this, MomentListActivity.class);
            startActivity(intent);
        }
    }

    private void getWxPeople() {
        try {
            JSONObject jsonTask = new JSONObject();
            jsonTask.put("type", 5);
            jsonTask.put("taskid", System.currentTimeMillis());
            jsonTask.put("content", new JSONObject());
            jsonTask.getJSONObject("content").put("pageindex", 0);
            jsonTask.getJSONObject("content").put("pagecount", 0);
            String content = wToolSDK.sendTask(jsonTask.toString());
            JSONObject jsonObject = new JSONObject(content);

            if (jsonObject.getInt("result") == 0) {
                wxPeopleList = jsonObject.getJSONArray("content");
                for (int i = 0; i < wxPeopleList.length(); i++) {
                    JSONObject item = wxPeopleList.getJSONObject(i);
                    String nickname = wToolSDK.decodeValue(item.getString("nickname"));//解码
                    String remark = wToolSDK.decodeValue(item.getString("remark"));
                    String wxid = wToolSDK.decodeValue(item.getString("wxid"));
                    String wxno = wToolSDK.decodeValue(item.getString("wxno"));
                    item.put("nickname", nickname);//昵称
                    item.put("remark", remark);//备注
                    item.put("wxid", wxid);//微信id
                    item.put("wxno", wxno);//微信号
                    wxPeopleList.put(i, item);
                }
                new MyDBHelper(this).addWxPeople(wxPeopleList);
            }
            wxPeopleList = new MyDBHelper(this).getWxPeople();
            Log.e("getWxPeople", wxPeopleList.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getWxInfo(View view) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", 26);
            String content = wToolSDK.sendTask(jsonObject.toString());
            Log.e("getWxInfo", content);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void sendWxMsg(int type, String wxid, String content) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", type);
            jsonObject.put("taskid", System.currentTimeMillis());
            jsonObject.put("content", new JSONObject());
            jsonObject.getJSONObject("content").put("talker", wxid);
            jsonObject.getJSONObject("content").put("timeout", -1);
            if (type == 1) {//文字
                jsonObject.getJSONObject("content").put("text", wToolSDK.encodeValue(content));
            } else if (type == 2) {//图片
                jsonObject.getJSONObject("content").put("imagefile", content);//content为图片地址
            } else if (type == 3) {//语音
                jsonObject.getJSONObject("content").put("voicefile", content);//content未语音地址
                jsonObject.getJSONObject("content").put("duration", 60);
            } else if (type == 4) {//视频
                //  jsonObject.getJSONObject("content").put("videofile",toVideoFile);//视频地址
                //  jsonObject.getJSONObject("content").put("videothumbfile", toVideoThumbFile);//视频缩略图地址
                //  jsonObject.getJSONObject("content").put("duration",60);
            }
            String result = wToolSDK.sendTask(jsonObject.toString());
            Log.e("sendWxMsg", result);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        wToolSDK.unload();
    }
}
