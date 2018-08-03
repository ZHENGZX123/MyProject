package cn.kiway.robot.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import cn.kiway.robot.R;
import cn.kiway.robot.db.MyDBHelper;
import cn.kiway.robot.entity.Filter;
import cn.kiway.robot.util.Utils;

import static cn.kiway.robot.entity.Filter.TYPE_OTHER;

/**
 * Created by Administrator on 2018/3/27.
 */

public class FilterActivity extends BaseActivity {

    private ListView lv;
    private MyAdapter adapter;
    private ArrayList<Filter> filters = new ArrayList<>();

    private EditText et;
    private Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        initView();
        setListener();
        refresh();
    }

    private void initView() {
        lv = (ListView) findViewById(R.id.lv);
        adapter = new MyAdapter();
        lv.setAdapter(adapter);
        et = (EditText) findViewById(R.id.et);
        add = (Button) findViewById(R.id.add);
    }

    private void setListener() {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et.getText().toString().trim();
                if (name.equals("")) {
                    toast("不能为空");
                    return;
                }
                et.setText("");
                Utils.addFilter(FilterActivity.this, new Filter(name, "", TYPE_OTHER));
                refresh();
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FilterActivity.this);
                builder.setTitle("提示");
                builder.setMessage("是否删除该昵称");
                builder.setNegativeButton("取消", null);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int id = filters.get(position).id;
                        new MyDBHelper(FilterActivity.this).deleteFilter(id);
                        refresh();
                    }
                }).create().show();
                return true;
            }
        });
    }


    private void refresh() {
        filters = new MyDBHelper(this).getAllFilters(0);
        adapter.notifyDataSetChanged();
    }

    private class MyAdapter extends BaseAdapter {

        private final LayoutInflater inflater;

        public MyAdapter() {
            inflater = LayoutInflater.from(FilterActivity.this);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            ViewHolder holder;
            if (rowView == null) {
                rowView = inflater.inflate(R.layout.item_filter, null);
                holder = new ViewHolder();
                holder.name = (TextView) rowView.findViewById(R.id.name);
                rowView.setTag(holder);
            } else {
                holder = (ViewHolder) rowView.getTag();
            }
            String name = filters.get(position).name;
            holder.name.setText(name);
            return rowView;
        }

        public class ViewHolder {
            public TextView name;
        }

        @Override
        public int getCount() {
            return filters.size();
        }

        @Override
        public Filter getItem(int arg0) {
            return filters.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

    }

}
