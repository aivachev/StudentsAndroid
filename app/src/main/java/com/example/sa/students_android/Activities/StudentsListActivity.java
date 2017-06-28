package com.example.sa.students_android.Activities;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sa.students_android.Fragments.Adapters.UsersAdapter;
import com.example.sa.students_android.Models.User;
import com.example.sa.students_android.R;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static com.example.sa.students_android.Activities.LoginActivity.usersManager;

/**
 * Created by sa on 24.06.17.
 */

public class StudentsListActivity extends Activity {

    RecyclerView recyclerViewFragment;
    EditText searchField;
    TextView searchResults;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);

        Bundle stuff = getIntent().getExtras();
        final Long groupId = Long.parseLong( stuff.get("groupId").toString());

        Fragment fragment = getFragmentManager().findFragmentById(R.id.users_rec_list_fragment);
        recyclerViewFragment = (RecyclerView) fragment.getView().findViewById(R.id.rec_list_in_fragment);
        recyclerViewFragment.setAdapter(new UsersAdapter(this, groupId));
        recyclerViewFragment.setLayoutManager(new LinearLayoutManager(this));


        searchResults = findViewById(R.id.found_amount);
        searchResults.setText(String.valueOf(recyclerViewFragment.getAdapter().getItemCount()));

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

                for(User user: usersManager.getGroupMembers(groupId)){

                    if(Pattern.compile(Pattern.quote(text), Pattern.CASE_INSENSITIVE).matcher(user.getLastName()).find()
                            || Pattern.compile(Pattern.quote(text), Pattern.CASE_INSENSITIVE).matcher(user.getFirstName()).find()
                            || Pattern.compile(Pattern.quote(text), Pattern.CASE_INSENSITIVE).matcher(user.getMiddleName()).find()
                            || Pattern.compile(Pattern.quote(text), Pattern.CASE_INSENSITIVE).matcher(user.getUserGroup().toString()).find()){
                        temp.add(user);
                    }
                }
                if(temp != null) {
                    searchResults.setText(String.valueOf(temp.size()));
                    ((UsersAdapter) recyclerViewFragment.getAdapter()).updateList(temp);
                }
            }
        });
    }
}
