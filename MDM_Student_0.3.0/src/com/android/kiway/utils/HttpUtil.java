package com.android.kiway.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.android.kiway.KWApp;
import com.android.kiway.activity.MainActivity;
import com.android.kiway.activity.SettingActivity;
import com.android.kiway.entity.App;
import com.android.kiway.entity.AppCharge;
import com.android.kiway.entity.Call;
import com.android.kiway.entity.Network;
import com.android.kiway.entity.Wifi;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.android.kiway.utils.Constant.clientUrl;
import static com.android.kiway.utils.Constant.serverUrl;

/**
 * Created by Administrator on 2018/1/10.
 */

public class HttpUtil {


    private static boolean is301 = false;

    public static void updateDefaultPwd(final Activity c, final String defaultPwd) {
        c.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    AsyncHttpClient client = new AsyncHttpClient();
                    client.addHeader("x-auth-token", c.getSharedPreferences("kiway", 0).getString("x-auth-token", ""));
                    client.setTimeout(10000);
                    RequestParams param = new RequestParams();
                    param.put("imei", Utils.getIMEI(c));
                    param.put("password", defaultPwd);
                    Log.d("test", "updateDefaultPwd params = " + param.toString());
                    String url = clientUrl + "device/student/defaultPassword";
                    Log.d("test", "updateDefaultPwd = " + url);
                    client.post(c, url, param, new TextHttpResponseHandler() {
                        @Override
                        public void onSuccess(int code, Header[] headers, String ret) {
                            Log.d("test", "updateDefaultPwd onSuccess = " + ret);

                        }

                        @Override
                        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                            Log.d("test", "updateDefaultPwd onFailure = " + s);
                            Toast.makeText(c, "密码保存到云服务器失败，请重新设置密码：设置-设置解锁方式-输入新密码。", Toast.LENGTH_SHORT).show();
                            check301(c, s);
                        }
                    });
                } catch (Exception e) {
                    Log.d("test", "e = " + e.toString());
                }
            }
        });
    }

    public static void uploadLocation(final MainActivity c, final double longitude, final double latitude, final String dateStr) {
        c.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    AsyncHttpClient client = new AsyncHttpClient();
                    client.addHeader("x-auth-token", c.getSharedPreferences("kiway", 0).getString("x-auth-token", ""));
                    client.setTimeout(10000);
                    JSONArray array = new JSONArray();
                    JSONObject o1 = new JSONObject();
                    o1.put("imei", Utils.getIMEI(c));
                    o1.put("longitude", "" + longitude);
                    o1.put("latitude", "" + latitude);
                    o1.put("operation", "GPS");
                    array.put(o1);
                    Log.d("test", "location array = " + array.toString());
                    StringEntity stringEntity = new StringEntity(array.toString(), "utf-8");
                    String url = clientUrl + "device/locationTrack";
                    Log.d("test", "locationTrack = " + url);
                    client.post(c, url, stringEntity, "application/json", new TextHttpResponseHandler() {
                        @Override
                        public void onSuccess(int code, Header[] headers, String ret) {
                            Log.d("test", "locationTrack onSuccess = " + ret);
                            c.getSharedPreferences("kiway", 0).edit().putFloat(dateStr + "_lastLongitude", (float) longitude).commit();
                            c.getSharedPreferences("kiway", 0).edit().putFloat(dateStr + "_lastLatitude", (float) latitude).commit();
                        }

                        @Override
                        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                            Log.d("test", "locationTrack onFailure = " + s);
                            check301(c, s);
                        }
                    });
                } catch (Exception e) {
                    Log.d("test", "e = " + e.toString());
                }
            }
        });
    }

    public static void deviceRuntime(final Activity c, final String flag, final boolean check301) {
        new Thread() {
            @Override
            public void run() {
                c.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String imei = Utils.getIMEI(c);
                            AsyncHttpClient client = new AsyncHttpClient();
                            client.addHeader("x-auth-token", c.getSharedPreferences("kiway", 0).getString
                                    ("x-auth-token", ""));
                            client.setTimeout(10000);
                            JSONArray array = new JSONArray();
                            JSONObject param = new JSONObject();
                            param.put("imei", imei);
                            param.put("flag", flag);
                            param.put("operation", "submitData");
                            array.put(param);
                            Log.d("test", "array = " + array.toString());
                            StringEntity stringEntity = new StringEntity(array.toString(), "utf-8");
                            String url = clientUrl + "device/deviceRuntime";
                            Log.d("test", "deviceRuntime = " + url);
                            client.post(c, url, stringEntity, "application/json", new TextHttpResponseHandler() {
                                @Override
                                public void onSuccess(int code, Header[] headers, String ret) {
                                    Log.d("test", "deviceRuntime onSuccess = " + ret);
                                }

                                @Override
                                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                                    if (check301) {
                                        check301(c, s);
                                    }
                                    Log.d("test", "deviceRuntime onFailure = " + s);
                                }
                            });
                        } catch (Exception e) {
                            Log.d("test", "e = " + e.toString());
                        }
                    }
                });
            }
        }.start();
    }

    public static void appCharge(final MainActivity c) {
        c.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    AsyncHttpClient client = new AsyncHttpClient();
                    client.addHeader("x-auth-token", c.getSharedPreferences("kiway", 0).getString("x-auth-token", ""));
                    client.setTimeout(10000);
                    RequestParams param = new RequestParams();
                    String url = clientUrl + "device/appCharge?imei=" + Utils.getIMEI(c);
                    Log.d("test", "appCharge = " + url);
                    client.get(c, url, param, new TextHttpResponseHandler() {
                        @Override
                        public void onSuccess(int code, Header[] headers, String ret) {
                            Log.d("test", "appCharge onSuccess = " + ret);
                            try {
                                JSONArray data = new JSONObject(ret).getJSONArray("data");
                                ArrayList<AppCharge> networks = new GsonBuilder().create().fromJson(data.toString(),
                                        new TypeToken<List<AppCharge>>() {
                                        }.getType());
                                for (AppCharge ac : networks) {
                                    ac.url = serverUrl + ac.url;
                                }
                                //存进数据库里
                                new MyDBHelper(c).deleteAppcharge(null);
                                for (AppCharge n : networks) {
                                    n.priority = 2;
                                    new MyDBHelper(c).addAppcharge(n);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                            check301(c, s);
                            Log.d("test", "appCharge onFailure = " + s);
                        }
                    });
                } catch (Exception e) {
                    Log.d("test", "e = " + e.toString());
                }
            }
        });
    }

    public static void networkDeviceCharge(final MainActivity c) {
        c.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    AsyncHttpClient client = new AsyncHttpClient();
                    client.addHeader("x-auth-token", c.getSharedPreferences("kiway", 0).getString("x-auth-token", ""));
                    client.setTimeout(10000);
                    RequestParams param = new RequestParams();
                    String url = clientUrl + "device/networkDeviceCharge?imei=" + Utils.getIMEI(c);
                    Log.d("test", "networkDeviceCharge = " + url);
                    client.get(c, url, param, new TextHttpResponseHandler() {
                        @Override
                        public void onSuccess(int code, Header[] headers, String ret) {
                            Log.d("test", "networkDeviceCharge onSuccess = " + ret);
                            try {
                                JSONArray data = new JSONObject(ret).getJSONArray("data");
                                ArrayList<Network> networks = new GsonBuilder().create().fromJson(data.toString(),
                                        new TypeToken<List<Network>>() {
                                        }.getType());
                                //1.存进数据库里
                                new MyDBHelper(c).deleteNetwork(null);
                                for (Network n : networks) {
                                    new MyDBHelper(c).addNetwork(n);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                            check301(c, s);
                            Log.d("test", "networkDeviceCharge onFailure = " + s);
                        }
                    });
                } catch (Exception e) {
                    Log.d("test", "e = " + e.toString());
                }
            }
        });
    }

    public static void wifi(final MainActivity c) {
        c.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    AsyncHttpClient client = new AsyncHttpClient();
                    client.addHeader("x-auth-token", c.getSharedPreferences("kiway", 0).getString("x-auth-token", ""));
                    client.setTimeout(10000);
                    RequestParams param = new RequestParams();
                    Log.d("test", "param = " + param.toString());
                    String url = clientUrl + "device/wifi";
                    Log.d("test", "wifi = " + url);
                    client.get(c, url, param, new TextHttpResponseHandler() {
                        @Override
                        public void onSuccess(int code, Header[] headers, String ret) {
                            Log.d("test", "wifi onSuccess = " + ret);

                            try {
                                JSONArray data = new JSONObject(ret).getJSONArray("data");
                                ArrayList<Wifi> wifis = new GsonBuilder().create().fromJson(data.toString(), new
                                        TypeToken<List<Wifi>>() {
                                        }.getType());
                                //存进数据库里
                                new MyDBHelper(c).deleteWifi(null);
                                for (Wifi n : wifis) {
                                    new MyDBHelper(c).addWifi(n);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                            check301(c, s);
                            Log.d("test", "wifi onFailure = " + s);
                        }
                    });
                } catch (Exception e) {
                    Log.d("test", "e = " + e.toString());
                }
            }
        });
    }

    public static void uploadApp(final MainActivity c) {
        //1.判断是不是wifi环境
        if (!NetworkUtil.isWifi(c)) {
            return;
        }
        new Thread() {
            @Override
            public void run() {
                //2.上报APP图标
                final ArrayList<App> installApps = Utils.scanLocalInstallAppList(c, true);
                for (App a : installApps) {
                    APPIconUploader.UploadAPPIcon(c, a.packageName);
                }
                c.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //3.上传APP列表
                        try {
                            AsyncHttpClient client = new AsyncHttpClient();
                            client.addHeader("x-auth-token", c.getSharedPreferences("kiway", 0).getString("x-auth-token", ""));
                            client.setTimeout(10000);
                            String url = clientUrl + "device/appInstallation";
                            Log.d("test", "applist url = " + url);
                            JSONArray array = new JSONArray();
                            int count = installApps.size();
                            String imei = Utils.getIMEI(c);
                            for (int i = 0; i < count; i++) {
                                JSONObject o1 = new JSONObject();
                                App a = installApps.get(i);
                                o1.put("imei", imei);
                                o1.put("appName", a.name);
                                o1.put("packages", a.packageName);
                                o1.put("versionName", a.versionName);
                                o1.put("versionCode", a.versionCode);
                                o1.put("category", a.category);
                                o1.put("icon", APPIconUploader.getAPPIcon(c, a.packageName));
                                array.put(o1);
                            }
                            Log.d("test", "applist array = " + array.toString());
                            StringEntity stringEntity = new StringEntity(array.toString(), "utf-8");
                            client.post(c, url, stringEntity, "application/json", new TextHttpResponseHandler() {
                                @Override
                                public void onSuccess(int code, Header[] headers, String ret) {
                                    Log.d("test", "applist onSuccess = " + ret);

                                    String today = Utils.getToday();
                                    c.getSharedPreferences("kiway", 0).edit().putBoolean(today, true).commit();
                                }

                                @Override
                                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                                    check301(c, s);
                                    Log.d("test", "applist onFailure = " + s);
                                }
                            });
                        } catch (Exception e) {
                            Log.d("test", "e = " + e.toString());
                        }
                    }
                });
            }
        }.start();
    }

    public static void appFunction(final MainActivity c) {
        c.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    AsyncHttpClient client = new AsyncHttpClient();
                    client.addHeader("x-auth-token", c.getSharedPreferences("kiway", 0).getString("x-auth-token", ""));
                    client.setTimeout(10000);
                    RequestParams param = new RequestParams();
                    Log.d("test", "param = " + param.toString());
                    String url = clientUrl + "device/appFunction?imei=" + Utils.getIMEI(c);
                    Log.d("test", "appFunction = " + url);
                    client.get(c, url, param, new TextHttpResponseHandler() {
                        @Override
                        public void onSuccess(int code, Header[] headers, String ret) {
                            Log.d("test", "appFunction onSuccess = " + ret);

                            try {
                                JSONArray data = new JSONObject(ret).getJSONArray("data");
                                int count = data.length();
                                for (int i = 0; i < count; i++) {
                                    JSONObject o = data.getJSONObject(i);
                                    String commandT = o.optString("command");
                                    int flag = o.optInt("flag");
                                    c.getSharedPreferences("kiway", 0).edit().putInt("flag_" + commandT, flag).commit();
                                }
                                KWApp.instance.excuteFlagCommand();
                            } catch (Exception e) {
                                Log.d("test", "e = " + e.toString());
                            }
                        }

                        @Override
                        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                            check301(c, s);
                            Log.d("test", "appFunction onFailure = " + s);
                        }
                    });
                } catch (Exception e) {
                    Log.d("test", "e = " + e.toString());
                }
            }
        });
    }

    public static void logout(final SettingActivity c) {
        new Thread() {
            @Override
            public void run() {
                c.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            AsyncHttpClient client = new AsyncHttpClient();
                            client.addHeader("x-auth-token", c.getSharedPreferences("kiway", 0).getString
                                    ("x-auth-token", ""));
                            client.setTimeout(10000);
                            String url = clientUrl + "device/logout";
                            Log.d("test", "url = " + url);
                            RequestParams param = new RequestParams();
                            param.put("operation", "invalidate");
                            param.put("froms", "studentDevice");
                            Log.d("test", "param = " + param.toString());
                            client.post(c, url, param, new TextHttpResponseHandler() {

                                @Override
                                public void onSuccess(int arg0, Header[] arg1, String ret) {
                                    Log.d("test", "logout success");
                                }

                                @Override
                                public void onFailure(int arg0, Header[] arg1, String s, Throwable arg3) {
                                    Log.d("test", "logout failure");
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.d("test", "exception = " + e.toString());
                        }
                    }
                });
            }
        }.start();
    }

    public static void installationPush(final Context c, final String token, final String imei) {
        try {
            String xtoken = c.getSharedPreferences("kiway", 0).getString("x-auth-token", "");
            if (TextUtils.isEmpty(xtoken)) {
                return;
            }
            AsyncHttpClient client = new AsyncHttpClient();
            Log.d("test", "xtoken = " + xtoken);
            client.addHeader("x-auth-token", xtoken);
            client.setTimeout(10000);
            Log.d("test", "huaweitoken = " + token);
            JSONObject param = new JSONObject();
            param.put("appId", Constant.APPID);
            param.put("type", "huawei");
            param.put("deviceId", imei);
            param.put("userId", token);
            param.put("module", "student");
            Log.d("test", "installationPush param = " + param.toString());
            StringEntity stringEntity = new StringEntity(param.toString(), "utf-8");
            String url = clientUrl + "push/installation";
            Log.d("test", "installationPush = " + url);
            client.post(c, url, stringEntity, "application/json", new TextHttpResponseHandler() {
                @Override
                public void onSuccess(int code, Header[] headers, String ret) {
                    Log.d("test", "installationPush onSuccess = " + ret);
                    if (MainActivity.instance != null) {
                        MainActivity.instance.initZbus();
                    }
                }

                @Override
                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                    Log.d("test", "installationPush onFailure = " + s);
                }
            });
        } catch (Exception e) {
            Log.d("test", "e = " + e.toString());
        }
    }

    public static void uninstallPush(final SettingActivity c) {
        new Thread() {
            @Override
            public void run() {
                c.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String xtoken = c.getSharedPreferences("kiway", 0).getString("x-auth-token", "");
                            if (TextUtils.isEmpty(xtoken)) {
                                return;
                            }
                            String token = c.getSharedPreferences("huawei", 0).getString("token", "");
                            Log.d("test", "huaweitoken = " + token);
                            AsyncHttpClient client = new AsyncHttpClient();
                            Log.d("test", "xtoken = " + xtoken);
                            client.addHeader("x-auth-token", xtoken);
                            client.setTimeout(10000);
                            RequestParams param = new RequestParams();
                            param.put("type", "huawei");
                            param.put("imei", Utils.getIMEI(c));
                            param.put("token", token);
                            Log.d("test", "param = " + param.toString());
                            String url = clientUrl + "device/uninstall";
                            Log.d("test", "uninstallPush = " + url);
                            client.post(c, url, param, new TextHttpResponseHandler() {
                                @Override
                                public void onSuccess(int code, Header[] headers, String ret) {
                                    Log.d("test", "uninstallPush onSuccess = " + ret);
                                }

                                @Override
                                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                                    Log.d("test", "uninstallPush onFailure = " + s);
                                }
                            });
                        } catch (Exception e) {
                            Log.d("test", "e = " + e.toString());
                        }
                    }
                });
            }
        }.start();
    }

    public static void doBind(final Activity c, final int flag, final String token) {
        c.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    AsyncHttpClient client = new AsyncHttpClient();
                    String xauthtoken = c.getSharedPreferences("kiway", 0).getString("x-auth-token", "");
                    Log.d("test", "xauthtoken = " + xauthtoken);
                    client.addHeader("x-auth-token", xauthtoken);
                    client.setTimeout(10000);
                    String url = clientUrl + "device/student/response";
                    Log.d("test", "doBind = " + url);
                    RequestParams param = new RequestParams();
                    param.put("flag", flag);
                    param.put("token", token);
                    Log.d("test", "doBind param = " + param.toString());
                    client.post(c, url, param, new TextHttpResponseHandler() {
                        @Override
                        public void onSuccess(int code, Header[] headers, String ret) {
                            Log.d("test", "doBind onSuccess = " + ret);
                        }

                        @Override
                        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                            Log.d("test", "doBind onFailure = " + s);
                            boolean happen301 = check301(c, s);
                            if (happen301) {
                                Log.d("test", "超时了，再次请求doBind");
                                new Thread() {
                                    @Override
                                    public void run() {
                                        try {
                                            sleep(3000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                        doBind(c, flag, token);
                                    }
                                }.start();
                            }
                        }
                    });
                } catch (Exception e) {
                    Log.d("test", "e = " + e.toString());
                }
            }
        });
    }

    public static void getCalls(final MainActivity c) {
        c.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    AsyncHttpClient client = new AsyncHttpClient();
                    client.addHeader("x-auth-token", c.getSharedPreferences("kiway", 0).getString("x-auth-token", ""));
                    client.setTimeout(10000);
                    RequestParams param = new RequestParams();
                    Log.d("test", "param = " + param.toString());
                    String url = clientUrl + "device/calls?imei=" + Utils.getIMEI(c);
                    Log.d("test", "calls = " + url);
                    client.get(c, url, param, new TextHttpResponseHandler() {
                        @Override
                        public void onSuccess(int code, Header[] headers, String ret) {
                            Log.d("test", "calls onSuccess = " + ret);

                            try {
                                JSONArray data = new JSONObject(ret).getJSONArray("data");
                                ArrayList<Call> calls = new GsonBuilder().create().fromJson(data.toString(), new
                                        TypeToken<List<Call>>() {
                                        }.getType());
                                //存进数据库里
                                new MyDBHelper(c).deleteCall(null);
                                for (Call n : calls) {
                                    new MyDBHelper(c).addCall(n);
                                }
                                //TODO 这里要调用一下华为的方法
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                            Log.d("test", "calls onFailure = " + s);
                            check301(c, s);
                        }
                    });
                } catch (Exception e) {
                    Log.d("test", "e = " + e.toString());
                }
            }
        });
    }

    public static void childOperation(final Activity c, String type, String message) {
        try {
            AsyncHttpClient client = new AsyncHttpClient();
            client.addHeader("x-auth-token", c.getSharedPreferences("kiway", 0).getString("x-auth-token", ""));
            client.setTimeout(10000);
            RequestParams param = new RequestParams();
            param.put("IMEI", Utils.getIMEI(c));
            param.put("type", type);
            param.put("message", message);
            param.put("froms", "studentDevice");
            Log.d("test", "param = " + param.toString());
            String url = clientUrl + "device/parent/child/operation";
            Log.d("test", "childOperation = " + url);
            client.post(c, url, param, new TextHttpResponseHandler() {
                @Override
                public void onSuccess(int code, Header[] headers, String ret) {
                    Log.d("test", "childOperation onSuccess = " + ret);

                }

                @Override
                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                    Log.d("test", "childOperation onFailure = " + s);
                    check301(c, s);
                }
            });
        } catch (Exception e) {
            Log.d("test", "e = " + e.toString());
        }
    }

    public static void oauth(final MainActivity c, final String key, final String packageName) {
        c.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    AsyncHttpClient client = new AsyncHttpClient();
                    client.addHeader("x-auth-token", c.getSharedPreferences("kiway", 0).getString("x-auth-token", ""));
                    client.setTimeout(10000);
                    RequestParams param = new RequestParams();
                    param.put("appId", Constant.APPID);
                    param.put("appKey", key);
                    param.put("secretKey", packageName);
                    Log.d("test", "oauth params = " + param.toString());
                    String url = serverUrl + "mdm/auth";
                    Log.d("test", "oauth = " + url);
                    client.post(c, url, param, new TextHttpResponseHandler() {
                        @Override
                        public void onSuccess(int code, Header[] headers, String ret) {

                            Log.d("test", "oauth onSuccess = " + ret);
                        }

                        @Override
                        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                            Log.d("test", "oauth onFailure = " + s);
                            check301(c, s);
                        }
                    });
                } catch (Exception e) {
                    Log.d("test", "e = " + e.toString());
                }
            }
        });
    }

    public static void exceptions(final MainActivity c) {
        try {
            AsyncHttpClient client = new AsyncHttpClient();
            client.addHeader("x-auth-token", c.getSharedPreferences("kiway", 0).getString("x-auth-token", ""));
            client.setTimeout(10000);
            RequestParams param = new RequestParams();
            param.put("imei", Utils.getIMEI(c));
            param.put("ip", Utils.getIP(c));
            param.put("className", "MainActivity");
            param.put("message", "NullPointException At line 76");
            param.put("operation", "submitData");
            Log.d("test", "param = " + param.toString());
            String url = clientUrl + "device/exceptions";
            Log.d("test", "exceptions = " + url);
            client.post(c, url, param, new TextHttpResponseHandler() {
                @Override
                public void onSuccess(int code, Header[] headers, String ret) {
                    Log.d("test", "exceptions onSuccess = " + ret);

                }

                @Override
                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                    check301(c, s);
                    Log.d("test", "exceptions onFailure = " + s);
                }
            });
        } catch (Exception e) {
            Log.d("test", "e = " + e.toString());
        }
    }

    public static boolean check301(final Activity c, String result) {
        if (c == null) {
            return false;
        }
        if (TextUtils.isEmpty(result)) {
            return false;
        }
        try {
            int statusCode = new JSONObject(result).optInt("statusCode");
            if (statusCode != 301) {
                return false;
            }
            Log.d("test", "301 happen");
            if (is301) {
                return true;
            }
            is301 = true;
            //TODO 重新登录后没有再次请求上一个接口。。。
            final String imei = Utils.getIMEI(c);
            String token = c.getSharedPreferences("huawei", 0).getString("token", "");
            AsyncHttpClient client = new AsyncHttpClient();
            client.setTimeout(10000);
            String url = clientUrl + "device/student/login";
            Log.d("test", "relogin url = " + url);
            RequestParams param = new RequestParams();
            param.put("schoolId", c.getSharedPreferences("kiway", 0).getString("schoolId", ""));
            param.put("classId", c.getSharedPreferences("kiway", 0).getString("classId", ""));
            param.put("studentNumber", c.getSharedPreferences("kiway", 0).getString("studentNumber", ""));
            param.put("name", c.getSharedPreferences("kiway", 0).getString("name", ""));
            param.put("mobileModel", Build.MODEL);
            param.put("mobileBrand", Build.BRAND);
            param.put("IMEI", imei);
            param.put("platform", "Android");
            param.put("token", token);
            param.put("operation", "login");
            param.put("froms", "studentDevice");
            Log.d("test", "relogin param = " + param.toString());
            client.post(c, url, param, new TextHttpResponseHandler() {

                @Override
                public void onSuccess(int arg0, Header[] arg1, String ret) {
                    Log.d("test", "relogin  onSuccess = " + ret);
                    try {
                        JSONObject o = new JSONObject(ret);
                        String token = o.getJSONObject("data").getString("token");
                        c.getSharedPreferences("kiway", 0).edit().putString("x-auth-token", token).commit();
                    } catch (Exception e) {
                    }
                    is301 = false;
                }

                @Override
                public void onFailure(int arg0, Header[] arg1, String ret, Throwable arg3) {
                    Log.d("test", "relogin  failure");
                    is301 = false;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("test", "relogin exception = " + e.toString());
            is301 = false;
        }

        return true;
    }
}
