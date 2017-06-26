package com.example.sa.students_android.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.sa.students_android.Adapters.GroupAdapter;
import com.example.sa.students_android.Managers.UsersManager;
import com.example.sa.students_android.Models.Group;
import com.example.sa.students_android.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sa on 22.06.17.
 */

public class GroupsListActivity extends Activity {

    RecyclerView recyclerView;
    EditText searchField;
    UsersManager usersManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        usersManager = new UsersManager(this);

        recyclerView = findViewById(R.id.groupsList);
        recyclerView.setAdapter(new GroupAdapter(this));

        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        searchField = findViewById(R.id.sort_by);
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
                HashMap<Long, Integer> temp = new HashMap<>();
                for(Map.Entry o: usersManager.getGroups().entrySet()){
                    //or use .contains(text)
                    if(o.getKey().toString().contains(text)){
                        temp.put((Long) o.getKey(), (Integer) o.getValue());
                    }
                }
                //update recyclerview
                ((GroupAdapter)recyclerView.getAdapter()).updateList(temp);
            }
        });
    }

}
