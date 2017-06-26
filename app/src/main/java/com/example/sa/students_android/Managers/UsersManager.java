package com.example.sa.students_android.Managers;

import android.app.Activity;
import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.sa.students_android.Models.Contacts;
import com.example.sa.students_android.Models.DummyData;
import com.example.sa.students_android.Models.Role;
import com.example.sa.students_android.Models.User;
import com.example.sa.students_android.MyUtilMethods.SerializationStuff;
import com.example.sa.students_android.MyUtilMethods.StringStuff;
import com.example.sa.students_android.SQLite.DatabaseHandler;

import org.w3c.dom.Document;

import java.util.*;

import static com.example.sa.students_android.MyUtilMethods.SerializationStuff.deserialize;
import static com.example.sa.students_android.MyUtilMethods.SerializationStuff.serialize;
import static com.example.sa.students_android.SQLite.DBContracts.Users.*;

public class UsersManager {

    private DatabaseHandler databaseHandler;

    private Random random = new Random();

    public UsersManager(Context context) {
        this.databaseHandler = new DatabaseHandler(context);
    }


    public User addUser(User user) {

        SQLiteDatabase db = databaseHandler.getWritableDatabase();

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

    // Checks if login is already taken
    public boolean containsLogin(String table, String login) {

        SQLiteDatabase db = databaseHandler.getReadableDatabase();

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

    // checks if password matches the one, stored in DB.
    public boolean checkPassword(String login, Integer inputPassword) {

        SQLiteDatabase db = databaseHandler.getReadableDatabase();

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

    // Returns all distinct groups and the number of people inside each
    public HashMap<Long, Integer> getGroups() {
        HashMap<Long, Integer> result = new HashMap<>();

        SQLiteDatabase db = databaseHandler.getReadableDatabase();
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

    // Returns a list of users that are members of queried group
    public List<User> getGroupMembers(Long groupId) {

        List<User> result = new ArrayList<>();

        SQLiteDatabase db = databaseHandler.getReadableDatabase();

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

        String[] selectionArgs;
        if(groupId == -1L) {
            projection = null;
            selectionArgs = null;
            selection = null;
        } else
            selectionArgs = new String[] {
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

        if(cursor.getCount() >= 1)
            while (cursor.moveToNext()) {

                Long personalId = cursor.getLong(cursor.getColumnIndex(KEY_PERSONAL_ID));
                String login = cursor.getString(cursor.getColumnIndex(KEY_LOGIN));
                Integer password = 0;
                String firstName = cursor.getString(cursor.getColumnIndex(KEY_FIRSTNAME));
                String middleName = cursor.getString(cursor.getColumnIndex(KEY_MIDDLENAME));
                String lastName = cursor.getString(cursor.getColumnIndex(KEY_LASTNAME));
                Date dateOfBirth = (java.util.Date) deserialize(cursor.getBlob(cursor.getColumnIndex(KEY_DATEOFBIRTH)));
                Long groupIdFromTable = cursor.getLong(cursor.getColumnIndex(KEY_GROUP_ID));
                Contacts contacts = (Contacts) deserialize(cursor.getBlob(cursor.getColumnIndex(KEY_CONTACTS)));
                Role role = (Role) deserialize(cursor.getBlob(cursor.getColumnIndex(KEY_ROLE)));

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

    // Returns a list of users that are members of queried group
    public Role getUserRole(String login) {

        SQLiteDatabase db = databaseHandler.getReadableDatabase();

        String[] projection = {
                KEY_ROLE
        };
        String selection = KEY_LOGIN + " = ?";
        String[] selectionArgs = {
                login
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

        Role role = Role.STUDENT;

        if(cursor.getCount() == 1)
            while (cursor.moveToNext()) {

                role = (Role) deserialize(cursor.getBlob(cursor.getColumnIndex(KEY_ROLE)));

            }
        cursor.close();
        return role;
    }


    private User createUser(String firstName, String middleName, String lastName, Date dateOfBirth, Long groupID, Role role) {

        User user = null;

        String login =
                  StringStuff.transliterate(lastName)
                + StringStuff.transliterate(firstName.substring(0, 1))
                + StringStuff.transliterate(middleName.substring(0, 1));

        String password = "123456";

        if (role == Role.TEACHER || role == Role.ADMIN)
            user = new User(login, password.hashCode(), firstName, middleName, lastName, dateOfBirth, 0L, role);
        if (role == Role.STUDENT)
            user = new User(login, password.hashCode(), firstName, middleName, lastName, dateOfBirth, groupID, role);

        if (user != null)
            addUser(user);
        return user;
    }

    public User addDummyUser(Integer year, Role role) {

        int gender = random.nextInt(2);
        int indexFNameMales = random.nextInt(DummyData.FIRSTNAMES_MALES.length);
        int indexFNameFemales = random.nextInt(DummyData.FIRSTNAMES_FEMALES.length);
        int indexMName = random.nextInt(DummyData.MIDDLENAMES.length);
        int indexLName = random.nextInt(DummyData.LASTNAMES.length);

        Random random = new Random();
        long groupID = 1000L + (long) random.nextInt(10);
        User user;

        if (gender == 0)
            user = createUser(DummyData.FIRSTNAMES_MALES[indexFNameMales],
                    DummyData.MIDDLENAMES[indexMName] + "вич",
                    DummyData.LASTNAMES[indexLName],
                    new Date(year - 1 + random.nextInt(3),
                            random.nextInt(12) + 1,
                            random.nextInt(28) + 1),
                    groupID, role);
        else
            user = createUser(DummyData.FIRSTNAMES_FEMALES[indexFNameFemales],
                    DummyData.MIDDLENAMES[indexMName] + "вна",
                    DummyData.LASTNAMES[indexLName] + "а",
                    new Date(year - 1 + random.nextInt(3),
                            random.nextInt(12) + 1,
                            random.nextInt(28) + 1),
                    groupID, role);
        return user;
    }
//    @Override
//    public User add(User user) {
//        return addUser(user);
//    }
//
//    @Override
//    public void remove(User user) {
////        user.getUserGroup().remove(user.getId());
////        this.users.remove(user.getId());
//    }
//
//    @Override
//    public void removeAll() {
////        users.clear();
//    }
//
//    @Override
//    public User get(Long userID) {
//        return null;
//    }
//
//    @Override
//    public List<User> getAll() {
//        List<User> allElements = new ArrayList<>();
////        allElements.addAll(users.values());
//        return allElements;
//    }
//public void removeAllUsersByRole(Role role) {
////        for(Object o : users.entrySet())
////            if(((User) ((Map.Entry) o).getValue()).getRole() == role)
////                users.remove(((Map.Entry) o).getKey());
//}
//
//    private List<User> getUsersByRole(Role role) {
//        List<User> result = new ArrayList<>();
//
////        for (Object o : users.entrySet()) {
////            Map.Entry pair = (Map.Entry) o;
////            if (((User) pair.getValue()).getRole() == role)
////                result.add((User) pair.getValue());
////        }
//
//        return result;
//    }
//public User getRandomUserByRole(Role role) {
//        List result = new ArrayList();
////        if(!users.isEmpty())
////            result = getUsersByRole(role);
////        Random random = new Random();
//
//        return (User) result.get(random.nextInt(result.size()));
//    }

//    public void serializeToFile(User user) {
//
//        String outputFileName = user.getLastName() +
//                user.getFirstName().substring(0, 1) +
//                user.getMiddleName().substring(0, 1);
//
//        SerializationStuff.serializeToFile(user, outputFileName);
//    }
//
//    public void serializeAllToFile(String outputFileName) {
////        SerializationStuff.serializeAllToFile(users, outputFileName);
//    }
//
//    public List<User> deserializeFromFile(String inputFileName) {
//        return SerializationStuff.deserializeFromFile(inputFileName);
//    }
//
//    public Document serializeToXML(User element, Document document) {
//        return SerializationStuff.serializeToXML(element, document, "User");
//    }
//
//    public Document serializeAllToXML(Document document) {
//
//        Document doc = document;
//
////        for(Object MapEntry : users.entrySet()) {
////            User user = (User) ((Map.Entry) MapEntry).getValue();
////            doc = serializeToXML(user, doc);
////        }
//
//        return doc;
//    }
//
//    public List<User> deserializeFromXML(String inputFileName) {
//        return null;
//    }
}
