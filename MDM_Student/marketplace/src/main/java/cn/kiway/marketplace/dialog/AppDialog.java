package cn.kiway.marketplace.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import cn.kiway.marketplace.R;
import cn.kiway.marketplace.util.MarketApp;

/**
 * Created by Administrator on 2017/12/8.
 */

public class AppDialog extends Dialog implements DialogInterface.OnShowListener {

    protected ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT);
    private Context c;
    MarketApp marketApp;

    public AppDialog(@NonNull Context context) {
        super(context);
        this.c = context;
        fullWindowCenter();
        setOnShowListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.marke_dialog_appview);
    }

    public void setMarketApp(MarketApp marketApp) {
        this.marketApp = marketApp;
    }

    protected void fullWindowCenter() {
        layoutParams = getWindow().getAttributes();
        Rect rect = new Rect();
        View v = getWindow().getDecorView();
        getWindow().setWindowAnimations(R.style.mystyle);  //添加动画
        v.getWindowVisibleDisplayFrame(rect);
    }

    @Override
    public void onShow(DialogInterface dialogInterface) {

    }
}
