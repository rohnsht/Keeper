package com.rohanshrestha.keeper;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by rohan on 3/10/17.
 */

public class KeeperApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
