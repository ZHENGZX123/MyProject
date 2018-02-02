package cn.kiway.brower.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.webkit.CookieManager;

import cn.kiway.brower.Activity.ClearActivity;
import cn.kiway.brower.Activity.TokenActivity;
import cn.kiway.brower.Activity.WhitelistActivity;
import cn.kiway.brower.Ninja.R;
import cn.kiway.brower.Task.ExportBookmarksTask;
import cn.kiway.brower.Task.ExportWhitelistTask;
import cn.kiway.brower.Unit.IntentUnit;
import cn.kiway.brower.Unit.Utils;
import cn.kiway.brower.View.NinjaToast;

public class SettingFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String LICENSE_TITLE = "LICENSE_TITLE";
    private static final String LICENSE_CONTENT = "LICENSE_CONTENT";
    private static final String LICENSE_AUTHOR = "LICENSE_AUTHOR";
    private static final String LICENSE_URL = "LICENSE_URL";

    private ListPreference searchEngine;
    private ListPreference notiPriority;
    private ListPreference volumeControl;
    private ListPreference userAgent;
    private ListPreference rendering;
    private Preference version;

    private String[] seEntries;
    private String[] npEntries;
    private String[] vcEntries;
    private String[] ucEntries;
    private String[] rdEntries;

    private boolean spChange = false;

    public boolean isSPChange() {
        return spChange;
    }

    private boolean dbChange = false;

    public boolean isDBChange() {
        return dbChange;
    }

    public void setDBChange(boolean dbChange) {
        this.dbChange = dbChange;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference_setting);
    }

    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences sp = getPreferenceScreen().getSharedPreferences();
        sp.registerOnSharedPreferenceChangeListener(this);
        String summary;

        seEntries = getResources().getStringArray(R.array.setting_entries_search_engine);
        searchEngine = (ListPreference) findPreference(getString(R.string.sp_search_engine));
        int num = Integer.valueOf(sp.getString(getString(R.string.sp_search_engine), "0"));
        if (0 <= num && num <= 4) {
            summary = seEntries[num];
            searchEngine.setSummary(summary);
        } else {
            summary = getString(R.string.setting_summary_search_engine_custom);
            searchEngine.setSummary(summary);
        }

        npEntries = getResources().getStringArray(R.array.setting_entries_notification_priority);
        summary = npEntries[Integer.valueOf(sp.getString(getString(R.string.sp_notification_priority), "0"))];
        notiPriority = (ListPreference) findPreference(getString(R.string.sp_notification_priority));
        notiPriority.setSummary(summary);


        vcEntries = getResources().getStringArray(R.array.setting_entries_volume_control);
        summary = vcEntries[Integer.valueOf(sp.getString(getString(R.string.sp_volume), "1"))];
        volumeControl = (ListPreference) findPreference(getString(R.string.sp_volume));
        volumeControl.setSummary(summary);

        ucEntries = getResources().getStringArray(R.array.setting_entries_user_agent);
        userAgent = (ListPreference) findPreference(getString(R.string.sp_user_agent));
        num = Integer.valueOf(sp.getString(getString(R.string.sp_user_agent), "0"));
        if (0 <= num && num <= 1) {
            summary = ucEntries[num];
            userAgent.setSummary(summary);
        } else {
            summary = getString(R.string.setting_summary_user_agent_custom);
            userAgent.setSummary(summary);
        }

        rdEntries = getResources().getStringArray(R.array.setting_entries_rendering);
        summary = rdEntries[Integer.valueOf(sp.getString(getString(R.string.sp_rendering), "0"))];
        rendering = (ListPreference) findPreference(getString(R.string.sp_rendering));
        rendering.setSummary(summary);

        version=findPreference(getString(R.string.sp_version));
        version.setSummary(Utils.getCurrentVersion(getActivity()));
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        switch (preference.getTitleRes()) {
            case R.string.setting_title_whitelist:
                Intent toWhitelist = new Intent(getActivity(), WhitelistActivity.class);
                getActivity().startActivity(toWhitelist);
                break;
            case R.string.setting_title_export_whilelist:
                new ExportWhitelistTask(getActivity()).execute();
                break;
            case R.string.setting_title_import_whilelist:
                Intent importWhitelist = new Intent(Intent.ACTION_GET_CONTENT);
                importWhitelist.setType(IntentUnit.INTENT_TYPE_TEXT_PLAIN);
                importWhitelist.addCategory(Intent.CATEGORY_OPENABLE);
                getActivity().startActivityForResult(importWhitelist, IntentUnit.REQUEST_WHITELIST);
                break;
            case R.string.setting_title_token:
                Intent toToken = new Intent(getActivity(), TokenActivity.class);
                getActivity().startActivity(toToken);
                break;
            case R.string.setting_title_export_bookmarks:
                new ExportBookmarksTask(getActivity()).execute();
                break;
            case R.string.setting_title_import_bookmarks:
                Intent importBookmarks = new Intent(Intent.ACTION_GET_CONTENT);
                importBookmarks.setType(IntentUnit.INTENT_TYPE_TEXT_PLAIN);
                importBookmarks.addCategory(Intent.CATEGORY_OPENABLE);
                getActivity().startActivityForResult(importBookmarks, IntentUnit.REQUEST_BOOKMARKS);
                break;
            case R.string.setting_title_clear_control:
                Intent clearControl = new Intent(getActivity(), ClearActivity.class);
                getActivity().startActivityForResult(clearControl, IntentUnit.REQUEST_CLEAR);
                break;
            case R.string.setting_title_version:
                NinjaToast.show(getActivity(), Utils.getCurrentVersion(getActivity()));
                break;
        }
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sp, String key) {
        spChange = true;
        if (key.equals(getString(R.string.sp_search_engine))) {
            int num = Integer.valueOf(sp.getString(key, "0"));
            if (0 <= num && num <= 4) {
                searchEngine.setSummary(seEntries[num]);
            } else {
                searchEngine.setValue("5");
                searchEngine.setSummary(R.string.setting_summary_search_engine_custom);
            }
        } else if (key.equals(getString(R.string.sp_notification_priority))) {
            String summary = npEntries[Integer.valueOf(sp.getString(key, "0"))];
            notiPriority.setSummary(summary);
        } else if (key.equals(getString(R.string.sp_volume))) {
            String summary = vcEntries[Integer.valueOf(sp.getString(key, "1"))];
            volumeControl.setSummary(summary);
        } else if (key.equals(getString(R.string.sp_user_agent))) {
            int num = Integer.valueOf(sp.getString(key, "0"));
            if (0 <= num && num <= 1) {
                userAgent.setSummary(ucEntries[num]);
            } else {
                userAgent.setValue("2");
                userAgent.setSummary(R.string.setting_summary_user_agent_custom);
            }
        } else if (key.equals(getString(R.string.sp_rendering))) {
            String summary = rdEntries[Integer.valueOf(sp.getString(key, "0"))];
            rendering.setSummary(summary);
        } else if (key.equals(getString(R.string.sp_cookies))) {
            CookieManager manager = CookieManager.getInstance();
            manager.setAcceptCookie(sp.getBoolean(getString(R.string.sp_cookies), true));
        }
    }

}