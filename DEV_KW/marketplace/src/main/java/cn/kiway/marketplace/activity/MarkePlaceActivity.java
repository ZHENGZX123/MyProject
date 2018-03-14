package cn.kiway.marketplace.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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
import cn.kiway.marketplace.util.AssetsHelper;

import static com.androidev.download.DownloadState.STATE_FAILED;
import static com.androidev.download.DownloadState.STATE_FINISHED;
import static com.androidev.download.DownloadState.STATE_PAUSED;
import static com.androidev.download.DownloadState.STATE_PREPARED;
import static com.androidev.download.DownloadState.STATE_RUNNING;
import static com.androidev.download.DownloadState.STATE_WAITING;

public class MarkePlaceActivity extends FragmentActivity {

    private List<DownloadTask> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
        setContentView(R.layout.marke_activity_main);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        //recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new DownloadAdapter());
    }

    private void initialize() {

        DownloadManager controller = DownloadManager.getInstance();
        tasks = new ArrayList<>();
        String data = AssetsHelper.readAsString(this, "data.json");
        try {
            JSONArray array = new JSONArray(data);
            for (int id = 0, length = array.length(); id < length; id++) {
                JSONObject item = array.getJSONObject(id);
                String name = item.optString("name");
                String icon = item.optString("icon");
                String url = item.optString("url");
                tasks.add(controller.newTask(id, url, name).extras(icon).create());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        for (DownloadTask task : tasks) {
            task.resumeListener();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        for (DownloadTask task : tasks) {
            task.pauseListener();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (DownloadTask task : tasks) {
            task.clear();
        }
    }

    public void onDown(View view) {
        Intent intent = new Intent();
        intent.setClass(this, DownloadActivity.class);
        startActivity(intent);
    }

    public void onBefore(View view) {
        finish();
    }

    private class DownloadAdapter extends RecyclerView.Adapter<DownloadViewHolder> {

        private LayoutInflater inflater = LayoutInflater.from(MarkePlaceActivity.this);

        @Override
        public DownloadViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = inflater.inflate(R.layout.marke_list_item, parent, false);
            return new DownloadViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(DownloadViewHolder holder, int position) {
            DownloadTask task = tasks.get(position);
            holder.setKey(task.key);
            task.setListener(holder);
            holder.title.setText(task.name);
            if (task.size == 0) {
                holder.size.setText(R.string.download_unknown);
            } else {
                holder.size.setText(String.format(Locale.US, "%.1fMB", task.size / 1048576.0f));
            }
            Glide.with(MarkePlaceActivity.this).load(task.extras).into(holder.icon);
        }

        @Override
        public int getItemCount() {
            return tasks == null ? 0 : tasks.size();
        }
    }

    private class DownloadViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, DownloadListener {

        int state;
        String key;
        ImageView icon;
        TextView title;
        TextView size;
        Button download;

        private DownloadViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.icon);
            title = (TextView) itemView.findViewById(R.id.title);
            size = (TextView) itemView.findViewById(R.id.size);
            download = (Button) itemView.findViewById(R.id.download);
            download.setOnClickListener(this);
        }

        void setKey(String key) {
            this.key = key;
        }

        @Override
        public void onClick(View v) {
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
