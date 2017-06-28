package com.example.sa.students_android.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.sa.students_android.R;

/**
 * Created by sa on 26.06.17.
 */

public class AdminOverviewActivity extends AppCompatActivity {

    Button Students;
    Button Groups;
    Button Lessons;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_overview);

        Students = (Button) findViewById(R.id.students);
        Groups = (Button) findViewById(R.id.groups);
        Lessons = (Button) findViewById(R.id.go_to_lessons);

        Students.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminOverviewActivity.this, StudentsListActivity.class).putExtra("groupId", -1L));
            }
        });

        Groups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminOverviewActivity.this, GroupsListActivity.class));
            }
        });

        Lessons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminOverviewActivity.this, LessonsListActivity.class));
            }
        });
    }
}
