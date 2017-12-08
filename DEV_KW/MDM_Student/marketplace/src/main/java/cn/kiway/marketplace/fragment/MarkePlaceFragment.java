package cn.kiway.marketplace.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidev.download.DownloadListener;
import com.androidev.download.DownloadManager;
import com.androidev.download.DownloadTask;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cn.kiway.marketplace.R;
import cn.kiway.marketplace.dialog.AppDialog;
import cn.kiway.marketplace.util.AssetsHelper;
import cn.kiway.marketplace.util.MarketApp;

import static com.androidev.download.DownloadState.STATE_FAILED;
import static com.androidev.download.DownloadState.STATE_FINISHED;
import static com.androidev.download.DownloadState.STATE_PAUSED;
import static com.androidev.download.DownloadState.STATE_PREPARED;
import static com.androidev.download.DownloadState.STATE_RUNNING;
import static com.androidev.download.DownloadState.STATE_WAITING;

public class MarkePlaceFragment extends Fragment {

    private List<DownloadTask> tasks;
    private List<MarketApp> marketAppList;
    DownloadManager controller;
AppDialog appDialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = DownloadManager.getInstance();
        appDialog=new AppDialog(getContext());
        initialize();
    }

    private View contentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {

        if (contentView != null) return contentView;
        contentView = inflater.inflate(R.layout.marke_fragment_main, container, false);
        RecyclerView recyclerView = (RecyclerView) contentView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new DownloadAdapter());
        return contentView;
    }

    private void initialize() {

        tasks = new ArrayList<>();
        marketAppList = new ArrayList<>();
        String data = AssetsHelper.readAsString(getContext(), "data.json");
        try {
            JSONArray array = new JSONArray(data);
            for (int id = 0, length = array.length(); id < length; id++) {
                JSONObject item = array.getJSONObject(id);
                MarketApp marketApp = new MarketApp();
                String name = item.optString("name");
                String icon = item.optString("icon");
                String url = item.optString("url");
                marketApp.appName = name;
                marketApp.appIcon = icon;
                marketApp.appDownUrl = url;
                marketApp.appDetial = item.optString("content");
                marketApp.appDownNumber = item.optString("downNumber");
                marketAppList.add(marketApp);
                tasks.add(controller.newTask(id, url, name).extras(icon).create());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        for (DownloadTask task : tasks) {
            task.resumeListener();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        for (DownloadTask task : tasks) {
            task.pauseListener();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        for (DownloadTask task : tasks) {
            task.clear();
        }
    }

    private class DownloadAdapter extends RecyclerView.Adapter<DownloadViewHolder> {

        private LayoutInflater inflater = LayoutInflater.from(getContext());

        @Override
        public DownloadViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = inflater.inflate(R.layout.marke_list_item, parent, false);
            return new DownloadViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(DownloadViewHolder holder, int position) {
            //---------------------
            DownloadTask task = tasks.get(position);
            holder.setKey(task.key);
            task.setListener(holder);
            //-------------------
            MarketApp app = marketAppList.get(position);
            holder.title.setText(app.appName);
            holder.downNumber.setText(app.appDownNumber + "次");
            holder.content.setText(app.appDetial);
            if (task.size == 0) {
                holder.size.setText(R.string.download_unknown);
            } else {
                holder.size.setText(String.format(Locale.US, "%.1fMB", task.size / 1048576.0f));
            }
            Glide.with(MarkePlaceFragment.this).load(app.appIcon).into(holder.icon);
        }

        @Override
        public int getItemCount() {
            return marketAppList == null ? 0 : marketAppList.size();
        }
    }

    private class DownloadViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, DownloadListener {

        int state;
        String key;
        ImageView icon;
        TextView title;
        TextView size;
        TextView content;
        TextView downNumber;
        Button download;

        private DownloadViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.icon);
            title = (TextView) itemView.findViewById(R.id.title);
            size = (TextView) itemView.findViewById(R.id.size);
            download = (Button) itemView.findViewById(R.id.download);
            content = (TextView) itemView.findViewById(R.id.content);
            downNumber = (TextView) itemView.findViewById(R.id.number);
            download.setOnClickListener(this);
//            icon.setOnClickListener(this);
//            title.setOnClickListener(this);
//            content.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        void setKey(String key) {
            this.key = key;
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.download) {
                final int position = getAdapterPosition();
                final DownloadTask task = tasks.get(position);
                switch (state) {
                    case STATE_FAILED:
                    case STATE_PREPARED:
                        task.start();
                        break;
                    case STATE_PAUSED:
                        task.resume();
                        break;
                    case STATE_WAITING:
                    case STATE_RUNNING:
                        task.pause();
                        break;
                    case STATE_FINISHED:
                        //task.path
                        break;
                }
            }else {
                appDialog.show();
            }
        }

        @Override
        public void onStateChanged(String key, int state) {
            if (!key.equals(this.key)) return;
            this.state = state;
            switch (state) {
                case STATE_FAILED:
                    download.setText(R.string.download_retry);
                    break;
                case STATE_PREPARED:
                    download.setText(R.string.label_download);
                    break;
                case STATE_PAUSED:
                    download.setText(R.string.download_resume);
                    break;
                case STATE_WAITING:
                    download.setText(R.string.download_wait);
                    break;
                case STATE_FINISHED:
                    download.setText(R.string.download_done);
                    break;
            }
        }

        @Override
        public void onProgressChanged(String key, long finishedLength, long contentLength) {
            if (!key.equals(this.key)) return;
            download.setText(String.format(Locale.US, "%.1f%%", finishedLength * 100.f / Math.max(contentLength, 1)));
            if (contentLength == 0) {
                size.setText(R.string.download_unknown);
            } else {
                size.setText(String.format(Locale.US, "%.1fMB", contentLength / 1048576.0f));
            }
        }
    }
}
