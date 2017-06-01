/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.taobao.weex.utils;

import android.content.Context;
import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class WXFileUtils {

    /**
     * Load file in device directory, if not exist, load from asset directory.
     *
     * @param path    FilePath
     * @param context Weex Context
     * @return the Content of the file
     */
    public static String loadFileOrAsset(String path, Context context) {
        if (!TextUtils.isEmpty(path)) {
            File file = new File(path);
            if (file.exists()) {
                try {
                    FileInputStream fis = new FileInputStream(file);
                    return readStreamToString(fis);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                return loadAsset(path, context);
            }
        }
        return "";
    }

    /**
     * Load file in asset directory.
     *
     * @param path    FilePath
     * @param context Weex Context
     * @return the Content of the file
     */
    public static String loadAsset(String path, Context context) {
        if (context == null || TextUtils.isEmpty(path)) {
            return null;
        }
        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open(path);
            return readStreamToString(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String readStreamToString(InputStream inputStream) {
        BufferedReader bufferedReader = null;
        try {
            StringBuilder builder = new StringBuilder(inputStream.available() + 10);
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            char[] data = new char[4096];
            int len = -1;
            while ((len = bufferedReader.read(data)) > 0) {
                builder.append(data, 0, len);
            }

            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            WXLogUtils.e("", e);
        } finally {
            try {
                if (bufferedReader != null)
                    bufferedReader.close();
            } catch (IOException e) {
                WXLogUtils.e("WXFileUtils loadAsset: ", e);
            }
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
                WXLogUtils.e("WXFileUtils loadAsset: ", e);
            }
        }

        return "";
    }

    public static boolean saveFile(String path, byte[] content, Context context) {
        if (TextUtils.isEmpty(path) || content == null || context == null) {
            return false;
        }
        FileOutputStream outStream = null;
        try {
            outStream = new FileOutputStream(path);
            outStream.write(content);
            return true;
        } catch (Exception e) {
            WXLogUtils.e("WXFileUtils saveFile: " + WXLogUtils.getStackTrace(e));
        } finally {
            if (outStream != null) {
                try {
                    outStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public static String readSDCardFile(String path, Context context) {
        if (path == null || context == null) {
            return null;
        }
        StringBuilder builder;
        try {
            File f = new File(path);

            InputStream in = new FileInputStream(f);

            builder = new StringBuilder(in.available() + 10);

            BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(in));
            char[] data = new char[2048];
            int len = -1;
            while ((len = localBufferedReader.read(data)) > 0) {
                builder.append(data, 0, len);
            }
            localBufferedReader.close();
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    WXLogUtils.e("WXFileUtils loadAsset: ", e.toString());
                }
            }
            return builder.toString();

        } catch (IOException e) {
            e.printStackTrace();
            WXLogUtils.e("", e.toString());
        }

        return "";
    }

    public static String readFileInZip(String file) {
        try {


            int index = file.indexOf("zip");
            String zipPath = file.substring(0, index + 3);
            System.out.println(zipPath);

            String targetFile = file.replace(zipPath, "");
            System.out.println(targetFile);

            ZipFile zf = new ZipFile(zipPath);
            System.out.println(zf.size());
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
                        break;
                    }
                }
            }
            zin.closeEntry();
//            System.out.println("sb = " + sb.toString());
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String findLoginJS(String path, String findFile) {
        try {
            File file = new File(path);
            File[] lists = file.listFiles();
            for (int i = 0; i < lists.length; i++) {
                if (!lists[i].getName().endsWith("zip")) {
                    continue;
                }
                ZipFile zf = new ZipFile(lists[i]);
                InputStream in = new BufferedInputStream(new FileInputStream(
                        lists[i]));
                ZipInputStream zin = new ZipInputStream(in);
                ZipEntry ze;

                while ((ze = zin.getNextEntry()) != null) {
                    if (ze.isDirectory()) {
                        System.out.println("directory = " + ze.getName());
                    } else {
                        System.out.println("file = " + ze.getName());
                        if (ze.getName().contains(findFile)) {
                            return lists[i].getAbsolutePath() + "/" + ze.getName();
                        }
                    }
                }
                zin.closeEntry();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
