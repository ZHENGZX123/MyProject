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
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cn.kiway.marketplace.R;
import cn.kiway.marketplace.db.MarkePlaceDBHelper;
import cn.kiway.marketplace.dialog.AppDialog;
import cn.kiway.marketplace.util.FileManager;
import cn.kiway.marketplace.util.MarketApp;
import cn.kiway.marketplace.util.MarketClassify;
import okhttp3.Call;

import static cn.kiway.marketplace.util.IUrContant.APP_LIST_URL;
import static cn.kiway.marketplace.util.IUrContant.BASE_URL;
import static com.androidev.download.DownloadState.STATE_FAILED;
import static com.androidev.download.DownloadState.STATE_FINISHED;
import static com.androidev.download.DownloadState.STATE_PAUSED;
import static com.androidev.download.DownloadState.STATE_PREPARED;
import static com.androidev.download.DownloadState.STATE_RUNNING;
import static com.androidev.download.DownloadState.STATE_WAITING;

public class MarkePlaceFragment extends Fragment {

    private List<DownloadTask> tasks = new ArrayList<>();
    ;
    private List<MarketApp> marketAppList = new ArrayList<>();
    private int pageSize = 20;
    private int currentPage = 1;
    DownloadManager controller;
    AppDialog appDialog;
    MarketClassify marketClassify;
    private View contentView;

    public MarkePlaceFragment() {
        super();
    }

    public MarkePlaceFragment(MarketClassify marketClassify) {
        this.marketClassify = marketClassify;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = DownloadManager.getInstance();
        appDialog = new AppDialog(getContext());
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        loadData();
        if (contentView != null) return contentView;
        contentView = inflater.inflate(R.layout.marke_fragment_main, container, false);
        return contentView;
    }

    private void loadData() {
        RequestCall requestCall;
        if (marketClassify==null)
            return;
        if (!marketClassify.id.equals("")) {//加载全部的时候 appClassify参数不加上去
            requestCall = OkHttpUtils.get().url(APP_LIST_URL)
                    .addParams("appClassify", marketClassify.id)
                    .addParams("currentPage", currentPage + "")
                    .addParams("pageSize", pageSize + "")
                    .build();
        } else {
            requestCall = OkHttpUtils.get().url(APP_LIST_URL)
                    .addParams("currentPage", currentPage + "")
                    .addParams("pageSize", pageSize + "")
                    .build();
        }
        requestCall.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                initialize();
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    JSONObject data = new JSONObject(response);
                    if (data.optInt("statusCode") == 200) {
                        ArrayList<MarketApp> apps = new ArrayList<MarketApp>();
                        JSONArray array = data.optJSONObject("data").optJSONArray("list");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject item = array.optJSONObject(i);
                            MarketApp marketApp = new MarketApp();
                            marketApp.remoteUpdate = item.optString("remoteUpdate");
                            marketApp.iocn = item.optString("icon");
                            marketApp.description = item.optString("description");
                            marketApp.language = item.optString("language");
                            marketApp.packages = item.optString("packages");
                            marketApp.systemRequirement = item.optString("systemRequirement");
                            marketApp.isPublish = item.optString("isPublish");
                            marketApp.version = item.optString("version");
                            marketApp.platform = item.optString("platform");
                            marketApp.url = item.optString("url");
                            marketApp.removeApp = item.optString("removeApp");
                            marketApp.supportPlatform = item.optString("supportPlatform");
                            marketApp.size = item.optString("size");
                            marketApp.name = item.optString("name");
                            marketApp.id = item.optString("id");
                            marketApp.deviceRequirement = item.optString("deviceRequirement");
                            marketApp.pushInstall = item.optString("pushInstall");
                            marketApp.downloadCount = item.optString("downloadCount");
                            marketApp.appClassify = marketClassify.id;
                            marketApp.appClassifyName = item.optString("appClassify");
                            marketApp.createDate = item.optString("createDate");
                            marketApp.page = currentPage + "";
                            apps.add(marketApp);
                        }
                        if (currentPage == 1) {//如果是第一页，则判断头部是否一样，一样不刷新
                            if (marketAppList.toString().startsWith(apps.toString()))
                                return;
                        }
                        new MarkePlaceDBHelper(getContext()).deleteAppList(marketClassify.id, currentPage + "");
                        new MarkePlaceDBHelper(getContext()).addAppList(apps);
                        initialize();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initialize() {
        tasks.clear();
        marketAppList = new MarkePlaceDBHelper(getContext()).getAllAppList(marketClassify.id, currentPage + "");
        for (int id = 0; id < marketAppList.size(); id++) {
            tasks.add(controller.newTask(Long.parseLong(marketAppList.get(id).id), marketAppList.get(id).url,
                    marketAppList.get(id).name + ".apk").extras
                    (marketAppList.get(id).iocn).create());
        }
        initDataView();
    }

    private void initDataView() {
        RecyclerView recyclerView = (RecyclerView) contentView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new DownloadAdapter());
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
            holder.title.setText(app.name);
            holder.downNumber.setText(app.downloadCount + "次");
            holder.content.setText(app.description);
            holder.size.setText(app.size);
            if (!app.iocn.startsWith("http://"))
                app.iocn = BASE_URL + app.iocn;
            Glide.with(MarkePlaceFragment.this).load(app.iocn).into(holder.icon);
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
            itemView.setOnClickListener(this);
        }

        void setKey(String key) {
            this.key = key;
        }

        @Override
        public void onClick(View v) {
            final int position = getAdapterPosition();
            if (v.getId() == R.id.download) {
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
                        new FileManager(getContext()).open(task.name, task.path);
                        break;
                }
            } else {
                appDialog.setMarketApp(marketAppList.get(position));
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
