package com.zk.myweex.extend.adapter;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by Administrator on 2017/3/14.
 */
public interface GitHubApi {

    //这里为了方便，返回值用String
    @GET("{url}")
    Call<String> doGet(@Path(value = "url", encoded = true) String url, @QueryMap Map<String, String> maps);


}