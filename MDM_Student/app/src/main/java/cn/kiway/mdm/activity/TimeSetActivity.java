package cn.kiway.mdm.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.borax12.materialdaterangepicker.time.RadialPickerLayout;
import com.borax12.materialdaterangepicker.time.TimePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import cn.kiway.mdm.R;
import cn.kiway.mdm.entity.App;
import cn.kiway.mdm.entity.TimeSet;
import cn.kiway.mdm.utils.MyDBHelper;

/**
 * Created by Administrator on 2017/11/22.
 */

public class TimeSetActivity extends BaseActivity implements View.OnClickListener, TimePickerDialog.OnTimeSetListener {

    private String TAG = this.getClass().getSimpleName();
    //装在所有动态添加的Item的LinearLayout容器
    private LinearLayout addTimeView;
    App app;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic);
        app = (App) getIntent().getSerializableExtra("app");
        addTimeView = (LinearLayout) findViewById(R.id.ll_addView);
        findViewById(R.id.btn_getData).setOnClickListener(this);
        findViewById(R.id.btn_addTime).setOnClickListener(this);//默认添加一个Item
        addViewItem(null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_addTime://点击添加按钮就动态添加Item
                addViewItem(v);
                break;
            case R.id.btn_getData://打印数据
                printData();
                break;
        }
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
            btn_remove.setText("删除");
            btn_remove.setTag("remove");//设置删除标记
            btn_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //从LinearLayout容器中删除当前点击到的ViewItem
                    addTimeView.removeView(childAt);
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
        timeSet.packageName = app.packageName;
        JSONArray array = new JSONArray();
        for (int i = 0; i < addTimeView.getChildCount(); i++) {
            View childAt = addTimeView.getChildAt(i);
            TextView time = (TextView) childAt.findViewById(R.id.txt_time);
            JSONObject jsonObject=new JSONObject();
            try {
                jsonObject.put("startTime",time.getText().toString().split("-")[0]);
                jsonObject.put("endTime",time.getText().toString().split("-")[1]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            array.put(jsonObject);
            Log.e(TAG, "时间设置：：：：" + time.getText().toString());
        }
        timeSet.times = array.toString();
        new MyDBHelper(this).deleteTime(timeSet.packageName);
        new MyDBHelper(this).addTime(timeSet);
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int hourOfDayEnd, int minuteEnd) {
        String startTime, endTime;
        startTime = String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute);
        endTime = String.format("%02d", hourOfDayEnd) + ":" + String.format("%02d", minuteEnd);
        if (hourOfDayEnd < hourOfDay) {
            txtItem.setText(endTime + " - " + startTime);
        } else if (hourOfDayEnd == hourOfDay) {
            if (minute < hourOfDay) {
                txtItem.setText(startTime + " - " + endTime);
            } else {
                txtItem.setText(endTime + " - " + startTime);
            }
        } else {
            txtItem.setText(startTime + " - " + endTime);
        }
    }
}
