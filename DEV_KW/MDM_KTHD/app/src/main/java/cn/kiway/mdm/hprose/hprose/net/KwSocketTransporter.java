package cn.kiway.mdm.hprose.hprose.net;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.util.LinkedList;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

import hprose.util.concurrent.Promise;
import hprose.util.concurrent.Threads;

/**
 * Created by Administrator on 2017/11/16.
 */

public abstract class KwSocketTransporter implements KwConnectionHandler {

    KwConntectionCallback conntectionCallback;

    public void setConntectionCallback(KwConntectionCallback conntectionCallback) {
        this.conntectionCallback = conntectionCallback;
    }

    protected final static class ConnectorHolder {
        private static volatile KwConnector connector;

        private static void init() {
            KwConnector temp = null;
            try {
                temp = new KwConnector(KwHproseTcpClient.getReactorThreads());
            } catch (IOException e) {
            } finally {
                connector = temp;
            }
        }

        static {
            init();
            Threads.registerShutdownHandler(new Runnable() {
                public void run() {
                    KwConnector temp = connector;
                    init();
                    if (temp != null) {
                        temp.close();
                    }
                }
            });
        }

        public static final void create(String uri, KwConnectionHandler handler, boolean keepAlive, boolean noDelay)
                throws IOException {
            if (!connector.isAlive()) {
                try {
                    connector.start();
                } catch (IllegalThreadStateException ignore) {
                }
            }
            connector.create(uri, handler, keepAlive, noDelay);
        }
    }

    protected final KwHproseTcpClient client;
    protected final LinkedList<KwConnection> idleConnections = new LinkedList<KwConnection>();
    protected final ConcurrentLinkedQueue<Request> requests = new ConcurrentLinkedQueue<Request>();
    protected final AtomicInteger size = new AtomicInteger(0);

    public KwSocketTransporter(KwHproseTcpClient client) {
        super();
        this.client = client;
    }

    public final int getReadTimeout() {
        return client.getReadTimeout();
    }

    public final int getWriteTimeout() {
        return client.getWriteTimeout();
    }

    public final int getConnectTimeout() {
        return client.getConnectTimeout();
    }

    protected final void create(Request request) {
        if (size.get() < client.getMaxPoolSize()) {
            try {
                ConnectorHolder.create(client.getUriList().get(0), this, client.isKeepAlive(), client.isNoDelay());
            } catch (IOException ex) {
                request.result.reject(ex);
//                while ((request = requests.poll()) != null) {
//                    request.result.reject(ex);
//                }
                return;
            }
        }
        requests.offer(request);
    }

    protected abstract KwConnection fetch(Request request);

    protected abstract void send(KwConnection conn, Request request);

    public final Promise<ByteBuffer> send(ByteBuffer buffer, int timeout) {
        Request request = new Request(buffer, timeout);
        KwConnection conn = fetch(request);
        if (conn != null) {
            send(conn, request);
        }
        return request.result;
    }

    protected void close(Set<KwConnection> conns) {
        while (!conns.isEmpty()) {
            for (KwConnection conn : conns) {
                conn.close();
            }
        }
        while (!requests.isEmpty()) {
            requests.poll().result.reject(new ClosedChannelException());
        }
    }

    public final void onClose(KwConnection conn) {
        size.decrementAndGet();
        synchronized (idleConnections) {
            idleConnections.remove(conn);
        }
        if (conntectionCallback != null)
            conntectionCallback.onClose();
        onError(conn, new ClosedChannelException());
    }


    public abstract void close();

}
