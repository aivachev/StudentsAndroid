package com.example.sa.students_android.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sa.students_android.Fragments.Adapters.ContactAdapter;
import com.example.sa.students_android.Managers.LessonsManager;
import com.example.sa.students_android.Managers.UsersManager;
import com.example.sa.students_android.Models.Journal;
import com.example.sa.students_android.Models.Role;
import com.example.sa.students_android.Models.User;
import com.example.sa.students_android.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sa on 20.06.17.
 */

public class StudentInfoActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        UsersManager usersManager = new UsersManager(this);
        LessonsManager lessonsManager = new LessonsManager(this);

        User thisUser = (User) getIntent().getSerializableExtra("currentStudent");

        TextView userFirstName = findViewById(R.id.spec_user_first_name);
        TextView userLastName = findViewById(R.id.spec_user_last_name);
        TextView userMiddleName = findViewById(R.id.spec_user_middle_name);

        userFirstName.setText(thisUser.getFirstName());
        userLastName.setText(thisUser.getLastName());
        userMiddleName.setText(thisUser.getMiddleName());

        TextView userRole = findViewById(R.id.role_desc);

        if(thisUser.getRole().equals(Role.TEACHER)) {
            userRole.setVisibility(View.VISIBLE);
            userRole.setText("Преподаватель");
        }

        ListView listContacts = findViewById(R.id.listContacts);

        ContactAdapter contactAdapter = new ContactAdapter(this, thisUser.getContacts());

        listContacts.setAdapter(contactAdapter);

        listContacts.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    }
                });

        ListView listJournal = (ListView) findViewById(R.id.journalList);

        List<Journal> journals = new ArrayList<>();
    }
}
