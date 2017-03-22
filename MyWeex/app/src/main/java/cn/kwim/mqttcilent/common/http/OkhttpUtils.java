package cn.kwim.mqttcilent.common.http;


import android.util.Log;

import org.apache.http.protocol.HTTP;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

/**
 * Created by hmg on 2017/1/16.
 */

public class OkhttpUtils {

    private static final OkHttpClient client = new OkHttpClient ();
    public static  String base_pic_url="http://sp.sz-edu.net:88/cszsjy/";
    public static final String url= base_pic_url+"servlet/FileUpload/upload";
    /**
     * 上传单个图片
     */
    public static void upLoadImg(String path, final PercentListener listener) {
        try {
            MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

            File f=new File(path);
            builder.addFormDataPart("file", f.getName(), createCustomRequestBody(MediaType.parse("image/png"), f, new ProgressListener(){

                @Override
                public void onProgress(long totalBytes, long remainingBytes, boolean done) {
                    Log.i("进度显示", totalBytes+"----------------"+remainingBytes+"。。。。。。。");
                    listener.onPercent((totalBytes - remainingBytes) * 100 / totalBytes+"");
                }
            }));
            builder.addFormDataPart("response_type", URLEncoder.encode("images", HTTP.UTF_8));
            //builder.addPart("file", new FileBody(f));
            MultipartBody requestBody = builder.build();

            //构建请求
            Request request = new Request.Builder()
                    .url(url)//地址
                    .post(requestBody)//添加请求体
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                    Log.i("上传失败",e.getLocalizedMessage()+"");
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String res = response.body().string();
                    Log.i("上传成功","上传照片成功：response = " + res);
                    listener.onReponse(res);
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public static RequestBody createCustomRequestBody(final MediaType contentType, final File file, final ProgressListener listener) {
        return new RequestBody() {
            @Override public MediaType contentType() {
                return contentType;
            }

            @Override public long contentLength() {
                return file.length();
            }

            @Override public void writeTo(BufferedSink sink) throws IOException {
                Source source;
                try {
                    source = Okio.source(file);
                   // sink.writeAll(source);
                    Buffer buf = new Buffer();
                    Long remaining = contentLength();
                    for (long readCount; (readCount = source.read(buf, 2048)) != -1; ) {
                        sink.write(buf, readCount);
                        listener.onProgress(contentLength(), remaining -= readCount, remaining == 0);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }
    interface ProgressListener {
        void onProgress(long totalBytes, long remainingBytes, boolean done);

    }


}
