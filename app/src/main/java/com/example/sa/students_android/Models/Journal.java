package com.example.sa.students_android.Models;

import com.example.sa.students_android.Managers.UsersManager;

public class Journal {

    private User user;
    private Lesson lesson;
    private boolean present;


    public void start() {
        UsersManager usersManager = new UsersManager();
        User user = usersManager.addDummyStudent(1993);
        user.getFirstName();
    }


}
