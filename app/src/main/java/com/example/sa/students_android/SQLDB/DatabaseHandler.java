package com.example.sa.students_android.SQLDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.Serializable;

/**
 * Created by sa on 20.06.17.
 */

public class DatabaseHandler extends SQLiteOpenHelper implements Serializable {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "users_info.db";

    // Contacts table name
    private static final String TABLE_USERS = "users";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_LOGIN = "login";
    private static final String KEY_PASSWORD = "password";

    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_DATE = "date";
    private static final String KEY_CONTENT = "content";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_SEEN = "seen";
    private static final String KEY_FAVORITE = "favorite";

    private static final String TAG = "DatabaseHandler";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     *
     * @param
     */

    // Adding new user
    public void addItem(String name, String login, Integer password) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_LOGIN, login);
        values.put(KEY_PASSWORD, password);

        db.insert(name, null, values);
        db.close();
    }

    public void createTable(String name) {

        SQLiteDatabase db = this.getWritableDatabase();

        String CREATE_NEW_TABLE = "CREATE TABLE IF NOT EXISTS " + name + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_LOGIN + " TEXT,"
                + KEY_PASSWORD + " INTEGER" + ")";
        db.execSQL(CREATE_NEW_TABLE);
    }

    public boolean containsUser(String login) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT count(*) FROM users WHERE login=?", new String[] {login + ""});
        return cursor.getCount() > 0;
    }

    public Integer getValue(String login) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT count(*) FROM users WHERE login=?", new String[] {login + ""});
        cursor.moveToNext();
        return cursor.getCount() > 0 ? cursor.getInt(cursor.getColumnIndex(KEY_PASSWORD)) : 0;
    }
}
