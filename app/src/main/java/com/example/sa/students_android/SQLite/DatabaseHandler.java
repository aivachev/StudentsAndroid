package com.example.sa.students_android.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sa.students_android.Models.Role;
import com.example.sa.students_android.Models.User;

import java.io.Serializable;

import static com.example.sa.students_android.SQLite.DBContracts.Users.*;

/**
 * Created by sa on 20.06.17.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "main_database.db";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBContracts.Users.SQL_CREATE_ENTRIES);

        // Adding 'admin' user
        ContentValues values = new ContentValues();
        values.put(KEY_PERSONAL_ID, 1);
        values.put(KEY_LOGIN, "admin");
        values.put(KEY_PASSWORD, "admin".hashCode());
        values.put(KEY_FIRSTNAME, "admin");
        values.put(KEY_LASTNAME, "admin");
        values.put(KEY_MIDDLENAME, "admin");
        values.put(KEY_DATEOFBIRTH, "1970-01-01");
        values.put(KEY_GROUPID, 1337);
        values.put(KEY_ROLE, Role.ADMIN.toString());
        db.insert(DBContracts.Users.TABLE_NAME, null, values);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
/*        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);

        // Create tables again
        onCreate(db);*/
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     *
     * @param
     */

    // Working with "users" table

    // Adding entry
    public User addUser(User user) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PERSONAL_ID, user.getId());
        values.put(KEY_LOGIN, user.getLogin());
        values.put(KEY_PASSWORD, user.getPassword());
        values.put(KEY_FIRSTNAME, user.getFirstName());
        values.put(KEY_MIDDLENAME, user.getMiddleName());
        values.put(KEY_LASTNAME, user.getLastName());
        values.put(KEY_DATEOFBIRTH, user.getDateOfBirth().toString());
        values.put(KEY_GROUPID, user.getUserGroup().getGroupID());
        values.put(KEY_ROLE, user.getRole().toString());


        db.insert(TABLE_NAME, null, values);
        db.close();
        return user;
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

    public boolean checkPassword(String login, Integer inputPassword) {

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
                TABLE_NAME,                     // The table to query
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

        // Should never be more than 1, but still...
        if(cursor.getCount() >= 1)
            while (cursor.moveToNext())
                if (cursor.getInt(cursor.getColumnIndex("password")) == inputPassword) {
                    cursor.close();
                    return true;
                }

        cursor.close();
        return false;
    }
}
