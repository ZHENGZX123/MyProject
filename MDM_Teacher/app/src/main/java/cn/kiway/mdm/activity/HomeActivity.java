package cn.kiway.mdm.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.kiway.mdm.WXApplication;
import cn.kiway.mdm.entity.Student;
import cn.kiway.mdm.teacher.R;


//首页：点名、上课
public class HomeActivity extends BaseActivity {

    private GridView gv;
    private MyAdapter adapter;
    private ArrayList<Student> students = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initView();
        initData();
        initListener();
    }

    public void initView() {
        super.initView();

        titleName.setText("二年级一班");

        gv = (GridView) findViewById(R.id.studentGV);
        adapter = new MyAdapter();
        gv.setAdapter(adapter);
    }

    private void initData() {
        try {
            String url = WXApplication.serverUrl + "/device/teacher/class/students";
            AsyncHttpClient client = new AsyncHttpClient();
            client.addHeader("x-auth-token", getSharedPreferences("kiway", 0).getString("accessToken", ""));
            client.setTimeout(10000);
            client.get(this, url, null, new TextHttpResponseHandler() {
                @Override
                public void onSuccess(int code, Header[] headers, String ret) {
                    Log.d("test", " onSuccess = " + ret);
                    try {
                        JSONArray data = new JSONObject(ret).optJSONArray("data");
                        students = new GsonBuilder().create().fromJson(data.toString(), new TypeToken<List<Student>>() {
                        }.getType());
                        adapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        Log.d("test", " onFailure = " + e.toString());
                    }
                }

                @Override
                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                    Log.d("test", " onFailure = " + s);
                    //TODO Utils.check301(HomeActivity.this, s, "students");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initListener() {
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Student s = students.get(position);

                toast(s.name + (s.status == 0 ? "没到" : "到了"));

            }
        });
    }

    private class MyAdapter extends BaseAdapter {

        private final LayoutInflater inflater;

        public MyAdapter() {
            inflater = LayoutInflater.from(HomeActivity.this);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            ViewHolder holder;
            if (rowView == null) {
                rowView = inflater.inflate(R.layout.item_student, null);
                holder = new ViewHolder();

                holder.name = (TextView) rowView.findViewById(R.id.name);
                holder.icon = (ImageView) rowView.findViewById(R.id.icon);

                rowView.setTag(holder);
            } else {
                holder = (ViewHolder) rowView.getTag();
            }

            final Student s = students.get(position);
            holder.name.setText(s.name);

            if (s.status == 0) {
                holder.icon.setImageResource(R.drawable.icon1);
            } else {
                holder.icon.setImageResource(R.drawable.icon2);
            }
            return rowView;
        }

        public class ViewHolder {
            public TextView name;
            public ImageView icon;
        }

        @Override
        public int getCount() {
            return students.size();
        }

        @Override
        public Student getItem(int arg0) {
            return students.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

    }

    public void dm(View view) {
        final Dialog dialog = new Dialog(this, R.style.popupDialog);
        dialog.setContentView(R.layout.dialog_dianming);
        dialog.show();

        Button dianming = (Button) dialog.findViewById(R.id.dianming);
        dianming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dianming.getText().toString().equals("开始点名")) {
                    dianming.setBackgroundResource(R.drawable.dianmingbutton2);
                    dianming.setText("结束点名");
                    //1.发送点名请求并实时刷新界面
//                    /device/push/teacher/sign/order


                } else {
                    //点名结束，跳到上课
                    dialog.dismiss();
                    startActivity(new Intent(HomeActivity.this, CourseListActivity.class));
                    finish();
                }
            }
        });
    }

}
