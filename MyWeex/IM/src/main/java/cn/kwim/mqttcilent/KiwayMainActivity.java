package cn.kwim.mqttcilent;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.kiway.Yjpty.R;
import cn.kwim.mqttcilent.app_ui.fragment.HomeSchoolFragment;
import cn.kwim.mqttcilent.app_ui.fragment.MeFragment;
import cn.kwim.mqttcilent.app_ui.fragment.ServerFragment;
import cn.kwim.mqttcilent.app_ui.fragment.StudyFragment;

public class KiwayMainActivity extends BaseActivity implements View.OnTouchListener{

    private TextView curapp_name;
    private TextView curapp_center;
    private ImageView iv_add;
    private ImageView iv_more;
    private RelativeLayout rl_home_school;
    private ImageView iv_home_school;
    private TextView tv_home_school;
    private RelativeLayout rl_study;
    private ImageView iv_study;
    private TextView tv_study;
    private RelativeLayout rl_server;
    private ImageView iv_server;
    private TextView tv_server;
    private RelativeLayout rl_me;
    private ImageView iv_me;
    private TextView tv_me;

    private int columns = 6;
    private int rows = 4;
    // 底部标签切换的Fragment
    private Fragment homeSchoolFragment, meFragment, serverFragment,
            studyFragment, defaultFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.im_activity_kiway_main);
        initView();
       // GroupListDao.saveGroupList(MqttInstance.getInstance().getPushInterface().getGroupList());
        /**
         * 获取每一个群的未读信息数量
         */

    }

    @Override
    protected void onStart() {
        super.onStart();
        initTab();
    }

    private void initTab() {

        if(homeSchoolFragment ==null){
            homeSchoolFragment = new HomeSchoolFragment();
        }
        if(!homeSchoolFragment.isAdded()){
            getSupportFragmentManager         ()
                    .beginTransaction()
                    .add(R.id.content_layout, homeSchoolFragment).commit();

        }
        defaultFragment = homeSchoolFragment;
        iv_home_school.setImageResource(R.drawable.im_tab_index_pressed);
        iv_study.setImageResource(R.drawable.im_tab_txl_normal);
        iv_server.setImageResource(R.drawable.im_tab_wd_normal);
        iv_me.setImageResource(R.drawable.im_tab_sz_normal);
        tv_home_school.setTextColor(Color.parseColor("#12b7f5"));
        tv_study.setTextColor(Color.parseColor("#cccccc"));
        tv_server.setTextColor(Color.parseColor("#cccccc"));
        tv_me.setTextColor(Color.parseColor("#cccccc"));
    }

    public void initView() {
        curapp_name =(TextView)findViewById(R.id.curapp_name);
        curapp_center =(TextView)findViewById(R.id.curapp_center);
        iv_add = (ImageView) findViewById(R.id.iv_add);
        iv_more = (ImageView) findViewById(R.id.iv_more);

        //tab1
        rl_home_school = (RelativeLayout) findViewById(R.id.rl_home_school);
        iv_home_school = (ImageView) findViewById(R.id.iv_home_school);
        tv_home_school = (TextView) findViewById(R.id.tv_home_school);
        rl_home_school.setOnTouchListener(this);

        //tab2
        rl_study = (RelativeLayout) findViewById(R.id.rl_study);
        iv_study = (ImageView) findViewById(R.id.iv_study);
        tv_study = (TextView) findViewById(R.id.tv_study);
        rl_study.setOnTouchListener(this);
        //tab3
        rl_server = (RelativeLayout) findViewById(R.id.rl_server);
        iv_server = (ImageView) findViewById(R.id.iv_server);
        tv_server = (TextView) findViewById(R.id.tv_server);
        rl_server.setOnTouchListener(this);
        //tab4
        rl_me = (RelativeLayout) findViewById(R.id.rl_me);
        iv_me = (ImageView) findViewById(R.id.iv_me);
        tv_me = (TextView) findViewById(R.id.tv_me);
        rl_me.setOnTouchListener(this);

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int i = view.getId();
        if (i == R.id.rl_home_school) {
            addHomeSchoolFragment();


        } else if (i == R.id.rl_study) {
            addStudyFragment();


        } else if (i == R.id.rl_server) {
            addServerFragment();


        } else if (i == R.id.rl_me) {
            addMeFragment();


        } else {
        }
        ;

        return false;
    }
    private void addMeFragment() {
        if(meFragment ==null){
            meFragment = new MeFragment();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), meFragment);
        iv_home_school.setImageResource(R.drawable.im_tab_index_normal);
        iv_study.setImageResource(R.drawable.im_tab_txl_normal);
        iv_server.setImageResource(R.drawable.im_tab_wd_normal);
        iv_me.setImageResource(R.drawable.im_tab_sz_pressed);
        tv_home_school.setTextColor(Color.parseColor("#cccccc"));
        tv_study.setTextColor(Color.parseColor("#cccccc"));
        tv_server.setTextColor(Color.parseColor("#cccccc"));
        tv_me.setTextColor(Color.parseColor("#12b7f5"));

    }
    private void addServerFragment() {
        if(serverFragment ==null){
            serverFragment = new ServerFragment();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), serverFragment);
        iv_home_school.setImageResource(R.drawable.im_tab_index_normal);
        iv_study.setImageResource(R.drawable.im_tab_txl_normal);
        iv_server.setImageResource(R.drawable.im_tab_wd_pressed);
        iv_me.setImageResource(R.drawable.im_tab_sz_normal);
        tv_home_school.setTextColor(Color.parseColor("#cccccc"));
        tv_study.setTextColor(Color.parseColor("#cccccc"));
        tv_server.setTextColor(Color.parseColor("#12b7f5"));
        tv_me.setTextColor(Color.parseColor("#cccccc"));

    }
    private void addStudyFragment() {
        if(studyFragment ==null){
            studyFragment = new StudyFragment();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), studyFragment);
        iv_home_school.setImageResource(R.drawable.im_tab_index_normal);
        iv_study.setImageResource(R.drawable.im_tab_txl_pressed);
        iv_server.setImageResource(R.drawable.im_tab_wd_normal);
        iv_me.setImageResource(R.drawable.im_tab_sz_normal);
        tv_home_school.setTextColor(Color.parseColor("#cccccc"));
        tv_study.setTextColor(Color.parseColor("#12b7f5"));
        tv_server.setTextColor(Color.parseColor("#cccccc"));
        tv_me.setTextColor(Color.parseColor("#cccccc"));

    }
    private void addHomeSchoolFragment() {
        if(homeSchoolFragment ==null){
            homeSchoolFragment = new HomeSchoolFragment();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), homeSchoolFragment);
//
        iv_home_school.setImageResource(R.drawable.im_tab_index_pressed);
        iv_study.setImageResource(R.drawable.im_tab_txl_normal);
        iv_server.setImageResource(R.drawable.im_tab_wd_normal);
        iv_me.setImageResource(R.drawable.im_tab_sz_normal);
        tv_home_school.setTextColor(Color.parseColor("#12b7f5"));
        tv_study.setTextColor(Color.parseColor("#cccccc"));
        tv_server.setTextColor(Color.parseColor("#cccccc"));
        tv_me.setTextColor(Color.parseColor("#cccccc"));

    }
    /**
     * 添加或删除碎片
     *
     * @param transaction
     * @param fragment
     */
    private void addOrShowFragment(FragmentTransaction transaction,
                                   Fragment fragment) {
        if (defaultFragment == fragment)
            return;

        if (!fragment.isAdded()) { // 如果当前fragment未被添加，则添加到Fragment管理器中
            transaction.hide(defaultFragment)
                    .add(R.id.content_layout, fragment).commit();
        } else {
            transaction.hide(defaultFragment).show(fragment).commit();
        }

        defaultFragment = fragment;
    }
}
