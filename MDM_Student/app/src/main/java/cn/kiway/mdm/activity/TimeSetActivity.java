package cn.kiway.mdm.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.borax12.materialdaterangepicker.time.RadialPickerLayout;
import com.borax12.materialdaterangepicker.time.TimePickerDialog;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.kiway.mdm.R;
import cn.kiway.mdm.entity.InStallAllApp;
import cn.kiway.mdm.entity.TimeSet;
import cn.kiway.mdm.utils.Logger;
import cn.kiway.mdm.utils.MyDBHelper;
import cn.kiway.mdm.utils.Utils;

import static cn.kiway.mdm.KWApp.clientUrl;

/**
 * Created by Administrator on 2017/11/22.
 */

public class TimeSetActivity extends BaseActivity implements View.OnClickListener, TimePickerDialog.OnTimeSetListener {

    private String TAG = this.getClass().getSimpleName();
    //装在所有动态添加的Item的LinearLayout容器
    private LinearLayout addTimeView;
    InStallAllApp app;

    String sessionStartTime = "";
    String sessionEndTime = "";
    String startTime = "";
    String endTime = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic);
        app = (InStallAllApp) getIntent().getSerializableExtra("app");
        addTimeView = (LinearLayout) findViewById(R.id.ll_addView);
        findViewById(R.id.btn_getData).setOnClickListener(this);
        findViewById(R.id.btn_addTime).setOnClickListener(this);//默认添加一个Item
        addViewItem(null);
        getSessionTime();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_addTime://点击添加按钮就动态添加Item
                addViewItem(v);
                break;
            case R.id.btn_getData://打印数据
                printData();
                if (sessionEndTime == null || sessionStartTime == null || (sessionStartTime.equals(sessionEndTime))) {
                } else {
                    if (checkInTimes(startTime + ":00") || checkInTimes(endTime + ":00")) {
                        Toast.makeText(this, "不可设置在上课时间段", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                if (app.record == null || app.getRecord().optString("recordId").equals("")) {//提交新的
                    uploadAppTime();
                } else {//修改
                    upadateAppTime();
                }
                break;
        }
    }

    //startTime小于endTime
    public boolean checkInTimes(String nowTime) {
        DateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        try {
            Date dt1 = sdf.parse(sessionStartTime);
            Date dt2 = sdf.parse(sessionEndTime);
            Date dtNow = sdf.parse(nowTime);
            if (dtNow.getTime() > dt1.getTime() && dtNow.getTime() < dt2.getTime()) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Item排序
     */
    private void sortHotelViewItem() {
        //获取LinearLayout里面所有的view
        for (int i = 0; i < addTimeView.getChildCount(); i++) {
            final View childAt = addTimeView.getChildAt(i);
            final Button btn_remove = (Button) childAt.findViewById(R.id.btn_removeTime);
            final TextView txt_time = (TextView) childAt.findViewById(R.id.txt_time);

            if (app.record != null) {
                txt_time.setText("可用时段:" + app.getRecord().optString("startTime") + "-" + app.getRecord().optString
                        ("endTime"));
                btn_remove.setText("删除");
                btn_remove.setTag("remove");//设置删除标记
                btn_remove.setVisibility(View.VISIBLE);
            } else {
                txt_time.setText("请选择可用时间");
                btn_remove.setVisibility(View.GONE);
            }
            btn_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //从LinearLayout容器中删除当前点击到的ViewItem
                    //  addTimeView.removeView(childAt);
                    txtItem = txt_time;
                    deleteTime();
                }
            });
            txt_time.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    txtItem = txt_time;
                    Calendar now = Calendar.getInstance();
                    TimePickerDialog tpd = TimePickerDialog.newInstance(
                            TimeSetActivity.this,
                            now.get(Calendar.HOUR_OF_DAY),
                            now.get(Calendar.MINUTE),
                            false
                    );
                    tpd.show(getFragmentManager(), "Timepickerdialog");
                }
            });
        }
    }

    TextView txtItem;

    //添加ViewItem
    private void addViewItem(View view) {
        View hoteladdTimeView = View.inflate(this, R.layout.item_hotel_evaluate, null);
        addTimeView.addView(hoteladdTimeView);
        sortHotelViewItem();
    }

    //获取所有动态添加的Item，找到控件的id，获取数据
    private void printData() {
        TimeSet timeSet = new TimeSet();
        timeSet.packageName = app.packages;
        JSONArray array = new JSONArray();
        for (int i = 0; i < addTimeView.getChildCount(); i++) {
            View childAt = addTimeView.getChildAt(i);
            TextView time = (TextView) childAt.findViewById(R.id.txt_time);
            if (time.getText().toString().contains("-")) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("startTime", time.getText().toString().split("-")[0]);
                    jsonObject.put("endTime", time.getText().toString().split("-")[1]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                array.put(jsonObject);
            }
            Log.e(TAG, "时间设置：：：：" + time.getText().toString());
        }
        timeSet.times = array.toString();
        timeSet.ids = ids;
        new MyDBHelper(this).deleteTime(timeSet.packageName, app.packages);
        new MyDBHelper(this).addTime(timeSet);//存可用时间表
        try {
            JSONObject jsonObject = new JSONObject();//存已安装app列表
            jsonObject.put("recordId", ids);
            jsonObject.put("startTime", startTime);
            jsonObject.put("endTime", endTime);
            app.record = jsonObject.toString();
            new MyDBHelper(this).updateInstallApp(app);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int hourOfDayEnd, int minuteEnd) {
        String onstartTime, onendTime;
        onstartTime = String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute);
        onendTime = String.format("%02d", hourOfDayEnd) + ":" + String.format("%02d", minuteEnd);
        if (hourOfDayEnd < hourOfDay) {
            txtItem.setText(onendTime + " - " + onstartTime);
        } else if (hourOfDayEnd == hourOfDay) {
            if (minute < hourOfDay) {
                txtItem.setText(onstartTime + " - " + onendTime);
            } else {
                txtItem.setText(onendTime + " - " + onstartTime);
            }
        } else {
            txtItem.setText(onstartTime + " - " + onendTime);
        }
        startTime = onstartTime;
        endTime = onendTime;
    }

    public void Before(View view) {
        finish();
    }

    //获取学生在校时间
    private void getSessionTime() {
        new Thread() {
            @Override
            public void run() {
                try {//+ Utils.getIMEI(TimeSetActivity.this)
                    HttpGet httpRequest = new HttpGet(clientUrl +
                            "device/parent/student/stay/schooltimetable");
                    httpRequest.addHeader("x-auth-token", getSharedPreferences("kiway", 0).getString("x-auth-token",
                            ""));
                    DefaultHttpClient client = new DefaultHttpClient();
                    HttpResponse response = client.execute(httpRequest);
                    String ret = EntityUtils.toString(response.getEntity());
                    JSONObject data = new JSONObject(ret);
                    if (data.optInt("statusCode") == 200) {
                        sessionStartTime = data.optJSONObject("data").optString("start");
                        sessionEndTime = data.optJSONObject("data").optString("end");
                        if (!sessionEndTime.equals("00:00:00") && !sessionStartTime.equals("00:00:00"))
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ((TextView) findViewById(R.id.time)).setText("此时间段为学生上课时间，不可设置" +
                                            sessionStartTime + "-"
                                            + sessionEndTime);
                                }
                            });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    String ids = "";
    //上传时间
    public void uploadAppTime() {
        try {
            showPD();
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(10000);
            client.addHeader("x-auth-token", getSharedPreferences("kiway", 0).getString("x-auth-token",
                    ""));
            String url = clientUrl + "device/parent/app/control";
            Log.d("test", "url = " + url);
            RequestParams param = new RequestParams();
            param.add("imei", Utils.getIMEI(TimeSetActivity.this));
            param.add("startTime", startTime);
            param.add("endTime", endTime);
            param.add("packages", app.packages);
            param.add("id", app.ids);
            Log.d("test", "param = " + param.toString());
            client.post(this, url, param, new TextHttpResponseHandler() {

                @Override
                public void onSuccess(int arg0, Header[] arg1, String ret) {
                    dismissPD();
                    Log.d("test", "login onSuccess = " + ret);
                    String errorMsg = "";
                    try {
                        JSONObject o = new JSONObject(ret);
                        int StatusCode = o.optInt("StatusCode");
                        errorMsg = o.optString("errorMsg");
                        if (StatusCode == 200) {
                            //finish();
                            ids = o.optString("data");
                            printData();
                            toast("请求成功");
                        } else {
                            toast("请求失败：" + errorMsg);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        toast("请求失败：" + errorMsg);
                    }
                }

                @Override
                public void onFailure(int arg0, Header[] arg1, String ret, Throwable arg3) {
                    Log.d("test", "onFailure ret = " + ret);
                    dismissPD();
                    toast("请求失败，请稍后再试");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("test", "exception = " + e.toString());
            toast("请求失败，请稍后再试");
            dismissPD();
        }
    }
    //更新时间
    public void upadateAppTime() {
        try {
            showPD();
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(10000);
            client.addHeader("x-auth-token", getSharedPreferences("kiway", 0).getString("x-auth-token",
                    ""));
            String url = clientUrl + "device/parent/app/control";
            Log.d("test", "url = " + url);
            RequestParams param = new RequestParams();
            param.add("startTime", startTime);
            param.add("endTime", endTime);
            param.add("recordId", app.getRecord().optString("recordId"));
            Log.d("test", "param = " + param.toString());
            client.put(this, url, param, new TextHttpResponseHandler() {

                @Override
                public void onSuccess(int arg0, Header[] arg1, String ret) {
                    dismissPD();
                    Log.d("test", "login onSuccess = " + ret);
                    String errorMsg = "";
                    try {
                        JSONObject o = new JSONObject(ret);
                        int StatusCode = o.optInt("StatusCode");
                        errorMsg = o.optString("errorMsg");
                        if (StatusCode == 200) {
                            //finish();
                            ids = app.getRecord().optString("recordId");
                            printData();
                            toast("请求成功");
                        } else {
                            toast("请求失败：" + errorMsg);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        toast("请求失败：" + errorMsg);
                    }
                }

                @Override
                public void onFailure(int arg0, Header[] arg1, String ret, Throwable arg3) {
                    Log.d("test", "onFailure ret = " + ret);
                    dismissPD();
                    toast("请求失败，请稍后再试");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("test", "exception = " + e.toString());
            toast("请求失败，请稍后再试");
            dismissPD();
        }
    }
    //删除时间
    public void deleteTime() {
        try {
            showPD();
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(10000);
            client.addHeader("x-auth-token", getSharedPreferences("kiway", 0).getString("x-auth-token",
                    ""));
            String url = clientUrl + "device/parent/app/control/" + app.getRecord().optString("recordId");
            Logger.log("::::::" + url);
            client.delete(this, url, new TextHttpResponseHandler() {
                @Override
                public void onSuccess(int arg0, Header[] arg1, String ret) {
                    dismissPD();
                    Log.d("test", "login onSuccess = " + ret);
                    String errorMsg = "";
                    try {
                        JSONObject o = new JSONObject(ret);
                        int StatusCode = o.optInt("StatusCode");
                        errorMsg = o.optString("errorMsg");
                        if (StatusCode == 200) {
                            app.record = null;
                            new MyDBHelper(TimeSetActivity.this).deleteTime(app.getRecord().optString("recordId"), app
                                    .packages);
                            new MyDBHelper(TimeSetActivity.this).updateInstallApp(app);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    txtItem.setText("请选择时间范围");
                                }
                            });
                            toast("请求成功");
                        } else {
                            toast("请求失败：" + errorMsg);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        toast("请求失败：" + errorMsg);
                    }
                }

                @Override
                public void onFailure(int arg0, Header[] arg1, String ret, Throwable arg3) {
                    Log.d("test", "onFailure ret = " + ret);
                    dismissPD();
                    toast("请求失败，请稍后再试");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("test", "exception = " + e.toString());
            toast("请求失败，请稍后再试");
            dismissPD();
        }
    }
}
