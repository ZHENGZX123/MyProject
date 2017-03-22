package cn.kwim.mqttcilent.app_ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import cn.kiway.App;
import com.zk.myweex.R;
import cn.kiway.utils.AppUtil;
import cn.kwim.mqttcilent.common.cache.dao.DaoType;
import cn.kwim.mqttcilent.common.cache.dao.MainListDao;
import cn.kwim.mqttcilent.common.cache.dao.MessageDao;
import cn.kwim.mqttcilent.common.cache.javabean.MainList;
import cn.kwim.mqttcilent.common.cache.javabean.Message;
import cn.kwim.mqttcilent.mqttclient.MqttInstance;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class HomeSchoolFragment extends Fragment implements View.OnClickListener {

    public static final int UPDATE = 1;
    private static final String TAG = HomeSchoolFragment.class.getSimpleName();
    private View view;
    private ListView list;
    private Context context;
    private View views;
    private Realm realm;
    private RealmChangeListener<Realm> realmListener;
    private HomeSchoolAdapter adapter;
    private RealmResults<MainList> s;
    private TextView zpfb, go_class, zjpd;
    private ImageView iv_add;
    private PopupWindow mPopupWindow;
    App app;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        context = this.getActivity();
        adapter = new HomeSchoolAdapter(context);
        app = AppUtil.getApplication(context);
        //先获取群列表
        s = MainListDao.getMainList();

        /**
         * 拉取服务器未读消息
         */
        if (MqttInstance.getInstance().getPushInterface() != null) {
            try {
                String json = MqttInstance.getInstance().getPushInterface().getUnReadMsg("0", null);
                Log.i("JSONsss", json);
                MessageDao.saveTSUnreadMessage(json);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.im_home_school_fragment, container,
                false);
        initView(view);
        return view;
    }

    void initPopupView() {
//        View popupView = ViewUtil.inflate(getActivity(), R.layout.yjpty_diaog_add);
//        mPopupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams
//                .WRAP_CONTENT, true);
//        mPopupWindow.setTouchable(true);
//        mPopupWindow.setOutsideTouchable(true);
//        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
//        popupView.findViewById(R.id.add_groud).setOnClickListener(this);
//        popupView.findViewById(R.id.add_class).setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        list.addHeaderView(views);
        list.setAdapter(adapter);
        adapter.setList(MainListDao.getMainList());
    }

    private void initView(View view) {
        list = (ListView) view.findViewById(R.id.list);
        views = LayoutInflater.from(context).inflate(R.layout.im_header_main, null);
        go_class = (TextView) views.findViewById(R.id.go_class);
        zjpd = (TextView) views.findViewById(R.id.zjpd);
        zpfb = (TextView) views.findViewById(R.id.zpfb);
        iv_add = (ImageView) views.findViewById(R.id.iv_add);
        go_class.setOnClickListener(this);
        zjpd.setOnClickListener(this);
        zpfb.setOnClickListener(this);
        iv_add.setOnClickListener(this);
        initPopupView();

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("onstart()","onstart");
        if(s!=null){
        for (int i = 0; i < s.size(); i++) {
            MainList groupList = s.get(i);
            String groupid = groupList.getId();
            String key = groupList.getKey();
            int sum = MessageDao.unreadCount(groupid, DaoType.SESSTIONTYPE.GROUP);
            Message message = MessageDao.getLastContent(groupid, DaoType.SESSTIONTYPE.GROUP);
            //Log.i("",message);
            if (message != null) {
                MainListDao.updateGroupListChat(sum + "", key, message.getMsg(), message.getMessageType(), message
                        .getSendName());
            }
        }
}
        realm = Realm.getDefaultInstance();
        realmListener = new RealmChangeListener<Realm>() {
            @Override
            public void onChange(Realm element) {

                adapter.setList(MainListDao.getMainList());
            }
        };
        realm.addChangeListener(realmListener);
    }

    View view1;

    public void hasclassGround() {
        if (adapter == null)
            return;
//        if (app.classModels.size() <= 0) {
//            adapter.setList(MainListDao.getMainZeroList());
//            if (view1 == null && list.getFooterViewsCount() <= 0) {
//                view1 = ViewUtil.inflate(context, R.layout.yjpty_no_class);
//                list.addFooterView(view1);
//                view1.findViewById(R.id.joins_class).setOnClickListener(this);
//            }
//        } else {
            if (list.getFooterViewsCount() > 0 && view1 != null)
                list.removeFooterView(view1);
            adapter.setList(MainListDao.getMainList());
//        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.removeAllChangeListeners();
        realm.close();
    }

    @Override
    public void onResume() {
        super.onResume();
        hasclassGround();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.go_class: {
//                getActivity().startActivity(new Intent(context, HeizInfoActivity.class));
//
//                break;
//            }
//            case R.id.zjpd: {
//                if (App.getInstance().classModels.size() <= 0) {
//                    ViewUtil.showMessage(getActivity(), "你现在还没加入班级，请先加入班级吧");
//                    return;
//                }
//                if(App.getInstance().classModels.size() == 1){
//                    Bundle bundle = new Bundle();
//                    bundle.putString(IConstant.BUNDLE_PARAMS, IUrContant.BEEBA_WB_URL);
//                    bundle.putString(IConstant.BUNDLE_PARAMS1, "早教频道");
//                    bundle.putString(IConstant.BUNDLE_PARAMS2, App.getInstance().classModels.get(0).getId());
//                    Intent intent = new Intent(context, WebViewActivity.class);
//                    intent.putExtras(bundle);
//                    getActivity().startActivity(intent);
//                    return;
//                }
//                ChooseClassDialog dialog = new ChooseClassDialog(getActivity());
//                if (dialog != null)
//                    dialog.show();
//                break;
//            }
//            case R.id.zpfb: {
//                getActivity().startActivity(new Intent(context, LocalAlbumListActivity.class));
//                break;
//            }
            case R.id.iv_add: {
//                mPopupWindow.showAsDropDown(view);
                break;
            }
//            case R.id.add_groud: {
//                if (App.getInstance().classModels.size() <= 0) {
//                    ViewUtil.showMessage(getActivity(), "你现在还没加入班级，请先加入班级吧");
//                    return;
//                }
//                Intent intent = new Intent(context, SelectClassGroupActivity.class);
//                getActivity().startActivity(intent);
//                mPopupWindow.dismiss();
//                mPopupWindow.dismiss();
//                break;
//            }
//            case R.id.add_class: {
//                Intent intent = new Intent(context, MipcaCaptureActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putInt(IConstant.BUNDLE_PARAMS, 2);
//                intent.putExtras(bundle);
//                getActivity().startActivity(intent);
//                mPopupWindow.dismiss();
//                break;
//            }
//            case R.id.joins_class: {
//                Intent intent = new Intent(context, JoinClassActivity.class);
//                getActivity().startActivity(intent);
//                break;
//            }
        }
    }

//	@Override
//	public void onActivityResult(int requestCode, int resultCode, Intent data) {
//		if(resultCode== Activity.RESULT_OK){
//			switch (requestCode){
//				case  UPDATE:{
//					adapter.setList(GroupListDao.getGroupList());
//					break;
//				}
//			}
//		}
//	}
}
