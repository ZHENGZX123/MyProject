package com.zk.testcamera;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Collections;

public class SetPointActivity extends AppCompatActivity {

    private ImageView iv;
    private RelativeLayout cover;
    private ImageView ball1;
    private ImageView ball2;
    private ImageView ball3;
    private ImageView ball4;

    private ImageView currentBall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_point);
        iv = (ImageView) findViewById(R.id.iv);

        String file = getIntent().getStringExtra("snapshot");
        final Bitmap bitmap = BitmapFactory.decodeFile(file);
        iv.setImageBitmap(bitmap);

        cover = (RelativeLayout) findViewById(R.id.cover);
        ball1 = (ImageView) findViewById(R.id.ball1);
        ball2 = (ImageView) findViewById(R.id.ball2);
        ball3 = (ImageView) findViewById(R.id.ball3);
        ball4 = (ImageView) findViewById(R.id.ball4);


        cover.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        getNearestBall((int) event.getX(), (int) event.getY());
                        doMovieBall((int) event.getX(), (int) event.getY());
                        return true;
                    case MotionEvent.ACTION_UP:
                        doMovieBall((int) event.getX(), (int) event.getY());
                        currentBall = null;
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        doMovieBall((int) event.getX(), (int) event.getY());
                        return true;
                }
                return false;
            }
        });
    }

    private void getNearestBall(int x, int y) {
        int x1 = (int) ball1.getX();
        int y1 = (int) ball1.getY();
        int x2 = (int) ball2.getX();
        int y2 = (int) ball2.getY();
        int x3 = (int) ball3.getX();
        int y3 = (int) ball3.getY();
        int x4 = (int) ball4.getX();
        int y4 = (int) ball4.getY();


        int distance1 = (x1 - x) * (x1 - x) + (y1 - y) * (y1 - y);
        int distance2 = (x2 - x) * (x2 - x) + (y2 - y) * (y2 - y);
        int distance3 = (x3 - x) * (x3 - x) + (y3 - y) * (y3 - y);
        int distance4 = (x4 - x) * (x4 - x) + (y4 - y) * (y4 - y);

        ArrayList list = new ArrayList();
        list.add(distance1);
        list.add(distance2);
        list.add(distance3);
        list.add(distance4);

        int min = Integer.parseInt(Collections.min(list).toString());
        if (min == distance1) {
            currentBall = ball1;
        } else if (min == distance2) {
            currentBall = ball2;
        } else if (min == distance3) {
            currentBall = ball3;
        } else if (min == distance4) {
            currentBall = ball4;
        }
    }

    private void doMovieBall(int x, int y) {
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ball1.getWidth(), ball1.getHeight());
        lp.leftMargin = x;
        lp.topMargin = y;
        currentBall.setLayoutParams(lp);
    }

    public void clickOK(View view) {
        int screenWidth;
        int screenHeight;

        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        screenWidth = wm.getDefaultDisplay().getWidth();
        screenHeight = wm.getDefaultDisplay().getHeight();

        Intent i = new Intent(this, Test4Activity.class);
        i.putExtra("snapshot", getIntent().getSerializableExtra("snapshot"));
//        i.putExtra("x1", 0.14f);
//        i.putExtra("y1", 0.11f);
//        i.putExtra("x2", 0.77f);
//        i.putExtra("y2", 0.10f);
//        i.putExtra("x3", 0.88f);
//        i.putExtra("y3", 0.83f);
//        i.putExtra("x4", 0.07f);
//        i.putExtra("y4", 0.86f);
        i.putExtra("x1", ball1.getX() / screenWidth);
        i.putExtra("y1", ball1.getY() / screenHeight);
        i.putExtra("x2", ball2.getX() / screenWidth);
        i.putExtra("y2", ball2.getY() / screenHeight);
        i.putExtra("x3", ball4.getX() / screenWidth);
        i.putExtra("y3", ball4.getY() / screenHeight);
        i.putExtra("x4", ball3.getX() / screenWidth);
        i.putExtra("y4", ball3.getY() / screenHeight);
        startActivity(i);
    }

    class MyDragListener implements View.OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    Log.d("test", "ACTION_DRAG_STARTED");
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    Log.d("test", "ACTION_DRAG_ENTERED");
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    Log.d("test", "ACTION_DRAG_EXITED");
                    break;
                case DragEvent.ACTION_DROP:
                    Log.d("test", "ACTION_DROP");

                    View view = (View) event.getLocalState();
                    int x = (int) event.getX();
                    int y = (int) event.getY();
                    int width = view.getWidth();
                    int height = view.getHeight();

                    Log.d("test", "x = " + x);
                    Log.d("test", "y = " + y);
//                     setContainer();
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    break;
                default:
                    break;
            }
            return true;
        }
    }

}
