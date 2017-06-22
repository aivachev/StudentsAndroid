package com.example.sa.students_android.Managers;

import com.example.sa.students_android.Models.DummyData;
import com.example.sa.students_android.Models.Group;
import com.example.sa.students_android.Models.Lesson;
import com.example.sa.students_android.MyUtilMethods.SerializationStuff;

import org.w3c.dom.Document;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class LessonsManager implements ManagerInterface<Lesson> {

    private HashMap<Long, Lesson> lessons = new HashMap<>();
    private Long lessonID;
    private Random random = new Random();

    public LessonsManager() { }

    private Lesson addLesson(String title, String dateTime, Integer auditorium, String description, String subject, String lector, List<Group> groups) {

        Lesson lesson;
        Date date = new Date(2018 - 1900, 00, 01, 00, 00, 00);

        lessonID = (long) subject.getBytes().length / lector.getBytes().length + auditorium * dateTime.hashCode();
        if(lessonID < 0)
            lessonID *= -1;

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        try {
            date = sdf.parse(dateTime);
//            date = new Date(sdf.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        lesson = new Lesson(lessonID, title, date, auditorium, description, subject, lector, groups);

        if(lessons.get(lessonID) == null)
            lessons.put(lessonID, lesson);
        else if(lessons.get(lessonID) != null) {
            System.out.println("Replacing existing lesson...");
            lessons.replace(lessonID, lesson);
        }
        return lesson;
    }

    public Lesson addDummyLesson() {
        String time = (random.nextInt(9) + 8) + ":00:00";
        String date = (random.nextInt(27) + 1) + ".06.2017 ";
        String dateTime = date + time;

        String title = DummyData.LESSONTITLES[random.nextInt(DummyData.LESSONTITLES.length)];

        String subject = DummyData.LESSONTITLES[random.nextInt(DummyData.LESSONTITLES.length)];

        Integer auditorium = 300 + random.nextInt(11);

        String description = DummyData.LESSONDESCRIPTION[random.nextInt(DummyData.LESSONDESCRIPTION.length)];

        String lector = DummyData.LASTNAMES[random.nextInt(DummyData.LASTNAMES.length)] + " "
                + DummyData.FIRSTNAMES_MALES[random.nextInt(DummyData.FIRSTNAMES_MALES.length)] + " "
                + DummyData.MIDDLENAMES[random.nextInt(DummyData.MIDDLENAMES.length)] + "вич";

        List<Group> groups = new ArrayList<>();
        for(Object object : GroupsManager.groups.entrySet()) {
            Group gr = (Group) ((Map.Entry) object).getValue();
            groups.add(gr);
        }

        return addLesson(title, dateTime, auditorium, description, subject, lector, groups);
    }

    public List<Lesson> getLessons() {
        List<Lesson> list = new ArrayList<>();
        list.addAll(lessons.values());
//        for (Object o : lessons.entrySet()) {
//            Map.Entry pair = (Map.Entry) o;
//            System.out.println(((Lesson) pair.getValue()).getTitle() + " at " + ((Lesson) pair.getValue()).getTime() + " in " + ((Lesson) pair.getValue()).getAuditorium() + ". Groups attending: " + ((Lesson) pair.getValue()).getGroupsAsString() + " (lessonID = " + ((Lesson) pair.getValue()).getLessonID() + ")");
//        }
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
