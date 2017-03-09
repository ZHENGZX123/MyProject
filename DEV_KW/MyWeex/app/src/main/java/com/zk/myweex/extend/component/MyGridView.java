package com.zk.myweex.extend.component;

/**
 * Created by Administrator on 2017/2/23.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.dom.WXDomObject;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;
import com.zk.myweex.R;
import com.zk.myweex.WXApplication;
import com.zk.myweex.activity.WXPageActivity;
import com.zk.myweex.entity.ZipPackage;
import com.zk.myweex.utils.HttpDownload;
import com.zk.myweex.utils.ServiceManager;

import java.io.File;
import java.util.ArrayList;

import cn.kiway.baas.sdk.KWQuery;
import cn.kiway.baas.sdk.model.service.Package;
import cn.kiway.baas.sdk.model.service.Service;
import io.realm.Realm;
import io.realm.RealmResults;

public class MyGridView extends WXComponent<GridView> {

    private String id;
    private GridView gridview;
    private MyAdapter adapter;
    private ArrayList<GridItem> items = new ArrayList<>();

    public MyGridView(WXSDKInstance instance, WXDomObject dom, WXVContainer parent) {
        super(instance, dom, parent);
    }

    @Override
    protected GridView initComponentHostView(@NonNull Context context) {
        this.gridview = new GridView(context);
        this.gridview.setNumColumns(4);//这种属性应该暴露出来，给js调用
        this.adapter = new MyAdapter();
        this.gridview.setAdapter(this.adapter);

        this.gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("test", "onItemClick id = " + MyGridView.this.id);
                final Service service = items.get(i).service;
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            load(service.get("name").toString() + ".zip", null);
                            ServiceManager.getInstance().getService();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();

            }
        });

        this.gridview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("test", "onItemLongClick id = " + MyGridView.this.id);
                if (MyGridView.this.id.equals("1")) {
                    //删除掉zip包和db的记录
                } else {
                    //不用管
                }
                return true;
            }
        });

        return this.gridview;
    }

    @WXComponentProp(name = "id")
    public void setId(String id) {
        this.id = id;
        refreshGridView(id);
    }

    private synchronized void refreshGridView(final String id) {
        ((Activity) getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (id.equals("1")) {
                    items.clear();
                    for (Service s : ServiceManager.downloaded) {
                        GridItem item = new GridItem();
                        item.service = s;
                        items.add(item);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    items.clear();
                    for (Service s : ServiceManager.notDownloaded) {
                        GridItem item = new GridItem();
                        item.service = s;
                        items.add(item);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });
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
                holder.iv = (ImageView) rowView.findViewById(R.id.iv);
                holder.tv = (TextView) rowView.findViewById(R.id.tv);
                rowView.setTag(holder);
            } else {
                holder = (ViewHolder) rowView.getTag();
            }

            GridItem item = items.get(position);
            holder.tv.setText(item.service.get("name").toString());
            return rowView;
        }

        public class ViewHolder {
            public ImageView iv;
            public TextView tv;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public GridItem getItem(int arg0) {
            return items.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }
    }


    //模拟而已，下面的要删掉。。。

    private void load(final String zipName, final JSCallback callback) throws Exception {
        Log.d("test", "load name = " + zipName);
        String path = WXApplication.PATH + zipName;
        if (new File(path).exists()) {
            Log.d("test", "存在，直接加载");
            ZipPackage zip = Realm.getDefaultInstance().where(ZipPackage.class).equalTo("name", zipName).findFirst();
            loadJSBundle(zipName, zip.indexPath);
        } else {
            Log.d("test", "不存在，下载");
            Service s = new Service().findOne(new KWQuery().equalTo("name", zipName.replace(".zip", "")));
            Log.d("test", "s  = " + s.toString());
            //返回最新的全量包
            Package p = new Package().findOne(new KWQuery().equalTo("serviceId", s.getId()).equalTo("updateType", "all").descending("version"));
            Log.d("test", "p = " + p.toString());
            String baseUrl = s.get("baseUrl").toString();
            String downloadUrl = p.get("url").toString();
            String version = p.get("version").toString();
            downloadJSBundle(zipName, downloadUrl, version, baseUrl);
        }
    }

    public void deleteFunction(String zipName, JSCallback callback) {
        String path = "file://" + WXApplication.PATH + zipName;
        Log.d("test", "path = " + path);
        if (new File(path).exists()) {
            Log.d("test", "存在，删除");
            new File(path).delete();
            final RealmResults<ZipPackage> results = Realm.getDefaultInstance().where(ZipPackage.class).equalTo("name", zipName).findAll();
            Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    results.clear();
                }
            });
            callback.invoke("delete success");
        } else {
            Log.d("test", "不存在，不用管");
        }
    }

    //首次下载
    public void downloadJSBundle(final String zipName, final String downloadUrl, final String version, final String baseUrl) {
        //1.访问接口，参数是zipName，返回是 name， 下载地址 ， 版本号
        HttpDownload httpDownload = new HttpDownload();
        int ret = httpDownload.downFile(downloadUrl, WXApplication.PATH, zipName);
        Log.d("test", "下载返回值 ret = " + ret);
        if (ret != 0) {
            toast("下载失败，请稍后再试");
            return;
        }
        Log.d("test", "下载成功，保存版本号");

        Realm.getDefaultInstance().beginTransaction();
        ZipPackage zip = Realm.getDefaultInstance().createObject(ZipPackage.class);
        zip.name = zipName;
        zip.indexPath = baseUrl;
        zip.version = version;
        Realm.getDefaultInstance().commitTransaction();
        Log.d("test", "下载成功，加载本地sdcard");
        loadJSBundle(zipName, baseUrl);
    }

    public void loadJSBundle(String zipName, String baseUrl) {
        String path = WXApplication.PATH + zipName + "/" + baseUrl;
        Log.d("test", "loadJSBundle path = " + path);
        Intent intent = new Intent(getContext(), WXPageActivity.class);
        intent.setData(Uri.parse(path));
        getContext().startActivity(intent);
    }

    private void toast(final String txt) {
        ((Activity) getContext()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), txt, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
