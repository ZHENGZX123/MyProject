package com.zk.myweex.https;

public interface WXRequestListener {

  void onSuccess(WXHttpTask task);

  void onError(WXHttpTask task);
}
