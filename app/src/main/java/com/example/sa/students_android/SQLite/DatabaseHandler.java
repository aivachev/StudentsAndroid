package com.example.sa.students_android.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.sa.students_android.Enums.ContactType;
import com.example.sa.students_android.Models.Contacts;
import com.example.sa.students_android.Models.Role;
import com.example.sa.students_android.Models.User;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.example.sa.students_android.MyUtilMethods.SerializationStuff.serialize;
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

        Contacts con = new Contacts<String, ContactType>();
        con.put("777-77-77", ContactType.PHONE);

        // Adding 'admin' user
        ContentValues values = new ContentValues();
        values.put(KEY_PERSONAL_ID, 1);
        values.put(KEY_LOGIN, "admin");
        values.put(KEY_PASSWORD, "admin".hashCode());
        values.put(KEY_FIRSTNAME, "admin");
        values.put(KEY_LASTNAME, "admin");
        values.put(KEY_MIDDLENAME, "admin");
        values.put(KEY_DATEOFBIRTH, serialize(new Date(1970,01,01)));
        values.put(KEY_GROUP_ID, 1337);
        values.put(KEY_CONTACTS, serialize(con));
        values.put(KEY_ROLE, serialize(Role.ADMIN));
        db.insert(DBContracts.Users.TABLE_USERS, null, values);

        db.execSQL(DBContracts.Lessons.SQL_CREATE_ENTRIES);
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
}
