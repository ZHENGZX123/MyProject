package cn.kiway.brower.Browser;

import android.os.Handler;
import android.os.Message;
import cn.kiway.brower.View.NinjaWebView;

public class NinjaClickHandler extends Handler {
    private NinjaWebView webView;

    public NinjaClickHandler(NinjaWebView webView) {
        super();
        this.webView = webView;
    }

    @Override
    public void handleMessage(Message message) {
        super.handleMessage(message);
        webView.getBrowserController().onLongPress(message.getData().getString("url"));
    }
}
