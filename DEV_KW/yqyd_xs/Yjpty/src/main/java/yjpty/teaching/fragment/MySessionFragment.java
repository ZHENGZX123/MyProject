package yjpty.teaching.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import yjpty.teaching.R;
import yjpty.teaching.adpater.TeacherTableAdapter;
import yjpty.teaching.http.HttpResponseModel;
import yjpty.teaching.http.IUrContant;
import yjpty.teaching.model.VideoCateMode;
import yjpty.teaching.model.VideoModel;
import yjpty.teaching.util.IConstant;
import yjpty.teaching.util.ViewUtil;
import yjpty.teaching.views.ClearEditText;


public class MySessionFragment extends BaseFragment implements TextWatcher {
    TeacherTableAdapter adapter;
    public ListView listView;
    ClearEditText srv1;
    VideoCateMode videoCateMode;

    public MySessionFragment() {
        super();
    }

    public static MySessionFragment newInstance(VideoCateMode videoCateMode) {
        MySessionFragment newFragment = new MySessionFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("videoCateMode", videoCateMode);
        newFragment.setArguments(bundle);
        return newFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            this.videoCateMode = (VideoCateMode) args.getSerializable("videoCateMode");
        }
    }

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
        if (videoCateMode == null)
            adapter = new TeacherTableAdapter(activity,
                    new ArrayList<VideoModel>(), true);
        else
            adapter = new TeacherTableAdapter(activity,
                    new ArrayList<VideoModel>(), false);
        listView = ViewUtil.findViewById(view, R.id.listview);
        srv1 = ViewUtil.findViewById(view, R.id.filter_edit);
        ViewUtil.setContent(view, R.id.no_data,activity.resources.getString(R.string.no_session));
        listView.setAdapter(adapter);
        listView.setTextFilterEnabled(true);
        listView.setSelected(true);
        // 根据输入框输入值的改变来过滤搜索
        srv1.addTextChangedListener(this);
    }

    @Override
    public void loadData() throws Exception {
        if (activity.app.classModels.size()<=0)
            return;
        if (videoCateMode == null)
            IConstant.HTTP_CONNECT_POOL.addRequest(IUrContant.GET_MY_SESSION + activity.app.classModels.get(activity
                    .app.getClassPosition()).getId(), null, fragmentHandler, true, 2);
        else
            IConstant.HTTP_CONNECT_POOL.addRequest(IUrContant.GET_ONE_SESSION.replace("{courseId}", videoCateMode
                            .getId() + "") + activity.app.classModels.get(activity.app.getClassPosition()).getId(),
                    null,
                    fragmentHandler, true, 2);
    }

    void setData() {
        JSONArray array;
        if (videoCateMode == null) {
            JSONObject da = activity.mCache
                    .getAsJSONObject(IUrContant.GET_MY_SESSION);
            if (da != null) {
                array = da.optJSONObject("data").optJSONArray("createSection");
                if (array != null) {
                    loadLessonFromCache(array, true);
                }
                array = da.optJSONObject("data").optJSONArray("collectSection");
                if (array != null) {
                    loadLessonFromCache(array, false);
                }
            }
        } else {
            JSONObject da = activity.mCache
                    .getAsJSONObject(IUrContant.GET_ONE_SESSION.replace("{courseId}", videoCateMode.getId() + ""));
            if (da != null) {
                array = da.optJSONArray("courseSectionList");
                if (array != null) {
                    loadLessonFromCache(array, true);
                }
            }
        }
        hasSessionData();
    }

    private void loadLessonFromCache(JSONArray array, boolean isClear) {
        if (isClear) {
            adapter.list.clear();
            adapter.listData.clear();
        }
        if (videoCateMode == null) {
            for (int i = 0; i < array.length(); i++) {
                JSONObject item = array.optJSONObject(i);
                VideoModel model = new VideoModel();
                model.setId(item.optInt("id"));
                model.setName(item.optString("name"));
                model.setHomework(item.optString("homework"));
                model.setPreview(item.optString("icon"));
                model.setTeachingPreare(item.optString("teaching_prepare"));
                model.setTeachingAim(item.optString("aim"));
                model.setReadCount(item.optInt("count"));
                model.setGrader(item.optString("grade_id"));
                adapter.list.add(model);
                adapter.listData.add(model);
            }
        } else {
            for (int i = 0; i < array.length(); i++) {
                JSONObject item = array.optJSONObject(i);
                VideoModel model = new VideoModel();
                model.setId(item.optInt("id"));
                model.setName(item.optString("name"));
                model.setHomework(item.optString("homework"));
                model.setPreview(item.optString("icon"));
                model.setSessionName(videoCateMode.getName());
                model.setTeachingPreare(item.optString("teaching_prepare"));
                model.setTeachingAim(item.optString("aim"));
                model.setReadCount(item.optInt("count"));
                model.setGrader(videoCateMode.getGradeId());
                adapter.list.add(model);
                adapter.listData.add(model);
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void httpSuccess(HttpResponseModel message) throws Exception {
        super.httpSuccess(message);
        JSONObject data = new JSONObject(new String(message.getResponse()));
        if (message.getUrl().equals(IUrContant.GET_MY_SESSION + activity.app.classModels.get(activity.app
                .getClassPosition()).getId())
                && data.optInt("StatusCode") == 200) {
            activity.mCache.put(IUrContant.GET_MY_SESSION,
                    data);
            setData();
        } else if (message.getUrl().equals(IUrContant.GET_ONE_SESSION.replace("{courseId}", videoCateMode.getId() +
                "") + activity.app.classModels.get(activity.app.getClassPosition()).getId())) {
            activity.mCache.put(IUrContant.GET_ONE_SESSION.replace("{courseId}", videoCateMode.getId() + ""),
                    data.optJSONObject("data"));
            setData();
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
                } else {
                    view.findViewById(R.id.relative)
                            .setVisibility(View.VISIBLE);
                    view.findViewById(R.id.no_data).setVisibility(View.GONE);
                }
            }
        });
    }
}
