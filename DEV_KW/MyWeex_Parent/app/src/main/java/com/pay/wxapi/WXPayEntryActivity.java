package com.pay.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.pay.util.Constants;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


/**
 * 微信支付回调
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.pay_result);
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        int reqCode = resp.getType();
        switch (reqCode) {
            case ConstantsAPI.COMMAND_PAY_BY_WX:
                int code = resp.errCode;
                switch (code) {
                    case 0:
                        try {
                            //支付成功后的代码
                            Log.d("test", "支付成功");
                            Intent i = new Intent();
                            setResult(8888, i);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case -1:
                    case -2:
                        Toast.makeText(this, "支付错误", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                }
                break;
            case ConstantsAPI.COMMAND_SENDAUTH:
                break;
        }
    }
}