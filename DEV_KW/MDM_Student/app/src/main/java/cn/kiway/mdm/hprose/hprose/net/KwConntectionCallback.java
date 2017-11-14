package cn.kiway.mdm.hprose.hprose.net;

import java.nio.ByteBuffer;

import hprose.net.TimeoutType;

/**
 * Created by Administrator on 2017/11/10.
 */

public interface KwConntectionCallback {
    void onConnect(KwConnection conn);
    void onConnected(KwConnection conn);
    void onReceived(KwConnection conn, ByteBuffer data, Integer id);
    void onClose();
    void onError(KwConnection conn, Exception e);
    void onTimeout(KwConnection conn, TimeoutType type);
}
