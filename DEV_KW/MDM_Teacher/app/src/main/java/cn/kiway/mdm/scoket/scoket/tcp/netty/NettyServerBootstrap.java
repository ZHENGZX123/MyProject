package cn.kiway.mdm.scoket.scoket.tcp.netty;

import android.util.Log;

import cn.kiway.mdm.scoket.scoket.tcp.MessageHander.AccpectMessageHander;
import cn.kiway.mdm.scoket.utils.Logger;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class NettyServerBootstrap {
    ServerBootstrap bootstrap;
    private int port;
    AccpectMessageHander accpectMessageHander;
    EventLoopGroup boss;
    EventLoopGroup worker;
    public static String staute = "1";

    public NettyServerBootstrap(AccpectMessageHander accpectMessageHander, int port) throws InterruptedException {
        this.port = port;
        this.accpectMessageHander = accpectMessageHander;
        bind();
    }

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
                p.addLast(new StringDecoder());
                p.addLast(new StringEncoder());
                p.addLast(new NettyServerHandler(accpectMessageHander));
            }
        });
        ChannelFuture f = bootstrap.bind(port).sync();
        if (f.isSuccess()) {
            staute = "1";
            Logger.log("-------------netty server start---------------");
        } else {
            staute = "0";
        }
    }

    public void stop() {
        if (boss != null) {
            boss.shutdownGracefully();
        }
        if (worker != null) {
            worker.shutdownGracefully();
        }
        staute = "0";
        Log.e("", "Stop Server!");
    }
}
