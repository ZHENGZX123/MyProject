package cn.kiway.mdm.scoket.scoket.tcp.netty;

import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.Map.Entry;

import cn.kiway.mdm.hprose.hprose.codec.Message;
import cn.kiway.mdm.scoket.scoket.tcp.MessageHander.AccpectMessageHander;
import cn.kiway.mdm.scoket.utils.Logger;
import hprose.io.ByteBufferStream;
import hprose.server.HproseClients;
import hprose.server.HproseService;
import hprose.server.ServiceContext;
import hprose.util.concurrent.Action;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.FullHttpMessage;
import io.netty.handler.codec.http.HttpMessage;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import static cn.kiway.mdm.scoket.scoket.tcp.netty.NettyServerBootstrap.staute;

/**
 * Created by Administrator on 2017/11/7.
 */

public class KwHproseNettyServer extends HproseService {
    private final static ThreadLocal<HproseTcpContext> currentContext = new ThreadLocal<HproseTcpContext>();
    private String host = null;
    private int port = 0;

    public KwHproseNettyServer(String uri) throws URISyntaxException {
        URI u = new URI(uri);
        host = u.getHost();
        port = u.getPort();
    }

    public KwHproseNettyServer(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String value) {
        host = value;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int value) {
        port = value;
    }

    public boolean isStarted() {
        return (boss != null);
    }

    public void start() throws IOException {
        if (!isStarted()) {
            try {
                bind();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    ServerBootstrap bootstrap;
    AccpectMessageHander accpectMessageHander;
    EventLoopGroup boss;
    EventLoopGroup worker;
    protected EventLoop loop;

    private void bind() throws InterruptedException {
        if (bootstrap != null)
            return;
        boss = new NioEventLoopGroup();
        worker = new NioEventLoopGroup();
        bootstrap = new ServerBootstrap();
        bootstrap.group(boss, worker);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.option(ChannelOption.SO_BACKLOG, 128);
        // 通过NoDelay禁用Nagle,使消息立即发出去，不用等待到一定的数据量才发出去
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        // 保持长连接状态
        bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel)
                    throws Exception {
                ChannelPipeline p = socketChannel.pipeline();
                p.addLast(new HttpServerCodec());
                p.addLast(new HttpObjectAggregator(1024*1024));
//               p.addLast("decoder",new HttpRequestDecoder());
//                p.addLast("encoder", new HttpRequestEncoder());
                p.addLast("decoder", new StringDecoder());
                p.addLast("encoder", new StringEncoder());
                p.addLast(new ServerHandler());
            }
        });
        ChannelFuture f = bootstrap.bind(port).sync();
        if (f.isSuccess()) {
            Logger.log("服务端启动：：：netty server start---------------");
        }
    }

    public void stop() {
        if (boss != null) {
            boss.shutdownGracefully();
        }
        if (worker != null) {
            worker.shutdownGracefully();
        }
        Log.e("", "Stop Server!");
    }

    class ServerHandler extends SimpleChannelInboundHandler<Object> {

        public ServerHandler() {
        }

