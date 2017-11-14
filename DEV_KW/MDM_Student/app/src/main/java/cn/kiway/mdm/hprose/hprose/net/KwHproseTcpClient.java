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

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

import cn.kiway.mdm.hprose.jrf.client.JRFClient;
import hprose.client.ClientContext;
import hprose.client.HproseClient;
import hprose.common.HproseException;
import hprose.common.InvokeSettings;
import hprose.io.HproseMode;
import hprose.net.TimeoutType;
import hprose.util.concurrent.Action;
import hprose.util.concurrent.Promise;
import hprose.util.concurrent.Threads;
import hprose.util.concurrent.Timer;

final class Request {
    public final ByteBuffer buffer;
    public final Promise<ByteBuffer> result = new Promise<ByteBuffer>();
    public final int timeout;

    public Request(ByteBuffer buffer, int timeout) {
        this.buffer = buffer;
        this.timeout = timeout;
    }
}

final class Response {
    public final Promise<ByteBuffer> result;
    public final Timer timer;

    public Response(Promise<ByteBuffer> result, Timer timer) {
        this.result = result;
        this.timer = timer;
    }
}

abstract class KwSocketTransporter implements KwConnectionHandler {
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
    KwConntectionCallback conntectionCallback;
    public KwSocketTransporter(KwHproseTcpClient client, KwConntectionCallback conntectionCallback) {
        super();
        this.client = client;
        this.conntectionCallback=conntectionCallback;
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
        if (conntectionCallback!=null)
            conntectionCallback.onClose();
        onError(conn, new ClosedChannelException());
    }


    public abstract void close();

}

final class KwFullDuplexSocketTransporter extends KwSocketTransporter {
    private final static AtomicInteger nextId = new AtomicInteger(0);
    private final Map<KwConnection, Map<Integer, Response>> responses = new ConcurrentHashMap<KwConnection,
            Map<Integer, Response>>();
    public KwFullDuplexSocketTransporter(KwHproseTcpClient client, KwConntectionCallback conntectionCallback) {
        super(client,conntectionCallback);
    }

    protected final KwConnection fetch(Request request) {
        KwConnection conn;
        synchronized (idleConnections) {
            do {
                conn = idleConnections.poll();
                if (conn != null && conn.isConnected()) {
                    if (responses.get(conn).isEmpty()) {
                        conn.clearTimeout();
                    } else {
                        conn = null;
                    }
                }
            } while (conn != null && !conn.isConnected());
            if (conn == null) create(request);
        }
        return conn;
    }

    private void recycle(KwConnection conn) {
        conn.setTimeout(client.getIdleTimeout(), TimeoutType.IDLE_TIMEOUT);
    }

    private Promise<ByteBuffer> clean(KwConnection conn, int id) {
        Map<Integer, Response> res = responses.get(conn);
        Promise<ByteBuffer> result = null;
        if (res != null) {
            Response response = res.remove(id);
            if (response != null) {
                response.timer.clear();
                result = response.result;
            }
            sendNext(conn, res);
        }
        return result;
    }

    private void sendNext(KwConnection conn, Map<Integer, Response> res) {
        if (res.size() < 10) {
            Request request = requests.poll();
            if (request != null) {
                send(conn, request);
            } else {
                synchronized (idleConnections) {
                    if (!idleConnections.contains(conn)) {
                        idleConnections.offer(conn);
                    }
                }
            }
        }
    }

    protected final void send(final KwConnection conn, Request request) {
        final Map<Integer, Response> res = responses.get(conn);
        if (res != null) {
            final int id = nextId.incrementAndGet() & 0x7fffffff;
            Timer timer = new Timer(new Runnable() {
                public void run() {
                    Promise<ByteBuffer> result = clean(conn, id);
                    if (res.isEmpty()) {
                        recycle(conn);
                    }
                    if (result != null) {
                        result.reject(new TimeoutException("timeout"));
                    }
                }
            });
            timer.setTimeout(request.timeout);
            res.put(id, new Response(request.result, timer));
            conn.send(request.buffer, id);
            sendNext(conn, res);
        }
    }

    @Override
    public final void close() {
        close(responses.keySet());
    }

    public final void onConnect(KwConnection conn) {
        size.incrementAndGet();
        responses.put(conn, new ConcurrentHashMap<Integer, Response>());
if (conntectionCallback!=null)
    conntectionCallback.onConnect(conn);
    }

