package com.example.sa.students_android.Managers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sa.students_android.Models.DayTime;
import com.example.sa.students_android.Models.DummyData;
import com.example.sa.students_android.Models.Lesson;
import com.example.sa.students_android.Models.Role;
import com.example.sa.students_android.Models.User;
import com.example.sa.students_android.MyUtilMethods.SerializationStuff;
import com.example.sa.students_android.SQLite.DatabaseHandler;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.example.sa.students_android.MyUtilMethods.SerializationStuff.deserialize;
import static com.example.sa.students_android.MyUtilMethods.SerializationStuff.serialize;
import static com.example.sa.students_android.SQLite.DBContracts.Lessons.KEY_AUDITORIUM;
import static com.example.sa.students_android.SQLite.DBContracts.Lessons.KEY_DAYSNTIMES;
import static com.example.sa.students_android.SQLite.DBContracts.Lessons.KEY_DESCRIPTION;
import static com.example.sa.students_android.SQLite.DBContracts.Lessons.KEY_GROUPS;
import static com.example.sa.students_android.SQLite.DBContracts.Lessons.KEY_LECTOR;
import static com.example.sa.students_android.SQLite.DBContracts.Lessons.KEY_LESSON_ID;
import static com.example.sa.students_android.SQLite.DBContracts.Lessons.KEY_SUBJECT;
import static com.example.sa.students_android.SQLite.DBContracts.Lessons.TABLE_LESSONS;
import static com.example.sa.students_android.SQLite.DBContracts.Users.KEY_LOGIN;
import static com.example.sa.students_android.SQLite.DBContracts.Users.KEY_ROLE;
import static com.example.sa.students_android.SQLite.DBContracts.Users.TABLE_USERS;

public class LessonsManager implements ManagerInterface<Lesson> {

    private HashMap<Long, Lesson> lessons = new HashMap<>();
    private Long lessonID;
    private Context context;
    private DatabaseHandler databaseHandler;

    private Random random = new Random();

    public LessonsManager(Context context) {
        this.context = context;
        this.databaseHandler = new DatabaseHandler(context);
    }

    private void addLesson(DayTime dayTime, Integer auditorium, String description, String subject, User lector, List<Long> groups) {

        lessonID = (long) subject.getBytes().length / lector.toString().getBytes().length + auditorium * dayTime.hashCode();
        if (lessonID < 0)
            lessonID *= -1;

        SQLiteDatabase db = databaseHandler.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_LESSON_ID, lessonID);
        values.put(KEY_DAYSNTIMES, serialize(dayTime));
        values.put(KEY_AUDITORIUM, auditorium);
        values.put(KEY_DESCRIPTION, description);
        values.put(KEY_SUBJECT, subject);
        values.put(KEY_LECTOR, serialize(lector));
        values.put(KEY_GROUPS, serialize(groups));


