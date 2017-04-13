package yjpty.teaching.fragment;

import android.annotation.SuppressLint;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import yjpty.teaching.adpater.AllSessionAdapter;
import yjpty.teaching.http.HttpResponseModel;
import yjpty.teaching.http.IUrContant;
import yjpty.teaching.model.VideoCateMode;
import yjpty.teaching.util.IConstant;
import yjpty.teaching.util.ViewUtil;
import yjpty.teaching.views.ClearEditText;

@SuppressLint("HandlerLeak")
public class AllSessionFragment extends BaseFragment implements TextWatcher {

    AllSessionAdapter adapter;
    public ListView listView;
    ClearEditText srv1;

    @SuppressLint("SimpleDateFormat")
    public AllSessionFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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

    void initView() throws Exception {
        adapter = new AllSessionAdapter(activity,
                new ArrayList<VideoCateMode>(), this);
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
        if (activity.app.classModels.size() <= 0)
            return;
        IConstant.HTTP_CONNECT_POOL.addRequest(IUrContant.GET_ALL_SESSION + activity.app.classModels.get(activity.app
                .getClassPosition()).getId()+ "&gradeId=" + activity.app.classModels.get(activity.app
                .getClassPosition()).getYear(), null, fragmentHandler, true, 2);
    }

    void setData() throws Exception {
        if (activity.app.classModels.size() <= 0)
            return;
        JSONArray array = activity.mCache
                .getAsJSONArray(IUrContant.GET_ALL_SESSION
                        + activity.app.classModels.get(activity.app.getClassPosition()).getId());
        if (array != null) {
            loadLessonFromCache(array);
        }
        hasSessionData();
    }

    private void loadLessonFromCache(JSONArray array) {
        JSONArray data = null;
        try {
            data = new JSONArray(activity.mCache.getAsJSONObject(IUrContant.GET_CUSE_BASE).optJSONObject("data")
                    .optString("fieldList"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapter.list.clear();
        adapter.listData.clear();
        for (int i = 0; i < array.length(); i++) {
            JSONObject item = array.optJSONObject(i);
            VideoCateMode model = new VideoCateMode();
            model.setKcln(item.optString("idea"));
            model.setKcjy(item.optString("teaching_suggest"));
            model.setAllCount(item.optString("section_count"));
            model.setReadCount(item.optString("count"));
            model.setGradeId(item.optString("grade_id"));
            model.setPreview(item.optString("icon"));
            String s = item.optString("field_id");
            for (int u = 0; u < s.split("#").length; u++) {
                if (data != null) {
                    for (int j = 0; j < data.length(); j++) {
                        JSONObject jsonObject = data.optJSONObject(j);
                        if (Integer.parseInt(s.split("#")[u]) == jsonObject.optInt("id"))
                            model.setType(model.getType() + " " + jsonObject.optString("label"));
                    }
                }
            }
            model.setId(item.optInt("id"));
            model.setName(item.optString("name"));
            adapter.list.add(model);
            adapter.listData.add(model);
        }
        adapter.notifyDataSetChanged();
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

    @Override
    public void httpSuccess(HttpResponseModel message) throws Exception {
        super.httpSuccess(message);
        JSONObject data = new JSONObject(new String(message.getResponse()));
        if (message.getUrl().equals(IUrContant.GET_ALL_SESSION + activity.app.classModels.get(activity.app
                .getClassPosition()).getId()+ "&gradeId=" + activity.app.classModels.get(activity.app
                .getClassPosition()).getYear())
                && data.optInt("StatusCode") == 200) {
            activity.mCache.put(IUrContant.GET_ALL_SESSION
                            + activity.app.classModels.get(activity.app.getClassPosition()).getId(),
                    data.optJSONArray("data"));
            setData();
        }
    }

    public void hasSessionData() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (adapter.getCount() <= 0) {
                    view.findViewById(R.id.relative).setVisibility(View.GONE);
                    view.findViewById(R.id.no_data).setVisibility(View.VISIBLE);
                } else {
                    view.findViewById(R.id.relative)
                            .setVisibility(View.VISIBLE);
                    view.findViewById(R.id.no_data).setVisibility(View.GONE);
                }
            }
        });
    }
}
