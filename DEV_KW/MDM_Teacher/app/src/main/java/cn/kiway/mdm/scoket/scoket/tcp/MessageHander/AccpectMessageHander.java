package cn.kiway.mdm.scoket.scoket.tcp.MessageHander;

import android.os.Handler;
import android.os.Message;

import cn.kiway.mdm.activity.MainActivity;
import cn.kiway.mdm.scoket.utils.Logger;
import cn.kiway.mdm.view.X5WebView;

import static cn.kiway.mdm.scoket.scoket.tcp.netty.MessageType.ANSWER;
import static cn.kiway.mdm.scoket.scoket.tcp.netty.MessageType.LOGIN;
import static cn.kiway.mdm.scoket.scoket.tcp.netty.MessageType.LOGINOUT;
import static cn.kiway.mdm.scoket.scoket.tcp.netty.MessageType.SENDMSG;
import static cn.kiway.mdm.scoket.scoket.tcp.netty.MessageType.SERVER_FIALIT;
import static cn.kiway.mdm.scoket.scoket.tcp.netty.MessageType.SIGN;
import static cn.kiway.mdm.scoket.scoket.tcp.netty.MessageType.SUREREPONSE;
import static cn.kiway.mdm.web.WebJsCallBack.accpterMessage;
import static cn.kiway.mdm.web.WebJsCallBack.scoketClientDis;
import static cn.kiway.mdm.web.WebJsCallBack.scoketStaute;

/**
 * Created by Administrator on 2017/3/14.
 */

public class AccpectMessageHander extends Handler {
    MainActivity activity;
    X5WebView webView;

    public AccpectMessageHander(MainActivity activity, X5WebView webView) {
        this.activity = activity;
        this.webView = webView;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case LOGIN://用户登录
                Logger.log("Client Login-----" + msg.obj);
                break;
            case SERVER_FIALIT://服务器异常
                webView.loadUrl(scoketStaute.replace("msg", (String) msg.obj));
                break;
            case SENDMSG://普通消息
            case SIGN://签到
            case ANSWER://抢答
                webView.loadUrl(accpterMessage.replace("msg", (String) msg.obj));
                break;
            case LOGINOUT:
                webView.loadUrl(scoketClientDis.replace("msg", (String) msg.obj));
                break;
            case SUREREPONSE://确认是否听懂  保存到本地数据库
                // try {
                webView.loadUrl(accpterMessage.replace("msg", (String) msg.obj));
                // JSONObject data = new JSONObject((String) msg.obj);
                //DbUtils.addResponse(activity, data.toString());
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
                break;
        }
    }
}
