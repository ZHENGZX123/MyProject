package yjpty.teaching.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.zk.myweex.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import yjpty.teaching.adpater.TeacherTableAdapter;
import yjpty.teaching.http.HttpResponseModel;
import yjpty.teaching.http.IUrContant;
import yjpty.teaching.model.VideoModel;
import yjpty.teaching.util.IConstant;
import yjpty.teaching.util.ViewUtil;
import yjpty.teaching.views.ClearEditText;

/**
 * Created by Administrator on 2017/3/28.
 */

public class KinectSessionFragment extends BaseFragment implements TextWatcher {
    TeacherTableAdapter adapter;
    public ListView listView;
    ClearEditText srv1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        view = ViewUtil.inflate(activity, R.layout.yjpty_fragment_tabel2);
        try {
            initView();
            setData();
            loadData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    void initView() {
        adapter = new TeacherTableAdapter(activity,
                new ArrayList<VideoModel>(), true);
        listView = ViewUtil.findViewById(view, R.id.listview);
        srv1 = ViewUtil.findViewById(view, R.id.filter_edit);
        ViewUtil.setContent(view, R.id.no_data, activity.resources.getString(R.string.no_session));
        listView.setAdapter(adapter);
        listView.setTextFilterEnabled(true);
        listView.setSelected(true);
        // 根据输入框输入值的改变来过滤搜索
        srv1.addTextChangedListener(this);
    }

    @Override
    public void loadData() throws Exception {
        super.loadData();
        IConstant.HTTP_CONNECT_POOL.addRequest(IUrContant.GET_KINECTSESSION_URL, null, fragmentHandler, 2);
    }

    void setData() {
        adapter.list.clear();
        adapter.listData.clear();
        JSONObject data = activity.mCache.getAsJSONObject(IUrContant.GET_KINECTSESSION_URL);
        if (data != null) {
            JSONArray array = data.optJSONArray("data");
            for (int i = 0; i < array.length(); i++) {
                VideoModel model = new VideoModel();
                JSONObject item = array.optJSONObject(i);
                if (item.optInt("isKT") == 1) {//判断是否开通了该课程，来显示隐藏，如果需要全部显示，去掉
                    model.setId(item.optInt("id"));
                    model.setName(item.optString("name"));
                    model.setPreview(item.optString("icon"));
                    model.setSessionName(item.optString("name"));
                    model.setTeachingPreare(item.optString("teachingPrepare"));
                    model.setTeachingAim(item.optString("aim"));
                    model.setReadCount(item.optInt("readCount"));
                    model.setKinectPackageName(item.optString("packageName"));
                    model.setKiectSession(true);
                    model.setKiectSessionContent(item.optString("production"));
                    model.setIsKT(item.optInt("isKT"));
                    model.setKiectApkDownLoadUrl(item.optString("downloadUrl"));
                    adapter.list.add(model);
                    adapter.listData.add(model);
                }
            }
        }

        hasSessionData();
    }

    @Override
    public void httpSuccess(HttpResponseModel message) throws Exception {
        super.httpSuccess(message);
        if (message.getUrl().equals(IUrContant.GET_KINECTSESSION_URL)) {
            JSONObject data = new JSONObject(new String(message.getResponse()));
            if (data.optInt("StatusCode") == 200) {
                activity.mCache.put(IUrContant.GET_KINECTSESSION_URL, data);
                setData();
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() != 0 || s.toString().equals("")) {
            adapter.getFilter().filter(s.toString());
        } else {
            listView.clearTextFilter();
        }
    }

    public void hasSessionData() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (adapter.getCount() <= 0) {
                    view.findViewById(R.id.relative).setVisibility(View.GONE);
                    view.findViewById(R.id.no_data).setVisibility(View.VISIBLE);
                    ViewUtil.setContent(activity, R.id.no_data, "尚未开通相关的体感课程，\n需要开通请联系我们");
                } else {
                    view.findViewById(R.id.relative)
                            .setVisibility(View.VISIBLE);
                    view.findViewById(R.id.no_data).setVisibility(View.GONE);
                }
            }
        });
    }
}
