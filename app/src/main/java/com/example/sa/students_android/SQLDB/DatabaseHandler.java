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

    public boolean containsLogin(String table, String login) {

        SQLiteDatabase db = this.getReadableDatabase();

        String selection = KEY_LOGIN + " = ?";
        String[] selectionArgs = { login };

        Cursor cursor = db.query(
                table,                     // The table to query
                null,                      // The columns to return
                selection,                 // The columns for the WHERE clause
                selectionArgs,             // The values for the WHERE clause
                null,                      // don't group the rows
                null,                      // don't filter by row groups
                null                       // The sort order
        );

        Integer count = cursor.getCount();

        cursor.close();
        db.close();

        return count > 0;
    }

    public boolean checkPassword(String table, String login, Integer inputPassword) {

        SQLiteDatabase db = this.getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                KEY_PASSWORD
        };

// Filter results WHERE "title" = 'My Title'
        String selection = KEY_LOGIN + " = ?";
        String[] selectionArgs = { login };

        Cursor cursor = db.query(
                table,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        if(cursor.getCount() == 0) {
            cursor.close();
            return false;
        }
        if(cursor.getCount() >= 1) {
            // Should never be more than 1, but still...
            while (cursor.moveToNext()) {
                if (cursor.getInt(cursor.getColumnIndex("password")) == inputPassword) {
                    cursor.close();
                    return true;
                }
            }
        }

        cursor.close();
        return false;
    }
}
