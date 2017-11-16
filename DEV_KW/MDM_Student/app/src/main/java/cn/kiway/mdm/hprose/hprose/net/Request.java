package cn.kiway.mdm.hprose.hprose.net;

import java.nio.ByteBuffer;

import hprose.util.concurrent.Promise;

/**
 * Created by Administrator on 2017/11/16.
 */

public final class Request {
    public final ByteBuffer buffer;
    public final Promise<ByteBuffer> result = new Promise<ByteBuffer>();
    public final int timeout;

    public Request(ByteBuffer buffer, int timeout) {
        this.buffer = buffer;
        this.timeout = timeout;
    }
}
