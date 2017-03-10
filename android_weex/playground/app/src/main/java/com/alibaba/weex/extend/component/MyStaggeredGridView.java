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
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.alibaba.weex.extend.view.Item;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.origamilabs.library.views.StaggeredGridView;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.dom.WXDomObject;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;
import com.wjc.R;

import java.util.ArrayList;

import static com.wjc.R.id.iv;

//未完成
public class MyStaggeredGridView extends WXComponent<StaggeredGridView> {

    private StaggeredGridView gridview;
    private MyAdapter adapter;
    private ArrayList<Item> items = new ArrayList<>();

    private String urls[] = {
            "http://farm7.staticflickr.com/6101/6853156632_6374976d38_c.jpg",
            "http://farm8.staticflickr.com/7232/6913504132_a0fce67a0e_c.jpg",
            "http://farm5.staticflickr.com/4133/5096108108_df62764fcc_b.jpg",
            "http://farm5.staticflickr.com/4074/4789681330_2e30dfcacb_b.jpg",
            "http://farm9.staticflickr.com/8208/8219397252_a04e2184b2.jpg",
            "http://farm9.staticflickr.com/8483/8218023445_02037c8fda.jpg",
            "http://farm9.staticflickr.com/8335/8144074340_38a4c622ab.jpg",
            "http://farm9.staticflickr.com/8060/8173387478_a117990661.jpg",
            "http://farm9.staticflickr.com/8056/8144042175_28c3564cd3.jpg",
            "http://farm9.staticflickr.com/8183/8088373701_c9281fc202.jpg",
            "http://farm9.staticflickr.com/8185/8081514424_270630b7a5.jpg",
            "http://farm9.staticflickr.com/8462/8005636463_0cb4ea6be2.jpg",
            "http://farm9.staticflickr.com/8306/7987149886_6535bf7055.jpg",
            "http://farm9.staticflickr.com/8444/7947923460_18ffdce3a5.jpg",
            "http://farm9.staticflickr.com/8182/7941954368_3c88ba4a28.jpg",
            "http://farm9.staticflickr.com/8304/7832284992_244762c43d.jpg",
            "http://farm9.staticflickr.com/8163/7709112696_3c7149a90a.jpg",
            "http://farm8.staticflickr.com/7127/7675112872_e92b1dbe35.jpg",
            "http://farm8.staticflickr.com/7111/7429651528_a23ebb0b8c.jpg",
            "http://farm9.staticflickr.com/8288/7525381378_aa2917fa0e.jpg",
            "http://farm6.staticflickr.com/5336/7384863678_5ef87814fe.jpg",
            "http://farm8.staticflickr.com/7102/7179457127_36e1cbaab7.jpg",
            "http://farm8.staticflickr.com/7086/7238812536_1334d78c05.jpg",
            "http://farm8.staticflickr.com/7243/7193236466_33a37765a4.jpg",
            "http://farm8.staticflickr.com/7251/7059629417_e0e96a4c46.jpg",
            "http://farm8.staticflickr.com/7084/6885444694_6272874cfc.jpg"
    };

    public MyStaggeredGridView(WXSDKInstance instance, WXDomObject dom, WXVContainer parent) {
        super(instance, dom, parent);
    }

    @Override
    protected StaggeredGridView initComponentHostView(@NonNull Context context) {
        this.gridview = new StaggeredGridView(context);
        //this.gridview.setNumColumns(4);//这种属性应该暴露出来，给js调用
        this.gridview.setItemMargin(50);
        this.adapter = new MyAdapter();
        this.gridview.setAdapter(this.adapter);


//        this.gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Log.d("test", "onItemClick");
//            }
//        });

        return this.gridview;
    }

    @WXComponentProp(name = "data")
    public void setData(String data) {
        //((GridView) getHostView())
        //设置数据，解析数据，刷新界面
        Log.d("test", "data = " + data);
        items.clear();
        for (int i = 0; i < urls.length; i++) {
            Item item = new Item();
            item.url = urls[i];
            items.add(item);
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
                rowView = inflater.inflate(R.layout.item_gridview, null);
                holder = new ViewHolder();
                holder.iv = (ImageView) rowView.findViewById(iv);
                rowView.setTag(holder);
            } else {
                holder = (ViewHolder) rowView.getTag();
            }

            Item i = items.get(position);
            ImageLoader.getInstance().displayImage(i.url, holder.iv);

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
