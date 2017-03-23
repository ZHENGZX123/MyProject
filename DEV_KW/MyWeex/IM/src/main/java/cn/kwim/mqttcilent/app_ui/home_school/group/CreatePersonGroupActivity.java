package cn.kwim.mqttcilent.app_ui.home_school.group;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.kiway.Yjpty.R;
import cn.kiway.utils.ViewUtil;
import cn.kwim.mqttcilent.BaseActivity;
import cn.kwim.mqttcilent.app_ui.dailog.CreateGroudDialog;
import cn.kwim.mqttcilent.common.cache.dao.DaoType;
import cn.kwim.mqttcilent.common.cache.dao.GroupMemberListDao;
import cn.kwim.mqttcilent.common.cache.dao.MainListDao;
import cn.kwim.mqttcilent.common.cache.javabean.GroupListMember;
import cn.kwim.mqttcilent.mqttclient.MqttInstance;
import cn.kwim.mqttcilent.mqttclient.mq.PushInterface;
import io.realm.Realm;
import io.realm.RealmChangeListener;

//import cn.kiway.http.BaseHttpConnectPool;


/**
 * Created by Administrator on 2017/2/16.
 *
 */

public class CreatePersonGroupActivity extends BaseActivity implements CreateGroudDialog.CreateGroup{

    private ImageView iv_back;
    private ListView list;
    private String groupId;
    private List<GroupListMember> memberLst;
    private Realm realm;
    private CheckAdapter adapter;
    private RealmChangeListener<Realm> realmListener;
    private List<Map> groupList = new ArrayList<>();
    private Button sure;
    private String name;
//    LoginDialog loginDialog;
    CreateGroudDialog createGroudDialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.im_create_person_group);
        groupId = getIntent().getStringExtra(SelectClassGroupActivity.GROUPID);
        initView();
        memberLst =  GroupMemberListDao.getGroupListMember(groupId);
        adapter = new CheckAdapter();
        list.setAdapter(adapter);
        realm = Realm.getDefaultInstance();
        realmListener = new RealmChangeListener<Realm>() {
            @Override
            public void onChange(Realm element) {
                memberLst = GroupMemberListDao.getGroupListMember(groupId);
                adapter.notifyDataSetChanged();
            }
        };
        realm.addChangeListener(realmListener);
//        loginDialog=new LoginDialog(this);
//        loginDialog.setTitle("创建中...");
        createGroudDialog =new CreateGroudDialog(this,this);
    }

    @Override
    protected void onStart() {
        new Thread() {
            @Override
            public void run() {
                try {
                    PushInterface interfaces = MqttInstance.getInstance().getPushInterface();
                    if (interfaces.groupMemberList(groupId) != null) {
                        GroupMemberListDao.saveGroupListMember(interfaces.groupMemberList(groupId), groupId);
                    }
                } catch (Exception e) {

                }
            }
        }.start();
        super.onStart();
    }

     public void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        list = (ListView) findViewById(R.id.listView);
        sure = (Button) findViewById(R.id.sure);

        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (groupList.size()==0){
                    Toast.makeText(CreatePersonGroupActivity.this, "请选择群成员", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(MqttInstance.getInstance().getPushInterface()==null){
                    Toast.makeText(CreatePersonGroupActivity.this, "网络连接失败，无法创建群", Toast.LENGTH_SHORT).show();
                    return;
                }
                createGroudDialog.show();
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    /**
     * 创建成功
     */
    private void createSuccess(){
//        BaseHttpConnectPool.loodingDialog.close();
//        startActivity(new Intent(this, MainActivity.class));
    }

    private String getJson(){
        //String json = new Gson().toJson(list);
        String json = "[";
        for (int i= 0; i<groupList.size(); i++){
            Map map = groupList.get(i);
            if(i!=groupList.size()-1){
                json = json +"{'uid':'"+map.get("uid")+"','nickname':'"+map.get("nickname")+"'},";
            }else{
                json = json +"{'uid':'"+map.get("uid")+"','nickname':'"+map.get("nickname")+"'}";
            }
        }
        return json+"]";
    }

    @Override
    public void createGroupCallBack(final String message, int viewId) throws Exception {
//        loginDialog.show();
        createGroudDialog.dismiss();
        new Thread(){
            @Override
            public void run() {
                String names="";
                names = "{'name':'"+message+"','icon':'','notice':'','intro':'','type':'','ispublic':'','isvalidate':'','maxnum':'1000'}";
                MqttInstance.getInstance().getPushInterface().creatGroup(names,getJson());
                MainListDao.saveGroupList(MqttInstance.getInstance().getPushInterface().getGroupList(), DaoType.SESSTIONTYPE.GROUP);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        createSuccess();
                    }
                });
            }
        }.start();
    }

    class CheckAdapter extends BaseAdapter {

        private LayoutInflater minflater = LayoutInflater.from(CreatePersonGroupActivity.this);

        @Override
        public int getCount() {
            return memberLst!=null?memberLst.size():0;
        }

        @Override
        public Object getItem(int i) {
            return memberLst.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            final GroupListMember groupmember = memberLst.get(i);
            ViewHolder viewholder = null;
            if(viewholder ==null){
                viewholder = new ViewHolder();
                view = minflater.inflate(R.layout.im_create_person_group_item, null);
                viewholder.classname = (TextView) view.findViewById(R.id.name);
                viewholder.check = (CheckBox) view.findViewById(R.id.check_box);
                view.setTag(viewholder);
            }else{
                viewholder = (ViewHolder)view.getTag();
            }
            viewholder.classname.setText(groupmember.getName());
            if (ViewUtil.getContent(viewholder.classname).equals(""))
                viewholder.classname.setText("匿名"+(i+1));
            int positions = getItemId(groupmember.getUserId());
            if(positions!=-1){
                viewholder.check.setChecked(true);
            }

            viewholder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    HashMap<String ,String> map = new HashMap<String, String>();
                    if(b){
                        map.put("uid", groupmember.getUserId());
                        map.put("nickname", groupmember.getName());
                        groupList.add(map);
                    }else{
                        int positions = getItemId(groupmember.getUserId());
                        Log.i("getItemId",positions+"");
                        if( positions!=-1){
                            groupList.remove(positions);
                            //notifyDataSetChanged();
                        }

                    }
                }
            });
            return view;
        }

        public int getItemId(String id){
            Log.i("getItemId", id+"******"+groupList.toString());
            for(int i = 0; i<groupList.size(); i++){
                String memberId = ((Map)groupList.get(i)).get("uid").toString();
                Log.i("getItemId", memberId+"******"+groupList.toString());
                if(memberId.equals(id)){
                    return i;
                }
            }

            return -1;
        }

        class ViewHolder {
            TextView classname;
            CheckBox check;
        }
    }
    
}
