package cn.kiway.mdm.hprose.socket.tcp;

import android.util.Log;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.security.AccessControlException;
import java.util.Iterator;
import java.util.Set;

import cn.kiway.mdm.hprose.socket.tcp.util.NETConfig;


/**
 * 客户端封装
 *
 * @author Lin
 */
public class Client extends Connection implements EndPoint {
    static {
        try {
            System.setProperty("java.net.preferIPv6Addresses", "false");
        } catch (AccessControlException ignored) {
        }
    }

    static final String TAG = NETConfig.TAG;
    private Selector selector;
    private int emptySelects;
    private volatile boolean shutdown;
    private final Object updateLock = new Object();
    private Thread updateThread;
    private int connectTimeout;
    private InetAddress connectHost;
    private int connectTcpPort;
    private int connectUdpPort;
    private boolean isClosed;
    private TcpMessageCallBack tcpMessageCallBack;

    /**
     * Creates a Client with a write buffer size of 8192 and an object buffer size of 2048.
     */
    public Client() {
        this(8192, 2048);
    }

    /**
     * @param writeBufferSize One buffer of this size is allocated. Objects are serialized to the write buffer where
     *                        the bytes are
     *                        queued until they can be written to the TCP socket.
     *                        <p>
     *                        Normally the socket is writable and the bytes are written immediately. If the socket
     *                        cannot be written to and
     *                        enough serialized objects are queued to overflow the buffer, then the connection will be
     *                        closed.
     *                        <p>
     *                        The write buffer should be sized at least as large as the largest object that will be
     *                        sent, plus some head room to
     *                        allow for some serialized objects to be queued in case the buffer is temporarily not
     *                        writable. The amount of head
     *                        room needed is dependent upon the size of objects being sent and how often they are sent.
     * @param readBufferSize  One (using only TCP) or three (using both TCP and UDP) buffers of this size are
     *                        allocated. These
     *                        buffers are used to hold the bytes for a single object graph until it can be sent over
     *                        the network or
     *                        deserialized.
     *                        <p>
     *                        The object buffers should be sized at least as large as the largest object that will be
     *                        sent or received.
     */
    public Client(int writeBufferSize, int readBufferSize) {
        super();
        endPoint = this;
        initialize(writeBufferSize, readBufferSize);
        try {
            selector = Selector.open();
        } catch (IOException ex) {
            throw new RuntimeException("Error opening selector.", ex);
        }
    }

    /**
     * Opens a TCP only client.
     *
     * @see #connect(int, InetAddress, int, int, TcpMessageCallBack)
     */
    public void connect(int timeout, String host, int tcpPort, TcpMessageCallBack tcpMessageCallBack) throws
            IOException {
        connect(timeout, InetAddress.getByName(host), tcpPort, -1, tcpMessageCallBack);
    }

    /**
     * Opens a TCP and UDP client.
     *
     * @see #connect(int, InetAddress, int, int, TcpMessageCallBack)
     */
    public void connect(int timeout, String host, int tcpPort, int udpPort, TcpMessageCallBack tcpMessageCallBack)
            throws IOException {
        connect(timeout, InetAddress.getByName(host), tcpPort, udpPort, tcpMessageCallBack);
    }

    /**
     * Opens a TCP only client.
     *
     * @see #connect(int, InetAddress, int, int, TcpMessageCallBack)
     */
    public void connect(int timeout, InetAddress host, int tcpPort, TcpMessageCallBack tcpMessageCallBack) throws
            IOException {
        connect(timeout, host, tcpPort, -1, tcpMessageCallBack);
    }

