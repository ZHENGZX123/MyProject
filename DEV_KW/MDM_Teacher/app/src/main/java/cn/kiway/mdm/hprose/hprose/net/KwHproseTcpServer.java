/**********************************************************\
 |                                                          |
 |                          hprose                          |
 |                                                          |
 | Official WebSite: http://www.hprose.com/                 |
 |                   http://www.hprose.org/                 |
 |                                                          |
 \**********************************************************/
/**********************************************************\
 *                                                        *
 * HproseTcpServer.java                                   *
 *                                                        *
 * hprose tcp server class for Java.                      *
 *                                                        *
 * LastModified: Jul 7, 2016                              *
 * Author: Ma Bingyao <andot@hprose.com>                  *
 *                                                        *
 \**********************************************************/
package cn.kiway.mdm.hprose.hprose.net;

import android.os.Message;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

import cn.kiway.mdm.hprose.jrf.server.JRFServer;
import cn.kiway.mdm.scoket.scoket.tcp.MessageHander.AccpectMessageHander;
import cn.kiway.mdm.scoket.scoket.tcp.netty.HproseChannelMapStatic;
import cn.kiway.mdm.scoket.scoket.tcp.netty.MessageType;
import cn.kiway.mdm.scoket.utils.Logger;
import hprose.common.HproseContext;
import hprose.common.HproseMethods;
import hprose.io.ByteBufferStream;
import hprose.net.TimeoutType;
import hprose.server.HproseService;
import hprose.server.HproseTcpMethods;
import hprose.server.HproseTcpServiceEvent;
import hprose.server.ServiceContext;
import hprose.server.TcpContext;
import hprose.util.concurrent.Action;

public class KwHproseTcpServer extends HproseService {
    public String setSharedir(String dir) {
        return JRFServer.setSharedir(dir);
    }

    public boolean shareFile(String file) {
        return JRFServer.shareFile(file);
    }

    private final static ThreadLocal<TcpContext> currentContext = new ThreadLocal<TcpContext>();
    private volatile ExecutorService threadPool = null;
    private volatile int readTimeout = 300*1000;
    private volatile int writeTimeout =  300*1000;
    private boolean threadPoolEnabled = false;
    private int reactorThreads = 2;
    private KwAcceptor acceptor = null;
    private String host = null;
    private int port = 0;
    JRFServer srv = null;

    private final class ServerHandler implements Runnable {
        private final KwConnection conn;
        private final ByteBuffer data;
        private final Integer id;

        public ServerHandler(KwConnection conn, ByteBuffer data, Integer id) {
            this.conn = conn;
            this.data = data;
            this.id = id;
        }

        @SuppressWarnings("unchecked")
        public final void run() {
            TcpContext context = new TcpContext(KwHproseTcpServer.this, conn.socketChannel());
            currentContext.set(context);
            KwHproseTcpServer.this.handle(data, context).then(new Action<ByteBuffer>() {
                public void call(ByteBuffer value) throws Throwable {
                    conn.send(value, id);
                }
            }).catchError(new Action<Throwable>() {
                public void call(Throwable e) throws Throwable {
                    conn.close();
                }
            }).whenComplete(new Runnable() {
                public void run() {
                    currentContext.remove();
                    ByteBufferStream.free(data);
                }
            });
        }
    }

    public static String getString(ByteBuffer buffer) {
        Charset charset = null;
        CharsetDecoder decoder = null;
        CharBuffer charBuffer = null;
        try {
            charset = Charset.forName("UTF-8");
            decoder = charset.newDecoder();
            charBuffer = decoder.decode(buffer.asReadOnlyBuffer());
            return charBuffer.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "error";
        }
    }

    private final class KwServerConnectionHandler implements KwConnectionHandler {

        public void onConnect(KwConnection conn) {
        }

        public void onConnected(KwConnection conn) {
            fireAcceptEvent(conn.socketChannel());
        }

        public final void onReceived(KwConnection conn, ByteBuffer data, Integer id) {
            if (getString(data).contains("publish")) {
                Logger.log("user  login");
                String userId = getString(data).split("\\{")[1].split("\"")[1];
                if (HproseChannelMapStatic.getChannel(userId) == null)//如果本地没有这个客户端，
                    HproseChannelMapStatic.addChannel(userId, conn.socketChannel());
            }
            ServerHandler handler = new ServerHandler(conn, data, id);
            if (threadPool != null) {
                try {
                    threadPool.execute(handler);
                } catch (RejectedExecutionException e) {
                    conn.close();
                }
            } else {
                handler.run();
            }
        }

        public final void onSended(KwConnection conn, Integer id) {
        }

        public final void onClose(KwConnection conn) {
            //   Logger.log("onClose----------------------");
            fireCloseEvent(conn.socketChannel());

//            //// TODO: 2017/11/13 移除通知
//            if (acpHandler != null) {
//                String userId = HproseChannelMapStatic.removeChannel(conn.socketChannel());
//                if (userId.equals(""))
//                    return;
//                Message msg1 = new Message();
//                msg1.what = MessageType.LOGINOUT;
//                msg1.obj = userId;
//                acpHandler.sendMessage(msg1);
//                Logger.log("User:::::" + userId + "leve");
//            }
        }

