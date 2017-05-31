package com.zk.myweex.utils;

import android.content.Context;
import android.util.Log;

import com.zk.myweex.WXApplication;
import com.zk.myweex.entity.ZipPackage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.kiway.baas.sdk.KWQuery;
import cn.kiway.baas.sdk.model.module.Module;
import cn.kiway.baas.sdk.model.module.Package;

/**
 * Created by Administrator on 2017/2/28.
 */

public class VersionUpManager {

    private Context context;

    public void init(Context c) {
        this.context = c;
    }

    public void getRemoteVersion() {
        try {
            File root = new File(WXApplication.PATH);
            File[] files = root.listFiles();
            if (files == null) {
                return;
            }
            if (files.length == 0) {
                return;
            }
            for (File f : files) {
                String name = f.getName();
                Log.d("version", "====================检测" + name + "的新版本===============================");
                ZipPackage zip = new MyDBHelper(context).getAllZipPackageByName(name);
                Module s = new Module().findOne(new KWQuery().equalTo("id", name.replace(".zip", "")));
                Log.d("version", "s  = " + s.toString());
                Package p = new Package().findOne(new KWQuery().equalTo("moduleId", s.getId()).equalTo("updateType", "all").equalTo("platform", "android").descending("version"));
                Log.d("version", "p = " + p.toString());
                String currentVersion = zip.version;
                String remoteVersion = p.get("version").toString();
                if (remoteVersion.compareTo(currentVersion) > 0) {
                    Log.d("version", "有新版本");
                    String downloadUrl = p.get("url").toString();
                    String version = p.get("version").toString();
                    downloadFull(downloadUrl, name, version);
                } else {
                    //如果版本号一样，检查增量包
                    List<Package> patchs = new Package().find(new KWQuery().equalTo("moduleId", s.getId()).equalTo("version", currentVersion).equalTo("updateType", "patch").equalTo("platform", "android"));
                    if (patchs.size() == 0) {
                        Log.d("version", "没有新版本");
                    } else {
                        Log.d("version", "有增量包，要比较一下");
                        for (Package patch : patchs) {
                            Log.d("version", "patch = " + patch);
                            //按顺序比较，打入增量包
                            if (zip.patchs.contains(patch.getId())) {
                                Log.d("version", "该增量包已经打过了。。。");
                            } else {
                                Log.d("version", "没有打过增量包,去打");
                                downloadPatch(name, zip, patch);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("version", "exception e = " + e.toString());
        }
    }

    private void downloadPatch(final String name, final ZipPackage zip, final Package patch) throws Exception {
        //1.下载增量包
        HttpDownload httpDownload = new HttpDownload();
        String patchName = name.replace(".zip", "") + "_" + patch.get("version") + "_patch_" + patch.get("id") + ".zip";
        int ret = httpDownload.downFile(patch.get("url").toString(), WXApplication.PATH_PATCH, patchName);
        if (ret != 0) {
            return;
        }

        String zipA = WXApplication.PATH + name;
        String zipB = WXApplication.PATH_PATCH + patchName;

        //0.检查旧包和新包的目录结构是否一致
        //comparePackage(zipA, zipB);

        // 1.先解压旧的，再解压新的，解压路径一致
        new ZipUtil().unZip(zipA);
        new File(zipA).delete();

        File newB = new File(zipA);
        new File(zipB).renameTo(newB);

        new ZipUtil().unZip(zipA);
        newB.delete();

        // 2.删除2个旧包

        //3.压缩
        new ZipUtil().doZip(zipA.replace(".zip", ""));

        //4.删除文件夹
        FileUtils.delFolder(zipA.replace(".zip", ""));

        zip.patchs = zip.patchs + " " + patch.getId();

        new MyDBHelper(context).updateZipPackagePatchs(zip.patchs, zipA);
        Log.d("version", "包打好了");
    }

    //全量包
    protected void downloadFull(final String downloadUrl, final String zipName, final String version) {
//        if (!NetworkUtil.isWifi(context)) {
//            Log.d("version", "不是wifi，不下载");
//            return;
//        }
        HttpDownload httpDownload = new HttpDownload();
        int ret = httpDownload.downFile(downloadUrl, WXApplication.PATH_TMP, zipName);
        Log.d("version", "下载返回值 ret = " + ret);
        if (ret != 0) {
            return;
        }
        Log.d("version", "下载成功");
        //0.检查旧包和新包的目录结构是否一致
        // comparePackage(WXApplication.PATH + zipName, WXApplication.PATH_TMP + zipName);

        //1.先保存旧包
        File currentFile = new File(WXApplication.PATH + zipName);
        boolean move1 = currentFile.renameTo(new File(WXApplication.PATH_BACKUP + zipName));
        //2.替换旧包
        File newFile = new File(WXApplication.PATH_TMP + zipName);
        boolean move2 = newFile.renameTo(new File(WXApplication.PATH + zipName));
        //3.保存版本号
        new MyDBHelper(context).updateZipPackageVersion(version, zipName);
        Log.d("version", "升级成功");
    }

    private void comparePackage(String currentPackage, String newPackage) {
        //不解压怎么判断。
        ArrayList<String> files1 = ZipUtil.readZip(currentPackage);
        ArrayList<String> files2 = ZipUtil.readZip(newPackage);
        System.out.println("file1 count =" + files1.size());
        System.out.println("file2 count =" + files2.size());
        if (files1.size() > files2.size()) {
            System.out.println(".......error.......");
            return;
        }
        boolean error = false;
        String errorFile = null;
        for (int i = 0; i < files1.size(); i++) {
            String file1 = files1.get(i);
            boolean existed = false;
            for (int j = 0; j < files2.size(); j++) {
                String file2 = files2.get(j);
                if (file1.equals(file2)) {
                    existed = true;
                }
            }
            if (!existed) {
                errorFile = file1;
                error = true;
                break;
            }
        }
        if (error) {
            System.out.println("error:" + errorFile);
        } else {
            System.out.println("no error");
        }
    }

    //目前没用到。。。
    public void getLocalVersion() {
        //检查tmp目录下有zip包，如果有，提示用户更新
        File root = new File(WXApplication.PATH_TMP);
        File[] files = root.listFiles();
        if (files == null) {
            return;
        }
        if (files.length == 0) {
            return;
        }

        //1.如果是完整的f.zip，直接替换

        //2.如果是增量的p.zip，增量替换
    }
}
