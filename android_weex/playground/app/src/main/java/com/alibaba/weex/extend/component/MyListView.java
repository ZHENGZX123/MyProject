package com.alibaba.weex.extend.component;

/**
 * Created by Administrator on 2017/2/23.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.alibaba.weex.extend.view.Item;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.dom.WXDomObject;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;
import com.wjc.R;

import java.util.ArrayList;

public class MyListView extends WXComponent<ListView> {

    private ListView ListView;
    private MyAdapter adapter;
    private ArrayList<Item> items = new ArrayList<>();

    public MyListView(WXSDKInstance instance, WXDomObject dom, WXVContainer parent) {
        super(instance, dom, parent);
    }

    @Override
    protected ListView initComponentHostView(@NonNull Context context) {
        this.ListView = new ListView(context);
        this.adapter = new MyAdapter();
        this.ListView.setAdapter(this.adapter);
        this.ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("test", "onItemClick");
            }
        });
        return this.ListView;
    }

    @WXComponentProp(name = "data")
    public void setData(String data) {
        //((ListView) getHostView())
        //设置数据，解析数据，刷新界面
        Log.d("test", "data = " + data);
        items.clear();
        for (int i = 0; i < 100; i++) {
            items.add(new Item());
        }
        adapter.notifyDataSetChanged();
    }

    private class MyAdapter extends BaseAdapter {

        private final LayoutInflater inflater;

        public MyAdapter() {
            inflater = LayoutInflater.from(getContext());
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            ViewHolder holder;
            if (rowView == null) {
                rowView = inflater.inflate(R.layout.item_listview, null);
                holder = new ViewHolder();
                holder.iv = (ImageView) rowView.findViewById(R.id.iv);
                rowView.setTag(holder);
            } else {
                holder = (ViewHolder) rowView.getTag();
            }

            return rowView;
        }

        public class ViewHolder {
            public ImageView iv;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Item getItem(int arg0) {
            return items.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }
    }


}
