package cn.kiway.mdm.utils;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

/**
 * Created by Administrator on 2017/10/31.
 */

public class MyWebChromeClient extends WebChromeClient {
    private ProgressBar pg1;

    public MyWebChromeClient(ProgressBar pg1) {
        this.pg1 = pg1;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        // TODO 自动生成的方法存根
        if (newProgress == 100) {
            pg1.setVisibility(View.GONE);//加载完网页进度条消失
        } else {
            pg1.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
            pg1.setProgress(newProgress);//设置进度值
        }
    }
}
