package cn.kiway.marketplace.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cn.kiway.marketplace.R;
import cn.kiway.marketplace.util.MarketApp;

import static cn.kiway.marketplace.util.IUrContant.BASE_URL;

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
        ImageView pic= (ImageView) findViewById(R.id.pic);
        TextView appName= (TextView) findViewById(R.id.appName);
        TextView appType= (TextView) findViewById(R.id.appType);
        TextView appPlatform= (TextView) findViewById(R.id.appPlatform);
        TextView createDate= (TextView) findViewById(R.id.createDate);
        TextView appContent= (TextView) findViewById(R.id.appContent);
        if (!marketApp.iocn.startsWith("http://"))
            marketApp.iocn = BASE_URL + marketApp.iocn;
        Glide.with(c).load(marketApp.iocn).into(pic);
        appName.setText(marketApp.name);
        appType.setText("类型:"+marketApp.appClassifyName);
        appPlatform.setText("所属平台："+marketApp.platform+"           大小 :"+marketApp.size);
        createDate.setText("更新时间:"+getDateField(Long.parseLong(marketApp.createDate)));
        appContent.setText(marketApp.description);
    }
    public static String getDateField(long time) {
        String s = null;
        Date date = new Date(time);
        SimpleDateFormat sdf;
                    sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                            Locale.getDefault());
                    s = sdf.format(date);
        return s;
    }
}
