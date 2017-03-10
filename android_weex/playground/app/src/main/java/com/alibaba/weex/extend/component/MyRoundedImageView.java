package com.alibaba.weex.extend.component;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;

import com.alibaba.weex.extend.view.RoundedImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.taobao.weex.WXSDKInstance;
import com.taobao.weex.dom.WXDomObject;
import com.taobao.weex.ui.component.WXComponent;
import com.taobao.weex.ui.component.WXComponentProp;
import com.taobao.weex.ui.component.WXVContainer;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class MyRoundedImageView extends WXComponent<RoundedImageView> {

    public MyRoundedImageView(WXSDKInstance instance, WXDomObject dom, WXVContainer parent) {
        super(instance, dom, parent);
    }

    @Override
    protected RoundedImageView initComponentHostView(@NonNull Context context) {
        RoundedImageView view = new RoundedImageView(context);
        return view;
    }

    @WXComponentProp(name = "url")
    public void setUrl(String url) {
        Log.d("test", "url = " + url);
        final RoundedImageView iv = ((RoundedImageView) getHostView());
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv.setCornerRadius(50.0f);

        if (url.contains("zip")) {
            url = url.replace("file://", "");

            int index = url.indexOf("zip");
            String zipPath = url.substring(0, index + 3);
            System.out.println("zipPath = " + zipPath);

            String targetFile = url.replace(zipPath, "");
            System.out.println("targetFile = " + targetFile);

            try {
                ZipFile zf = new ZipFile(zipPath);
                InputStream in = new BufferedInputStream(new FileInputStream(
                        zipPath));
                ZipInputStream zin = new ZipInputStream(in);
                ZipEntry ze;
                StringBuilder sb = new StringBuilder();
                while ((ze = zin.getNextEntry()) != null) {
                    if (ze.isDirectory()) {
                        System.out.println("directory = " + ze.getName());
                    } else {
                        System.out.println("file = " + ze.getName());
                        if (targetFile.contains(ze.getName())) {
                            zf.getInputStream(ze);
                            Bitmap result = new BitmapDrawable(zf.getInputStream(ze)).getBitmap();
                            iv.setImageBitmap(result);
                            break;
                        }
                    }
                }
                zin.closeEntry();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("e = " + e.toString());
            }
        } else {
            ImageLoader.getInstance().displayImage(url, iv, getLoaderOptions());
        }
    }


    public static String readFileInZip(String file) {
        int index = file.indexOf("zip");
        String zipPath = file.substring(0, index + 3);
        System.out.println("zipPath = " + zipPath);

        String targetFile = file.replace(zipPath, "");
        System.out.println("targetFile = " + targetFile);

        try {
            ZipFile zf = new ZipFile(zipPath);
            InputStream in = new BufferedInputStream(new FileInputStream(
                    zipPath));
            ZipInputStream zin = new ZipInputStream(in);
            ZipEntry ze;
            StringBuilder sb = new StringBuilder();
            while ((ze = zin.getNextEntry()) != null) {
                if (ze.isDirectory()) {
                    System.out.println("directory = " + ze.getName());
                } else {
                    System.out.println("file = " + ze.getName());
                    if (targetFile.contains(ze.getName())) {
                        BufferedReader localBufferedReader = new BufferedReader(
                                new InputStreamReader(zf.getInputStream(ze)));
                        char[] data = new char[2048];
                        int len = -1;
                        while ((len = localBufferedReader.read(data)) > 0) {
                            sb.append(data, 0, len);
                        }
                    }
                }
            }
            zin.closeEntry();
            System.out.println("sb = " + sb.toString());
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("e = " + e.toString());
            return "";
        }
    }

    public DisplayImageOptions getLoaderOptions() {
        DisplayImageOptions.Builder displayImageOptionsBuilder = new DisplayImageOptions.Builder();
        // displayImageOptionsBuilder.showImageForEmptyUri(R.drawable.loading);
        displayImageOptionsBuilder.cacheInMemory(false);
        displayImageOptionsBuilder.cacheOnDisc(true);
        // RoundedBitmapDisplayer displayer = new RoundedBitmapDisplayer(10);
        // displayImageOptionsBuilder.displayer(displayer);
        DisplayImageOptions defaultDisplayImageOptions = displayImageOptionsBuilder
                .build();
        return defaultDisplayImageOptions;
    }
}
