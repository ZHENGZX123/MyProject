package cn.kiway.mdm.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.xutils.common.util.DensityUtil;

import cn.kiway.mdm.KWApplication;
import cn.kiway.mdm.teacher.R;

/**
 * Created by Administrator on 2018/1/17.
 */
//班级测评统计
public class TongjiActivity2 extends BaseActivity {

    private TextView rate;
    private TextView people1;
    private TextView people2;
    private TextView people3;

    private View color1;
    private View color2;
    private View color3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tongji2);
        initView();
        initData();
    }

    @Override
    public void initView() {
        super.initView();

        titleName.setText("知识点统计结果");
        rate = (TextView) findViewById(R.id.rate);
        people1 = (TextView) findViewById(R.id.people1);
        people2 = (TextView) findViewById(R.id.people2);
        people3 = (TextView) findViewById(R.id.people3);

        color1 = findViewById(R.id.color1);
        color2 = findViewById(R.id.color2);
        color3 = findViewById(R.id.color3);
    }

    private void initData() {
        showPD();
        try {
            String url = KWApplication.clientUrl + "/device/teacher/course/attend?currentPage=1&pageSize=1024";
            AsyncHttpClient client = new AsyncHttpClient();
            client.addHeader("x-auth-token", getSharedPreferences("kiway", 0).getString("accessToken", ""));
            client.setTimeout(10000);
            client.get(this, url, null, new TextHttpResponseHandler() {
                @Override
                public void onSuccess(int code, Header[] headers, String ret) {
                    Log.d("test", " onSuccess = " + ret);
                    hidePD();
                    setData(8, 10, 12);
                }

                @Override
                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                    Log.d("test", " onFailure = " + s);
                    hidePD();
//                    if (!Utils.check301(TongjiActivity2.this, s, "")) {
//                        toast("请求失败，请稍后再试");
//                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            toast("请求失败，请稍后再试");
        }
    }

    private void setData(int people1, int people2, int people3) {
        int total = people1 + people2 + people3;
        float rate1 = (float) people1 / total;
        float rate2 = (float) people2 / total;
        float rate3 = (float) people3 / total;

        int totalHeight = DensityUtil.dip2px(200);
        int height1 = (int) (totalHeight * rate1);
        int height2 = (int) (totalHeight * rate2);
        int height3 = (int) (totalHeight * rate3);

        color1.getLayoutParams().height = height1;
        color2.getLayoutParams().height = height2;
        color3.getLayoutParams().height = height3;

        this.people1.setText(people1 + "人");
        this.people1.setText(people2 + "人");
        this.people1.setText(people3 + "人");
    }

}
