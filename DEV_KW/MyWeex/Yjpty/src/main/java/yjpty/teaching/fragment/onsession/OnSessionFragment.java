package yjpty.teaching.fragment.onsession;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.zk.myweex.R;

import java.io.Serializable;
import java.util.List;

import yjpty.teaching.adpater.OnSessionAdapter;
import yjpty.teaching.fragment.BaseFragment;
import yjpty.teaching.model.OnClassModel;
import yjpty.teaching.model.VideoModel;
import yjpty.teaching.util.ViewUtil;

public class OnSessionFragment extends BaseFragment {
    public ListView listView;
    public List<OnClassModel> list;
    public OnSessionAdapter adapter;
    VideoModel videoModel;
    View head;
    boolean isAttendClass;

    public OnSessionFragment() {
        super();
    }

    public static OnSessionFragment newInstance(List<OnClassModel> list, VideoModel videoModel, boolean isAttendClass) {
        OnSessionFragment newFragment = new OnSessionFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("list", (Serializable) list);
        bundle.putSerializable("videoModel", videoModel);
        bundle.putBoolean("isAttendClass", isAttendClass);
        newFragment.setArguments(bundle);
        return newFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            this.list = (List<OnClassModel>) args.getSerializable("list");
            this.isAttendClass = args.getBoolean("isAttendClass");
            this.videoModel = (VideoModel) args.getSerializable("videoModel");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = ViewUtil.inflate(activity, R.layout.yjpty_fragment_session);
        initView();
        return view;
    }

    void initView() {
        adapter = new OnSessionAdapter(activity, list, isAttendClass);
        listView = ViewUtil.findViewById(view, R.id.list);
        if (videoModel != null) {
            head = ViewUtil.inflate(activity, R.layout.yjpty_head_session_plan);
            listView.addHeaderView(head);
            ViewUtil.setContent(head, R.id.gold_content,
                    videoModel.getTeachingAim());// 教学目标
            ViewUtil.setContent(head, R.id.really_content,
                    videoModel.getTeachingPreare());// 教学准备
            if (videoModel.getTeachingAim().equals(""))
                head.findViewById(R.id.gold).setVisibility(
                        View.GONE);
            if (videoModel.getTeachingPreare().equals(""))
                head.findViewById(R.id.really).setVisibility(
                        View.GONE);
        }
        listView.setAdapter(adapter);
        listView.setSelected(true);
    }

    @Override
    public void loadData() throws Exception {

    }
}
