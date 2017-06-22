package com.example.sa.students_android.Activities;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sa.students_android.Adapters.GroupHolder;
import com.example.sa.students_android.Models.Group;
import com.example.sa.students_android.R;
import com.example.sa.students_android.SQLite.DatabaseHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Created by sa on 22.06.17.
 */

public class GroupsListActivity extends Activity {

    RecyclerView recyclerView;
    DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        databaseHandler = new DatabaseHandler(this);

        recyclerView = findViewById(R.id.groupsList);
        recyclerView.setAdapter(new GroupsAdapter());

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        DividerItemDecoration dividerItemDecorationHor = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.HORIZONTAL);
        DividerItemDecoration dividerItemDecorationVert = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);

        recyclerView.addItemDecoration(dividerItemDecorationHor);
        recyclerView.addItemDecoration(dividerItemDecorationVert);
    }

    class GroupsAdapter extends RecyclerView.Adapter<GroupHolder> {


        List<Long> groupList = new ArrayList<>();

        public GroupsAdapter() {
            for(Map.Entry o : databaseHandler.getGroups().entrySet()) {
                groupList.add((Long) o.getKey());
            }
        }

        @Override
        public GroupHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.group_item, parent, false);

            return new GroupHolder(v);
        }

        @Override
        public void onBindViewHolder(GroupHolder holder, int position) {

            Arrays.sort(databaseHandler.getGroups().entrySet().toArray(), new Comparator<Object>() {
                @Override
                public int compare(Object o, Object t1) {
                    switch ( ( (Long) (((Map.Entry)o).getKey())).compareTo(((Long)(((Map.Entry)t1).getKey())))) {
                        case -1:
                            return -1;
                        case 0:
                            return 0;
                        case 1:
                            return 1;
                    }
                    return 0;
                }
            });
            //Map.Entry[] entries = (Map.Entry[]) databaseHandler.getGroups().entrySet().toArray();
            holder.bindData(databaseHandler.getGroups().entrySet().toArray()[position]);
        }

        @Override
        public int getItemCount() {
            return databaseHandler.getGroups().size();
        }

        public void delete(int position) {
            groupList.remove(position);
            notifyItemRemoved(position);
        }
    }
}
