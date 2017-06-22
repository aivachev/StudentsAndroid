package com.example.sa.students_android.SQLite;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

/**
 * Created by sa on 22.06.17.
 */

public final class DBContracts {

    // private empty constructor
    private DBContracts() {}

    // "users" table, containing all users info
    public static class Users implements BaseColumns {

        static final String TABLE_NAME = "users";
        static final String KEY_PERSONAL_ID = "personal_id";
        static final String KEY_LOGIN = "login";
        static final String KEY_PASSWORD = "password";
        static final String KEY_FIRSTNAME = "first_name";
        static final String KEY_MIDDLENAME = "middle_name";
        static final String KEY_LASTNAME = "last_name";
        static final String KEY_DATEOFBIRTH = "date_of_birth";
        static final String KEY_GROUPID = "group_id";
        static final String KEY_ROLE = "role";

        static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE IF NOT EXISTS " + DBContracts.Users.TABLE_NAME + "("
                        + Users._ID + " INTEGER PRIMARY KEY,"
                        + KEY_PERSONAL_ID + " INTEGER,"
                        + KEY_LOGIN + " TEXT,"
                        + KEY_PASSWORD + " INTEGER,"
                        + KEY_FIRSTNAME + " TEXT,"
                        + KEY_MIDDLENAME + " TEXT,"
                        + KEY_LASTNAME + " TEXT,"
                        + KEY_DATEOFBIRTH + " TEXT,"
                        + KEY_GROUPID + " INTEGER,"
                        + KEY_ROLE + " TEXT"
                        + ")";

        static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    //
    public static class Groups implements BaseColumns {

        public Groups(Long groupId) {

        }

        static final String TABLE_NAME = "groups";
        static final String KEY_PERSONAL_ID = "personal_id";
        static final String KEY_LOGIN = "login";
        static final String KEY_PASSWORD = "password";
        static final String KEY_FIRSTNAME = "first_name";
        static final String KEY_MIDDLENAME = "middle_name";
        static final String KEY_LASTNAME = "last_name";
        static final String KEY_DATEOFBIRTH = "date_of_birth";
        static final String KEY_GROUPID = "group_id";
        static final String KEY_ROLE = "role";

        static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE IF NOT EXISTS " + DBContracts.Users.TABLE_NAME + "("
                        + Users._ID + " INTEGER PRIMARY KEY,"
                        + KEY_PERSONAL_ID + " INTEGER,"
                        + KEY_LOGIN + " TEXT,"
                        + KEY_PASSWORD + " INTEGER,"
                        + KEY_FIRSTNAME + " TEXT,"
                        + KEY_MIDDLENAME + " TEXT,"
                        + KEY_LASTNAME + " TEXT,"
                        + KEY_DATEOFBIRTH + " BLOB,"
                        + KEY_GROUPID + " INTEGER,"
                        + KEY_ROLE + " INTEGER"
                        + ")";

        static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
