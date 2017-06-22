package com.example.sa.students_android.Models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Lesson {

    private Long lessonID;
    private String title;
    private Date time;
    private Integer auditorium;
    private String description;
    private String subject;
    private String lector;
    private List<Group> groups;

    public Lesson(Long lessonID, String title, Date time, Integer auditorium, String description, String subject, String lector, List<Group> groups) {
        this.lessonID = lessonID;
        this.title = title;
        this.time = time;
        this.auditorium = auditorium;
        this.description = description;
        this.subject = subject;
        this.lector = lector;
        this.groups = groups;
    }

    public Long getLessonID() {
        return lessonID;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public String getGroupsAsString() {
        Integer counter = 0;
        StringBuilder stringBuilder = new StringBuilder();
        for(Group group : groups)
            if(++counter < groups.size())
                stringBuilder.append(group.getGroupID()).append(", ");
            else
                stringBuilder.append(group.getGroupID());
        return stringBuilder.toString();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getAuditorium() {
        return auditorium;
    }

    public void setAuditorium(Integer auditorium) {
        this.auditorium = auditorium;
    }

    public String getDescription() {
        return description;
    }

    public String getSubject() {
        return subject;
    }

    public String getLector() {
        return lector;
    }

    public void setLector(String lector) {
        this.lector = lector;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Lesson)) return false;
        if (this.getLessonID() != ((Lesson) obj).getLessonID()) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + time.toString().hashCode();
        result = 31 * result + auditorium.hashCode();
        result = 31 * result + subject.hashCode();
        result = 31 * result + lector.hashCode();
        return result;
    }
}
