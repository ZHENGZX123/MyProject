package cn.kwim.mqttcilent.app_ui.home_school.friend;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import cn.kiway.Yjpty.R;
import cn.kwim.mqttcilent.BaseActivity;
import cn.kwim.mqttcilent.common.cache.dao.FriendListdao;
import cn.kwim.mqttcilent.common.views.sort.ClearEditText;
import cn.kwim.mqttcilent.common.views.sort.SideBar;

public class FriendListActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = FriendListActivity.class.getSimpleName();
    private ImageView iv_back;
    private ClearEditText filter_edit;
    private ListView listView;
    private TextView dialog;
    private SideBar sidebar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.im_activity_friend_list);
        initView();
//        String friendJson=MqttInstance.getInstance().getPushInterface().getFriendList();
//        Toast.makeText(this,friendJson,Toast.LENGTH_SHORT).show();
        FriendListAdapter adapter = new FriendListAdapter(FriendListActivity.this,FriendListdao.getFriendList());
        Toast.makeText(this, FriendListdao.getFriendList().toString(), Toast.LENGTH_SHORT).show();

        listView.setAdapter(adapter);
    }
    //初始化
    public void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        filter_edit = (ClearEditText) findViewById(R.id.filter_edit);
        listView = (ListView) findViewById(R.id.listView);
        dialog = (TextView) findViewById(R.id.dialog);
        sidebar = (SideBar) findViewById(R.id.sidebars);
    }

    @Override
    public void onClick(View view) {

    }
}
