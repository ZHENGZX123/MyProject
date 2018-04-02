package cn.kiway.autoreply.util;


import org.json.JSONException;
import org.json.JSONObject;

import de.robv.android.xposed.XSharedPreferences;

import static cn.kiway.autoreply.util.Constant.SHE_FREIEND_ROOM;
import static cn.kiway.autoreply.util.Constant.SHE_MSG_TOFORDARD_ROOMMB;
import static cn.kiway.autoreply.util.Constant.SHE_MSG_TOFOREARD_ROOM;
import static cn.kiway.autoreply.util.Constant.SHE_MSG_TOFORWARDSERVIDE;

public class PreferencesUtils {

    private static XSharedPreferences instance = null;

    private static XSharedPreferences getInstance() {
        if (instance == null) {
            instance = new XSharedPreferences("cn.kiway.autoreply","kiway");
            instance.makeWorldReadable();
        } else {
            instance.reload();
        }
        return instance;
    }

    public static boolean open() {
        return getInstance().getBoolean("open", false);
    }

    public static boolean notSelf() {
        return getInstance().getBoolean("not_self", false);
    }

    public static boolean notWhisper() {
        return getInstance().getBoolean("not_whisper", false);
    }

    public static String notContains() {
        return getInstance().getString("not_contains", "").replace("，", ",");
    }

    public static boolean delay() {
        return getInstance().getBoolean("delay", false);
    }

    public static int delayMin() {
        return getInstance().getInt("delay_min", 0);
    }

    public static int delayMax() {
        return getInstance().getInt("delay_max", 0);
    }

    public static boolean receiveTransfer() {
        return getInstance().getBoolean("receive_transfer", true);
    }

    public static boolean quickOpen() {
        return getInstance().getBoolean("quick_open", true);
    }

    public static boolean showWechatId() {
        return getInstance().getBoolean("show_wechat_id", false);
    }

    public static String blackList() {
        return getInstance().getString("black_list", "").replace("，", ",");
    }

    public static JSONObject getPeopleMsgToService() {
        try {
            return new JSONObject(getInstance().getString(SHE_MSG_TOFORWARDSERVIDE, ""));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }

    public static JSONObject getZFRoom() {
        try {
            return new JSONObject(getInstance().getString(SHE_MSG_TOFOREARD_ROOM, ""));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }

    public static JSONObject getZFRoomPeople() {
        try {
            return new JSONObject(getInstance().getString(SHE_MSG_TOFORDARD_ROOMMB, ""));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }

    public static JSONObject getZFFriendsPeople() {
        try {
            return new JSONObject(getInstance().getString(SHE_FREIEND_ROOM, ""));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }
}


