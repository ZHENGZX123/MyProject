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
 * HproseTcpClient.java                                   *
 *                                                        *
 * hprose tcp client class for Java.                      *
 *                                                        *
 * LastModified: Sep 19, 2016                             *
 * Author: Ma Bingyao <andot@hprose.com>                  *
 *                                                        *
 \**********************************************************/
package cn.kiway.mdm.hprose.hprose.net;

import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.ByteBuffer;

import cn.kiway.mdm.hprose.jrf.client.JRFClient;
import hprose.client.ClientContext;
import hprose.client.HproseClient;
import hprose.common.InvokeSettings;
import hprose.io.HproseMode;
import hprose.util.concurrent.Promise;


public class KwHproseTcpClient extends HproseClient {

    private volatile boolean fullDuplex = false;
    private volatile boolean noDelay = false;
    private volatile int maxPoolSize = 4;
    private volatile int idleTimeout = 30000;
    private volatile int readTimeout = 30000;
    private volatile int writeTimeout = 30000;
    private volatile int connectTimeout = 30000;
    private volatile boolean keepAlive = true;
    private KwSocketTransporter fdTrans = new KwFullDuplexSocketTransporter(this);
    private KwSocketTransporter hdTrans = new KwHalfDuplexSocketTransporter(this);
    private String uri;
    private static int reactorThreads = 20;

    public KwHproseTcpClient() {
        super();
    }

    public KwHproseTcpClient(String uri, KwConntectionCallback conntectionCallback) {
        super(uri);
        this.uri = uri;
        hdTrans.setConntectionCallback(conntectionCallback);
        fdTrans.setConntectionCallback(conntectionCallback);
    }

    public KwHproseTcpClient(String uri, HproseMode mode) {
        super(uri, mode);
        this.uri = uri;
    }

    public KwHproseTcpClient(String[] uris, HproseMode mode) {
        super(uris, mode);
        this.uri = uris[0];
    }

    public long getFile(String local, String remote, JRFClient.DownLoadCallBack callBack) {
        long len = 0l;
        JRFClient cli = null;
        try {
            URI u = new URI(uri);
            cli = new JRFClient(new InetSocketAddress(u.getHost(), u.getPort() + 10));
            cli.start();
            len = cli.getFile(remote, 3, local, 1500, callBack);
            cli.requestStop();
            cli.join();
        } catch (Exception e) {
        }
        if (cli != null) {
            try {
                cli.requestStop();
                cli.join();
            } catch (InterruptedException e) {
            }
        }
        return len;
    }

    /**
     * @param local  存放地址
     * @param remote 下载地址
     */
    public long getFile(String local, String remote) {
        long len = 0l;
        JRFClient cli = null;
        try {
            URI u = new URI(uri);
            cli = new JRFClient(new InetSocketAddress(u.getHost(), u.getPort() + 10));
            cli.start();
            len = cli.getFile(remote, 3, local, 1500, null);
            System.out.println("len=" + len);
            cli.requestStop();
            cli.join();
        } catch (Exception e) {
        }
        if (cli != null) {
            try {
                cli.requestStop();
                cli.join();
            } catch (InterruptedException e) {
            }
        }
        return len;
    }


    public static int getReactorThreads() {
        return reactorThreads;
    }

    public static void setReactorThreads(int aReactorThreads) {
        reactorThreads = aReactorThreads;
    }

    @Override
    public final void close() {
        //fdTrans.close();
        hdTrans.close();
        super.close();
    }

    public final boolean isFullDuplex() {
        return fullDuplex;
    }

    public final void setFullDuplex(boolean fullDuplex) {
        this.fullDuplex = fullDuplex;
    }

    public final boolean isNoDelay() {
        return noDelay;
    }

    public final void setNoDelay(boolean noDelay) {
        this.noDelay = noDelay;
    }

    public final int getMaxPoolSize() {
        return maxPoolSize;
    }

    public final void setMaxPoolSize(int maxPoolSize) {
        if (maxPoolSize < 1) throw new IllegalArgumentException("maxPoolSize must be great than 0");
        this.maxPoolSize = maxPoolSize;
    }

    public final int getIdleTimeout() {
        return idleTimeout;
    }

    public final void setIdleTimeout(int idleTimeout) {
        if (idleTimeout < 0) throw new IllegalArgumentException("idleTimeout must be great than -1");
        this.idleTimeout = idleTimeout;
    }

    public final int getReadTimeout() {
        return readTimeout;
    }

    public final void setReadTimeout(int readTimeout) {
        if (readTimeout < 1) throw new IllegalArgumentException("readTimeout must be great than 0");
        this.readTimeout = readTimeout;
    }

    public final int getWriteTimeout() {
        return writeTimeout;
    }

    public final void setWriteTimeout(int writeTimeout) {
        if (writeTimeout < 1) throw new IllegalArgumentException("writeTimeout must be great than 0");
        this.writeTimeout = writeTimeout;
    }

    public final int getConnectTimeout() {
        return connectTimeout;
    }

    public final void setConnectTimeout(int connectTimeout) {
        if (connectTimeout < 1) throw new IllegalArgumentException("connectTimeout must be great than 0");
        this.connectTimeout = connectTimeout;
    }

    public final boolean isKeepAlive() {
        return keepAlive;
    }

    public final void setKeepAlive(boolean keepAlive) {
        this.keepAlive = keepAlive;
    }

    protected Promise<ByteBuffer> sendAndReceive(final ByteBuffer request, ClientContext context) {
        final InvokeSettings settings = context.getSettings();
        if (fullDuplex) {
            return fdTrans.send(request, settings.getTimeout());
        } else {
            return hdTrans.send(request, settings.getTimeout());
        }
    }
}