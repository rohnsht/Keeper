package com.rohanshrestha.keeper.data;

import com.google.firebase.database.ServerValue;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rohan on 3/30/16.
 */
public class Credential implements Serializable {

    public String title;
    public String username;
    public String password;
    public long createdTime;

    public Credential() {
    }

    public Credential(String title, String username, String password) {
        this.title = title;
        this.username = username;
        this.password = password;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("title", title);
        result.put("username", username);
        result.put("password", password);
        result.put("createdTime", ServerValue.TIMESTAMP);

        return result;
    }

}
