package yjpty.teaching.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.view.View;

import com.zk.myweex.R;

import yjpty.teaching.acitivity.BaseActivity;
import yjpty.teaching.util.ViewUtil;


public class NewVersionDialog extends BaseDialog implements OnShowListener {
	String apkUrl, title;
	boolean isShow = false;
	BaseActivity activity;
	NewVersionCallBack back;

	public NewVersionDialog(Context context, NewVersionCallBack back) {
		super(context);
		this.activity = (BaseActivity) context;
		this.back = back;
		view = ViewUtil.inflate(context, R.layout.yjpty_dialog_new_version);
		fullWindowCenter(context);
		setContentView(view, layoutParams);
		findViewById(R.id.cancle).setOnClickListener(this);
		findViewById(R.id.ok).setOnClickListener(this);
		setOnShowListener(this);
	}

	public void setApkUrl(String apkUrl) {
		this.apkUrl = apkUrl;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setIsShow(boolean isShow) {
		this.isShow = isShow;
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		int i = v.getId();
		if (i == R.id.ok) {
			dismiss();
			if (back != null)
				try {
					back.newVersionOkCallBack();
				} catch (Exception e) {
					e.printStackTrace();
				}

			dismiss();
			if (isShow)
				return;
			if (back != null)
				try {
					back.newVersionCallBack();
				} catch (Exception e) {
					e.printStackTrace();
				}

		} else if (i == R.id.cancle) {
			dismiss();
			if (isShow)
				return;
			if (back != null)
				try {
					back.newVersionCallBack();
				} catch (Exception e) {
					e.printStackTrace();
				}

		}
	}

	public interface NewVersionCallBack {
		public void newVersionCallBack() throws Exception;

		public void newVersionOkCallBack() throws Exception;
	}

	@Override
	public void onShow(DialogInterface arg0) {
		ViewUtil.setContent(view, R.id.title, title);
		if (!isShow) {
			view.findViewById(R.id.ok).setVisibility(View.GONE);
			ViewUtil.setContent(view, R.id.cancle, activity.resources.getString(R.string.sure));
		}
	}
}
