package com.crazy.wang.photoscan;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.crazy.wang.photoscan.model.Images;
import com.crazy.wang.photoscan.ui.PhotoViewPager;
import com.crazy.wang.photoscan.util.DisplayImageOptionsUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.Arrays;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;
import uk.co.senab.photoview.PhotoViewAttacher.OnViewTapListener;

/**
 * @author Cloudsoar(wangyb)
 * @datetime 2015-11-19 20:53 GMT+8
 * @email 395044952@qq.com
 */
public class PhotoviewActivity extends AppCompatActivity implements OnViewTapListener {
    private PhotoViewPager mViewPager;
    private PhotoView mPhotoView;
    private List<String> mImgUrls;
    private PhotoViewAdapter mAdapter;
    private PhotoViewAttacher mAttacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_viewpager);
        setupView();
        setupData();
    }

    private void setupView() {
        mViewPager = (PhotoViewPager) findViewById(R.id.view_pager);
        mPhotoView = (PhotoView) findViewById(R.id.photo);
    }

    private void setupData() {
        int mCurrentUrl = getIntent().getIntExtra(MainActivity.PHOTO_POSITION, 0);
        mImgUrls = Arrays.asList(Images.imageThumbUrls);
        mAdapter = new PhotoViewAdapter();
        mViewPager.setAdapter(mAdapter);
        //设置当前需要显示的图片
        mViewPager.setCurrentItem(mCurrentUrl);
    }

    @Override
    public void onViewTap(View view, float x, float y) {
        finish();
    }

    class PhotoViewAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = container.inflate(PhotoviewActivity.this,
                    R.layout.item_photo_view, null);
            mPhotoView = (PhotoView) view.findViewById(R.id.photo);
            //使用ImageLoader加载图片
            ImageLoader.getInstance().displayImage(
                    mImgUrls.get(position), mPhotoView, DisplayImageOptionsUtil.defaultOptions);
            //给图片增加点击事件
            mAttacher = new PhotoViewAttacher(mPhotoView);
            mAttacher.setOnViewTapListener(PhotoviewActivity.this);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            mAttacher = null;
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return mImgUrls.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
