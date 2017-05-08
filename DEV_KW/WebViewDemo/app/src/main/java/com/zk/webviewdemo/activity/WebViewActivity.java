package com.zk.webviewdemo.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zk.webviewdemo.R;
import com.zk.webviewdemo.utils.FileUtils;
import com.zk.webviewdemo.utils.HttpDownload;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import java.io.File;

public class WebViewActivity extends Activity {

    private EditText et;
    private Button go;
    private WebView wv;
    private RelativeLayout rl;
    private TextView title;
    private ProgressBar pb;
    private ProgressDialog pd;
    private boolean first = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        et = (EditText) findViewById(R.id.et);
        go = (Button) findViewById(R.id.go);
        wv = (WebView) findViewById(R.id.wv);
        rl = (RelativeLayout) findViewById(R.id.rl);
        title = (TextView) findViewById(R.id.title);
        pb = (ProgressBar) findViewById(R.id.pb);
        pd = new ProgressDialog(this);
        pd.setMessage("正在下载，请稍等");
        pd.setCancelable(false);


        WebSettings settings = wv.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        wv.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        String url = getSharedPreferences("webview", 0).getString("url", "http://202.104.136.9:8280/weex/yqyd_xs.zip/yqyd/index/index.html");
        et.setText(url);

        clickGo(null);

        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });

        wv.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    // 网页加载完成
                    String url = et.getText().toString();
                    getSharedPreferences("webview", 0).edit().putString("url", url).commit();
                    WebViewActivity.this.title.setText(view.getTitle());
                }
                pb.setProgress(newProgress);
            }

//            @Override
//            public void onReceivedTitle(WebView view, String title) {
//                WebViewActivity.this.title.setText(title);
//            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (wv.canGoBack()) {
                wv.goBack();//返回上一页面
                return true;
            } else {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public void clickInput(View v) {
        rl.setVisibility(View.VISIBLE);
    }

    public void clickGo(View view) {
        String url = et.getText().toString();
        if (url.equals("")) {
            Toast.makeText(this, "不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!url.startsWith("http")) {
            Toast.makeText(this, "请以http或https开头", Toast.LENGTH_SHORT).show();
            return;
        }
        rl.setVisibility(View.GONE);

        if (url.contains("zip")) {
            //询问是否替换
            downloadFile(url);
        } else {
            wv.loadUrl(url);
        }
    }

    private void downloadFile(final String url) {
        new Thread() {
            @Override
            public void run() {
                showPD();

                final String root = "/mnt/sdcard/webview/";
                if (!new File(root).exists()) {
                    new File(root).mkdirs();
                }
                //http://192.168.31.6/a/test.zip/aaa/ddd.html
                int index = url.indexOf("zip") + 3;
                final String zipUrl = url.substring(0, index);
                final String fileName = new File(zipUrl).getName();
                final String route = url.replace(zipUrl, "");
                final String filePath = root + fileName;

                if (new File(filePath).exists()) {
                    if (first) {
                        first = false;
                        load(filePath, route);
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AlertDialog.Builder builder = new AlertDialog.Builder(WebViewActivity.this);
                                builder.setMessage("包已存在，是否替换旧的包？");
                                builder.setTitle("提示");
                                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        new Thread() {
                                            @Override
                                            public void run() {
                                                new File(filePath).delete();
                                                FileUtils.delFolder(filePath.replace(".zip", ""));
                                                try {
                                                    doDownload(zipUrl, root, fileName, filePath, route);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                    dismissPD();
                                                }
                                            }
                                        }.start();
                                    }
                                });
                                builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        load(filePath, route);
                                    }
                                });
                                builder.create().show();
                            }
                        });
                    }
                } else {
                    try {
                        doDownload(zipUrl, root, fileName, filePath, route);
                    } catch (Exception e) {
                        e.printStackTrace();
                        dismissPD();
                    }
                }
            }
        }.start();
    }

    private void doDownload(String zipUrl, String root, String fileName, String filePath, String route) throws Exception {
        int ret = new HttpDownload().downFile(zipUrl, root, fileName);
        Log.d("test", "download ret = " + ret);
        if (ret != 0) {
            toast("下载失败，请稍后再试");
            throw new Exception();
        }
        //下载完成后解压
        unZip(filePath);
        //加载路径下的index.html
        load(filePath, route);
    }

    private void unZip(String filePath) throws Exception {
        ZipFile zFile = new ZipFile(filePath); // 首先创建ZipFile指向磁盘上的.zip文件
        zFile.setFileNameCharset("GBK"); // 设置文件名编码，在GBK系统中需要设置
        if (!zFile.isValidZipFile()) { // 验证.zip文件是否合法，包括文件是否存在、是否为zip文件、是否被损坏等
            throw new ZipException();
        }
        File destDir = new File(filePath.replace(".zip", "")); // 解压目录
        if (destDir.isDirectory() && !destDir.exists()) {
            destDir.mkdir();
        }
//            if (zFile.isEncrypted()) {
//                zFile.setPassword(passwd.toCharArray()); // 设置密码
//            }
        zFile.extractAll(destDir.getAbsolutePath()); // 将文件抽出到解压目录(解压)
    }

    private void load(final String filePath, final String route) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                wv.loadUrl(("file://" + filePath + route).replace(".zip", ""));
                dismissPD();
            }
        });
    }

    private void showPD() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pd.show();
            }
        });
    }


    private void dismissPD() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pd.dismiss();
            }
        });
    }


    private void toast(final String txt) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(WebViewActivity.this, txt, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
