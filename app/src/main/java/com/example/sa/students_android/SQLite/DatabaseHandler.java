package com.example.sa.students_android.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.sa.students_android.Models.ContactType;
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
        values.put(KEY_DATEOFBIRTH, serialize(user.getDateOfBirth()));
        values.put(KEY_GROUP_ID, user.getUserGroup().getGroupID().toString());
        values.put(KEY_CONTACTS, serialize(user.getContacts()));
        values.put(KEY_ROLE, serialize(user.getRole()));


        db.insert(TABLE_USERS, null, values);
        db.close();
        return user;
    }

    //
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

    //
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
                TABLE_USERS,                     // The table to query
                projection,                      // The columns to return
                selection,                       // The columns for the WHERE clause
                selectionArgs,                   // The values for the WHERE clause
                null,                            // don't group the rows
                null,                            // don't filter by row groups
                null                             // The sort order
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

    //
    public HashMap<Long, Integer> getGroups() {
        HashMap<Long, Integer> result = new HashMap<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String query =
                "SELECT " + KEY_GROUP_ID + ", COUNT(" + KEY_GROUP_ID + ") FROM " + TABLE_USERS + " GROUP BY " + KEY_GROUP_ID;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor != null) {
            String str = "";
            while (cursor.moveToNext()) {
                result.put(
                        cursor.getLong(cursor.getColumnIndex(KEY_GROUP_ID)),
                        cursor.getInt(cursor.getColumnIndex("COUNT(" + KEY_GROUP_ID + ")")));

                //Logs results
                for(String cn : cursor.getColumnNames())
                    str = str.concat(cn + " = " + cursor.getString(cursor.getColumnIndex(cn)) + " ");
                    Log.d("SQL_QUERY_RESULT ", str);
                    str = "";
            }
        }

        if(cursor != null)
            cursor.close();

        return result;
    }

    public List<User> getGroupMembers(Long groupId) {

        List<User> result = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                KEY_PERSONAL_ID,
                KEY_LOGIN,
                KEY_FIRSTNAME,
                KEY_LASTNAME,
                KEY_MIDDLENAME,
                KEY_DATEOFBIRTH,
                KEY_GROUP_ID,
                KEY_CONTACTS,
                KEY_ROLE,
        };
        String selection = KEY_GROUP_ID + " = ?";
        String[] selectionArgs = {
                groupId.toString()
        };

        Cursor cursor = db.query(
                TABLE_USERS,                     // The table to query
                projection,                      // The columns to return
                selection,                       // The columns for the WHERE clause
                selectionArgs,                   // The values for the WHERE clause
                null,                            // don't group the rows
                null,                            // don't filter by row groups
                null                             // The sort order
        );

        if(cursor.getCount() == 0) {
            cursor.close();
            return null;
        }
        Long personalId;
        String login = "";
        Integer password = 0;
        String firstName = "";
        String middleName = "";
        String lastName = "";
        java.util.Date dateOfBirth = new Date(1999, 12, 31);
        Long groupIdFromTable = 0L;
        Contacts contacts = new Contacts();
        Role role = Role.STUDENT;

        if(cursor.getCount() >= 1)
            while (cursor.moveToNext()) {

                personalId = cursor.getLong(cursor.getColumnIndex(KEY_PERSONAL_ID));
                login = cursor.getString(cursor.getColumnIndex(KEY_LOGIN));
                password = 0;
                firstName = cursor.getString(cursor.getColumnIndex(KEY_FIRSTNAME));
                middleName = cursor.getString(cursor.getColumnIndex(KEY_MIDDLENAME));
                lastName = cursor.getString(cursor.getColumnIndex(KEY_LASTNAME));
                dateOfBirth = (java.util.Date) deserialize(cursor.getBlob(cursor.getColumnIndex(KEY_DATEOFBIRTH)));
                groupIdFromTable = cursor.getLong(cursor.getColumnIndex(KEY_GROUP_ID));
                contacts = (Contacts) deserialize(cursor.getBlob(cursor.getColumnIndex(KEY_CONTACTS)));
                role = (Role) deserialize(cursor.getBlob(cursor.getColumnIndex(KEY_ROLE)));

                Log.i("ItsMeDB", String.valueOf(contacts.isEmpty()));

                result.add(
                        new User(
                                personalId,
                                login,
                                password,
                                firstName,
                                middleName,
                                lastName,
                                dateOfBirth,
                                groupIdFromTable,
                                contacts,
                                role
                        )
                );

            }
        cursor.close();
        return result;
    }

    private static byte[] serialize(Object obj) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os;
        try {
            os = new ObjectOutputStream(out);
            os.writeObject(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    private static Object deserialize(byte[] data) {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is;
        try {
            is = new ObjectInputStream(in);
            return is.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
