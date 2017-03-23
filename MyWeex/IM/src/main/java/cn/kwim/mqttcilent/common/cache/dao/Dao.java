package cn.kwim.mqttcilent.common.cache.dao;

import cn.kwim.mqttcilent.common.Global;
import io.realm.Realm;

/**
 * Created by Administrator on 2017/1/5.
 */

public class Dao {

    /**
     *  共有Dao
     * @return
     */
    public static Realm getRealm() {
       // RealmConfiguration configuration = new RealmConfiguration.Builder().name("kw.realm").build();

        return Realm.getDefaultInstance();
    }

    /**
     * 拼接字段 生成当前用户唯一
     * @param content  传入用户标识
     * @return 用户所要生成的Key，拼接规则当前用户Id加上所要拼接的字段
     */
    public static String getKey(String content){

        return Global.getInstance().getUserId()+content.replace(".0","");
    }
}
