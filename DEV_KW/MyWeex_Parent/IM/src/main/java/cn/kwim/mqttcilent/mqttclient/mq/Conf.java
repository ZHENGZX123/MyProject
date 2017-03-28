package cn.kwim.mqttcilent.mqttclient.mq;

import android.content.Context;

import cn.kiway.Yjpty.R;


/**
 * Created by hmg on 2017/1/5.
 * 获取配置文件信息
 */

public class Conf {
    private Context context;
    private static Conf instance;

    public static Conf getInstance (){
        if(instance==null){
            instance = new Conf();
        }
        return  instance;
    }
    private Conf(){

    }

    public void init(Context context){
        this.context = context;
    }
    public String getHost(){
        return context.getString(R.string.mqttserver_tcp_host);

    }
    public String getPort(){
        return context.getString(R.string.mqttserver_tcp_port);

    }
    public String getId(){
        return context.getString(R.string.mqttclient_connect_id);

    }
    public String getuserName(){

        return context.getString(R.string.mqttclient_connect_username);
    }
    public String getPasswd(){
        return context.getString(R.string.mqttclient_connect_passwd);

    }

    public String getTopics() {
        return context.getString(R.string.mqttclient_subscribe_topics);
    }


}
