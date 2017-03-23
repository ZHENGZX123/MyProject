package cn.kwim.mqttcilent.app_ui.home_school.group;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.kiway.Yjpty.R;
import cn.kwim.mqttcilent.BaseActivity;
import cn.kwim.mqttcilent.common.cache.dao.MainListDao;
import cn.kwim.mqttcilent.common.cache.javabean.MainList;
import io.realm.RealmResults;

/**
 * Created by Administrator on 2017/2/16.
 * 选择班级群添加群成员
 */

public class SelectClassGroupActivity extends BaseActivity {

    private ImageView iv_back;
    private ListView list;
    private RealmResults<MainList> dataList;
    public static final String GROUPID = "groupid";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.im_select_class_group);
        initView();
        dataList = MainListDao.getClassGroup();
        SelectAdapter adapter = new SelectAdapter();
        list.setAdapter(adapter);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MainList mainlist = dataList.get(i);
                Intent intent = new Intent(SelectClassGroupActivity.this, CreatePersonGroupActivity.class);
                intent.putExtra(GROUPID, mainlist.getId());
                startActivity(intent);
            }
        });
        if (adapter.getCount() <= 0) {
//            list.setVisibility(View.GONE);
//            findViewById(R.id.no_data).setVisibility(View.VISIBLE);
//            ViewUtil.setContent(this, R.id.no_data, "获取不到班级列表");
        } else {
            list.setVisibility(View.VISIBLE);
//            findViewById(R.id.no_data).setVisibility(View.GONE);
        }
    }

    public void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        list = (ListView) findViewById(R.id.listView);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    class SelectAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return dataList != null ? dataList.size() : 0;
        }

        @Override
        public Object getItem(int i) {
            return dataList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            MainList mainlist = dataList.get(i);
            ViewHolder viewholder = null;
            LayoutInflater minflater = LayoutInflater.from(SelectClassGroupActivity.this);
            if (viewholder == null) {
                viewholder = new ViewHolder();
                view = minflater.inflate(R.layout.im_select_class_item, null);
                viewholder.classname = (TextView) view.findViewById(R.id.names);
                viewholder.item = (RelativeLayout) view.findViewById(R.id.select_item);
                view.setTag(viewholder);
            } else {
                viewholder = (ViewHolder) view.getTag();
            }
            viewholder.classname.setText(mainlist.getName());

            return view;
        }

        class ViewHolder {
            TextView classname;
            RelativeLayout item;
        }
    }
}