        db.insert(TABLE_LESSONS, null, values);
        db.close();
    }

    public List<Lesson> getAllLessons() {

        List<Lesson> result = new ArrayList<>();

        SQLiteDatabase db = databaseHandler.getReadableDatabase();

        String[] projection = null;
        String selection = null;
        String[] selectionArgs = null;

        Cursor cursor = db.query(
                TABLE_LESSONS,                     // The table to query
                projection,                      // The columns to return
                selection,                       // The columns for the WHERE clause
                selectionArgs,                   // The values for the WHERE clause
                null,                            // don't group the rows
                null,                            // don't filter by row groups
                null                             // The sort order
        );

        if(cursor.getCount() == 0) {
            cursor.close();
            return result;
        }

        if(cursor.getCount() > 0)
            while (cursor.moveToNext()) {

                long lessonId = cursor.getLong(cursor.getColumnIndex(KEY_LESSON_ID));
                List<DayTime> daysNtimes = (List<DayTime>) deserialize(cursor.getBlob(cursor.getColumnIndex(KEY_DAYSNTIMES)));
                int auditorium = cursor.getInt(cursor.getColumnIndex(KEY_AUDITORIUM));
                String description = cursor.getString(cursor.getColumnIndex(KEY_DESCRIPTION));
                String subject = cursor.getString(cursor.getColumnIndex(KEY_SUBJECT));
                User lector = (User) deserialize(cursor.getBlob(cursor.getColumnIndex(KEY_LECTOR)));
                List<Long> groups = (List<Long>) deserialize(cursor.getBlob(cursor.getColumnIndex(KEY_GROUPS)));

                result.add(
                        new Lesson(
                                lessonId,
                                daysNtimes,
                                auditorium,
                                description,
                                subject,
                                lector,
                                groups
                        ));

            }
        cursor.close();

        return result;
    }

    public List<Lesson> getLessonsForGroup(Long groupId) {

        List<Lesson> result = new ArrayList<>();
//
//        SQLiteDatabase db = databaseHandler.getReadableDatabase();
//
//        String[] projection = null;
//        String selection = KEY_GR + " = ?";
//        String[] selectionArgs = {
//                login
//        };
//
//        Cursor cursor = db.query(
//                TABLE_USERS,                     // The table to query
//                projection,                      // The columns to return
//                selection,                       // The columns for the WHERE clause
//                selectionArgs,                   // The values for the WHERE clause
//                null,                            // don't group the rows
//                null,                            // don't filter by row groups
//                null                             // The sort order
//        );
//
//        if(cursor.getCount() == 0) {
//            cursor.close();
//            return null;
//        }
//
//        Role role = Role.STUDENT;
//
//        if(cursor.getCount() == 1)
//            while (cursor.moveToNext()) {
//
//                role = (Role) deserialize(cursor.getBlob(cursor.getColumnIndex(KEY_ROLE)));
//
//            }
//        cursor.close();
//

        return result;
    }

    public void addDummyLesson() {

        Random random = new Random();

        DayTime dayTime = new DayTime(DayTime.Days.values()[random.nextInt(DayTime.Days.values().length)],
                DayTime.Times.values()[random.nextInt(DayTime.Times.values().length)]);

        String title = DummyData.LESSONTITLES[random.nextInt(DummyData.LESSONTITLES.length)];

        String subject = DummyData.LESSONTITLES[random.nextInt(DummyData.LESSONTITLES.length)];

        Integer auditorium = 300 + random.nextInt(11);

        String description = DummyData.LESSONDESCRIPTION[random.nextInt(DummyData.LESSONDESCRIPTION.length)];
        UsersManager usersManager = new UsersManager(context);
        User lector = usersManager.addDummyUser(1980, Role.TEACHER);

        List<Long> groups = new ArrayList<>();

        for(Long k : usersManager.getGroups().keySet())
            groups.add(k);

        addLesson(dayTime, auditorium, description, subject, lector, groups);
    }

    public List<Lesson> getLessons() {
        List<Lesson> list = new ArrayList<>();
        list.addAll(lessons.values());

        return list;
    }

    @Override
    public Lesson add(Lesson lesson) {
        return lessons.put(lessonID, lesson);
    }

    @Override
    public void remove(Lesson lesson) {
        lessons.remove(lesson);
    }

    @Override
    public void removeAll() {
        lessons.clear();
    }

    @Override
    public Lesson get(Long number) {
        return lessons.get(number);
    }

    @Override
    public List<Lesson> getAll() {
        return (List<Lesson>) lessons.values();
    }

    @Override
    public void serializeToFile(Lesson lesson) {

        String outputFileName = lesson.getSubject() +
                " (" + lesson.getAuditorium() + ")";

        SerializationStuff.serializeToFile(lesson, outputFileName);
    }

    @Override
    public void serializeAllToFile(String outputFileName) {
        SerializationStuff.serializeAllToFile(lessons, outputFileName);
    }

    @Override
    public List<Lesson> deserializeFromFile(String inputFileName) {
        return SerializationStuff.deserializeFromFile(inputFileName);
    }

    @Override
    public Document serializeToXML(Lesson element, Document document) {
        return SerializationStuff.serializeToXML(element, document, "Lesson");
    }

    @Override
    public Document serializeAllToXML(Document document) {

        Document doc = document;

        for(Object MapEntry : lessons.entrySet()) {
            Lesson lesson = (Lesson) ((Map.Entry) MapEntry).getValue();
            doc = serializeToXML(lesson, doc);
        }

        return doc;
    }

    @Override
    public List<Lesson> deserializeFromXML(String inputFileName) {
        return null;
    }
}
