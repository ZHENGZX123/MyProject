package cn.kiway.mdm.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/6/9.
 */

public interface Constant {
    public   int _16 = 16;
    public ExecutorService executorService = Executors.newCachedThreadPool();
    public String ZHIHUIKETANGPG="cn.kiway.session";
}
