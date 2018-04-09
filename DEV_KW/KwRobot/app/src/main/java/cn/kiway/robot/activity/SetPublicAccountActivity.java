package cn.kiway.robot.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import cn.kiway.robot.R;

/**
 * Created by Administrator on 2018/4/9.
 */

public class SetPublicAccountActivity extends BaseActivity {

    private EditText forwardfromET;
    private EditText sendContentET;
    private TimePicker picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_publicaccount);
        forwardfromET = (EditText) findViewById(R.id.forwardfromET);
        String forwardfrom = getSharedPreferences("forwardfrom", 0).getString("forwardfrom", "wxid_cokkmqud47e121的接口测试号");
        forwardfromET.setText(forwardfrom);


        sendContentET = (EditText) findViewById(R.id.sendContentET);
        String sendContent = getSharedPreferences("sendContent", 0).getString("sendContent", "你好，请问客服在吗？");
        sendContentET.setText(sendContent);

        picker = (TimePicker) findViewById(R.id.picker);
        picker.setIs24HourView(true);
        int hour = getSharedPreferences("sendHour", 0).getInt("sendHour", 0);
        int minute = getSharedPreferences("sendMinute", 0).getInt("sendMinute", 0);
        picker.setCurrentHour(hour);
        picker.setCurrentMinute(minute);
    }

    public void clickSave(View view) {
        String forwardfrom = forwardfromET.getText().toString().trim();
        if (TextUtils.isEmpty(forwardfrom)) {
            toast("不能为空");
            return;
        }
        getSharedPreferences("forwardfrom", 0).edit().putString("forwardfrom", forwardfrom).commit();
        toast("设置成功");
    }

    public void clickSave2(View view) {
        String sendContent = sendContentET.getText().toString().trim();
        if (TextUtils.isEmpty(sendContent)) {
            toast("不能为空");
            return;
        }
        getSharedPreferences("sendContent", 0).edit().putString("sendContent", sendContent).commit();


        int hour = picker.getCurrentHour();
        int minute = picker.getCurrentMinute();
        Log.d("test", "hour = " + hour + " , minute = " + minute);
        getSharedPreferences("sendHour", 0).edit().putInt("sendHour", hour).commit();
        getSharedPreferences("sendMinute", 0).edit().putInt("sendMinute", minute).commit();

        toast("设置成功");
    }


}
