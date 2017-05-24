package com.zk.myweex.utils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

/**
 * Created by Administrator on 2017/5/10.
 */

public class HttpUploadFile {

    public static String BaseUrl = "http://yqyd.qgjydd.com/yqyd/";
    public static String loginUrl = BaseUrl + "app/teaLogin?username={userName}&password=";//登录
    public static String updateUserInfoUrl = "http://yqyd.qgjydd.com/yqyd/v1/app/student/uc/updatePersonalInfo";//BaseUrl + "v1/app/student/uc/editPersonalInfo";//更新用户头像
    public static String uploadUserPicUrl = BaseUrl + "v1/app/upload";//上传图像
    public static String checkVersion = BaseUrl + "static/version/zip_ls.json";

    public static RequestBody createCustomRequestBody(final MediaType contentType, final File file) {
        return new RequestBody() {
            @Override
            public MediaType contentType() {
                return contentType;
            }

            @Override
            public long contentLength() {
                return file.length();
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                Source source;
                try {
                    source = Okio.source(file);
                    Buffer buf = new Buffer();
                    for (long readCount; (readCount = source.read(buf, 2048)) != -1; ) {
                        sink.write(buf, readCount);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public static Request returnUploadImgRequser(File fileName, String fileType, String token) throws UnsupportedEncodingException {
        Logger.log("图片地址：：：" + fileName.getPath());
        Logger.log("图片地址：：：：" + fileName.getName());
        Logger.log("图片地址：：：" + fileName.length() / 1024 + "K");
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("file", fileName.getName(), createCustomRequestBody(MediaType.parse
                        (fileType),
                fileName));
        builder.addFormDataPart("token", token);
        MultipartBody requestBody = builder.build();

        return new Request.Builder()
                .url(uploadUserPicUrl)//地址
                .post(requestBody)//添加请求体
                .build();
    }

    public class FileType {
        public static final String Image = "image/png";
        public static final String Mp3 = "audio/mp3";
        public static final String Video = "video/mp4";
    }
}
