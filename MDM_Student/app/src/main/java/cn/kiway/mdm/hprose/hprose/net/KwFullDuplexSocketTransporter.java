package cn.kiway.mdm.hprose.hprose.net;

import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

import hprose.net.TimeoutType;
import hprose.util.concurrent.Promise;
import hprose.util.concurrent.Timer;

/**
 * Created by Administrator on 2017/11/16.
 */

public final class KwFullDuplexSocketTransporter extends KwSocketTransporter {
    private final static AtomicInteger nextId = new AtomicInteger(0);
    private final Map<KwConnection, Map<Integer, Response>> responses = new ConcurrentHashMap<KwConnection,
            Map<Integer, Response>>();
    KwConntectionCallback conntectionCallback;

    public KwFullDuplexSocketTransporter(KwHproseTcpClient client) {
        super(client);
    }

    public void setKwConntectionCallback(KwConntectionCallback conntectionCallback) {
        this.conntectionCallback = conntectionCallback;
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
        if (conntectionCallback != null)
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
        if (conntectionCallback != null)
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
        if (conntectionCallback != null)
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
        if (conntectionCallback != null)
            conntectionCallback.onError(conn, e);
    }
}