    /**
     * Opens a TCP and UDP client. Blocks until the connection is complete or the timeout is reached.
     * <p>
     * Because the framework must perform some minimal communication before the connection is considered successful,
     * {@link #update(int)} must be called on a separate thread during the connection process.
     *
     * @throws IllegalStateException if called from the connection's update thread.
     * @throws IOException           if the client could not be opened or connecting times out.
     */
    public void connect(int timeout, InetAddress host, int tcpPort, int udpPort, TcpMessageCallBack
            tcpMessageCallBack) throws IOException {
        if (host == null) throw new IllegalArgumentException("host cannot be null.");
        if (Thread.currentThread() == getUpdateThread())
            throw new IllegalStateException("Cannot connect on the connection's update thread.");
        this.connectTimeout = timeout;
        this.connectHost = host;
        this.connectTcpPort = tcpPort;
        this.connectUdpPort = udpPort;
        this.tcpMessageCallBack = tcpMessageCallBack;
        close();
        if (NETConfig.NET_DEBUG) {
            if (udpPort != -1)
                Log.i(TAG, "Connecting: " + this + host + ":" + tcpPort + "/" + udpPort);
            else
                Log.i(TAG, "Connecting: " + this + host + ":" + tcpPort);
        }

        try {
            if (tcpPort != -1) {
                synchronized (updateLock) {
                    selector.wakeup();
                    tcp.connect(selector, new InetSocketAddress(host, tcpPort), 5000, tcpMessageCallBack);
                    setConnected(true);
                    if (NETConfig.NET_DEBUG) {//连接成功
                        if (tcpMessageCallBack != null)
                            tcpMessageCallBack.connectTcpSuccess();
                        Log.i(TAG, "Connected:  TCP" + this + host + ":" + udpPort);
                    }
                }
            }
            if (udpPort != -1) {
            }
        } catch (IOException ex) {
            close();
            if (tcpMessageCallBack != null)
                try {
                    tcpMessageCallBack.connectTcpFailed();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            throw ex;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Calls {@link #connect(int, InetAddress, int, int, TcpMessageCallBack) connect} with the values last passed to
     * connect.
     *
     * @throws IllegalStateException if connect has never been called.
     */
    public void reconnect() throws IOException {
        reconnect(connectTimeout);
    }

    /**
     * Calls {@link #connect(int, InetAddress, int, int, TcpMessageCallBack) connect} with the specified timeout and
     * the other values
     * last passed to
     * connect.
     *
     * @throws IllegalStateException if connect has never been called.
     */
    public void reconnect(int timeout) throws IOException {
        if (connectHost == null) throw new IllegalStateException("This client has never been connected.");
        connect(timeout, connectHost, connectTcpPort, connectUdpPort, tcpMessageCallBack);
    }

    /**
     * Reads or writes any pending data for this client. Multiple threads should not call this method at the same time.
     *
     * @param timeout Wait for up to the specified milliseconds for data to be ready to process. May be zero to
     *                return immediately
     *                if there is no data to process.
     */
    public void update(int timeout) throws IOException {
        updateThread = Thread.currentThread();
        synchronized (updateLock) { // Blocks to avoid a select while the selector is used to bind the server
            // connection.
        }
        long startTime = System.currentTimeMillis();
        int select = 0;
        if (timeout > 0) {
            select = selector.select(timeout);
        } else {
            select = selector.selectNow();
        }
        if (select == 0) {
            emptySelects++;
            if (emptySelects == 100) {
                emptySelects = 0;
                // NIO freaks and returns immediately with 0 sometimes, so try to keep from hogging the CPU.
                long elapsedTime = System.currentTimeMillis() - startTime;
                try {
                    if (elapsedTime < 25) Thread.sleep(25 - elapsedTime);
                } catch (InterruptedException ex) {
                }
            }
        } else {
            emptySelects = 0;
            isClosed = false;
            Set<SelectionKey> keys = selector.selectedKeys();
            synchronized (keys) {
                for (Iterator<SelectionKey> iter = keys.iterator(); iter.hasNext(); ) {
                    SelectionKey selectionKey = iter.next();
                    iter.remove();
                    try {
                        int ops = selectionKey.readyOps();
                        if ((ops & SelectionKey.OP_READ) == SelectionKey.OP_READ) {
                            if (selectionKey.attachment() == tcp) {
                                byte[] tcpdata = tcp.readData(this);
                                if (tcpdata == null) break;
                                if (NETConfig.NET_DEBUG) {
                                    Log.i(TAG, this + " received TCP: " + new String(tcpdata));
                                }
                                if (tcpMessageCallBack != null)
                                    tcpMessageCallBack.accpetMessage(new String(tcpdata));
                                notifyReceived(tcpdata);
                            } else {
                            }
                        }
                        if ((ops & SelectionKey.OP_WRITE) == SelectionKey.OP_WRITE) tcp.writeOperation();
                    } catch (CancelledKeyException ignored) {
                        // Connection is closed.
                        if (tcpMessageCallBack != null)
                            try {
                                tcpMessageCallBack.disconnectTcp();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                    } catch (Exception e) {
                        // Connection is closed.
                        if (tcpMessageCallBack != null)
                            try {
                                tcpMessageCallBack.disconnectTcp();
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }
                        e.printStackTrace();
                    }
                }
            }
        }


    }

    void keepAlive() {
        FrameworkMessage.KeepAlive mKeepAlive = new FrameworkMessage.KeepAlive();
        if (!isConnected) return;
        long time = System.currentTimeMillis();
        if (tcp.needsKeepAlive(time)) sendTCP(mKeepAlive.getByte());
    }

    public void run() {
        if (NETConfig.NET_DEBUG) Log.i(TAG, "Client thread started.");
        shutdown = false;
        while (!shutdown) {
            try {
                update(250);
            } catch (IOException ex) {
                if (NETConfig.NET_DEBUG) {
                    if (isConnected)
                        Log.i(TAG, "Unable to update connection: " + this, ex);
                    else
                        Log.i(TAG, "Unable to update connection.", ex);
                }
                close();
            }
        }
        if (NETConfig.NET_DEBUG) Log.i(TAG, "Client thread stopped.");
    }

    public void start() {
        // Try to let any previous update thread stop.
        if (updateThread != null) {
            shutdown = true;
            try {
                updateThread.join(2000);
            } catch (InterruptedException ignored) {
            }
        }
        updateThread = new Thread(this, "Client");
        updateThread.setDaemon(true);
        updateThread.start();
    }

    public void stop() {
        if (shutdown) return;
        close();
        if (NETConfig.NET_DEBUG) Log.i(TAG, "Client thread stopping.");
        shutdown = true;
        selector.wakeup();
    }

    public void close() {
        super.close();
        synchronized (updateLock) { // Blocks to avoid a select while the selector is used to bind the server
            // connection.
        }
        // Select one last time to complete closing the socket.
        if (!isClosed) {
            isClosed = true;
            selector.wakeup();
            try {
                selector.selectNow();
            } catch (IOException ignored) {
            }
        }
    }


    Listener listener;

    public void addListener(Listener listener) {
        super.addListener(listener);
        if (NETConfig.NET_DEBUG) Log.i(TAG, "Client listener added.");
        this.listener = listener;
    }

    public void removeListener(Listener listener) {
        super.removeListener(listener);
        if (NETConfig.NET_DEBUG) Log.i(TAG, "Client listener removed.");
    }


    public Thread getUpdateThread() {
        return updateThread;
    }

    public interface TcpMessageCallBack {
        public void accpetMessage(String message) throws Exception;//接收消息

        public void connectTcpSuccess() throws Exception;//tcp连接成功

        public void connectTcpFailed() throws Exception;//tcp 连接错误

        public void disconnectTcp() throws Exception;//tcp 断开连接
    }
}
