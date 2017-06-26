package com.example.sa.students_android.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.sa.students_android.Adapters.StudentAdapter;
import com.example.sa.students_android.Models.User;
import com.example.sa.students_android.R;
import com.example.sa.students_android.SQLite.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sa on 24.06.17.
 */

public class StudentsListActivity extends Activity {

    ListView listViewStudents;
    StudentAdapter studentAdapter;
    List<User> allItems = new ArrayList<>();
    DatabaseHandler databaseHandler = new DatabaseHandler(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);

        Bundle stuff = getIntent().getExtras();
        final Long groupId = Long.parseLong((String) stuff.get("groupId"));

        listViewStudents = (ListView) findViewById(R.id.listStudents);

        studentAdapter = new StudentAdapter(this, groupId);
        allItems = studentAdapter.getAllItems();
        listViewStudents.setAdapter(studentAdapter);

        listViewStudents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Log.i("ItsMeStudLiAc", String.valueOf(databaseHandler.getGroupMembers(groupId).get(i).getContacts().isEmpty()));
                Intent intentForActivity =
                        new Intent(StudentsListActivity.this, StudentInfoActivity.class)
                                .putExtra("currentStudent", databaseHandler.getGroupMembers(groupId).get(i));
                startActivity(intentForActivity);
            }
        });
    }
}
