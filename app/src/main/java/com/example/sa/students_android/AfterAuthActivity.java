package com.example.sa.students_android;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sa.students_android.Adapters.JournalListAdapter;
import com.example.sa.students_android.Managers.LessonsManager;
import com.example.sa.students_android.Managers.UsersManager;
import com.example.sa.students_android.Models.Journal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sa on 20.06.17.
 */

public class AfterAuthActivity extends Activity {

    private ListView listContacts;
    private ListView listJournal;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside);

        UsersManager usersManager = new UsersManager();
        LessonsManager lessonsManager = new LessonsManager();

        listContacts = findViewById(R.id.listContacts);
        ArrayAdapter<String> arrayAdapterContacts =
                new ArrayAdapter<String>(AfterAuthActivity.this,
                android.R.layout.simple_list_item_1,
                        new String[] { "+7 (981) 777-77-77", "@thisIsATest", "vk.com/id01"});
        listContacts.setAdapter(arrayAdapterContacts);

        listContacts.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView,
                                            View view, int i, long l) {
                        TextView clicked =
                                (TextView) view;
                        Toast.makeText(AfterAuthActivity.this,
                                clicked.getText().toString(),
                                Toast.LENGTH_SHORT).show();
                    }
                });

        listJournal = (ListView) findViewById(R.id.journalList);
        Journal journal =
                new Journal(
                        usersManager.addDummyStudent(1990),
                        lessonsManager.addDummyLesson(), true);
        List<Journal> journals = new ArrayList<>();
        journals.add(journal);
        JournalListAdapter journalListAdapter =
                new JournalListAdapter(this, journals);

        listJournal.setAdapter(journalListAdapter);
    }
}
