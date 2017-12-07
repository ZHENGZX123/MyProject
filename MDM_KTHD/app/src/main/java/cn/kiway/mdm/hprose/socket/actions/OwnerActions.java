package cn.kiway.mdm.hprose.socket.actions;

import cn.kiway.mdm.activity.BaseActivity;
import hprose.util.concurrent.Action;

/**
 * Created by Administrator on 2017/11/9.
 */

public class OwnerActions<String> implements Action<String> {
    BaseActivity activity;

    public OwnerActions(BaseActivity activity) {
        this.activity = activity;
    }

    @Override
    public void call(String msg) throws Throwable {
        System.out.println("---------老师给你自己的消息---------" + msg);
        ActionsMessageHandle.MessageHandle(activity, msg.toString());
    }
}
