package cn.kiway.mdm.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.kiway.mdm.activity.BaseActivity;
import cn.kiway.mdm.modle.IpModel;
import studentsession.kiway.cn.mdm_studentsession.R;

/**
 * Created by Administrator on 2017/12/6.
 */

public class UdpConnectDialog extends Dialog {
    protected ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT);
    ArrayList<IpModel> ipModels = new ArrayList<IpModel>();
    ListView listView;
    BaseActivity context;
    IpAdapter adapter;
    ProgressBar progressBar;

    public void setList(IpModel ipModel) {
        if (ipModels.toString().contains(ipModel.ip)) {
           for (int i=0;i<ipModels.size();i++){
               if (ipModels.get(i).ip.equals(ipModel.ip)){
                   ipModels.get(i).time=System.currentTimeMillis();
               }
           }
        } else {
            ipModels.add(ipModel);
        }
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (ipModels.size() > 0) {
                    listView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                } else {
                    listView.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    public UdpConnectDialog(Context context) {
        super(context, R.style.LoadingDialog);
        this.context = (BaseActivity) context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_list);
        fullWindowCenter();
        listView = findViewById(R.id.listView);
        progressBar = findViewById(R.id.progressBar);
        listView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        adapter = new IpAdapter(context);
        listView.setAdapter(adapter);
        handler.sendEmptyMessageDelayed(0,20000);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    JSONObject data=new JSONObject();
                    data.put("ip",ipModels.get(position).ip);
                    data.put("platform",ipModels.get(position).platform);
                    ((BaseActivity) context).app.onClass(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    protected void fullWindowCenter() {
        layoutParams = getWindow().getAttributes();
        Rect rect = new Rect();
        View v = getWindow().getDecorView();
        v.getWindowVisibleDisplayFrame(rect);
    }

    public class IpAdapter extends ArrayAdapter<IpModel> {
        ipHolder holder;

        public IpAdapter(@NonNull Context context) {
            super(context, -1);
        }

        @Override
        public int getCount() {
            return ipModels.size();
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                holder = new ipHolder();
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_ip, null);
                holder.name = convertView.findViewById(R.id.name);
                convertView.setTag(holder);
            } else {
                holder = (ipHolder) convertView.getTag();
            }
            holder.name.setText(ipModels.get(position).className);
            if(System.currentTimeMillis()-ipModels.get(position).time>20*1000){
                convertView.setVisibility(View.GONE);
            }else {
                convertView.setVisibility(View.VISIBLE);
            }
            return convertView;
        }

        class ipHolder {
            TextView name;
        }
    }
    Handler handler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            adapter.notifyDataSetChanged();
            handler.sendEmptyMessageDelayed(0,20000);
        }
    };
}
