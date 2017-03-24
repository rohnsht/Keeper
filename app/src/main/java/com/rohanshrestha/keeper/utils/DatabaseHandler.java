package com.rohanshrestha.keeper.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.rohanshrestha.keeper.data.Credential;

import java.util.ArrayList;

/**
 * Created by rohan on 3/30/16.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "keeper";

    // table name
    private static final String TABLE_NAME = "credentials";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_CREATED_TIME = "created_time";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CREDENTIALS_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + KEY_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_TITLE + " VARCHAR," + KEY_USERNAME + " VARCHAR,"
                + KEY_PASSWORD + " VARCHAR, " + KEY_CREATED_TIME + " VARCHAR " + ")";

        db.execSQL(CREATE_CREDENTIALS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // Create tables again
        onCreate(db);
    }


    /*public long addCredentials(Credential credential) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, credential.getTitle());
        values.put(KEY_USERNAME, credential.getUsername());
        values.put(KEY_PASSWORD, credential.getPassword());
        values.put(KEY_CREATED_TIME, credential.getCreatedTime());

        long count = db.insert(TABLE_NAME, null, values);
        db.close();
        return count;
    }

    public ArrayList<Credential> getCredentials() {
        ArrayList<Credential> credentialses = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Credential credential = new Credential();
                credential.setTitle(cursor.getString(cursor.getColumnIndex(KEY_TITLE)));
                credential.setUsername(cursor.getString(cursor.getColumnIndex(KEY_USERNAME)));
                credential.setPassword(cursor.getString(cursor.getColumnIndex(KEY_PASSWORD)));
                credential.setCreatedTime(cursor.getLong(cursor.getColumnIndex(KEY_CREATED_TIME)));
                credentialses.add(credential);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return credentialses;
    }

    public ArrayList<Credential> searchCredentials(String query) {
        ArrayList<Credential> credentialses = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String searchQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + KEY_TITLE + " LIKE ? OR " + KEY_USERNAME + " LIKE ?";
        Cursor cursor = db.rawQuery(searchQuery, new String[]{"%" + query + "%", "%" + query + "%"});
        if (cursor.moveToFirst()) {
            do {
                Credential credential = new Credential();
                credential.setTitle(cursor.getString(cursor.getColumnIndex(KEY_TITLE)));
                credential.setUsername(cursor.getString(cursor.getColumnIndex(KEY_USERNAME)));
                credential.setPassword(cursor.getString(cursor.getColumnIndex(KEY_PASSWORD)));
                credential.setCreatedTime(cursor.getLong(cursor.getColumnIndex(KEY_CREATED_TIME)));
                credentialses.add(credential);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return credentialses;
    }

    public int deleteCredentials(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, KEY_ID + "= ?", new String[]{String.valueOf(id)});
    }*/
}
