package cn.kiway.mdm.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import cn.kiway.mdm.model.Fenxi;
import studentsession.kiway.cn.mdm_studentsession.R;


/**
 * Created by Administrator on 2018/1/18.
 */
public class FenxiActivity extends BaseActivity {

    private Button tab1;
    private Button tab2;
    private Button tab3;
    private Button tab4;

    private ListView listView;
    private FenxiAdapter adapter;
    private ArrayList<Fenxi> fenxis = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fenxi);

        initView();
        initListener();
        initData(1);
    }

    private void initListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //知识点没有详情。
                Fenxi fenxi = fenxis.get(position);
                startActivity(new Intent(FenxiActivity.this, FenxiDetailActivity.class));
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

        listView = (ListView) findViewById(R.id.list_view);
        adapter = new FenxiAdapter(this);
        listView.setAdapter(adapter);
    }

    public void clickTab1(View view) {
        tab1.setTextColor(Color.parseColor("#6699ff"));
        tab2.setTextColor(Color.parseColor("#000000"));
        tab3.setTextColor(Color.parseColor("#000000"));
        tab4.setTextColor(Color.parseColor("#000000"));
        initData(1);
    }


    public void clickTab2(View view) {
        tab1.setTextColor(Color.parseColor("#000000"));
        tab2.setTextColor(Color.parseColor("#6699ff"));
        tab3.setTextColor(Color.parseColor("#000000"));
        tab4.setTextColor(Color.parseColor("#000000"));
        initData(2);
    }

    public void clickTab3(View view) {
        tab1.setTextColor(Color.parseColor("#000000"));
        tab2.setTextColor(Color.parseColor("#000000"));
        tab3.setTextColor(Color.parseColor("#6699ff"));
        tab4.setTextColor(Color.parseColor("#000000"));
        initData(3);
    }

    public void clickTab4(View view) {
        tab1.setTextColor(Color.parseColor("#000000"));
        tab2.setTextColor(Color.parseColor("#000000"));
        tab3.setTextColor(Color.parseColor("#000000"));
        tab4.setTextColor(Color.parseColor("#6699ff"));
        initData(4);
    }

    private void initData(int tab) {
        //假数据
        Fenxi f1 = new Fenxi();
        fenxis.add(f1);
        Fenxi f2 = new Fenxi();
        fenxis.add(f2);
        Fenxi f3 = new Fenxi();
        fenxis.add(f3);
        Fenxi f4 = new Fenxi();
        fenxis.add(f4);
        Fenxi f5 = new Fenxi();
        fenxis.add(f5);

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

                convertView.setTag(holder);
            } else {
                holder = (FileHolder) convertView.getTag();
            }

            //知识点没有详情


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
        }
    }

}
