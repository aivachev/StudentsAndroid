package com.example.sa.students_android.Models;

import java.io.Serializable;
import java.util.List;

public class Lesson implements Serializable {

    private Long lessonID;

    private List<DayTime> daysNtimes;
    private Integer auditorium;
    private String description;
    private String subject;
    private User lector;
    private List<Long> groups;

    public Lesson(Long lessonID, List<DayTime> daysNtimes,
                  Integer auditorium, String description, String subject,
                  User lector, List<Long> groups) {

        this.lessonID = lessonID;

        this.auditorium = auditorium;
        this.description = description;
        this.subject = subject;
        if(lector.getRole().equals(Role.TEACHER))
            this.lector = lector;
        else
            this.lector = null;
        this.groups = groups;
        this.daysNtimes = daysNtimes;
    }

    public List<DayTime> getDaysNtimes() {
        return daysNtimes;
    }

    public Long getId() {
        return lessonID;
    }

    public List<Long> getGroups() {
        return groups;
    }

    public String getGroupsAsString() {
        Integer counter = 0;
        StringBuilder stringBuilder = new StringBuilder();
//        for(Group group : groups)
//            if(++counter < groups.size())
//                stringBuilder.append(group.getGroupID()).append(", ");
//            else
//                stringBuilder.append(group.getGroupID());
        return stringBuilder.toString();
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

    public User getLector() {
        return lector;
    }

    public void setLector(User lector) {
        if(lector.getRole().equals(Role.TEACHER))
            this.lector = lector;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Lesson)) return false;
        if (this.getId() != ((Lesson) obj).getId()) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = subject.hashCode();
        result = 31 * result + daysNtimes.toString().hashCode();
        result = 31 * result + auditorium.hashCode();
        result = 31 * result + subject.hashCode();
        result = 31 * result + lector.hashCode();
        return result;
    }
}
