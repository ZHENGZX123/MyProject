package cn.kwim.mqttcilent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.zk.myweex.R;
import cn.kwim.mqttcilent.common.utils.Utils;
import cn.kwim.mqttcilent.common.views.ProgressImageView;


public class SplashActivity extends Activity implements View.OnClickListener{

    private Button bt5;
    private View view1,view2,view3,view4,view5;

    private ProgressImageView processImageView ;
    private final int SUCCESS=0;
    int progress=0;

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SUCCESS:
                    Toast.makeText(SplashActivity.this, "图片上传完成", Toast.LENGTH_SHORT).show();
                    processImageView.setVisibility(View.GONE);
                    break;
            }
        }
    };
    private Button bt1;
    private Button bt2;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private LayoutInflater mInflater;
    private List<String> mTitleList = new ArrayList<>();//页卡标题集合
    private List<View> mViewList = new ArrayList<>();//页卡视图集合

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.im_activity_splash);

        initView();

        tab_layout();

    }

    private void tab_layout() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);

        mInflater = LayoutInflater.from(this);
        view1 = mInflater.inflate(R.layout.im_activity_splash, null);
        view2 = mInflater.inflate(R.layout.im_activity_splash, null);
        view3 = mInflater.inflate(R.layout.im_activity_splash, null);
        view4 = mInflater.inflate(R.layout.im_activity_splash, null);
        view5 = mInflater.inflate(R.layout.im_activity_splash, null);



        //添加页卡视图
        mViewList.add(view1);
        mViewList.add(view2);
        mViewList.add(view3);
        mViewList.add(view4);
        mViewList.add(view5);

        //添加页卡标题
        mTitleList.add("No:1");
        mTitleList.add("No:2");
        mTitleList.add("No:3");
        mTitleList.add("No:4");
        mTitleList.add("No:5");


        mTabLayout.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模式
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(0)));//添加tab选项卡
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(2)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(3)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(4)));


        MyPagerAdapter mAdapter = new MyPagerAdapter(mViewList);
        mViewPager.setAdapter(mAdapter);//给ViewPager设置适配器
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。
        mTabLayout.setTabsFromPagerAdapter(mAdapter);//给Tabs设置适配器

        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.i("Tag",tab+"");
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    /**
     * splash 页面
     * 1. 判断如果有数据直接登录， 不跳到登录页面
     * 2. 处理显示时间
     */


    //ViewPager适配器
    class MyPagerAdapter extends PagerAdapter {
        private List<View> mViewList;

        public MyPagerAdapter(List<View> mViewList) {
            this.mViewList = mViewList;
        }

        @Override
        public int getCount() {
            return mViewList.size();//页卡数
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;//官方推荐写法
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViewList.get(position));//添加页卡
            return mViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViewList.get(position));//删除页卡
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList.get(position);//页卡标题
        }

    }



    private void sss() {
        processImageView=(ProgressImageView) findViewById(R.id.iv_chatcontent);
        //模拟图片上传进度
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    if(progress==100){//图片上传完成
                        handler.sendEmptyMessage(SUCCESS);
                        return;
                    }
                    progress++;
                    if(processImageView!=null){
                        processImageView.setProgress(progress);
                    }

                    try{
                        Thread.sleep(200);  //暂停0.2秒
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();


    }

    //初始化
    private void initView() {
        bt5 = (Button)findViewById(R.id.bt5);
        bt1 = (Button)findViewById(R.id.bt1);
        bt2 = (Button)findViewById(R.id.bt2);

        bt5.setOnClickListener(this);
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt5:
               Intent intent = new Intent(this,LoginActivity.class);
                startActivity(intent);
                finish();

                break;
            case  R.id.bt1:{
                sss();
            }
            case R.id.bt2:{

                Toast.makeText(this, Utils.getMacAddress(SplashActivity.this),Toast.LENGTH_SHORT).show();

            }
            default:
                break;
        }

    }
}
