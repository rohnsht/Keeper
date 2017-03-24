package com.rohanshrestha.keeper.utils;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by rohan on 3/10/17.
 */

public class FirebaseUtils {
    public static String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
}
