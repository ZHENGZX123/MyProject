package cn.kiway.mdm.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.pulltorefreshlib.PullToRefreshBase;
import com.itheima.pulltorefreshlib.PullToRefreshListView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import cn.kiway.mdm.model.FileModel;
import cn.kiway.mdm.utils.FileUtils;
import cn.kiway.mdm.utils.HttpDownload;
import cn.kiway.mdm.utils.Logger;
import cn.kiway.mdm.utils.Utils;
import studentsession.kiway.cn.mdm_studentsession.R;

import static cn.kiway.mdm.dialog.MyProgressDialog.downPath;
import static cn.kiway.mdm.utils.HttpUtil.getMyFile;
import static studentsession.kiway.cn.mdm_studentsession.R.id.sender;
import static studentsession.kiway.cn.mdm_studentsession.R.id.size;

/**
 * Created by Administrator on 2017/11/16.
 */

public class FileListActivity extends BaseActivity {

    private PullToRefreshListView listView;
    private FileAdapter adapter;
    private ArrayList<FileModel> files = new ArrayList<>();
    private Button tab1;
    private Button tab2;
    int Page = 1; //当前请求的页数
    int totalPage;//总的页数
    int totalRecord;//总的数据
    boolean isClear = true;

    int tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_list);
        initView();
        initListener();
        Page = 1;
        getDataFromServer(1);
    }


    private void getDataFromServer(final int tag) {
        // initData(type);
        this.tag = tag;
        try {
            AsyncHttpClient client = new AsyncHttpClient();
            client.addHeader("x-auth-token", getSharedPreferences("kiway", 0).getString("x-auth-token", ""));
            client.setTimeout(10000);
            RequestParams param = new RequestParams();
            Log.d("test", "param = " + param.toString());
            String url = getMyFile + tag + "&currentPage=" + Page;//截图资料;
            Logger.log(url);
            client.get(this, url, param, new
                    TextHttpResponseHandler() {
                        @Override
                        public void onSuccess(int code, Header[] headers, String ret) {
                            Log.e("test", "calls onSuccess = " + ret);
                            listView.onRefreshComplete();
                            try {
                                JSONObject data = new JSONObject(ret);
                                if (data.optInt("statusCode") == 201) {
                                    Toast.makeText(FileListActivity.this, data.optString("errorMsg"), Toast
                                            .LENGTH_SHORT).show();
                                } else if (data.optInt("statusCode") == 200) {
                                    totalPage = data.optJSONObject("data").optInt("totalPage");
                                    totalRecord = data.optJSONObject("data").optInt("totalRecord");
                                    JSONArray array = data.optJSONObject("data").optJSONArray("list");
                                    initData(array);
                                }
                                if (adapter.getCount() > 0) {
                                    listView.setVisibility(View.VISIBLE);
                                    findViewById(R.id.no_data).setVisibility(View.GONE);
                                } else {
                                    listView.setVisibility(View.GONE);
                                    findViewById(R.id.no_data).setVisibility(View.VISIBLE);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int i, Header[] headers, String ret, Throwable throwable) {
                            Log.d("test", "calls onFailure = " + ret);
                            Logger.log("::::::::::::onFailure" + ret);
                            if (ret != null && !ret.equals("")) {
                                try {
                                    JSONObject data = new JSONObject(ret);
                                    if (data.optInt("statusCode") != 200) {
                                        Utils.login(FileListActivity.this, new Utils.ReLogin() {
                                            @Override
                                            public void onSuccess() {
                                                getDataFromServer(tag);
                                            }

                                            @Override
                                            public void onFailure() {
                                                listView.onRefreshComplete();
                                            }
                                        });
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                toast("请求失败，请稍后再试");
                                listView.onRefreshComplete();
                            }
                        }
                    });
        } catch (Exception e) {
            Log.d("test", "e = " + e.toString());
        }
    }

    private void initData(JSONArray array) {
        if (isClear)
            files.clear();
        for (int i = 0; i < array.length(); i++) {
            FileModel fm = new FileModel();
            JSONObject item = array.optJSONObject(i);
            fm.setSize(item.optString("size"));
            fm.setId(item.optString("id"));
            fm.setUserId(item.optString("userId"));
            fm.setUrl(item.optString("url"));
            fm.setType(item.optString("type"));
            fm.setCreateDate(item.optString("createDate"));
            fm.setName(item.optString("name"));
            fm.setUserName(item.optString("userName"));
            files.add(fm);
        }
        adapter.notifyDataSetChanged();
    }

    private void initListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final FileModel fm = files.get(i - 1);
                final String filepath = downPath + fm.name;
                File f = new File(filepath);
                if (!f.exists()) {
                    final ProgressDialog progressDialog = new ProgressDialog(FileListActivity.this);
                    progressDialog.setMessage("正在打开中，请稍后");
                    progressDialog.show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            int ret = new HttpDownload().downFile(fm.url, downPath, fm.name);//开始下载
                            Log.d("test", "download ret = " + ret);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.dismiss();
                                }
                            });
                            if (ret == -1) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        toast("文件不存在");
                                    }
                                });
                            } else {
                                FileUtils.openFile(FileListActivity.this, filepath);
                            }
                        }
                    }).start();
                } else {
                    //TODO可以使用X5来完成
                    FileUtils.openFile(FileListActivity.this, filepath);
                }
            }
        });
    }

    @Override
    public void initView() {
        super.initView();
        titleName.setText("我的文件");
        tab1 = (Button) findViewById(R.id.tab1);
        tab2 = (Button) findViewById(R.id.tab2);
        listView = (PullToRefreshListView) findViewById(R.id.list_view);
        listView.setMode(PullToRefreshBase.Mode.BOTH);
        //设置刷新监听
        listView.setOnRefreshListener(mListViewOnRefreshListener2);
        adapter = new FileAdapter(this);
        listView.setAdapter(adapter);
    }

    public void clickTab1(View view) {
        tab1.setTextColor(Color.parseColor("#6699ff"));
        tab2.setTextColor(Color.parseColor("#000000"));
        Page = 1;
        isClear = true;
        files.clear();
        adapter.notifyDataSetChanged();
        getDataFromServer(1);
    }

    public void clickTab2(View view) {
        tab1.setTextColor(Color.parseColor("#000000"));
        tab2.setTextColor(Color.parseColor("#6699ff"));
        Page = 1;
        isClear = true;
        files.clear();
        adapter.notifyDataSetChanged();
        getDataFromServer(2);
    }


    public class FileAdapter extends ArrayAdapter {
        FileHolder holder;

        public FileAdapter(@NonNull Context context) {
            super(context, -1);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                holder = new FileHolder();
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_file, null);
                holder.name = (TextView) convertView.findViewById(R.id.name);
                holder.time = (TextView) convertView.findViewById(R.id.time);
                holder.type = (ImageView) convertView.findViewById(R.id.type);
                holder.sender = (TextView) convertView.findViewById(sender);
                holder.size = (TextView) convertView.findViewById(size);
                convertView.setTag(holder);
            } else {
                holder = (FileHolder) convertView.getTag();
            }
            FileModel fm = files.get(position);
            holder.name.setText(fm.getName());
            holder.time.setText(Utils.getDateField(Long.parseLong(fm.getCreateDate()), 9));
            if (holder.size.equals("")) {
                holder.size.setText("未知");
            } else {
                holder.size.setText(fm.getSize());
            }
            if (tag == 2) {
                holder.sender.setText("来自: " + getSharedPreferences("kiwaykthd", 0).getString("studentName", ""));
            } else {
                holder.sender.setText("来自: " + fm.getUserName());
            }
            String suffix = fm.createDate.toLowerCase();
            if (suffix.endsWith("pdf")) {
                holder.type.setBackgroundResource(R.drawable.pdf);
            } else if (suffix.endsWith("doc") || suffix.endsWith("docx")) {
                holder.type.setBackgroundResource(R.drawable.doc);
            } else if (suffix.endsWith("ppt") || suffix.endsWith("pptx")) {
                holder.type.setBackgroundResource(R.drawable.ppt);
            } else {
                holder.type.setBackgroundResource(R.drawable.png);
            }
            return convertView;
        }

        @Override
        public int getCount() {
            return files.size();
        }

        public class FileHolder {
            TextView name, time, size, sender;
            ImageView type;
        }
    }

    private PullToRefreshBase.OnRefreshListener2<ListView> mListViewOnRefreshListener2 = new PullToRefreshBase
            .OnRefreshListener2<ListView>() {

        /**
         * 下拉刷新回调
         * @param refreshView
         */
        @Override
        public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
            Page = 1;
            isClear = true;
            getDataFromServer(tag);
        }

        /**
         * 上拉加载更多回调
         * @param refreshView
         */
        @Override
        public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
            if (Page >= totalPage) {
                Toast.makeText(FileListActivity.this, "已加载全部", Toast.LENGTH_SHORT).show();
                listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listView.onRefreshComplete();
                    }
                }, 1000);
                return;
            }
            Page++;
            isClear = false;
            getDataFromServer(tag);
        }
    };
}
