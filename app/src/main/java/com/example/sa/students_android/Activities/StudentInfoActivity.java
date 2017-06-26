package com.example.sa.students_android.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sa.students_android.Adapters.ContactAdapter;
import com.example.sa.students_android.Adapters.JournalListAdapter;
import com.example.sa.students_android.Managers.LessonsManager;
import com.example.sa.students_android.Managers.UsersManager;
import com.example.sa.students_android.Models.Journal;
import com.example.sa.students_android.Models.User;
import com.example.sa.students_android.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sa on 20.06.17.
 */

public class StudentInfoActivity extends Activity {

    private ListView listContacts;
    private ListView listJournal;

    private TextView studFirstName;
    private TextView studLastName;
    private TextView studMiddleName;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        UsersManager usersManager = new UsersManager(this);
        LessonsManager lessonsManager = new LessonsManager();

        User thisUser = (User) getIntent().getSerializableExtra("currentStudent");

        studFirstName = findViewById(R.id.spec_stud_first_name);
        studLastName = findViewById(R.id.spec_stud_last_name);
        studMiddleName = findViewById(R.id.spec_stud_middle_name);

        studFirstName.setText(thisUser.getFirstName());
        studLastName.setText(thisUser.getLastName());
        studMiddleName.setText(thisUser.getMiddleName());

        listContacts = findViewById(R.id.listContacts);

        Log.i("StudInfoActivity", String.valueOf(thisUser.getContacts().isEmpty()));
        ContactAdapter contactAdapter = new ContactAdapter(this, thisUser.getContacts());
//        ArrayAdapter<String> arrayAdapterContacts =
//                new ArrayAdapter<>(StudentInfoActivity.this,
//                        android.R.layout.simple_list_item_1,
//                        new String[]{"+7 (981) 777-77-77", "@thisIsATest", "vk.com/id01"});
        listContacts.setAdapter(contactAdapter);

        listContacts.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    }
                });

        listJournal = (ListView) findViewById(R.id.journalList);
        Journal journal =
                new Journal(
                        thisUser,
                        lessonsManager.addDummyLesson(), true);
        List<Journal> journals = new ArrayList<>();
        journals.add(journal);
        JournalListAdapter journalListAdapter =
                new JournalListAdapter(this, journals);

        listJournal.setAdapter(journalListAdapter);
    }
}