        // 这里是从客户端过来的消息
        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object obj) throws Exception {
            Logger.log("-----------"+obj);
            if((obj instanceof HttpMessage)) {
               // throw new IllegalArgumentException("HttpMessage object required: " + obj);
                HttpMessage httpMsg = (HttpMessage) obj;
                Message msg = new Message();
                Iterator<Entry<String, String>> iter = httpMsg.headers().iterator();
                while (iter.hasNext()) {
                    Entry<String, String> e = iter.next();
                    if(e.getKey().equalsIgnoreCase(Message.CONTENT_TYPE)){ //encoding and type
                        String[] typeInfo = httpContentType(e.getValue());
                        msg.setHeader(Message.CONTENT_TYPE, typeInfo[0]);
                        if(msg.getHeader(Message.ENCODING) == null) {
                            msg.setHeader(Message.ENCODING, typeInfo[1]);
                        }
                    } else {
                        msg.setHeader(e.getKey().toLowerCase(), e.getValue());
                    }
                }

                if (httpMsg instanceof HttpRequest) {
                    HttpRequest req = (HttpRequest) httpMsg;
                    msg.setMethod(req.getMethod().name());
                    msg.setUrl(req.getUri());
                } else if (httpMsg instanceof HttpResponse) {
                    HttpResponse resp = (HttpResponse) httpMsg;
                    int status = resp.getStatus().code();
                    msg.setStatus(status);
                }

                if (httpMsg instanceof FullHttpMessage) {
                    FullHttpMessage fullReq = (FullHttpMessage) httpMsg;
                    int size = fullReq.content().readableBytes();
                    if (size > 0) {
                        byte[] data = new byte[size];
                        fullReq.content().readBytes(data);
                        msg.setBody(data);
                        HproseTcpContext context = new HproseTcpContext(KwHproseNettyServer.this, channelHandlerContext);
                        currentContext.set(context);
                        ByteBuffer buf = ByteBuffer.wrap(data);
                        KwHproseNettyServer.this.handle(buf, context).then(new Action<ByteBuffer>() {
                            public void call(ByteBuffer value) throws Throwable {
                                Logger.log("------------------handle");
                                channelHandlerContext.writeAndFlush(value);
                            }
                        }).catchError(new Action<Throwable>() {
                            public void call(Throwable e) throws Throwable {
                                Logger.log("------------------close");
                                channelHandlerContext.close();
                            }
                        }).whenComplete(new Runnable() {
                            public void run() {
                                Logger.log("------------------whenComplete");
                                currentContext.remove();
                                ByteBufferStream.free(buf);
                            }
                        });
                    }
                }
            }else if (obj instanceof String){
                JSONObject data = new JSONObject((String) obj);
                String clientId = data.optString("clientId");
                if (data.optInt("msgType") == MessageType.LOGIN) {//如果是登录
                    if (ChannelMapStatic.getChannel(clientId) == null)//如果本地没有这个客户端，
                        ChannelMapStatic.addChannel(clientId, (SocketChannel) channelHandlerContext.channel());
                    // TODO: 2017/11/1 还要把客户端的信息给web
                    Logger.log("有客户端进入----" + clientId);
                } else {//发送来的消息
                    // TODO: 2017/11/1  给指定客户端发消息，和处理客户端的消息
                    Logger.log(clientId + "客户端发来消息----" + data.optString("msg"));
                }
            }
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            super.channelInactive(ctx);
            String clientId = ChannelMapStatic.removeChannel((SocketChannel) ctx.channel());
            JSONObject da = new JSONObject();
            da.put("clientId", clientId);
            da.put("msgType", MessageType.LOGINOUT);
            Logger.log("用户:::::" + clientId + "离开");
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            cause.printStackTrace();
            ctx.close();
            ChannelMapStatic.removeAll();
            staute = "3";
            Logger.log("服务器端出现异常！");
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            super.channelActive(ctx);
        }

    }
    private static String[] httpContentType(String value){
        String type="text/plain", charset="utf-8";
        String[] bb = value.split(";");
        if(bb.length>0){
            type = bb[0].trim();
        }
        if(bb.length>1){
            String[] bb2 = bb[1].trim().split("=");
            if(bb2[0].trim().equalsIgnoreCase("charset")){
                charset = bb2[1].trim();
            }
        }
        return new String[]{type, charset};
    }

    class HproseTcpContext extends ServiceContext {
        private final ChannelHandlerContext ctx;

        public HproseTcpContext(HproseClients clients,
                                ChannelHandlerContext ctx1) {
            super(clients);
            this.ctx = ctx1;
        }

        public ChannelHandlerContext getSocketChannel() {
            return ctx;
        }

//        public Socket getSocket() {
//            return ctx.;
//        }
    }
}
