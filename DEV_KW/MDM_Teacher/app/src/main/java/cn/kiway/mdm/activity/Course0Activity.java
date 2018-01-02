package cn.kiway.mdm.activity;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.tencent.smtt.sdk.TbsReaderView;

import java.io.File;
import java.util.ArrayList;

import cn.kiway.mdm.entity.KnowledgePoint;
import cn.kiway.mdm.service.RecordService;
import cn.kiway.mdm.teacher.R;
import cn.kiway.mdm.util.Utils;

import static cn.kiway.mdm.entity.KnowledgePoint.TYPE0;
import static cn.kiway.mdm.entity.KnowledgePoint.TYPE1;
import static cn.kiway.mdm.entity.KnowledgePoint.TYPE_END;
import static cn.kiway.mdm.util.ResultMessage.RECORD_REQUEST_CODE;

/**
 * Created by Administrator on 2017/12/28.
 */

//未上课
public class Course0Activity extends ScreenSharingActivity {

    private FrameLayout x5FileLayout;
    private TbsReaderView readerView;
    private ListView lv;
    private MyAdapter adapter;
    private ArrayList<KnowledgePoint> KnowledgePoints = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course0);

        initView();
        initData();
        initRecord();
    }

    @Override
    public void initView() {
        super.initView();
        titleName.setText("1.1 正数和负数、数轴");
        x5FileLayout = (FrameLayout) findViewById(R.id.x5FileLayout);
        lv = (ListView) findViewById(R.id.KnowledgePointLV);
        adapter = new MyAdapter();
        lv.setAdapter(adapter);
    }

    private void initData() {
        KnowledgePoint c1 = new KnowledgePoint();
        c1.title = "知识点1：用正数、负数表示具有相反意义的量";
        c1.type = TYPE0;
        KnowledgePoint c2 = new KnowledgePoint();
        c2.title = "知识点2：数轴的概念及画法";
        c2.type = TYPE1;
        KnowledgePoint c3 = new KnowledgePoint();
        c3.title = "知识点3：三角形勾股定理";
        c3.type = TYPE0;
        KnowledgePoint c4 = new KnowledgePoint();
        c4.title = "知识点4：三角形内角和";
        c4.type = TYPE1;
        KnowledgePoint c5 = new KnowledgePoint();
        c5.type = TYPE_END;
        KnowledgePoints.add(c1);
        KnowledgePoints.add(c2);
        KnowledgePoints.add(c3);
        KnowledgePoints.add(c4);
        KnowledgePoints.add(c5);
        adapter.notifyDataSetChanged();
    }

    private void initRecord() {
        projectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);

        Intent intent = new Intent(this, RecordService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RECORD_REQUEST_CODE && resultCode == RESULT_OK) {
            mediaProjection = projectionManager.getMediaProjection(resultCode, data);
            recordService.setMediaProject(mediaProjection);
            recordService.startRecord();
        }
    }

    @Override
    public void onBackPressed() {
        if (x5FileLayout.isShown()) {
            readerView.onStop();
            readerView = null;
            x5FileLayout.removeAllViews();
            x5FileLayout.setVisibility(View.GONE);
        } else {
            super.onBackPressed();
        }
    }

    //-------------------------tools1----------------------

    public void jieping(View view) {
        Utils.GetandSaveCurrentImage(this);
    }

    public void paizhao(View view) {
        //众人通是拍照后，放在画布上。
        String picPath = "/mnt/sdcard/" + System.currentTimeMillis() + ".jpg";
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uri = Uri.fromFile(new File(picPath));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivity(intent);
    }

    public void huabi(View view) {
        //等彭毅
        startActivity(new Intent(this, WhiteBoardActivity.class));
    }

    private boolean tuiping = false;

    public void tuiping(View view) {
        //先接入声网
        if (tuiping) {
            toast("结束推屏");
            mRtcEngine.leaveChannel();
            stopCapture();
        } else {
            toast("开始推屏");
            initModules();
            startCapture();
            mRtcEngine.joinChannel(null, "kiway", "", 0);
        }
        tuiping = !tuiping;
    }

    public void chaping(View view) {
        //查看学生屏幕，需要获取学生列表。
        startActivity(new Intent(this, MultiScreenActivity.class));
    }

    public void suoping(View view) {
        //锁定学生屏幕，需要获取学生列表。
    }

    public void wenjian(View view) {
        //给学生发文件，需要获取学生列表。
    }

    public void shezhi(View view) {
        //设置？？？不知道是什么。测试用
    }

    //-------------------------tools2----------------------
    public void tongji(View view) {
        //知识点统计，给全班发送统计命令。
        final Dialog dialog = new Dialog(this, R.style.popupDialog);
        dialog.setContentView(R.layout.dialog_tongji);
        dialog.show();
        Button tongji = (Button) dialog.findViewById(R.id.tongji);
        Button cancel = (Button) dialog.findViewById(R.id.cancel);
        Button close = (Button) dialog.findViewById(R.id.close);
        ListView lv = (ListView) dialog.findViewById(R.id.lv);

        tongji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    public void dianmingda(View view) {
        //点名答，需要获取学生列表。
    }

    public void qiangda(View view) {
        //抢答，给全班发送抢答命令。
        qiangda_suijichouda_ceping(1);
    }

    public void suijichouda(View view) {
        //随机抽答，随机找几个发命令。
        qiangda_suijichouda_ceping(2);
    }

    public void ceping(View view) {
        //测评，给全班发测评命令
        qiangda_suijichouda_ceping(3);
    }

    private void qiangda_suijichouda_ceping(int type) {
        final Dialog dialog = new Dialog(this, R.style.popupDialog);
        dialog.setContentView(R.layout.dialog_qiangda);
        dialog.show();
        Button kaishidati = (Button) dialog.findViewById(R.id.kaishidati);
        Button close = (Button) dialog.findViewById(R.id.close);
        ListView lv = (ListView) dialog.findViewById(R.id.lv);

        kaishidati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                startActivity(new Intent(Course0Activity.this, ResultActivity.class).putExtra("type", type));
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    //-------------------------------录屏相关-----------------------------
    private MediaProjectionManager projectionManager;
    private MediaProjection mediaProjection;
    private RecordService recordService;


    public void startRecord() {
        Intent captureIntent = projectionManager.createScreenCaptureIntent();
        startActivityForResult(captureIntent, RECORD_REQUEST_CODE);
    }

    public void stopRecord() {
        if (recordService.isRunning()) {
            recordService.stopRecord();
        }
        //上传视频文件。。。
        //上传课程记录。。。
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            RecordService.RecordBinder binder = (RecordService.RecordBinder) service;
            recordService = binder.getRecordService();
            recordService.setConfig(metrics.widthPixels, metrics.heightPixels, metrics.densityDpi);
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
        }
    };

    //------------------------内容列表相关-------------------------------------------------

    private class MyAdapter extends BaseAdapter {

        private final LayoutInflater inflater;

        public MyAdapter() {
            inflater = LayoutInflater.from(Course0Activity.this);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            ViewHolder holder;
            if (rowView == null) {
                rowView = inflater.inflate(R.layout.item_knowledge_point, null);
                holder = new ViewHolder();

                holder.title = (TextView) rowView.findViewById(R.id.title);
                holder.clock = (ImageView) rowView.findViewById(R.id.clock);
                holder.type0RL = (LinearLayout) rowView.findViewById(R.id.type0RL);
                holder.type1RL = (LinearLayout) rowView.findViewById(R.id.type1RL);
                holder.type_2RL = (LinearLayout) rowView.findViewById(R.id.type_2RL);
                holder.endBtn = (Button) rowView.findViewById(R.id.endBtn);
                holder.ball = (ImageView) rowView.findViewById(R.id.ball);
                holder.line2 = (TextView) rowView.findViewById(R.id.line2);

                rowView.setTag(holder);
            } else {
                holder = (ViewHolder) rowView.getTag();
            }

            if (position == 0) {
                holder.clock.setVisibility(View.VISIBLE);
            } else {
                holder.clock.setVisibility(View.GONE);
            }

            final KnowledgePoint s = KnowledgePoints.get(position);
            holder.title.setText(s.title);

            if (s.type == TYPE0) {
                holder.type0RL.setVisibility(View.VISIBLE);
                holder.type1RL.setVisibility(View.GONE);
                holder.type_2RL.setVisibility(View.GONE);
                holder.ball.setVisibility(View.VISIBLE);
                holder.line2.setVisibility(View.VISIBLE);

                //add content0
                addContent0(holder);

            } else if (s.type == TYPE1) {
                holder.type0RL.setVisibility(View.GONE);
                holder.type1RL.setVisibility(View.VISIBLE);
                holder.type_2RL.setVisibility(View.GONE);
                holder.ball.setVisibility(View.VISIBLE);
                holder.line2.setVisibility(View.VISIBLE);

                //add content1
                addContent1(holder);

            } else if (s.type == TYPE_END) {
                holder.type0RL.setVisibility(View.GONE);
                holder.type1RL.setVisibility(View.GONE);
                holder.type_2RL.setVisibility(View.VISIBLE);
                holder.ball.setVisibility(View.GONE);
                holder.line2.setVisibility(View.GONE);
            }


            holder.endBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utils.xiake(Course0Activity.this);
                    finish();
                }
            });

            return rowView;
        }

        private void addContent1(ViewHolder holder) {
            holder.type1RL.removeAllViews();
            LinearLayout layout_doc = (LinearLayout) inflater.inflate(R.layout.layout_doc, null);
            holder.type1RL.addView(layout_doc, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            layout_doc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openFileByX5("");
                }
            });
        }

        private void addContent0(ViewHolder holder) {
            holder.type0RL.removeAllViews();
            TextView tv = new TextView(Course0Activity.this);
            tv.setTextColor(Color.WHITE);
            tv.setText("（一）创设情境，导入新课");
            holder.type0RL.addView(tv);
            TextView tv2 = new TextView(Course0Activity.this);
            tv2.setTextColor(Color.WHITE);
            tv2.setText("（二）合作交流，解读探究");
            holder.type0RL.addView(tv2);
        }

        public class ViewHolder {
            public TextView title;
            public ImageView clock;
            public LinearLayout type0RL;
            public LinearLayout type1RL;
            public LinearLayout type_2RL;
            public Button endBtn;
            public TextView line2;
            public ImageView ball;
        }

        @Override
        public int getCount() {
            return KnowledgePoints.size();
        }

        @Override
        public KnowledgePoint getItem(int arg0) {
            return KnowledgePoints.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }
    }

    public void openFileByX5(String path) {
        path = "/mnt/sdcard/test.docx";

        String finalPath = path;

        x5FileLayout.setVisibility(View.VISIBLE);

        readerView = new TbsReaderView(this, new TbsReaderView.ReaderCallback() {
            @Override
            public void onCallBackAction(Integer integer, Object o, Object o1) {

            }
        });
        //通过bundle把文件传给x5,打开的事情交由x5处理
        Bundle bundle = new Bundle();
        //传递文件路径
        bundle.putString("filePath", finalPath);
        //加载插件保存的路径
        bundle.putString("tempPath", Environment.getExternalStorageDirectory() + File.separator + "temp");
        //加载文件前的初始化工作,加载支持不同格式的插件
        boolean b = readerView.preOpen(Utils.getFileType(finalPath), false);
        if (b) {
            readerView.openFile(bundle);
        } else {
            toast("打开文件失败");
        }
        x5FileLayout.addView(readerView);
    }
}
