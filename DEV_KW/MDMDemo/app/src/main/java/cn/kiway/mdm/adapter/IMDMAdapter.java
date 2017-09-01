package cn.kiway.mdm.adapter;

import android.content.Context;

/**
 * Created by Administrator on 2017/9/1.
 */

public interface IMDMAdapter {

    void init(Context c);

    void setWifiDisabled(boolean disabled);

    void isWifiDisabled();

    void setSettingsApplicationDisabled();

    void isSettingsApplicationDisabled();
}
