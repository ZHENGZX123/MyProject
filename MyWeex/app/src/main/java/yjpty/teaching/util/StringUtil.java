package yjpty.teaching.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import yjpty.teaching.acitivity.BaseActivity;
import yjpty.teaching.http.IUrContant;

/**
 * Created by Administrator on 2017/3/17.
 */

public class StringUtil {
    public static String getResourse(BaseActivity activity, String gradId) {
        String string = "";
        if (activity.mCache.getAsJSONObject(IUrContant.GET_CUSE_BASE) == null)
            return string;
        if( activity.mCache.getAsJSONObject(IUrContant.GET_CUSE_BASE).optJSONObject("data")==null)
            return string;
        String s = activity.mCache.getAsJSONObject(IUrContant.GET_CUSE_BASE).optJSONObject("data").optString
                ("gradeList");
        JSONArray data = null;
        try {
            data = new JSONArray(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (data != null) {
            for (int i = 0; i < data.length(); i++) {
                JSONObject item = data.optJSONObject(i);
                if (gradId.equals(item.optString("id")))
                    string = item.optString("name");
            }
        }
        return string;
    }
}
