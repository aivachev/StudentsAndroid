package com.example.sa.students_android.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.example.sa.students_android.Managers.LessonsManager;
import com.example.sa.students_android.Managers.UsersManager;
import com.example.sa.students_android.Models.Role;
import com.example.sa.students_android.R;
import com.example.sa.students_android.SQLite.DatabaseHandler;

/**
 * Created by sa on 22.06.17.
 */

public class DebugActivity extends Activity {

    UsersManager usersManager;

    Button getGroups;
    Button dummyData;
    Button dummyLessons;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);

        usersManager = new UsersManager(this);

        // Print existing groups to log
        getGroups = (Button) findViewById(R.id.get_groups);
        getGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usersManager.getGroups();
            }
        });

        // Create a number of dummy students to populate users table
        dummyData = (Button) findViewById(R.id.dummy_users);
        dummyData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UsersManager usersManager = new UsersManager(DebugActivity.this);
                for(int i = 0; i < 100; i++)
                    usersManager.addDummyUser(1990, Role.STUDENT);
            }
        });

        // Create a number of dummy lessons to populate lessons table
        dummyData = (Button) findViewById(R.id.dummy_lessons);
        dummyData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LessonsManager lessonsManager = new LessonsManager(DebugActivity.this);
                for(int i = 0; i < 100; i++)
                    lessonsManager.addDummyLesson();
            }
        });
    }
}