    public final void onConnected(KwConnection conn) {
        Request request = requests.poll();
        if (request != null) {
            send(conn, request);
        } else {
            synchronized (idleConnections) {
                if (!idleConnections.contains(conn)) {
                    idleConnections.offer(conn);
                }
            }
            recycle(conn);
        }
        if (conntectionCallback!=null)
            conntectionCallback.onConnected(conn);
    }

    public final void onTimeout(KwConnection conn, TimeoutType type) {
        if (TimeoutType.CONNECT_TIMEOUT == type) {
            responses.remove(conn);
            Request request;
            while ((request = requests.poll()) != null) {
                request.result.reject(new TimeoutException("connect timeout"));
            }
        } else if (TimeoutType.IDLE_TIMEOUT != type) {
            Map<Integer, Response> res = responses.get(conn);
            if (res != null) {
                Iterator<Map.Entry<Integer, Response>> it = res.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<Integer, Response> entry = it.next();
                    it.remove();
                    Response response = entry.getValue();
                    response.timer.clear();
                    response.result.reject(new TimeoutException(type.toString()));
                }
            }
        }
        if (conntectionCallback!=null)
            conntectionCallback.onTimeout(conn, type);
    }

    public final void onReceived(KwConnection conn, ByteBuffer data, Integer id) {
        Promise<ByteBuffer> result = clean(conn, id);
        if (result != null) {
            if (data.position() != 0) {
                data.flip();
            }
            result.resolve(data);
        } else {
            recycle(conn);
        }
        if (conntectionCallback!=null)
            conntectionCallback.onReceived(conn, data, id);
    }

    public final void onSended(KwConnection conn, Integer id) {
        synchronized (idleConnections) {
            if (!idleConnections.contains(conn)) {
                idleConnections.offer(conn);
            }
        }
    }

    public final void onError(KwConnection conn, Exception e) {
        Map<Integer, Response> res = responses.remove(conn);
        if (res != null) {
            Iterator<Map.Entry<Integer, Response>> it = res.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<Integer, Response> entry = it.next();
                it.remove();
                Response response = entry.getValue();
                response.timer.clear();
                response.result.reject(e);
            }
        }
        if (conntectionCallback!=null)
            conntectionCallback.onError(conn, e);
    }
}

final class KwHalfDuplexSocketTransporter extends KwSocketTransporter {
    private final Map<KwConnection, Response> responses = new ConcurrentHashMap<KwConnection, Response>();

    public KwHalfDuplexSocketTransporter(KwHproseTcpClient client, KwConntectionCallback conntectionCallback) {
        super(client,conntectionCallback);
    }

    protected final KwConnection fetch(Request request) {
        KwConnection conn;
        synchronized (idleConnections) {
            do {
                conn = idleConnections.poll();
            } while (conn != null && !conn.isConnected());
            if (conn != null) {
                conn.clearTimeout();
            } else {
                create(request);
            }
        }
        return conn;
    }

    private void recycle(KwConnection conn) {
        synchronized (idleConnections) {
            if (!idleConnections.contains(conn)) {
                idleConnections.offer(conn);
                conn.setTimeout(client.getIdleTimeout(), TimeoutType.IDLE_TIMEOUT);
            }
        }
    }

    private Promise<ByteBuffer> clean(KwConnection conn) {
        Response response = responses.remove(conn);
        if (response != null) {
            response.timer.clear();
            return response.result;
        }
        return null;
    }

    private void sendNext(KwConnection conn) {
        Request request = requests.poll();
        if (request != null) {
            send(conn, request);
        } else {
            recycle(conn);
        }
    }

    protected final void send(final KwConnection conn, Request request) {
        Timer timer = new Timer(new Runnable() {
            public void run() {
                Promise<ByteBuffer> result = clean(conn);
                conn.close();
                if (result != null) {
                    result.reject(new TimeoutException("timeout"));
                }
            }
        });
        timer.setTimeout(request.timeout);
        responses.put(conn, new Response(request.result, timer));
        conn.send(request.buffer, null);
    }

    @Override
    public final void close() {
        close(responses.keySet());
    }

    public final void onConnect(KwConnection conn) {
        if (conntectionCallback!=null)
            conntectionCallback.onConnect(conn);
        size.incrementAndGet();
    }

    public final void onConnected(KwConnection conn) {
        if (conntectionCallback!=null)
            conntectionCallback.onConnected(conn);
        sendNext(conn);
    }

    public final void onTimeout(KwConnection conn, TimeoutType type) {
        if (TimeoutType.CONNECT_TIMEOUT == type) {
            responses.remove(conn);
            Request request;
            while ((request = requests.poll()) != null) {
                request.result.reject(new TimeoutException("connect timeout"));
            }
        } else if (TimeoutType.IDLE_TIMEOUT != type) {
            Response response = responses.remove(conn);
            if (response != null) {
                response.timer.clear();
                response.result.reject(new TimeoutException(type.toString()));
            }
        }
        conn.close();
        if (conntectionCallback!=null)
            conntectionCallback.onTimeout(conn, type);
    }

