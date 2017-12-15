package cn.kiway.mdm.adapter;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anarchy.classify.simple.SimpleAdapter;
import com.anarchy.classify.simple.widget.MiViewHolder;

import org.json.JSONArray;

import java.util.List;

import cn.kiway.marketplace.activity.MarkePlaceViewActivity;
import cn.kiway.mdm.R;
import cn.kiway.mdm.activity.MainActivity;
import cn.kiway.mdm.activity.SystemSetupActivity;
import cn.kiway.mdm.entity.App;
import cn.kiway.mdm.entity.AppCharge;
import cn.kiway.mdm.utils.FileACache;
import cn.kiway.mdm.utils.MyDBHelper;
import cn.kiway.mdm.utils.Utils;

import static cn.kiway.mdm.utils.AppListUtils.isAppInstalled;
import static cn.kiway.mdm.utils.Constant.KIWAYSETTING;
import static cn.kiway.mdm.utils.Constant.MARKETPLACE;
import static cn.kiway.mdm.utils.Constant._16;
import static cn.kiway.mdm.utils.FileACache.ListFileName;

/**
 * Created by Administrator on 2017/10/13.
 */

public class AppListAdapter extends SimpleAdapter<App, AppListAdapter.ViewHolder> {

    int index;
    MainActivity context;
    List<List<App>> AllList;
    int size;

    public AppListAdapter(MainActivity context, List<List<App>> mData, List<List<App>> AllList, int index) {
        super(mData);
        this.index = index;
        this.context = context;
        this.AllList = AllList;
        this.size = mData.size();
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
        if (app.packageName.equals(MARKETPLACE)) {
            itemViewHolder.iv.setImageResource(R.drawable.yysc);
        } else if (app.packageName.equals(KIWAYSETTING)) {
            itemViewHolder.iv.setImageResource(R.drawable.setting);
        } else {
            if (Utils.getIconByPackageName(context.getPackageManager(), app.packageName) == null) {
                itemViewHolder.iv.setImageResource(R.mipmap.ic_launcher);
            } else {
                itemViewHolder.iv.setImageDrawable(Utils.getIconByPackageName(context.getPackageManager(), app
                        .packageName));
            }
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
        notifyData();
    }

    @Override
    protected void onBindSubViewHolder(ViewHolder holder, int mainPosition, int subPosition) {
        holder.name.setText(mData.get(mainPosition).get(subPosition).name + "");
    }

    @Override
    public void notifyData() {
        super.notifyData();
        if (mData.size() != size) {//合并或者移除了文件
            for (int i = index * _16; i < index * _16 + size; i++) {//先移除原始的数据
                AllList.remove(index * _16);
            }
            for (int i = 0; i < mData.size(); i++) {//添加改变后的数据
                AllList.add(index * _16, mData.get(mData.size() - 1 - i));
            }
        } else {
            for (int i = index * _16; i < index * _16 + size; i++) {//只是拖动排序的时候
                AllList.remove(i);//替换数据
                AllList.add(i, mData.get(i - index * _16));
            }
        }
        FileACache.saveListCache(context, AllList, ListFileName);//保存数据
        this.size = mData.size();
        if (mData.size() > _16) {//单个页面数据大于16时，更新整个界面，从文件夹拖出来的时候
            context.initData(AllList);
        }
    }

    @Override
    protected void onItemClick(View view, int parentIndex, int index) {//跳转到其他APK
        try {
            if (index == -1)
                index = 0;
            App a = mData.get(parentIndex).get(index);
            String packageName = a.packageName;
            String name = a.name;
            int flag_app_open = context.getSharedPreferences("kiway", 0).getInt("flag_app_open", 1);
            if (flag_app_open == 0) {
                Toast.makeText(context, "所有的APP已被禁止使用", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(packageName)) {
                Toast.makeText(context, "包名错误", Toast.LENGTH_SHORT).show();
                return;
            }
            if (packageName.equals(MARKETPLACE)) {//应用市场
                // context.startActivity(new Intent(context, MarkePlaceActivity.class));
                context.startActivity(new Intent(context, MarkePlaceViewActivity.class));
                return;
            }
            if (packageName.equals(KIWAYSETTING)) {//应用市场
                context.startActivity(new Intent(context, SystemSetupActivity.class));
                return;
            }
            //1.判断app是否安装
            boolean installed = isAppInstalled(context.getApplicationContext(), packageName);
            if (!installed) {
                Toast.makeText(context, "该APP未安装", Toast.LENGTH_SHORT).show();
                return;
            }
            //zzx 检查当前时间能不能用
            if (!Utils.checkAPPTimeUse(new MyDBHelper(context).getTime(packageName), "HH:mm")) {
                Toast.makeText(context, "该时间段内不可以使用", Toast.LENGTH_SHORT).show();
                return;
            }

            //2.检查当前时间段能不能使用
            AppCharge app = new MyDBHelper(context).getAppChargesByPackage(packageName);
            if (app != null) {
                String timeRange = app.timeRange;// [{start end}{start end}]
                Log.d("test", "timeRange = " + timeRange);
                JSONArray array = new JSONArray(timeRange);
                boolean in = Utils.checkAPPTimeUse(array, "HH:mm:ss");
                if (in) {
                    launchAPP(packageName, name);
                } else {
                    Toast.makeText(context, "该时间段内不可以使用", Toast.LENGTH_SHORT).show();
                }
            } else {
                launchAPP(packageName, name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void launchAPP(String packageName, String name) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        context.startActivity(intent);
        Utils.childOperation(context, "useApp", "使用了" + name + "APP");
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
