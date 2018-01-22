package com.android.kiway.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/6/9.
 */

public interface Constant {
    public int _16 = 16;
    public ExecutorService executorService = Executors.newCachedThreadPool();
    public String ZHIHUIKETANGPG = "cn.kiway.session";
    public String MARKETPLACE = "cn.kiway.marketplace";
    public String PARENTMESSAGE = "cn.kiway.parentmessage";
    public String KIWAYSETTING = "cn.kiway.setting";
}
