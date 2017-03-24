package yjpty.teaching.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import yjpty.teaching.R;
import yjpty.teaching.model.VideoCateMode;
import yjpty.teaching.util.ViewUtil;


public class SessionLNFragment extends BaseFragment {
    VideoCateMode videoCateMode;

    public SessionLNFragment() {
        super();
    }

    public static SessionLNFragment newInstance(VideoCateMode videoCateMode) {
        SessionLNFragment newFragment = new SessionLNFragment();
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
//    public SessionLNFragment(VideoCateMode videoCateMode) {
//        this.videoCateMode = videoCateMode;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = ViewUtil.inflate(activity, R.layout.yjpty_fragment_session_ln);
        try {
            loadData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    @Override
    public void loadData() throws Exception {
        ViewUtil.setContent(view, R.id.kcln, videoCateMode.getKcln());
        ViewUtil.setContent(view, R.id.jxjy, videoCateMode.getKcjy());
        if (videoCateMode.getKcln().equals("")) {
            view.findViewById(R.id.kcln_title).setVisibility(View.GONE);
        }
        if (videoCateMode.getKcjy().equals("")) {
            view.findViewById(R.id.jxjy_title).setVisibility(View.GONE);
        }
    }

}
