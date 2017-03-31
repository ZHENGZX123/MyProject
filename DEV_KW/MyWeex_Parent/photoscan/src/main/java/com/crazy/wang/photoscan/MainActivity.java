package com.crazy.wang.photoscan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.crazy.wang.photoscan.adapter.GridAdapter;
import com.crazy.wang.photoscan.model.Images;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnItemClickListener{
    private GridView mGridView;
    private GridAdapter mAdapter;
    private List<String> mImgUrls;
    public static final String PHOTO_POSITION = "photo_position";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupView();
        setupEvent();
        setupData();

    }

    private void setupView(){
        mGridView = (GridView) findViewById(R.id.grid_photo);
    }

    private void setupEvent(){
        mGridView.setOnItemClickListener(this);
    }
    private void setupData(){
        mImgUrls = Arrays.asList(Images.imageThumbUrls);
        mAdapter = new GridAdapter(this,R.layout.item_grid,mImgUrls);
        mGridView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this,PhotoviewActivity.class);
        intent.putExtra(PHOTO_POSITION,position);
        startActivity(intent);
    }
}
