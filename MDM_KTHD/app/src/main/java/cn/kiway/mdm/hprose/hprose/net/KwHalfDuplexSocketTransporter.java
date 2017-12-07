package cn.kiway.mdm.hprose.hprose.net;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeoutException;

import hprose.net.TimeoutType;
import hprose.util.concurrent.Promise;
import hprose.util.concurrent.Timer;

/**
 * Created by Administrator on 2017/11/16.
 */

public final class KwHalfDuplexSocketTransporter extends KwSocketTransporter {
    private final Map<KwConnection, Response> responses = new ConcurrentHashMap<KwConnection, Response>();

    public KwHalfDuplexSocketTransporter(KwHproseTcpClient client) {
        super(client);
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
        if (conntectionCallback != null)
            conntectionCallback.onConnect(conn);
        size.incrementAndGet();
    }

    public final void onConnected(KwConnection conn) {
        if (conntectionCallback != null)
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
        if (conntectionCallback != null)
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
        if (conntectionCallback != null)
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
        if (conntectionCallback != null)
            conntectionCallback.onError(conn, e);
    }


}