package com.leon.lfilepickerlibrary.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.leon.lfilepickerlibrary.R;
import com.leon.lfilepickerlibrary.adapter.PathAdapter;
import com.leon.lfilepickerlibrary.filter.LFileFilter;
import com.leon.lfilepickerlibrary.model.ParamEntity;
import com.leon.lfilepickerlibrary.utils.Constant;
import com.leon.lfilepickerlibrary.utils.FileUtils;
import com.leon.lfilepickerlibrary.widget.EmptyRecyclerView;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LFilePickerActivity extends AppCompatActivity {

    private final String TAG = "FilePickerLeon";
    private EmptyRecyclerView mRecylerView;
    private View mEmptyView;
    private TextView mTvPath;
    private TextView mTvBack;
    private Button mBtnAddBook;
    private String mPath;
    private List<File> mListFiles;
    private ArrayList<String> mListNumbers = new ArrayList<String>();//存放选中条目的数据地址
    private PathAdapter mPathAdapter;
    private Toolbar mToolbar;
    private ParamEntity mParamEntity;
    private final int RESULTCODE = 1024;
    private LFileFilter mFilter;
    private boolean mIsAllSelected = false;
    private Menu mMenu;
    private String choosePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lfile_picker);
        mParamEntity = (ParamEntity) getIntent().getExtras().getSerializable("param");
        initView();
        initImageCache();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initToolbar();
        if (!checkSDState()) {
            Toast.makeText(this, R.string.NotFoundPath, Toast.LENGTH_SHORT).show();
            return;
        }
        mPath = mParamEntity.getFilePath();
        choosePath = mParamEntity.getFilePath();
        mTvPath.setText(mPath);
        mFilter = new LFileFilter(mParamEntity.getFileTypes());
        mListFiles = getFileList(mPath);
        mPathAdapter = new PathAdapter(mListFiles, this, mFilter, mParamEntity.isMutilyMode());
        mRecylerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mPathAdapter.setmIconStyle(mParamEntity.getIconStyle());
        mRecylerView.setAdapter(mPathAdapter);
        mRecylerView.setmEmptyView(mEmptyView);
        initListener();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            backFile();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 更新Toolbar展示
     */
    private void initToolbar() {
        if (mParamEntity.getTitle() != null) {
            mToolbar.setTitle(mParamEntity.getTitle());
        }
        if (mParamEntity.getTitleColor() != null) {
            mToolbar.setTitleTextColor(Color.parseColor(mParamEntity.getTitleColor())); //设置标题颜色
        }
        if (mParamEntity.getBackgroundColor() != null) {
            mToolbar.setBackgroundColor(Color.parseColor(mParamEntity.getBackgroundColor()));
        }
        if (!mParamEntity.isMutilyMode()) {
            mBtnAddBook.setVisibility(View.GONE);
        }
        switch (mParamEntity.getBackIcon()) {
            case Constant.BACKICON_STYLEONE:
                mToolbar.setNavigationIcon(R.mipmap.backincostyleone);
                break;
            case Constant.BACKICON_STYLETWO:
                mToolbar.setNavigationIcon(R.mipmap.backincostyletwo);
                break;
            case Constant.BACKICON_STYLETHREE:
                //默认风格
                break;
        }
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 添加点击事件处理
     */
    private void initListener() {
        // 返回目录上一级
        mTvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backFile();
            }
        });
        mPathAdapter.setOnItemClickListener(new PathAdapter.OnItemClickListener() {
            @Override
            public void click(int position) {
                if (mParamEntity.isMutilyMode()) {
                    if (mListFiles.get(position).isDirectory()) {
                        //如果当前是目录，则进入继续查看目录
                        chekInDirectory(position);
                        mPathAdapter.updateAllSelelcted(false);
                        mIsAllSelected = false;
                        updateMenuTitle();
                        mBtnAddBook.setText(getString(R.string.Selected));
                    } else {
                        //如果已经选择则取消，否则添加进来
                        if (mListNumbers.contains(mListFiles.get(position).getAbsolutePath())) {
                            mListNumbers.remove(mListFiles.get(position).getAbsolutePath());
                        } else {
                            mListNumbers.add(mListFiles.get(position).getAbsolutePath());
                        }
                        if (mParamEntity.getAddText() != null) {
                            mBtnAddBook.setText(mParamEntity.getAddText() + "( " + mListNumbers.size() + " )");
                        } else {
                            mBtnAddBook.setText(getString(R.string.Selected) + "( " + mListNumbers.size() + " )");
                        }

                        //先判断是否达到最大数量，如果数量达到上限提示，否则继续添加
                        if (mParamEntity.getMaxNum() > 0 && mListNumbers.size() > mParamEntity.getMaxNum()) {
                            Toast.makeText(LFilePickerActivity.this, R.string.OutSize, Toast.LENGTH_SHORT).show();
                            return;
                        }

                    }
                } else {
                    //单选模式直接返回
                    if (mListFiles.get(position).isDirectory()) {
                        chekInDirectory(position);
                        return;
                    }
                    mListNumbers.add(mListFiles.get(position).getAbsolutePath());
                    chooseDone();
                }

            }
        });

        mBtnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListNumbers.size() < 1) {
                    String info = mParamEntity.getNotFoundFiles();
                    if (TextUtils.isEmpty(info)) {
                        Toast.makeText(LFilePickerActivity.this, R.string.NotFoundBooks, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LFilePickerActivity.this, info, Toast.LENGTH_SHORT).show();
                    }

                } else {
                    //返回
                    chooseDone();
                }
            }
        });
    }

    public void backFile() {
        String tempPath = new File(mPath).getParent();
        if (tempPath == null) {
            return;
        }
        if (choosePath.equals(mPath)) {
            finish();
            return;
        }
        mPath = tempPath;
        mListFiles = getFileList(mPath);
        mPathAdapter.setmListData(mListFiles);
        mPathAdapter.updateAllSelelcted(false);
        mIsAllSelected = false;
        updateMenuTitle();
        mBtnAddBook.setText(getString(R.string.Selected));
        mRecylerView.scrollToPosition(0);
        setShowPath(mPath);
        //清除添加集合中数据
        mListNumbers.clear();
        if (mParamEntity.getAddText() != null) {
            mBtnAddBook.setText(mParamEntity.getAddText());
        } else {
            mBtnAddBook.setText(R.string.Selected);
        }
    }

    /**
     * 点击进入目录
     *
     * @param position
     */
    private void chekInDirectory(int position) {
        mPath = mListFiles.get(position).getAbsolutePath();
        setShowPath(mPath);
        //更新数据源
        mListFiles = getFileList(mPath);
        mPathAdapter.setmListData(mListFiles);
        mPathAdapter.notifyDataSetChanged();
        mRecylerView.scrollToPosition(0);
    }

    /**
     * 完成提交
     */
    private void chooseDone() {
        //判断是否数量符合要求
        if (mParamEntity.getMaxNum() > 0 && mListNumbers.size() > mParamEntity.getMaxNum()) {
            Toast.makeText(LFilePickerActivity.this, R.string.OutSize, Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent();
        intent.putStringArrayListExtra("paths", mListNumbers);
        setResult(RESULT_OK, intent);
        this.finish();
    }

    /**
     * 根据地址获取当前地址下的所有目录和文件，并且排序
     *
     * @param path
     * @return List<File>
     */
    private List<File> getFileList(String path) {
        File file = new File(path);
        List<File> list = FileUtils.getFileListByDirPath(path, mFilter);
        return list;
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mRecylerView = (EmptyRecyclerView) findViewById(R.id.recylerview);
        mTvPath = (TextView) findViewById(R.id.tv_path);
        mTvBack = (TextView) findViewById(R.id.tv_back);
        mBtnAddBook = (Button) findViewById(R.id.btn_addbook);
        mEmptyView = findViewById(R.id.empty_view);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mParamEntity.getAddText() != null) {
            mBtnAddBook.setText(mParamEntity.getAddText());
        }
    }

    /**
     * 检测SD卡是否可用
     */
    private boolean checkSDState() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 显示顶部地址
     *
     * @param path
     */
    private void setShowPath(String path) {
        mTvPath.setText(path);
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_toolbar, menu);
        mMenu = menu;
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_selecteall_cancel) {
            //将当前目录下所有文件选中或者取消
            mPathAdapter.updateAllSelelcted(!mIsAllSelected);
            mIsAllSelected = !mIsAllSelected;
            if (mIsAllSelected) {
                for (File mListFile : mListFiles) {
                    if (!mListFile.isDirectory()) {
                        mListNumbers.add(mListFile.getAbsolutePath());
                    }
                    if (mParamEntity.getAddText() != null) {
                        mBtnAddBook.setText(mParamEntity.getAddText() + "( " + mListNumbers.size() + " )");
                    } else {
                        mBtnAddBook.setText(getString(R.string.Selected) + "( " + mListNumbers.size() + " )");
                    }
                }
            } else {
                mListNumbers.clear();
                mBtnAddBook.setText(getString(R.string.Selected));
            }
            updateMenuTitle();
        }
        return true;
    }

    /**
     * 更新选项菜单文字
     */
    public void updateMenuTitle() {
//        if (mIsAllSelected) {
//            mMenu.getItem(0).setTitle(getString(R.string.Cancel));
//        } else {
//            mMenu.getItem(0).setTitle(getString(R.string.SelectAll));
//        }
    }

    private void initImageCache() {
        DisplayMetrics displayMetrics = getApplicationContext().getResources()
                .getDisplayMetrics();

        //设置默认显示情况
        DisplayImageOptions.Builder displayImageOptionsBuilder = new DisplayImageOptions.Builder();
        // displayImageOptionsBuilder.showImageForEmptyUri(R.drawable.loading);
        // // 空uri的情况
        displayImageOptionsBuilder.cacheInMemory(false); // 缓存在内存
        displayImageOptionsBuilder.cacheOnDisc(true); // 缓存在磁盘
        displayImageOptionsBuilder.bitmapConfig(Bitmap.Config.ARGB_8888);
        displayImageOptionsBuilder
                .imageScaleType(ImageScaleType.NONE);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                this)
                .memoryCacheExtraOptions(displayMetrics.widthPixels,
                        displayMetrics.heightPixels)
                // maxwidth, max height，即保存的每个缓存文件的最大长宽
                .threadPoolSize(10)
                // 线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 1)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCacheExtraOptions(200, 200)
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
                // You can pass your own memory cache
                // implementation/你可以通过自己的内存缓存实现
                // .memoryCacheSize(2 * 1024 * 1024)
                // .discCacheSize(50 * 1024 * 1024)
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                // 将保存的时候的URI名称用MD5 加密
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .discCacheFileCount(100)
                // 缓存的文件数量
                // .discCache(new UnlimitedDiscCache(cacheDir))
                // 自定义缓存路径
                .denyCacheImageMultipleSizesInMemory()
                // .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .defaultDisplayImageOptions(displayImageOptionsBuilder.build())
                .imageDownloader(
                        new BaseImageDownloader(this, 5 * 1000, 30 * 1000)) // connectTimeout
                /* .writeDebugLogs() */.build(); // Remove for release app
        ImageLoader.getInstance().init(config);
    }
}
