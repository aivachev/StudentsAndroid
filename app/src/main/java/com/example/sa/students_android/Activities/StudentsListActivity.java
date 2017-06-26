package com.example.sa.students_android.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.sa.students_android.Adapters.GroupAdapter;
import com.example.sa.students_android.Adapters.StudentAdapter;
import com.example.sa.students_android.Managers.UsersManager;
import com.example.sa.students_android.Models.User;
import com.example.sa.students_android.R;
import com.example.sa.students_android.SQLite.DatabaseHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sa on 24.06.17.
 */

public class StudentsListActivity extends Activity {

    ListView listViewStudents;
    StudentAdapter studentAdapter;
    List<User> allItems = new ArrayList<>();
    UsersManager usersManager = new UsersManager(this);
    EditText searchField;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);

        Bundle stuff = getIntent().getExtras();
        final Long groupId = Long.parseLong( stuff.get("groupId").toString());

        listViewStudents = (ListView) findViewById(R.id.listStudents);

        studentAdapter = new StudentAdapter(this, groupId);
        allItems = studentAdapter.getAllItems();
        listViewStudents.setAdapter(studentAdapter);

        listViewStudents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intentForActivity =
                        new Intent(StudentsListActivity.this, StudentInfoActivity.class)
                                .putExtra("currentStudent", usersManager.getGroupMembers(groupId).get(i));
                startActivity(intentForActivity);
            }
        });

        searchField = findViewById(R.id.sort_by_user);
        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                filter(editable.toString());

            }

            void filter(String text){
                List<User> temp = new ArrayList<User>();
                for(User user: usersManager.getGroupMembers(-1L)){
                    //or use .contains(text)
                    if(user.getLastName().contains(text)){
                        temp.add(user);
                    }
                }
                //update recyclerview
                ((StudentAdapter)listViewStudents.getAdapter()).updateList(temp);
            }
        });
    }
}
