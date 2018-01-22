package com.android.kiway.activity;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.kiway.adapter.ChatMsgViewAdapter;
import com.android.kiway.entity.Call;
import com.android.kiway.entity.SMS;
import com.android.kiway.utils.HttpUtil;
import com.android.kiway.utils.MyDBHelper;
import com.android.launcher3.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author way
 */
public class SendSMSActivity extends BaseActivity implements OnClickListener {

    private TextView nameTV;
    private Button mBtnSend;// 发送btn
    private EditText mEditTextContent;
    private ListView mListView;
    private ChatMsgViewAdapter mAdapter;// 消息视图的Adapter
    private List<SMS> mDataArrays = new ArrayList<SMS>();// 消息对象数组
    private String phone;
    private String name;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sms);

        initView();
        initData();
        mListView.setSelection(mAdapter.getCount() - 1);
    }

    /**
     * 初始化view
     */
    public void initView() {
        nameTV = (TextView) findViewById(R.id.name);
        mListView = (ListView) findViewById(R.id.listview);
        mBtnSend = (Button) findViewById(R.id.btn_send);
        mBtnSend.setOnClickListener(this);
        mEditTextContent = (EditText) findViewById(R.id.et_sendmessage);

        phone = getIntent().getStringExtra("phone");
        name = getIntent().getStringExtra("name");
        if (TextUtils.isEmpty(name)) {
            nameTV.setText(phone);
        } else {
            nameTV.setText(name);
        }
    }

    /**
     * 模拟加载消息历史，实际开发可以从数据库中读出
     */
    public void initData() {
        ArrayList<SMS> temp = new MyDBHelper(this).getAllSMS(phone);
        Collections.reverse(temp);
        mDataArrays = temp;
        mAdapter = new ChatMsgViewAdapter(this, mDataArrays);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:
                send();
                break;
        }
    }

    /**
     * 发送消息
     */
    private void send() {
        String contString = mEditTextContent.getText().toString();
        if (contString.length() == 0) {
            return;
        }
        //0.判断是不是在去电白名单
        ArrayList<Call> calls = new MyDBHelper(this).getAllCalls(2);
        boolean in = false;
        for (Call c : calls) {
            if (c.phone.equals(phone)) {
                in = true;
                break;
            }
        }
        if (!in) {
            toast("该号码不在联系人白名单");
            return;
        }

        //1.发送消息
        sendSMS(phone, contString);

        //2.加入数据库
        SMS s = new SMS();
        s.phone = phone;
        s.content = contString;
        s.froms = 2;
        s.time = "" + System.currentTimeMillis();
        new MyDBHelper(this).addSMS(s);

        //3.刷新页面
        initData();
        mEditTextContent.setText("");
        mListView.setSelection(mListView.getCount() - 1);

        //4.记录请求
        HttpUtil.childOperation(this, "sendSms", "发送短信给" + phone);

    }

    public void refresh() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d("test", "refresh is called");
                initData();
                mListView.setSelection(mAdapter.getCount() - 1);
            }
        });
    }

    private static final String SENT_SMS_ACTION = "demo_sms_send_action";
    private static final String KEY_PHONENUM = "phone_num";

    public void sendSMS(String phoneNumber, String message) {
        //获取短信管理器
        android.telephony.SmsManager smsManager = android.telephony.SmsManager.getDefault();
        //拆分短信内容（手机短信长度限制）
        List<String> divideContents = smsManager.divideMessage(message);
        Intent itSend = new Intent(SENT_SMS_ACTION);
        itSend.putExtra(KEY_PHONENUM, phoneNumber);
        PendingIntent sentPI = PendingIntent.getBroadcast(getApplicationContext(), 0, itSend, PendingIntent.FLAG_UPDATE_CURRENT);
        for (String text : divideContents) {
            smsManager.sendTextMessage(phoneNumber, null, text, sentPI, null);
        }
    }
}