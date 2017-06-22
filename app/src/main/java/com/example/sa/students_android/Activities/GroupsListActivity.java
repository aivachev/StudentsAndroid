package com.example.sa.students_android.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sa.students_android.Adapters.GroupHolder;
import com.example.sa.students_android.Models.Group;
import com.example.sa.students_android.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sa on 22.06.17.
 */

public class GroupsListActivity extends Activity {

    RecyclerView recyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        ArrayList<Group> groups = new ArrayList<>();
        groups.add(new Group(1000));
        recyclerView = findViewById(R.id.groupsList);
        recyclerView.setAdapter(new GroupsAdapter());

        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
    }

    class GroupsAdapter extends RecyclerView.Adapter<GroupHolder> {


        List<Group> groupList = new ArrayList<>();

        public GroupsAdapter() {
            groupList.add(new Group(1000));
            groupList.add(new Group(1004));
            groupList.add(new Group(1005));
            groupList.add(new Group(1006));
        }

        @Override
        public GroupHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.group_item, parent, false);

            return new GroupHolder(v);
        }

        @Override
        public void onBindViewHolder(GroupHolder holder, int position) {
            holder.bindData(groupList.get(position));
        }

        @Override
        public int getItemCount() {
            return groupList.size();
        }

        public void delete(int position) {
            groupList.remove(position);
            notifyItemRemoved(position);
        }
    }
}
