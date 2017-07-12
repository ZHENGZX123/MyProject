package com.zk.testcamera.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.zk.testcamera.R;
import com.zk.testcamera.view.DrawView;

public class SetPointActivity extends AppCompatActivity {

    private DrawView dv;
    private RelativeLayout cover;
    private int screenWidth;
    private int screenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_point);
        cover = (RelativeLayout) findViewById(R.id.cover);
        dv = (DrawView) findViewById(R.id.dv);

        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        screenWidth = wm.getDefaultDisplay().getWidth();
        screenHeight = wm.getDefaultDisplay().getHeight();
        String file = getIntent().getStringExtra("snapshot");
        final Bitmap bitmap = BitmapFactory.decodeFile(file);

        dv.init(screenWidth, screenHeight, bitmap);

        cover.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        dv.doTouchDown((int) event.getX(), (int) event.getY());
                        return true;
                    case MotionEvent.ACTION_UP:
                        dv.doTouchUp((int) event.getX(), (int) event.getY());
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        dv.doTouchMove((int) event.getX(), (int) event.getY());
                        return true;
                }
                return false;
            }
        });
    }

    public void clickOK(View view) {
        Intent i = new Intent(this, Test4Activity.class);
        i.putExtra("snapshot", getIntent().getSerializableExtra("snapshot"));
        i.putExtra("x1", (float) dv.x1 / screenWidth);
        i.putExtra("y1", (float) dv.y1 / screenHeight);
        i.putExtra("x2", (float) dv.x2 / screenWidth);
        i.putExtra("y2", (float) dv.y2 / screenHeight);
        i.putExtra("x3", (float) dv.x3 / screenWidth);
        i.putExtra("y3", (float) dv.y3 / screenHeight);
        i.putExtra("x4", (float) dv.x4 / screenWidth);
        i.putExtra("y4", (float) dv.y4 / screenHeight);
        startActivity(i);
    }
}
