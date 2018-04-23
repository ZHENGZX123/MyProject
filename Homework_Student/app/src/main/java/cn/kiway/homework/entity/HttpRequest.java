package cn.kiway.homework.entity;

import com.loopj.android.http.AsyncHttpClient;

/**
 * Created by Administrator on 2018/4/18.
 */

public class HttpRequest {
    public AsyncHttpClient client;
    public String url;
    public String param;
    public String method;
    public String tagname;
    public String time;
    public String related;
    public boolean finished;


    public HttpRequest(AsyncHttpClient client, String url, String param, String method, String tagname, String time, String related) {
        this.client = client;
        this.url = url;
        this.param = param;
        this.method = method;
        this.tagname = tagname;
        this.time = time;
        this.related = related;
    }
}
