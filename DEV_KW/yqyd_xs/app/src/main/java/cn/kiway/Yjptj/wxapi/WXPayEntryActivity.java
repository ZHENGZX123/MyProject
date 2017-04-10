package cn.kiway.Yjptj.wxapi;

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

import static com.zk.myweex.WXApplication.callback;


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
        Log.d("test", "onResp reqCode = " + reqCode);
        switch (reqCode) {
            case ConstantsAPI.COMMAND_PAY_BY_WX:
                int code = resp.errCode;
                switch (code) {
                    case 0: {
                        Log.d("test", "支付成功");
                        callback.invoke("1");
                        finish();
                    }
                    break;
                    case -1:
                    case -2: {
                        Log.d("test", "支付错误");
                        callback.invoke("0");
                        Toast.makeText(this, "支付错误", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    break;
                }
                break;
            case ConstantsAPI.COMMAND_SENDAUTH:
                break;
        }
    }
}