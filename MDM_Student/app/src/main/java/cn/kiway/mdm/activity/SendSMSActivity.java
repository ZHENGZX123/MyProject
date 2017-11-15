package cn.kiway.mdm.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.kiway.mdm.R;
import cn.kiway.mdm.adapter.ChatMsgViewAdapter;
import cn.kiway.mdm.entity.SMS;
import cn.kiway.mdm.utils.MyDBHelper;

/**
 * @author way
 */
public class SendSMSActivity extends Activity implements OnClickListener {

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

        initView();// 初始化view
        initData();// 初始化数据
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
        mDataArrays.addAll(new MyDBHelper(this).getAllSMS(phone));
        mAdapter = new ChatMsgViewAdapter(this, mDataArrays);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:// 发送按钮点击事件
                send();
                break;
        }
    }

    /**
     * 发送消息
     */
    private void send() {
        String contString = mEditTextContent.getText().toString();
        if (contString.length() > 0) {
            SMS s = new SMS();
            s.phone = phone;
            s.content = contString;
            s.froms = 2;
            s.time = "" + System.currentTimeMillis();
            new MyDBHelper(this).addSMS(s);

            initData();
            mEditTextContent.setText("");// 清空编辑框数据
            mListView.setSelection(mListView.getCount() - 1);// 发送一条消息时，ListView显示选择最后一项
        }
    }

    public void refresh() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                initData();
            }
        });
    }
}