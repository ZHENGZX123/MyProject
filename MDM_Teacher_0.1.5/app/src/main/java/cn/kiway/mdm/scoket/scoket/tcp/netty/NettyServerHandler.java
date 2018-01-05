package cn.kiway.mdm.scoket.scoket.tcp.netty;

import android.os.Message;

import org.json.JSONObject;

import cn.kiway.mdm.scoket.scoket.tcp.MessageHander.AccpectMessageHander;
import cn.kiway.mdm.scoket.utils.Logger;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;


public class NettyServerHandler extends SimpleChannelInboundHandler<Object> {


    private AccpectMessageHander mhandler;

    public NettyServerHandler(AccpectMessageHander handler) {
        this.mhandler = handler;
    }


    // 这里是从客户端过来的消息
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
        if (msg instanceof String) {
            JSONObject data = new JSONObject((String) msg);
            String clientId = data.optString("clientId");
            if (data.optInt("msgType") == MessageType.LOGIN) {//如果是登录
                if (ChannelMapStatic.getChannel(clientId) == null)//如果本地没有这个客户端，
                    ChannelMapStatic.addChannel(clientId, (SocketChannel) channelHandlerContext.channel());
                // TODO: 2017/11/1 还要把客户端的信息给web
                Message msg1 = new Message();
                msg1.what = MessageType.LOGIN;
                msg1.obj = msg;
                mhandler.sendMessage(msg1);
                Logger.log("有客户端进入----" + clientId);
            } else {//发送来的消息
                // TODO: 2017/11/1  给指定客户端发消息，和处理客户端的消息
                Message msg1 = new Message();
                msg1.what = data.optInt("msgType");
                msg1.obj = msg;
                mhandler.sendMessage(msg1);
                Logger.log(clientId + "客户端发来消息----" + data.optString("msg"));
            }
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        String clientId = ChannelMapStatic.removeChannel((SocketChannel) ctx.channel());
        Message msg1 = new Message();
        msg1.what = MessageType.LOGINOUT;
        msg1.obj = clientId;
        mhandler.sendMessage(msg1);
        Logger.log("用户:::::" + clientId + "离开");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        Message msg1 = new Message();
        msg1.what = MessageType.SERVER_FIALIT;
        mhandler.sendMessage(msg1);
        ctx.close();
        ChannelMapStatic.removeAll();
        Logger.log("服务器端出现异常！");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

}