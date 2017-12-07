package cn.kiway.mdm.hprose.hprose.net;

/**
 * Created by Administrator on 2017/11/16.
 */

import java.nio.ByteBuffer;

import hprose.util.concurrent.Promise;
import hprose.util.concurrent.Timer;

public final class Response {
    public final Promise<ByteBuffer> result;
    public final Timer timer;

    public Response(Promise<ByteBuffer> result, Timer timer) {
        this.result = result;
        this.timer = timer;
    }
}