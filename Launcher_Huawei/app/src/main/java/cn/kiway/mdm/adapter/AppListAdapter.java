package cn.kiway.mdm.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anarchy.classify.simple.SimpleAdapter;
import com.anarchy.classify.simple.widget.MiViewHolder;

import java.util.List;

import cn.kiway.mdm.R;
import cn.kiway.mdm.activity.AppListActivity2;
import cn.kiway.mdm.entity.App;
import cn.kiway.mdm.utils.FileACache;
import cn.kiway.mdm.utils.Utils;

import static cn.kiway.mdm.utils.AppListUtils.isAppInstalled;
import static cn.kiway.mdm.utils.Constant.kiwayQiTa;

/**
 * Created by Administrator on 2017/10/13.
 */

public class AppListAdapter extends SimpleAdapter<App, AppListAdapter.ViewHolder> {

    int index;
    Context context;

    public AppListAdapter(Context context, List<List<App>> mData, int index) {
        super(mData);
        this.index = index;
        this.context = context;
    }


    @Override
    protected ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sample_vertical, parent, false);
        return new AppListAdapter.ViewHolder(view);
    }


    @Override
    public View getView(ViewGroup parent, View convertView, int mainPosition, int subPosition) {
        ItemViewHolder itemViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_app, parent, false);
            itemViewHolder = new ItemViewHolder(convertView);
            convertView.setTag(itemViewHolder);
        } else {
            itemViewHolder = (ItemViewHolder) convertView.getTag();
        }
        App app = mData.get(mainPosition).get(subPosition);
        if (Utils.getIconByPackageName(context.getPackageManager(), app) == null) {
            itemViewHolder.iv.setImageResource(R.mipmap.ic_launcher);
        } else {
            itemViewHolder.iv.setImageDrawable(Utils.getIconByPackageName(context.getPackageManager(), app));
        }
        return convertView;
    }

    @Override
    protected void onBindMainViewHolder(ViewHolder holder, int position) {
        List<App> books = mData.get(position);
        if (books.size() > 1) {
            holder.name.setText("文件夹");
        } else {
            holder.name.setText(books.get(0).name);
        }
        FileACache.saveListCache(context, mData, index + "list.txt");
    }

    @Override
    protected void onBindSubViewHolder(ViewHolder holder, int mainPosition, int subPosition) {
        holder.name.setText(mData.get(mainPosition).get(subPosition).name + "");
    }

    @Override
    public void notifyData() {
        super.notifyData();
        FileACache.saveListCache(context, mData, index + "list.txt");
    }

    @Override
    protected void onItemClick(View view, int parentIndex, int index) {
        //跳转到其他APK
        try {
            if (index == -1)
                index = 0;
            App a = mData.get(parentIndex).get(index);
            String packageName = a.packageName;
            if (packageName.equals(kiwayQiTa)) {//如果点击的是其他应用
                if (!view.getContext().getSharedPreferences("kiway", 0).getBoolean("locked", false)) {
                    Toast.makeText(view.getContext(), "请先锁定在进入其他应用", Toast.LENGTH_SHORT).show();
                    return;
                }
                view.getContext().startActivity(new Intent(view.getContext(), AppListActivity2.class));
                return;
            }
            if (TextUtils.isEmpty(packageName)) {
                Toast.makeText(view.getContext(), "包名错误", Toast.LENGTH_SHORT).show();
                return;
            }
            //1.判断app是否安装
            boolean installed = isAppInstalled(view.getContext().getApplicationContext(), packageName);
            if (!installed) {
                Toast.makeText(view.getContext(), "该APP未安装", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = view.getContext().getPackageManager().getLaunchIntentForPackage(packageName);
            view.getContext().startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    static class ViewHolder extends SimpleAdapter.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
        }

        public TextView name;
    }

    static class ItemViewHolder extends MiViewHolder {
        ImageView iv;

        public ItemViewHolder(View itemView) {
            iv = (ImageView) itemView.findViewById(R.id.iv);
        }
    }

}
