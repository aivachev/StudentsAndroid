package com.example.sa.students_android.Models;

import com.example.sa.students_android.Managers.UsersManager;

import java.util.List;

public class Journal {



    private User user;

    public User getUser() {
        return user;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public boolean isPresent() {
        return present;
    }

    private Lesson lesson;
    private boolean present;

    public Journal(User user, Lesson lesson, boolean present) {
        this.user = user;
        this.lesson = lesson;
        this.present = present;
    }

    public void start() {
        //UsersManager usersManager = new UsersManager();
        //User user = usersManager.addDummyUser(1993);
        //user.getFirstName();
    }


}
