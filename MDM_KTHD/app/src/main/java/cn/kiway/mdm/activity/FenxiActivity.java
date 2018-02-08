package cn.kiway.mdm.activity;

import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.pulltorefreshlib.PullToRefreshListView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.kiway.mdm.model.Fenxi;
import cn.kiway.mdm.utils.Logger;
import cn.kiway.mdm.utils.Utils;
import studentsession.kiway.cn.mdm_studentsession.R;

import static cn.kiway.mdm.utils.HttpUtil.getMyClassAnalysis;


/**
 * Created by Administrator on 2018/1/18.
 */
public class FenxiActivity extends BaseActivity {

    private Button tab1;
    private Button tab2;
    private Button tab3;
    private Button tab4;

    private PullToRefreshListView listView;
    private FenxiAdapter adapter;
    private ArrayList<Fenxi> fenxis = new ArrayList<>();
    int tag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fenxi);

        initView();
        initListener();
        getDataFromServer(0);
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
            String url = getMyClassAnalysis + tag;//截图资料;
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
                                    Toast.makeText(FenxiActivity.this, data.optString("errorMsg"), Toast
                                            .LENGTH_SHORT).show();
                                } else if (data.optInt("statusCode") == 200) {
                                    JSONArray array = data.optJSONArray("data");
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
                            if (!ret.equals("")) {
                                try {
                                    JSONObject data = new JSONObject(ret);
                                    if (data.optInt("statusCode") != 200) {
                                        Utils.login(FenxiActivity.this, new Utils.ReLogin() {
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
                                listView.onRefreshComplete();
                            }
                        }
                    });
        } catch (Exception e) {
            Log.d("test", "e = " + e.toString());
        }
    }


    private void initListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //知识点没有详情。
                Fenxi fenxi = fenxis.get(position - 1);
                startActivity(new Intent(FenxiActivity.this, FenxiDetailActivity.class).putExtra("fenxi", fenxi));
            }
        });
    }

    @Override
    public void initView() {
        super.initView();
        titleName.setText("课堂分析");

        tab1 = (Button) findViewById(R.id.tab1);
        tab2 = (Button) findViewById(R.id.tab2);
        tab3 = (Button) findViewById(R.id.tab3);
        tab4 = (Button) findViewById(R.id.tab4);

        listView = (PullToRefreshListView) findViewById(R.id.list_view);
        adapter = new FenxiAdapter(this);
        listView.setAdapter(adapter);
    }

    public void clickTab1(View view) {
        tab1.setTextColor(Color.parseColor("#6699ff"));
        tab2.setTextColor(Color.parseColor("#000000"));
        tab3.setTextColor(Color.parseColor("#000000"));
        tab4.setTextColor(Color.parseColor("#000000"));
        getDataFromServer(0);
    }


    public void clickTab2(View view) {
        tab1.setTextColor(Color.parseColor("#000000"));
        tab2.setTextColor(Color.parseColor("#6699ff"));
        tab3.setTextColor(Color.parseColor("#000000"));
        tab4.setTextColor(Color.parseColor("#000000"));
        getDataFromServer(1);
    }

    public void clickTab3(View view) {
        tab1.setTextColor(Color.parseColor("#000000"));
        tab2.setTextColor(Color.parseColor("#000000"));
        tab3.setTextColor(Color.parseColor("#6699ff"));
        tab4.setTextColor(Color.parseColor("#000000"));
        getDataFromServer(2);
    }

    public void clickTab4(View view) {
        tab1.setTextColor(Color.parseColor("#000000"));
        tab2.setTextColor(Color.parseColor("#000000"));
        tab3.setTextColor(Color.parseColor("#000000"));
        tab4.setTextColor(Color.parseColor("#6699ff"));
        getDataFromServer(3);
    }

    private void initData(JSONArray array) {
        fenxis.clear();
        for (int i = 0; i < array.length(); i++) {
            Fenxi fenxi = new Fenxi();
            JSONObject item = array.optJSONObject(i);
            fenxi.setContent(item.optString("content"));
            fenxi.setId(item.optString("id"));
            fenxi.setStatus(item.optInt("status"));
            fenxi.setTime(item.optString("createDate"));
            fenxi.setType(item.optInt("type"));
            fenxis.add(fenxi);
        }

        adapter.notifyDataSetChanged();
    }

    public class FenxiAdapter extends ArrayAdapter {
        FileHolder holder;

        public FenxiAdapter(@NonNull Context context) {
            super(context, -1);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                holder = new FileHolder();
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_fenxi, null);
                holder.content = (TextView) convertView.findViewById(R.id.content);
                holder.type = (TextView) convertView.findViewById(R.id.type);
                holder.time = (TextView) convertView.findViewById(R.id.time);
                holder.detail = (TextView) convertView.findViewById(R.id.detail);
                holder.status = (ImageView) convertView.findViewById(R.id.status);
                convertView.setTag(holder);
            } else {
                holder = (FileHolder) convertView.getTag();
            }
            Fenxi fenxi = fenxis.get(position);
            //知识点没有详情
            holder.content.setText(fenxi.getContent());
            holder.time.setText(Utils.getDateField(Long.parseLong(fenxi.getTime()), 9));
            switch (fenxi.getType()) {
                case 0:
                    holder.type.setText("知识点");
                    break;
                case 1:
                    holder.type.setText("点名答");
                    break;
                case 2:
                    holder.type.setText("抢答");
                    break;
                case 3:
                    holder.type.setText("随机抽签");
                    break;
                case 4:
                    holder.type.setText("测评");
                    break;
            }
            if (fenxi.type == 0) {
                switch (fenxi.getStatus()) {
                    case 0:
                        holder.status.setBackground(getDrawable(R.drawable.u4620));
                        break;
                    case 1:
                        holder.status.setBackground(getDrawable(R.drawable.u4622));
                        break;
                    case 2:
                        holder.status.setBackground(getDrawable(R.drawable.u4624));
                        break;
                }
            } else {
                switch (fenxi.getStatus()) {
                    case 0:
                        holder.status.setBackground(getDrawable(R.drawable.u4632));
                        break;
                    case 1:
                        holder.status.setBackground(getDrawable(R.drawable.u4618));
                        break;
                    case 2:
                        holder.status.setBackground(getDrawable(R.drawable.u4624));
                        break;
                }
            }
            if (tag==2){
                holder.status.setVisibility(View.GONE);
            }else {
                holder.status.setVisibility(View.VISIBLE);
            }
            return convertView;
        }

        @Override
        public int getCount() {
            return fenxis.size();
        }

        public class FileHolder {
            public TextView content;
            public TextView type;
            public TextView time;
            public TextView detail;
            public ImageView status;
        }
    }

}
