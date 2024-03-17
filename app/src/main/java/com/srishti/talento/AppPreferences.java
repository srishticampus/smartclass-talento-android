package com.srishti.talento;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferences {
    private final SharedPreferences appSharedPrefs;
    private final SharedPreferences.Editor prefsEditor;

    public AppPreferences(Context context, String Preferncename) {
        this.appSharedPrefs = context.getSharedPreferences(Preferncename,
                Activity.MODE_PRIVATE);
        this.prefsEditor = appSharedPrefs.edit();
    }


    /**
     * *
     * <p/>
     * GeTData() get the value of the preference
     */
    public String getData(String key) {
        return appSharedPrefs.getString(key, "");
    }

    public boolean getDataBoolean(String key) {
        return appSharedPrefs.getBoolean(key, false);
    }

    public int getIntData(String key) {
        return appSharedPrefs.getInt(key, 0);
    }


    /**
     * *
     * <p/>
     * SaveData() save the value to the preference
     */


    public void SaveData(String Tag, String text) {
        prefsEditor.putString(Tag, text);
        prefsEditor.commit();
    }

    public void SaveIntData(String Tag, Integer text) {
        // prefsEditor.putString(Tag, text);
        prefsEditor.putInt(Tag, text);
        prefsEditor.commit();
    }

    public void SaveDataBoolean(String Tag, Boolean text) {
        prefsEditor.putBoolean(Tag, text);
        prefsEditor.commit();
    }




    /**
     * *
     * <p/>
     * Utils
     */
    public Boolean contains(String Tag) {
        boolean aa = appSharedPrefs.contains(Tag);
        prefsEditor.commit();
        return aa;
    }

    public void clearData() {
        prefsEditor.clear();
        prefsEditor.commit();
    }

    public void removeKey(String Tag) {
        prefsEditor.remove(Tag);
        prefsEditor.commit();
    }
    private static AppPreferences instance;
    public static AppPreferences getInstance(Context context, String Preferncename) {
        if (instance == null)
            instance = new AppPreferences(context, Preferncename);
        return instance;
    }
}