        public void onError(KwConnection conn, Exception e) {
            if (conn == null) {
                Logger.log("onError----------------------");
                fireErrorEvent(e, null);
                if (acpHandler != null) {
                    String userId = HproseChannelMapStatic.removeChannel(conn.socketChannel());
                    if (userId.equals(""))
                        return;
                    Message msg1 = new Message();
                    msg1.what = MessageType.LOGINOUT;
                    msg1.obj = userId;
                    acpHandler.sendMessage(msg1);
                    Logger.log("User:::::" + userId + "leve");
                }
            }
        }

        public void onTimeout(KwConnection conn, TimeoutType type) {
            Logger.log("onTimeout----------------------");
        }

        public int getReadTimeout() {
            return readTimeout;
        }

        public int getWriteTimeout() {
            return writeTimeout;
        }

        public int getConnectTimeout() {
            throw new UnsupportedOperationException();
        }
    }

    public KwHproseTcpServer(String uri) throws URISyntaxException {
        URI u = new URI(uri);
        host = u.getHost();
        port = u.getPort();
    }

    AccpectMessageHander acpHandler;

    public KwHproseTcpServer(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public KwHproseTcpServer(String host, int port, AccpectMessageHander acpHandler) {
        this.host = host;
        this.port = port;
        this.acpHandler = acpHandler;
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

    public int getReactorThreads() {
        return reactorThreads;
    }

    public void setReactorThreads(int reactorThreads) {
        this.reactorThreads = reactorThreads;
    }

    public boolean isStarted() {
        return (acceptor != null);
    }

    public void start() throws IOException {
        if (!isStarted()) {
            acceptor = new KwAcceptor(host, port, new KwServerConnectionHandler(), reactorThreads);
            acceptor.start();
            srv = JRFServer.get(new InetSocketAddress(port + 10));
            srv.start();
            Logger.log("srv::::::::start");
        }
    }

    public void stop() {
        if (isStarted()) {
            acceptor.close();
            if (threadPool != null && !threadPool.isShutdown()) {
                try {
                    threadPool.shutdown();
                } catch (SecurityException e) {
                    fireErrorEvent(e, null);
                }
            }
            acceptor = null;

            try {
                srv.requestStop(); // Disconnects all connected clients
                srv.join();
            } catch (InterruptedException e) {

            }
            srv = null;
        }
    }

    @Override
    public HproseMethods getGlobalMethods() {
        if (globalMethods == null) {
            globalMethods = new HproseTcpMethods();
        }
        return globalMethods;
    }

    @Override
    public void setGlobalMethods(HproseMethods methods) {
        if (methods instanceof HproseTcpMethods) {
            this.globalMethods = methods;
        } else {
            throw new ClassCastException("methods must be a HproseTcpMethods instance");
        }
    }

    @Override
    protected Object[] fixArguments(Type[] argumentTypes, Object[] arguments, ServiceContext context) {
        int count = arguments.length;
        TcpContext tcpContext = (TcpContext) context;
        if (argumentTypes.length != count) {
            Object[] args = new Object[argumentTypes.length];
            System.arraycopy(arguments, 0, args, 0, count);
            Class<?> argType = (Class<?>) argumentTypes[count];
            if (argType.equals(HproseContext.class) || argType.equals(ServiceContext.class)) {
                args[count] = context;
            } else if (argType.equals(TcpContext.class)) {
                args[count] = tcpContext;
            } else if (argType.equals(SocketChannel.class)) {
                args[count] = tcpContext.getSocketChannel();
            } else if (argType.equals(Socket.class)) {
                args[count] = tcpContext.getSocket();
            }
            return args;
        }
        return arguments;
    }

    public static TcpContext getCurrentContext() {
        return currentContext.get();
    }

    /**
     * Is thread pool enabled.
     * This thread pool is not for the service threads, it is for the user service method.
     * The default value is false.
     *
     * @return is thread pool enabled
     */
    public boolean isThreadPoolEnabled() {
        return threadPoolEnabled;
    }

    /**
     * Set thread pool enabled.
     * This thread pool is not for the service threads, it is for the user service method.
     * If your service method takes a long time, or will be blocked, please set this property to be true.
     *
     * @param value is thread pool enabled
     */
    public void setThreadPoolEnabled(boolean value) {
        if (value && (threadPool == null)) {
            threadPool = Executors.newCachedThreadPool();
        }
        threadPoolEnabled = value;
    }

    /**
     * get the thread pool.
     * This thread pool is not for the service threads, it is for the user service method.
     * The default value is null.
     *
     * @return the thread pool
     */
    public ExecutorService getThreadPool() {
        return threadPool;
    }

    /**
     * set the thread pool.
     * This thread pool is not for the service threads, it is for the user service method.
     * Set it to null will disable thread pool.
     *
     * @param value is the thread pool
     */
    public void setThreadPool(ExecutorService value) {
        threadPool = value;
        threadPoolEnabled = (value != null);
    }

    protected void fireAcceptEvent(SocketChannel channel) {
        if (event != null && HproseTcpServiceEvent.class.isInstance(event)) {
            ((HproseTcpServiceEvent) event).onAccept(new TcpContext(this, channel));
        }
    }

    protected void fireCloseEvent(SocketChannel channel) {
        if (event != null && HproseTcpServiceEvent.class.isInstance(event)) {
            ((HproseTcpServiceEvent) event).onClose(new TcpContext(this, channel));
        }
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public int getWriteTimeout() {
        return writeTimeout;
    }

    public void setWriteTimeout(int writeTimeout) {
        this.writeTimeout = writeTimeout;
    }

}
