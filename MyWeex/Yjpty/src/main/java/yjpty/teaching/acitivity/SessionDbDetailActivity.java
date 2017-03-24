package yjpty.teaching.acitivity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import yjpty.teaching.R;
import yjpty.teaching.fragment.BaseFragment;
import yjpty.teaching.fragment.MySessionFragment;
import yjpty.teaching.fragment.SessionLNFragment;
import yjpty.teaching.model.VideoCateMode;
import yjpty.teaching.util.IConstant;
import yjpty.teaching.util.ViewUtil;

public class SessionDbDetailActivity extends BaseActivity implements
		OnCheckedChangeListener {
	RadioGroup rg;
	BaseFragment sessionMuLuFragment, sessionAbout;
	int page = 0;
	VideoCateMode videoCateMode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.yjpty_activity_teacher_plan);
		videoCateMode = (VideoCateMode) bundle
				.getSerializable(IConstant.BUNDLE_PARAMS);
		try {
			initView();
			loadData();
			setData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initView() throws Exception {
		rg = ViewUtil.findViewById(this, R.id.rg);
		rg.setOnCheckedChangeListener(this);
		// 进来默认课程表
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		hideFragment(transaction, sessionMuLuFragment, sessionAbout);
		if (sessionMuLuFragment == null) {
			sessionMuLuFragment = MySessionFragment.newInstance(videoCateMode);
			transaction.add(R.id.fragment, sessionMuLuFragment,
					"sessionTableBaseFragment");
		} else if (sessionMuLuFragment.isAdded()
				&& sessionMuLuFragment.isHidden()) {
			transaction.show(sessionMuLuFragment);
		}
		transaction.commit();
		findViewById(R.id.previos_class).setOnClickListener(this);
		ViewUtil.setContent(this, R.id.title, videoCateMode.getName());
		ViewUtil.setContent(this, R.id.rb1, R.string.kcml);
		ViewUtil.setContent(this, R.id.rb2, R.string.kcjj);
		if (videoCateMode.getKcjy().equals("")
				&& videoCateMode.getKcln().equals("")) {
			findViewById(R.id.rg).setVisibility(View.GONE);
			findViewById(R.id.views).setVisibility(View.GONE);
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		int pg = 0;
		if (checkedId == R.id.rb1) {
			pg = 0;

		} else if (checkedId == R.id.rb2) {
			pg = 1;

		}
		if (page > pg) {// 切换动画
			transaction.setCustomAnimations(R.anim.yjpty_silde_in_left,
					R.anim.yjpty_silde_out_right);
		} else {
			transaction.setCustomAnimations(R.anim.yjpty_silde_in_right,
					R.anim.yjpty_silde_out_left);
		}
		page = pg;
		hideFragment(transaction, sessionMuLuFragment, sessionAbout);
		if (checkedId == R.id.rb1) {
			if (sessionMuLuFragment == null) {
				sessionMuLuFragment = MySessionFragment.newInstance(videoCateMode);
				transaction.add(R.id.fragment, sessionMuLuFragment,
						"sessionMuLuFragment");
			} else if (sessionMuLuFragment.isAdded()
					&& sessionMuLuFragment.isHidden()) {
				transaction.show(sessionMuLuFragment);
			}
			ViewUtil.moveFrontBg(findViewById(R.id.view),
					displayMetrics.widthPixels / 2, 0, 0, 0);

		} else if (checkedId == R.id.rb2) {
			if (sessionAbout == null) {
				sessionAbout = SessionLNFragment.newInstance(videoCateMode);
				transaction.add(R.id.fragment, sessionAbout, "sessionAbout");
			} else if (sessionAbout.isAdded() && sessionAbout.isHidden()) {
				transaction.show(sessionAbout);
			}
			ViewUtil.moveFrontBg(findViewById(R.id.view), 0,
					displayMetrics.widthPixels / 2, 0, 0);

		}
		transaction.commit();
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		int i = v.getId();
		if (i == R.id.previos_class) {
			finish();

		}
	}
}
