package com.example.sa.students_android.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.example.sa.students_android.Managers.UsersManager;
import com.example.sa.students_android.R;
import com.example.sa.students_android.SQLite.DatabaseHandler;

/**
 * Created by sa on 22.06.17.
 */

public class DebugActivity extends Activity {

    DatabaseHandler databaseHandler;

    Button getGroups;
    Button dummyData;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debug);

        databaseHandler = new DatabaseHandler(this);

        // Print existing groups to log
        getGroups = (Button) findViewById(R.id.get_groups);
        getGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseHandler.getGroups();
            }
        });

        // Create a number of dummy students to populate main table
        dummyData = (Button) findViewById(R.id.dummy_data);
        dummyData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UsersManager usersManager = new UsersManager(DebugActivity.this);
                for(int i = 0; i < 100; i++)
                    usersManager.addDummyStudent(1990);
            }
        });
    }
}
