package com.example.cr7.mobieshop;

import android.content.Context;
import android.content.SharedPreferences;

public class Pref {

    private SharedPreferences _pref;
    private static final String PREF_FILE = "com.inway";
    private SharedPreferences.Editor _editorPref;

    public Pref(Context context)
    {
        _pref = context.getSharedPreferences(PREF_FILE,Context.MODE_PRIVATE);
        _editorPref = _pref.edit();
    }

    public String getFCMId()
    {
        return _pref.getString("fcm_device_id","");
    }

    public void saveFCMId(String fcmToken)
    {
        _editorPref.putString("fcm_device_id",fcmToken);
        _editorPref.commit();
    }
}
