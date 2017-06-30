package com.example.sa.students_android.Activities;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.sa.students_android.Fragments.Adapters.ContactsAdapter;
import com.example.sa.students_android.Fragments.Adapters.LessonsAdapter;
import com.example.sa.students_android.Models.Role;
import com.example.sa.students_android.Models.User;
import com.example.sa.students_android.R;

/**
 * Created by sa on 20.06.17.
 */

public class StudentInfoActivity extends Activity {

    TextView userFirstName;
    TextView userLastName;
    TextView userMiddleName;
    TextView userRole;

    RecyclerView rvFragmentLessons;
    RecyclerView rvFragmentContacts;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        User thisUser = (User) getIntent().getSerializableExtra("currentStudent");

        userFirstName = findViewById(R.id.spec_user_first_name);
        userLastName = findViewById(R.id.spec_user_last_name);
        userMiddleName = findViewById(R.id.spec_user_middle_name);

        userFirstName.setText(thisUser.getFirstName());
        userLastName.setText(thisUser.getLastName());
        userMiddleName.setText(thisUser.getMiddleName());
        userRole = findViewById(R.id.role_desc);

        if(thisUser.getRole().equals(Role.TEACHER)) {
            userRole.setVisibility(View.VISIBLE);
            userRole.setText("Преподаватель");
        }

        Fragment lessonsFragment = getFragmentManager().findFragmentById(R.id.lessons_rec_list_fragment);
        rvFragmentLessons = (RecyclerView) lessonsFragment.getView().findViewById(R.id.rec_list_in_fragment);
        rvFragmentLessons.setAdapter(new LessonsAdapter(this, 1L));
        rvFragmentLessons.setLayoutManager(new LinearLayoutManager(this));

        Fragment contactsFragment = getFragmentManager().findFragmentById(R.id.contacts_rec_list_fragment);
        rvFragmentContacts = (RecyclerView) contactsFragment.getView().findViewById(R.id.rec_list_in_fragment);
        rvFragmentContacts.setAdapter(new ContactsAdapter(this, thisUser.getId()));
        rvFragmentContacts.setLayoutManager(new LinearLayoutManager(this));


    }
}