    public final void onReceived(KwConnection conn, ByteBuffer data, Integer id) {
        Promise<ByteBuffer> result = clean(conn);
        if (result != null) {
            if (data.position() != 0) {
                data.flip();
            }
            result.resolve(data);
        }
        sendNext(conn);
        if (conntectionCallback!=null)
            conntectionCallback.onReceived(conn, data, id);
    }

    public final void onSended(KwConnection conn, Integer id) {
    }

    public final void onError(KwConnection conn, Exception e) {
        Response response = responses.remove(conn);
        if (response != null) {
            response.timer.clear();
            response.result.reject(e);
        }
        if (conntectionCallback!=null)
            conntectionCallback.onError(conn, e);
    }


}


public class KwHproseTcpClient extends HproseClient {
    private String uri;

    public long getFile(String local, String remote,JRFClient.processCallBack callBack) {
        long len = 0l;
        JRFClient cli = null;
        try {
            URI u = new URI(uri);
            cli = new JRFClient(new InetSocketAddress(u.getHost(), u.getPort() + 10));
            cli.start();
            len = cli.getFile(remote, 3, local, 1500,callBack);
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

    public long getFile(String local, String remote) {
        long len = 0l;
        JRFClient cli = null;
        try {
            URI u = new URI(uri);
            cli = new JRFClient(new InetSocketAddress(u.getHost(), u.getPort() + 10));
            cli.start();
            len = cli.getFile(remote, 3, local, 1500,null);
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

    protected Action<Object> connectedAction = new Action<Object>() {

        @Override
        public void call(Object arg0) throws Throwable {


        }

    };

    public void setContentedAction(Action<Object> a) {
        this.connectedAction = a;
    }

    private static int reactorThreads = 2;

    public static int getReactorThreads() {
        return reactorThreads;
    }

    public static void setReactorThreads(int aReactorThreads) {
        reactorThreads = aReactorThreads;
    }


    private volatile boolean fullDuplex = false;
    private volatile boolean noDelay = false;
    private volatile int maxPoolSize = 4;
    private volatile int idleTimeout = 30000;
    private volatile int readTimeout = 30000;
    private volatile int writeTimeout = 30000;
    private volatile int connectTimeout = 30000;
    private volatile boolean keepAlive = true;
    private KwSocketTransporter fdTrans;
    private KwSocketTransporter hdTrans;
    KwConntectionCallback conntectionCallback;

    public KwHproseTcpClient() {
        super();
    }

    public KwHproseTcpClient(String uri, KwConntectionCallback conntectionCallback) {
        super(uri);
        this.uri = uri;
        this.conntectionCallback=conntectionCallback;
        fdTrans = new KwFullDuplexSocketTransporter(this,conntectionCallback);
        hdTrans = new KwHalfDuplexSocketTransporter(this,conntectionCallback);
    }

    public KwHproseTcpClient(HproseMode mode) {
        super(mode);
    }

    public KwHproseTcpClient(String uri, HproseMode mode) {
        super(uri, mode);
        this.uri = uri;
    }

    public KwHproseTcpClient(String[] uris) {
        super(uris);
        this.uri = uris[0];
    }

    public KwHproseTcpClient(String[] uris, HproseMode mode) {
        super(uris, mode);
        this.uri = uris[0];
    }

    public static HproseClient create(String uri, HproseMode mode) throws IOException, URISyntaxException {
        String scheme = (new URI(uri)).getScheme().toLowerCase();
        if (!scheme.equals("tcp") &&
                !scheme.equals("tcp4") &&
                !scheme.equals("tcp6")) {
            throw new HproseException("This client doesn't support " + scheme + " scheme.");
        }
        return new KwHproseTcpClient(uri, mode);
    }

    public static HproseClient create(String[] uris, HproseMode mode) throws IOException, URISyntaxException {
        for (int i = 0, n = uris.length; i < n; ++i) {
            String scheme = (new URI(uris[i])).getScheme().toLowerCase();
            if (!scheme.equals("tcp") &&
                    !scheme.equals("tcp4") &&
                    !scheme.equals("tcp6")) {
                throw new HproseException("This client doesn't support " + scheme + " scheme.");
            }
        }
        return new KwHproseTcpClient(uris, mode);
    }

    @Override
    public final void close() {
        fdTrans.close();
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