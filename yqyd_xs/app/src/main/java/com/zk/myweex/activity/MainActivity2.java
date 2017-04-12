package com.zk.myweex.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.http.WXStreamModule;
import com.taobao.weex.utils.OfflineTask;
import com.taobao.weex.utils.WXDBHelper;
import com.zk.myweex.entity.TabEntity;
import com.zk.myweex.utils.MyDBHelper;
import com.zk.myweex.utils.ScreenManager;
import com.zk.myweex.utils.UploadUtil;
import com.zk.myweex.utils.Utils;
import com.zk.myweex.utils.VersionUpManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.kiway.baas.sdk.KWQuery;
import cn.kiway.baas.sdk.model.service.Service;
import cn.kiway.yiqiyuedu.R;

import static uk.co.senab.photoview.sample.ViewPagerActivity.getLoaderOptions;


public class MainActivity2 extends TabActivity {

    private TabHost tabhost;
    private LinearLayout bottom;
    private ArrayList<LinearLayout> lls = new ArrayList<>();
    public static MainActivity2 main;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ScreenManager.getScreenManager().pushActivity(this);
        main = this;
        Utils.checkNetWork(this);

        checkRemoteService();
        checkZipVersion();

        ArrayList<TabEntity> tabs = new MyDBHelper(getApplicationContext()).getAllTabEntity();
        Log.d("test", "main initView");
        tabs = new MyDBHelper(getApplicationContext()).getAllTabEntity();
        initView(tabs);

    }

    private void checkRemoteService() {
        new Thread() {
            @Override
            public void run() {
                List<Service> services = null;
                try {
                    services = new Service().find(new KWQuery().like("id", "yqydTab%"));
                    Log.d("test", "services count  = " + services.size());
                    for (Service s : services) {
                        Log.d("test", "service  = " + s.toString());
                        TabEntity tab = new TabEntity();
                        tab.idStr = s.get("id").toString();
                        tab.name = s.get("name").toString();
                        try {
                            tab.image_default = s.get("icon").toString();
                            tab.image_selected = s.get("iconActive").toString();
                        } catch (Exception e) {
                        }
                        new MyDBHelper(getApplicationContext()).updateTabEntity(tab);

                        String baseUrl = s.get("baseUrl").toString();
                        new MyDBHelper(getApplicationContext()).updateZipPackageBaseUrl(baseUrl, tab.idStr + ".zip");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void checkZipVersion() {
        new Thread() {
            @Override
            public void run() {
                VersionUpManager manager = new VersionUpManager();
                manager.init(getApplication());
                manager.getLocalVersion();
                manager.getRemoteVersion();
            }
        }.start();
    }

    private void initView(final ArrayList<TabEntity> tabs) {
        if (tabs == null) {
            return;
        }
        int tabcount = tabs.size();
        if (tabcount == 0) {
            return;
        }
        bottom = (LinearLayout) findViewById(R.id.bottom);
        bottom.setWeightSum(tabcount);
        tabhost = getTabHost();
        for (int i = 0; i < tabcount; i++) {
            final int ii = i;
            LayoutInflater inflater = LayoutInflater.from(this);
            LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.layout_tab, null);
            bottom.addView(ll, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
            lls.add(ll);
            Intent tab = new Intent(this, MyTabActivity.class);
            tab.putExtra("position", ii);
            tabhost.addTab(tabhost
                    .newTabSpec("tab" + ii)
                    .setIndicator("tab" + ii,
                            getResources().getDrawable(R.mipmap.ic_launcher))
                    .setContent(tab));
            ll.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    tabhost.setCurrentTab(ii);

                    refreshUI(ii, tabs);
                }
            });
        }
        refreshUI(0, tabs);
    }

    private void refreshUI(int position, ArrayList<TabEntity> tabs) {
        Log.d("test", "refreshUI = " + position);

        for (int i = 0; i < lls.size(); i++) {
            LinearLayout ll = lls.get(i);
            ImageView iv = (ImageView) ll.findViewById(R.id.iv);
            TextView tv = (TextView) ll.findViewById(R.id.tv);
            tv.setText(tabs.get(i).name);

            if (i == position) {
                tv.setTextColor(getResources().getColor(R.color.orange));
                ImageLoader.getInstance().displayImage(getTabImage(tabs, i, true), iv, getLoaderOptions());
            } else {
                tv.setTextColor(getResources().getColor(R.color.lightblack));
                ImageLoader.getInstance().displayImage(getTabImage(tabs, i, false), iv, getLoaderOptions());
            }
        }
    }

    private String getTabImage(ArrayList<TabEntity> tabs, int position, boolean select) {
        String url = "";
        if (select) {
            url = tabs.get(position).image_selected;
        } else {
            url = tabs.get(position).image_default;
        }
        if (url == null || url.equals("")) {
            if (select) {
                if (position == 0) {
                    url = "drawable://" + R.drawable.tab12;
                } else if (position == 1) {
                    url = "drawable://" + R.drawable.tab22;
                } else if (position == 2) {
                    url = "drawable://" + R.drawable.tab32;
                }
            } else {
                if (position == 0) {
                    url = "drawable://" + R.drawable.tab11;
                } else if (position == 1) {
                    url = "drawable://" + R.drawable.tab21;
                } else if (position == 2) {
                    url = "drawable://" + R.drawable.tab31;
                }
            }
        }
        return url;
    }

    protected void toast(final String id) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity2.this, id, Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    protected void toast(final int id) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity2.this, id, Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            RelativeLayout rl_nonet = (RelativeLayout) findViewById(R.id.rl_nonet);
            int arg1 = msg.arg1;
            if (arg1 == 0) {
                rl_nonet.setVisibility(View.VISIBLE);
            } else {
                rl_nonet.setVisibility(View.GONE);
                //2.check offline task
                checkOfflineTask();
            }
        }
    };

    private void checkOfflineTask() {
        new Thread() {
            @Override
            public void run() {
                ArrayList<OfflineTask> tasks = new WXDBHelper(getApplicationContext()).getAllOfflineTask();
                int count = tasks.size();
                Log.d("stream", "tasks count = " + count);
                for (OfflineTask task : tasks) {
                    Log.d("stream", "dotask , task.id = " + task.id);
                    if (task.request.contains("classes")) {
                        doQzqTask(task);
                    } else {
                        WXStreamModule stream = new WXStreamModule();
                        stream.mWXSDKInstance = new WXSDKInstance(getApplicationContext());
                        stream.fetch(task.request, null, null);
                    }
                }
            }
        }.start();
    }


    private void doQzqTask(final OfflineTask task) {
        Log.d("stream", "doQzqTask = " + task.request);
        //1.上传图片
        //2.成功后保存成数组，替换原数组
        //3.调用提交接口

        try {
            JSONObject obj = new JSONObject(task.request);
            String url = obj.getString("url");
            String jsessionid = obj.getString("jsessionid");
            String content = obj.getString("content");
            JSONArray img_url = obj.getJSONArray("img_url");
            String classes = obj.getString("classes");

            Log.d("stream", "before upload img_url = " + img_url.toString());
            int count = img_url.length();
            for (int i = 0; i < count; i++) {
                String filepagh = img_url.getString(i);
                File file = new File(filepagh.replace("file://", ""));
                String ret = UploadUtil.uploadFile(file, "http://www.yuertong.com/yjpts/course/file", "qzq", "JSESSIONID=" + jsessionid);
                Log.d("stream", "upload ret = " + ret);
                if (ret != null && ret.contains("200")) {
                    String imgUrl = new JSONObject(ret).getJSONObject("data").getString("url");
                    img_url.put(i, imgUrl);
                }
            }
            Log.d("stream", "after upload img_url = " + img_url.toString());
            //3.检查有没有上传失败的，只要有一个失败，就不用管了。。。
            boolean success = true;
            for (int i = 0; i < count; i++) {
                String filepagh = img_url.getString(i);
                if (filepagh.startsWith("file")) {
                    success = false;
                }
            }
            Log.d("stream", "success = " + success);
            if (!success) {
                return;
            }
            //call publish
            SyncHttpClient client = new SyncHttpClient();
            client.setTimeout(10000);
            client.addHeader("Cookie", "JSESSIONID=" + jsessionid);
            RequestParams params = new RequestParams();
            params.put("classes", classes);
            params.put("content", content);

            String temp = "";
            if (img_url.length() > 0) {
                for (int i = 0; i < img_url.length(); i++) {
                    temp += img_url.get(i).toString() + "#";
                }
                temp = temp.substring(0, temp.length() - 1);
            }

            params.put("img_url", temp);
            client.post(getApplicationContext(), url, params, new TextHttpResponseHandler() {

                public void onFailure(int statusCode, org.apache.http.Header[] headers, String responseString, Throwable throwable) {
                    Log.d("stream", "onFailure = " + responseString);
                }

                public void onSuccess(int statusCode, org.apache.http.Header[] headers, final String responseString) {
                    Log.d("stream", "onSuccess = " + responseString);
                    if (responseString != null && responseString.contains("200")) {
                        new WXDBHelper(getApplicationContext()).deleteOfflineTask(task.request);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickNetwork(View view) {
        startActivity(new Intent(this, NoNetActivity.class));
    }

    public void setCurrentTab(int tab) {
        tabhost.setCurrentTab(tab);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ScreenManager.getScreenManager().popActivity(this);
    }
}
