package com.rohanshrestha.keeper.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by rohan on 3/31/16.
 */
public class Prefs {

    private static final String APP_SHARED_PREFS = Prefs.class.getSimpleName();
    private static final String KEY_PASSCODE = "PASSCODE";
    private static final String KEY_PASSCODE_SET = "PASSCODE_SET";
    private SharedPreferences appSharedPrefs;
    private SharedPreferences.Editor prefsEditor;

    public Prefs(Context context) {
        this.appSharedPrefs = context.getSharedPreferences(APP_SHARED_PREFS, Context.MODE_PRIVATE);
        this.prefsEditor = appSharedPrefs.edit();
    }

    public void createPasscode(String passcode) {
        prefsEditor.putString(KEY_PASSCODE, passcode);
        prefsEditor.commit();
        setPasscodeStatus(true);
    }

    public String getPasscode() {
        return appSharedPrefs.getString(KEY_PASSCODE, "");
    }

    public void setPasscodeStatus(Boolean pinSet) {
        prefsEditor.putBoolean(KEY_PASSCODE_SET, pinSet);
        prefsEditor.commit();
    }

    public boolean isPasscodeSet() {
        return appSharedPrefs.getBoolean(KEY_PASSCODE_SET, false);
    }
}
