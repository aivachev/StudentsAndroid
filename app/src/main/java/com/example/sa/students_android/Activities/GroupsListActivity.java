package com.example.sa.students_android.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.sa.students_android.Adapters.GroupAdapter;
import com.example.sa.students_android.R;

/**
 * Created by sa on 22.06.17.
 */

public class GroupsListActivity extends Activity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        recyclerView = findViewById(R.id.groupsList);
        recyclerView.setAdapter(new GroupAdapter(this));

        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
    }

}
