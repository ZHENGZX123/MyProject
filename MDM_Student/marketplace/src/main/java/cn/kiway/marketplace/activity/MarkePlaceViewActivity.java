package cn.kiway.marketplace.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.kiway.marketplace.R;
import cn.kiway.marketplace.db.MarkePlaceDBHelper;
import cn.kiway.marketplace.fragment.MarkePlaceFragment;
import cn.kiway.marketplace.util.MarketClassify;
import okhttp3.Call;

import static cn.kiway.marketplace.util.IUrContant.CALSSFIY_URL;

public class MarkePlaceViewActivity extends FragmentActivity {

    private ViewPager viewPager;
    FragmentPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marke_activity_mainview);
        loadData();
    }

    public void onDown(View view) {
        Intent intent = new Intent();
        intent.setClass(this, DownloadActivity.class);
        startActivity(intent);
    }

    public void onBefore(View view) {
        finish();
    }

    public void initContentView() {
        final ArrayList<MarketClassify> marketClassifies = new MarkePlaceDBHelper(this).getAllClassify();
        MarketClassify classify = new MarketClassify();
        classify.id = "";
        classify.name = "全部";
        marketClassifies.add(0, classify);
        Resources resources = getResources();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ResourcesCompat.getColor(resources, R.color.colorPrimary, getTheme()));
        }
        adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return new MarkePlaceFragment(marketClassifies.get(position));
            }

            @Override
            public int getCount() {
                return marketClassifies.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return marketClassifies.get(position).name;
            }
        };
        viewPager = (ViewPager) findViewById(R.id.download_viewpager);
        viewPager.setAdapter(adapter);
        ((TabLayout) findViewById(R.id.download_tabs)).setupWithViewPager(viewPager);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadData() {
        OkHttpUtils.get().url(CALSSFIY_URL).addParams("type", "appClassify").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                initContentView();
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject data = new JSONObject(response);
                    if (data.optInt("statusCode") == 200) {
                        ArrayList<MarketClassify> marketClassifies = new ArrayList<MarketClassify>();
                        JSONArray array = data.optJSONArray("data");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject item = array.optJSONObject(i);
                            MarketClassify classify = new MarketClassify();
                            classify.id = item.optString("id");
                            classify.name = item.optString("name");
                            classify.type = item.optString("type");
                            classify.description = item.optString("description");
                            classify.parentId = item.optString("parentId");
                            classify.hasSub = item.optString("hasSub");
                            classify.delFlag = item.optString("delFlag");
                            classify.startDate = item.optString("startDate");
                            classify.endDate = item.optString("endDate");
                            classify.createDate = item.optString("createDate");
                            classify.label = item.optString("label");
                            marketClassifies.add(classify);
                        }
                        new MarkePlaceDBHelper(MarkePlaceViewActivity.this).deleteAllClassify();
                        new MarkePlaceDBHelper(MarkePlaceViewActivity.this).addClassify(marketClassifies);
                    }
                    initContentView();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
