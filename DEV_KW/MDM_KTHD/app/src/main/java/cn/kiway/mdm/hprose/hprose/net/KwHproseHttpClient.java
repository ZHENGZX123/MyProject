package cn.kiway.mdm.hprose.hprose.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

import cn.kiway.mdm.hprose.jrf.client.JRFClient;
import hprose.client.ClientContext;
import hprose.client.CookieManager;
import hprose.client.HproseClient;
import hprose.client.HproseHttpClient;
import hprose.common.HproseException;
import hprose.common.InvokeSettings;
import hprose.io.ByteBufferStream;
import hprose.io.HproseMode;
import hprose.util.Base64;
import hprose.util.concurrent.Promise;
import hprose.util.concurrent.Threads;

public class KwHproseHttpClient extends HproseClient {
    private String uri;
    KwConntectionCallback conntectionCallback;

    public long getFile(String local, String remote, JRFClient.DownLoadCallBack callBack) {
        long len = 0l;
        JRFClient cli = null;
        try {
            URI u = new URI(uri);
            cli = new JRFClient(new InetSocketAddress(u.getHost(), u.getPort() + 10));
            cli.start();
            len = cli.getFile(remote, 3, local, 1500, callBack);
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

    private static volatile ExecutorService pool = Executors.newCachedThreadPool();

    static {
        Threads.registerShutdownHandler(new Runnable() {
            public void run() {
                ExecutorService p = pool;
                pool = Executors.newCachedThreadPool();
                p.shutdownNow();
            }
        });
    }

    private final ConcurrentHashMap<String, String> headers = new ConcurrentHashMap<String, String>();
    private static boolean disableGlobalCookie = false;
    private static CookieManager globalCookieManager = new CookieManager();
    private final CookieManager cookieManager = disableGlobalCookie ? new CookieManager() : globalCookieManager;
    private boolean keepAlive = true;
    private int keepAliveTimeout = 300;
    private String proxyHost = null;
    private int proxyPort = 80;
    private String proxyUser = null;
    private String proxyPass = null;
    private HostnameVerifier hv = null;
    private SSLSocketFactory sslsf = null;

    public static void setThreadPool(ExecutorService threadPool) {
        pool = threadPool;
    }

    public static void setDisableGlobalCookie(boolean value) {
        disableGlobalCookie = value;
    }

    public static boolean isDisableGlobalCookie() {
        return disableGlobalCookie;
    }

    public KwHproseHttpClient() {
        super();
    }

    public KwHproseHttpClient(String uri, KwConntectionCallback conntectionCallback) {
        super(uri);
        this.uri = uri;
        this.conntectionCallback = conntectionCallback;
    }

    public KwHproseHttpClient(HproseMode mode) {
        super(mode);
    }

    public KwHproseHttpClient(String uri, HproseMode mode) {
        super(uri, mode);
    }

    public KwHproseHttpClient(String[] uris) {
        super(uris);
    }

    public KwHproseHttpClient(String[] uris, HproseMode mode) {
        super(uris, mode);
    }

    public static HproseClient create(String uri, HproseMode mode) throws IOException, URISyntaxException {
        String scheme = (new URI(uri)).getScheme().toLowerCase();
        if (!scheme.equals("http") && !scheme.equals("https")) {
            throw new HproseException("This client doesn't support " + scheme + " scheme.");
        }
        return new HproseHttpClient(uri, mode);
    }

    public static HproseClient create(String[] uris, HproseMode mode) throws IOException, URISyntaxException {
        for (int i = 0, n = uris.length; i < n; ++i) {
            String scheme = (new URI(uris[i])).getScheme().toLowerCase();
            if (!scheme.equals("http") && !scheme.equals("https")) {
                throw new HproseException("This client doesn't support " + scheme + " scheme.");
            }
        }
        return new HproseHttpClient(uris, mode);
    }

    public void setHeader(String name, String value) {
        String nl = name.toLowerCase();
        if (!nl.equals("content-type") &&
                !nl.equals("content-length") &&
                !nl.equals("connection") &&
                !nl.equals("keep-alive") &&
                !nl.equals("host")) {
            if (value == null) {
                headers.remove(name);
            } else {
                headers.put(name, value);
            }
        }
    }

    public String getHeader(String name) {
        return headers.get(name);
    }

    public boolean isKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(boolean keepAlive) {
        this.keepAlive = keepAlive;
    }

    public int getKeepAliveTimeout() {
        return keepAliveTimeout;
    }

    public void setKeepAliveTimeout(int keepAliveTimeout) {
        this.keepAliveTimeout = keepAliveTimeout;
    }

    public String getProxyHost() {
        return proxyHost;
    }

    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    public int getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
    }

    public String getProxyUser() {
        return proxyUser;
    }

    public void setProxyUser(String proxyUser) {
        this.proxyUser = proxyUser;
    }

    public String getProxyPass() {
        return proxyPass;
    }

    public void setProxyPass(String proxyPass) {
        this.proxyPass = proxyPass;
    }

    public HostnameVerifier getHostnameVerifier() {
        return hv;
    }

    public void setHostnameVerifier(HostnameVerifier hv) {
        this.hv = hv;
    }

    public SSLSocketFactory getSSLSocketFactory() {
        return sslsf;
    }

    public void setSSLSocketFactory(SSLSocketFactory sslsf) {
        this.sslsf = sslsf;
    }

    @SuppressWarnings({"unchecked"})
    private ByteBuffer syncSendAndReceive(ByteBuffer request, ClientContext context) throws Throwable {
        final InvokeSettings settings = context.getSettings();
        int timeout = settings.getTimeout();
        URL url = new URL(uri);
        Properties prop = System.getProperties();
        prop.put("http.keepAlive", Boolean.toString(keepAlive));
        if (proxyHost != null) {
            prop.put("http.proxyHost", proxyHost);
            prop.put("http.proxyPort", Integer.toString(proxyPort));
        } else {
            prop.remove("http.proxyHost");
            prop.remove("http.proxyPort");
        }
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //this.conntectionCallback.onConnected();
        if (url.getProtocol().equals("https")) {
            if (hv != null) ((HttpsURLConnection) conn).setHostnameVerifier(hv);
            if (sslsf != null) ((HttpsURLConnection) conn).setSSLSocketFactory(sslsf);
        }
        conn.setConnectTimeout(timeout);
        conn.setReadTimeout(timeout);
        conn.setRequestProperty("Cookie", cookieManager.getCookie(url.getHost(),
                url.getFile(),
                url.getProtocol().equals("https")));
        if (keepAlive) {
            conn.setRequestProperty("Connection", "keep-alive");
            conn.setRequestProperty("Keep-Alive", Integer.toString(keepAliveTimeout));
        } else {
            conn.setRequestProperty("Connection", "close");
        }
        if (proxyUser != null && proxyPass != null) {
            conn.setRequestProperty("Proxy-Authorization",
                    "Basic " + Base64.encode((proxyUser + ":" + proxyPass).getBytes()));
        }
        for (Entry<String, String> entry : headers.entrySet()) {
            conn.setRequestProperty(entry.getKey(), entry.getValue());
        }
        Map<String, List<String>> header = (Map<String, List<String>>) (context.get("httpHeader"));
        if (header != null) {
            for (Entry<String, List<String>> entry : header.entrySet()) {
                String key = entry.getKey();
                for (String value : entry.getValue()) {
                    conn.addRequestProperty(key, value);
                }
            }
        }
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.setRequestProperty("Content-Type", "application/hprose");
        conn.setRequestProperty("Content-Length", Integer.toString(request.remaining()));
        OutputStream ostream = null;
        try {
            ostream = conn.getOutputStream();
            ByteBufferStream stream = new ByteBufferStream(request);
            stream.writeTo(ostream);
            ostream.flush();
        } finally {
            if (ostream != null) ostream.close();
        }
        context.set("httpHeader", conn.getHeaderFields());
        List<String> cookieList = new ArrayList<String>();
        int i = 1;
        String key;
        while ((key = conn.getHeaderFieldKey(i)) != null) {
            if (key.equalsIgnoreCase("set-cookie") ||
                    key.equalsIgnoreCase("set-cookie2")) {
                cookieList.add(conn.getHeaderField(i));
            }
            ++i;
        }
        cookieManager.setCookie(cookieList, url.getHost());
        InputStream istream = null;
        ByteBufferStream response = new ByteBufferStream();
        try {
            istream = conn.getInputStream();
            response.readFrom(istream);
            response.flip();
            return response.buffer;
        } catch (IOException e) {
            InputStream estream = null;
            try {
                estream = conn.getErrorStream();
                if (estream != null) {
                    response.readFrom(estream);
                    response.flip();
                    return response.buffer;
                } else {
                    throw e;
                }
            } finally {
                if (estream != null) estream.close();
            }
        } finally {
            if (istream != null) istream.close();
        }
    }

    @Override
    protected Promise<ByteBuffer> sendAndReceive(final ByteBuffer request, final ClientContext context) {
        final Promise<ByteBuffer> promise = new Promise<ByteBuffer>();
        pool.submit(new Runnable() {
            public void run() {
                try {
                    promise.resolve(syncSendAndReceive(request, context));
                } catch (Throwable ex) {
                    promise.reject(ex);
                }
            }
        });
        return promise;
    }
}


