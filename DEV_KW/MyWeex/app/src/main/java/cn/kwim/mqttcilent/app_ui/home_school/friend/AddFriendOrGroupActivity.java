package cn.kwim.mqttcilent.app_ui.home_school.friend;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.List;

import com.zk.myweex.R;
import cn.kwim.mqttcilent.BaseActivity;
import cn.kwim.mqttcilent.common.cache.javabean.Converse;
import cn.kwim.mqttcilent.mqttclient.MqttInstance;

public class AddFriendOrGroupActivity extends BaseActivity implements View.OnClickListener {

    private static final String NEW_FRIEND = "new_friend";
    private static final String GROUP = "im_group";

    private Button new_friend,group;
    //判断查群，还是查询新的好友
    private String type;
    private Button bt_search;
    private ListView list;
    private AddFriendOrGroupAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.im_activity_add_friend_or_group);
        initView();

        //设置适配
        adapter = new AddFriendOrGroupAdapter(AddFriendOrGroupActivity.this);
        list.setAdapter(adapter);
    }

    public void initView() {
        new_friend = (Button) findViewById(R.id.new_friend);
        group = (Button) findViewById(R.id.group);
        bt_search = (Button) findViewById(R.id.bt_search);
        list = (ListView) findViewById(R.id.lists);

        new_friend.setOnClickListener(this);
        group.setOnClickListener(this);
        bt_search.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.new_friend:{
                type = NEW_FRIEND;
                new_friend.setBackgroundResource(R.drawable.im_addselector_press);
                new_friend.setTextColor(Color.parseColor("#ffffff"));
                group.setBackgroundResource(R.drawable.im_addselector_unpress);
                group.setTextColor(Color.parseColor("#888888"));
                break;
            }
            case R.id.group:{
                type = GROUP;
                group.setBackgroundResource(R.drawable.im_addselector_press);
                group.setTextColor(Color.parseColor("#ffffff"));
                new_friend.setBackgroundResource(R.drawable.im_addselector_unpress);
                new_friend.setTextColor(Color.parseColor("#888888"));

                break;
            }
            case R.id.bt_search:{
                //查询群或者好友
                String friend=MqttInstance.getInstance().getPushInterface().searchFriend("小");
                Converse converse = new Gson().fromJson(friend,Converse.class);
                if(converse.getStatusCode().equals("200")){
                    List lst = (List)converse.getData();

                    adapter.setLst(lst);
                }
                break;
            }
            default:{
                break;
            }
        }
    }
}
