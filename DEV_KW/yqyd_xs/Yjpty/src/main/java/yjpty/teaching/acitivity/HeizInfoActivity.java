package yjpty.teaching.acitivity;

import android.annotation.SuppressLint;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;

import yjpty.teaching.R;
import yjpty.teaching.adpater.HeizInfoAdapter;
import yjpty.teaching.http.HttpResponseModel;
import yjpty.teaching.http.IUrContant;
import yjpty.teaching.model.ClassModel;
import yjpty.teaching.model.HeziStautsModel;
import yjpty.teaching.util.IConstant;
import yjpty.teaching.util.ViewUtil;
import yjpty.teaching.wifi.WifiAdmin;

import static android.content.Context.WIFI_SERVICE;

public class HeizInfoActivity extends BaseActivity {
    ListView listView;
    WifiManager.MulticastLock lock;
    static DatagramSocket udpSocket = null;
    static DatagramPacket udpPacket = null;
    HeizInfoAdapter adapter;
    boolean isRun;
    String codeString, codeResoucre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yjpty_activity_common_list);
        if (app.client != null) {
            app.client.close();
            app.client.destory();
        }
        WifiManager manager = (WifiManager) getApplicationContext()
                .getSystemService(WIFI_SERVICE);
        lock = manager.createMulticastLock("localWifi");
        try {
            initView();
            loadData();
            setData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        new UDPClientThread().start();
        handle.sendEmptyMessageDelayed(0, 20000);// 20秒检查更新盒子状态
        if (null != bundle && null != bundle.getString(IConstant.BUNDLE_PARAMS) &&
                !bundle.getString(IConstant.BUNDLE_PARAMS).equals("")) {
            WifiAdmin admin = new WifiAdmin(this);
            admin.addNetwork(admin.CreateWifiInfo(bundle.getString(IConstant.BUNDLE_PARAMS), bundle.getString
                    (IConstant.BUNDLE_PARAMS1), bundle.getInt(IConstant.BUNDLE_PARAMS2)));
        }
    }

    @Override
    public void initView() throws Exception {
        super.initView();
        findViewById(R.id.wifi_info).setVisibility(View.VISIBLE);
        listView = ViewUtil.findViewById(this, R.id.boy_list);
        ViewUtil.setContent(this, R.id.title, R.string.connect_box);
        adapter = new HeizInfoAdapter(this, new ArrayList<HeziStautsModel>(),
                app.classModels);
        listView.setAdapter(adapter);
    }

    @Override
    public void loadData() throws Exception {
        super.loadData();
        if (mCache.getAsJSONObject(IUrContant.GET_CUSE_BASE) == null)
            IConstant.HTTP_CONNECT_POOL.addRequest(IUrContant.GET_CUSE_BASE, null, activityHandler, 2);
        IConstant.HTTP_CONNECT_POOL.addRequest(IUrContant.GET_MY_CLASS_LIST, null, activityHandler, 2);
    }

    @Override
    public void setData() throws Exception {
        super.setData();
        adapter.list.clear();
        for (int i = 0; i < app.classModels.size(); i++) {
            HeziStautsModel model = new HeziStautsModel();
            model.setClassName(app.classModels.get(i).getClassName());
            model.setGrade(app.classModels.get(i).getYear());
            model.setHeziCode(app.classModels.get(i).getHezi_code());
            model.setHeziType(2);
            adapter.list.add(model);
        }
        adapter.notifyDataSetChanged();
        if (adapter.getCount() <= 0) {
            listView.setVisibility(View.GONE);
            ViewUtil.setContent(this, R.id.no_data, "您还没有班级，赶快去加入班级吧！");
            findViewById(R.id.no_data).setVisibility(View.VISIBLE);
        } else {
            listView.setVisibility(View.VISIBLE);
            findViewById(R.id.no_data).setVisibility(View.GONE);
        }
    }

    private class UDPClientThread extends Thread {
        public UDPClientThread() {
            /* 开启线程 */
            System.out.println("监听广播开启");
            isRun = true;
        }

        @Override
        public void run() {
            byte[] data = new byte[256];
            try {
                udpSocket = new DatagramSocket(43708);
                udpPacket = new DatagramPacket(data, data.length);
            } catch (SocketException e1) {
                e1.printStackTrace();
            }
            while (isRun) {
                try {
                    lock.acquire();
                    udpSocket.receive(udpPacket);
                } catch (Exception e) {
                }
                if (udpPacket != null && null != udpPacket.getAddress()) {
                    codeString = new String(data, 0, udpPacket.getLength());
                    System.out.println("内容：：：" + codeString);
                    final String ip = udpPacket.getAddress().toString()
                            .substring(1);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            if (codeString.indexOf(":::") > 0) {
                                codeResoucre = codeString.split(":::")[1];
                                codeString = codeString.split(":::")[0];
                            }
                            for (int i = 0; i < adapter.list.size(); i++) {
                                if (codeString.equals(adapter.list.get(i)
                                        .getHeziCode())
                                        && !ip.equals("192.168.43.1")) {
                                    adapter.list.get(i).setHeziIP(ip);
                                    adapter.list.get(i).setHeziType(1);
                                    adapter.list.get(i).setHeziResoures(
                                            codeResoucre);
                                    adapter.list.get(i).setAcceptUdpTime(
                                            System.currentTimeMillis());
                                }
                            }
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
                lock.release();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (udpSocket != null) {
            udpSocket.close();
            udpSocket.disconnect();
            isRun = false;
        }
    }

    @SuppressLint("HandlerLeak")
    Handler handle = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 0) {
                for (int i = 0; i < adapter.list.size(); i++) {
                    if (System.currentTimeMillis()
                            - adapter.list.get(i).getAcceptUdpTime() > 20 * 1000L) {
                        adapter.list.get(i).setHeziType(2);
                    }
                }
                adapter.notifyDataSetChanged();
                handle.sendEmptyMessageDelayed(0, 20000);
            } else if (msg.what == 1) {

            }
        }

        ;
    };

    @Override
    public void httpSuccess(HttpResponseModel message) throws Exception {
        super.httpSuccess(message);
        if (message.getUrl().equals(IUrContant.GET_CUSE_BASE)) {
            JSONObject data = new JSONObject(new String(message.getResponse()));
            if (data.optInt("StatusCode") == 200) {
                mCache.put(IUrContant.GET_CUSE_BASE, data);
            }
        } else if (message.getUrl().equals(IUrContant.GET_MY_CLASS_LIST)) {
            JSONObject data = new JSONObject(new String(message.getResponse()));
            if (data.optInt("StatusCode") == 200) {
                mCache.put(IUrContant.GET_MY_CLASS_LIST, data);
                JSONArray array = data.optJSONArray("data");
                if (array != null) {
                    app.classModels.clear();
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject item = array.optJSONObject(i);
                        ClassModel model = new ClassModel();
                        model.setId(item.optString("id"));// 班级id
                        model.setClassName(item.optString("name"));// 班级名字
                        model.setHezi_code(item.optString("mac"));// 盒子编号
                        model.setSchoolId(item.optString("schoolId"));// 学校id
                        model.setYear(item.optString("gradeId"));// 年级
                        app.classModels.add(model);
                    }
                }
                setData();
            }
        }
    }
}
