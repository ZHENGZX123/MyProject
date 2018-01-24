package cn.kiway.mdm.activity;

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

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

import java.io.File;
import java.util.ArrayList;

import cn.kiway.mdm.db.MyDBHelper;
import cn.kiway.mdm.model.FileModel;
import cn.kiway.mdm.utils.FileUtils;
import cn.kiway.mdm.utils.Utils;
import studentsession.kiway.cn.mdm_studentsession.R;

import static cn.kiway.mdm.App.clientUrl;

/**
 * Created by Administrator on 2017/11/16.
 */

public class FileListActivity extends BaseActivity {

    private ListView listView;
    private FileAdapter adapter;
    private ArrayList<FileModel> files = new ArrayList<>();
    private Button tab1;
    private Button tab2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_list);

        //假数据==改成易敏的，是用接口来做的。
        //new MyDBHelper(this).addFile(new FileModel("test1.pdf", "/mnt/sdcard/test1.pdf", "1516587513000", "李老师"));
        //new MyDBHelper(this).addFile(new FileModel("test2.doc", "/mnt/sdcard/test2.doc", "1516587513000", "陈老师"));
        //new MyDBHelper(this).addFile(new FileModel("test3.png", "/mnt/sdcard/test3.pdf", "1516587513000", "snapshot"));
        getDataFromServer();
        initView();
        initListener();
        initData(1);
    }

    private void getDataFromServer() {
        try {
            AsyncHttpClient client = new AsyncHttpClient();
            client.addHeader("x-auth-token", getSharedPreferences("kiway", 0).getString("x-auth-token", ""));
            client.setTimeout(10000);
            RequestParams param = new RequestParams();
            Log.d("test", "param = " + param.toString());
            String url = clientUrl + "/device/student/myScreenshotFile?type=1";
            Log.d("test", "myScreenshotFile url = " + url);
            client.get(this, url, param, new TextHttpResponseHandler() {
                @Override
                public void onSuccess(int code, Header[] headers, String ret) {
                    Log.d("test", "calls onSuccess = " + ret);
                }

                @Override
                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                    Log.d("test", "calls onFailure = " + s);
                    //check301(FileListActivity.this, s);
                }
            });
        } catch (Exception e) {
            Log.d("test", "e = " + e.toString());
        }
    }

    private void initData(int tab) {
        files.clear();
        ArrayList<FileModel> temp = new MyDBHelper(this).getFiles();
        for (FileModel fm : temp) {
            if (tab == 1 && !fm.sender.equals("snapshot")) {
                files.add(fm);
            } else if (tab == 2 && fm.sender.equals("snapshot")) {
                files.add(fm);
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void initListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FileModel fm = files.get(i);
                String filepath = fm.filepath;
                File f = new File(filepath);
                if (!f.exists()) {
                    toast("文件不存在");
                } else {
                    //TODO可以使用X5来完成
                    FileUtils.openFile(FileListActivity.this, fm.filepath);
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

        listView = (ListView) findViewById(R.id.list_view);

        adapter = new FileAdapter(this);
        listView.setAdapter(adapter);
    }

    public void clickTab1(View view) {
        tab1.setTextColor(Color.parseColor("#6699ff"));
        tab2.setTextColor(Color.parseColor("#000000"));
        initData(1);
    }

    public void clickTab2(View view) {
        tab1.setTextColor(Color.parseColor("#000000"));
        tab2.setTextColor(Color.parseColor("#6699ff"));
        initData(2);
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
                holder.sender = (TextView) convertView.findViewById(R.id.sender);
                holder.size = (TextView) convertView.findViewById(R.id.size);
                convertView.setTag(holder);
            } else {
                holder = (FileHolder) convertView.getTag();
            }
            FileModel fm = files.get(position);
            String filepath = fm.filepath;
            File f = new File(filepath);
            holder.name.setText(fm.filename);
            holder.time.setText(Utils.getDateField(Long.parseLong(fm.time), 9));
            if (!f.exists()) {
                holder.size.setText("未知");
            } else {
                holder.size.setText(FileUtils.GetFileSize(f));
            }
            String suffix = fm.filename.toLowerCase();
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

}
