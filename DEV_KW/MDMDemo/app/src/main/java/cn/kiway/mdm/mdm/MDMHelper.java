package cn.kiway.mdm.mdm;

import cn.kiway.mdm.adapter.IMDMAdapter;

/**
 * Created by Administrator on 2017/9/1.
 */

public class MDMHelper {

    final IMDMAdapter mAdapter;

    public MDMHelper(IMDMAdapter adapter) {
        mAdapter = adapter;
    }

    private void setWifiDisabled(boolean disabled) {
        mAdapter.setWifiDisabled(disabled);
    }
}
