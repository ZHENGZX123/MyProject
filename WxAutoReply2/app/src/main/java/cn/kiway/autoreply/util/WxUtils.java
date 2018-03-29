package cn.kiway.autoreply.util;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Administrator on 2018/3/26.
 */

public class WxUtils {

    public static String wxRoomId1 = "5158239880@chatroom";

    public final static int TextMessage = 1;
    public final static int ImageMessage = 2;
    public final static int AudioMessage = 3;
    public final static int VideoMessage = 4;

    public static String getWxId(String nickname) {
        String wxid = "";
        if (wxarray == null)
            wxid = "";
        for (int i = 0; i < wxarray.length(); i++) {
            JSONObject item = wxarray.optJSONObject(i);
            String name = item.optString("nickname");
            String rename = item.optString("remark");
            if (rename.equals(nickname) || name.equals(nickname)) {
                wxid = item.optString("wxid");
                break;
            }
        }
        return wxid;
    }

    public static String getWxNickName(String wxid) {
        String nickName = "";
        if (wxarray == null)
            nickName = "";
        for (int i = 0; i < wxarray.length(); i++) {
            JSONObject item = wxarray.optJSONObject(i);
            String wxid1 = item.optString("wxid");
            if (wxid1.equals(wxid)) {
                nickName = item.optString("nickname");
                break;
            }
        }
        return nickName;
    }

    static JSONArray wxarray = new JSONArray();

    public static void setWxPeopleList(JSONArray array) {
        wxarray = array;
    }
}
