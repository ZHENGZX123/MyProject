package com.zk.testcamera.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.zk.testcamera.R;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView1 = (ImageView) findViewById(R.id.imageView1);
    }

    public void clickSnapshot(View view) {
        startActivityForResult(new Intent(this, CameraActivity.class), 999);
    }

    public void clickTest(View view) {
        startActivity(new Intent(this, TestActivity.class));
    }

    public void clickTest2(View view) {
        startActivity(new Intent(this, Test3Activity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 999) {
            final String filepath = data.getStringExtra("snapshot");
            Bitmap bm = BitmapFactory.decodeFile(filepath);
            imageView1.setImageBitmap(bm);

            imageView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setAction(android.content.Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse(filepath), "image/*");
                    startActivity(intent);
                }
            });
        }
    }
}
