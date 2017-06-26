package com.example.sa.students_android.SQLite;

import android.provider.BaseColumns;

import com.example.sa.students_android.Models.User;

/**
 * Created by sa on 22.06.17.
 */

final public class DBContracts {

    // private empty constructor
    private DBContracts() {}

    // "users" table, containing all users info
    public static class Users implements BaseColumns {

        public static final String TABLE_USERS = "users";
        public static final String KEY_PERSONAL_ID = "personal_id";
        public static final String KEY_LOGIN = "login";
        public static final String KEY_PASSWORD = "password";
        public static final String KEY_FIRSTNAME = "first_name";
        public static final String KEY_MIDDLENAME = "middle_name";
        public static final String KEY_LASTNAME = "last_name";
        public static final String KEY_DATEOFBIRTH = "date_of_birth";
        public static final String KEY_GROUP_ID = "group_id";
        public static final String KEY_CONTACTS = "contacts";
        public static final String KEY_ROLE = "role";

        static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE IF NOT EXISTS " + DBContracts.Users.TABLE_USERS + "("
                        + Users._ID + " INTEGER PRIMARY KEY,"
                        + KEY_PERSONAL_ID + " INTEGER,"
                        + KEY_LOGIN + " TEXT,"
                        + KEY_PASSWORD + " INTEGER,"
                        + KEY_FIRSTNAME + " TEXT,"
                        + KEY_MIDDLENAME + " TEXT,"
                        + KEY_LASTNAME + " TEXT,"
                        + KEY_DATEOFBIRTH + " BLOB,"
                        + KEY_GROUP_ID + " INTEGER,"
                        + KEY_CONTACTS + " BLOB,"
                        + KEY_ROLE + " TEXT"
                        + ")";

        static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + TABLE_USERS;
    }

    // "lessons" table, containing all lessons info
    public static class Lessons implements BaseColumns {

        public static final String TABLE_LESSONS = "lessons";
        public static final String KEY_LESSON_ID = "lesson_id";
        public static final String KEY_DAYSNTIMES = "days_n_times";
        public static final String KEY_AUDITORIUM = "auditorium";
        public static final String KEY_DESCRIPTION = "description";
        public static final String KEY_SUBJECT = "subject";
        public static final String KEY_LECTOR = "lector";
        public static final String KEY_GROUPS = "groups";

        static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE IF NOT EXISTS " + Lessons.TABLE_LESSONS + "("
                        + Lessons._ID + " INTEGER PRIMARY KEY,"
                        + KEY_LESSON_ID + " INTEGER,"
                        + KEY_DAYSNTIMES + " BLOB,"
                        + KEY_AUDITORIUM + " INTEGER,"
                        + KEY_DESCRIPTION + " TEXT,"
                        + KEY_SUBJECT + " TEXT,"
                        + KEY_LECTOR + " TEXT,"
                        + KEY_GROUPS + " BLOB"
                        + ")";

        static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + TABLE_LESSONS;
    }
}
