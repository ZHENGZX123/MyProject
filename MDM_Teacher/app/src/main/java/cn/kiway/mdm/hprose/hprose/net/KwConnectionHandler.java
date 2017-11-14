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
 * ConnectionHandler.java                                 *
 *                                                        *
 * hprose ConnectionHandler interface for Java.           *
 *                                                        *
 * LastModified: Apr 21, 2016                             *
 * Author: Ma Bingyao <andot@hprose.com>                  *
 *                                                        *
\**********************************************************/
package cn.kiway.mdm.hprose.hprose.net;

import hprose.net.TimeoutType;

import java.nio.ByteBuffer;

public interface KwConnectionHandler {
    void onConnect(KwConnection conn);
    void onConnected(KwConnection conn);
    void onReceived(KwConnection conn, ByteBuffer data, Integer id);
    void onSended(KwConnection conn, Integer id);
    void onClose(KwConnection conn);
    void onError(KwConnection conn, Exception e);
    void onTimeout(KwConnection conn, TimeoutType type);
    int getReadTimeout();
    int getWriteTimeout();
    int getConnectTimeout();
}
