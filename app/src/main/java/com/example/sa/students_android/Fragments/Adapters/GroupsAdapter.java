package com.example.sa.students_android.Fragments.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sa.students_android.Activities.GroupsListActivity;
import com.example.sa.students_android.Activities.StudentsListActivity;
import com.example.sa.students_android.Managers.UsersManager;
import com.example.sa.students_android.Models.User;
import com.example.sa.students_android.R;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by sa on 22.06.17.
 */

public class GroupsAdapter extends RecyclerView.Adapter<GroupsAdapter.GroupHolder> {

    private UsersManager usersManager;
    private Context context;
    private HashMap<Long, Integer> entries;

    public GroupsAdapter(Context context) {
        this.context = context;
        this.usersManager = new UsersManager(context);
        this.entries = usersManager.getGroups();
    }

    @Override
    public GroupHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.group_item, parent, false);

        entries = usersManager.getGroups();

        return new GroupHolder(view);
    }

    public void updateList(HashMap<Long, Integer> entries) {
        this.entries = entries;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(GroupHolder holder, int position) {
        holder.bindData(entries.entrySet().toArray()[position]);
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    // UsersHolder

    class GroupHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener  {

        private TextView groupName;
        private TextView groupCount;

        GroupHolder(View itemView) {
            super(itemView);

            groupName = (TextView) itemView.findViewById(R.id.groupListItemName);
            groupCount = (TextView) itemView.findViewById(R.id.groupListItemCount);
            super.itemView.setOnClickListener(this);
            super.itemView.setOnLongClickListener(this);
        }

        void bindData(Object object) {

            Map.Entry mapEntry = (Map.Entry) object;
            groupName.setText(String.format(Locale.getDefault(), "#%d", (Long) mapEntry.getKey()));
            groupCount.setText(String.format(Locale.getDefault(), "Людей: %d" , (Integer) mapEntry.getValue()));

        }

        @Override
        public void onClick(View view) {
            Context viewContext = view.getContext();
            Intent intent = new Intent(viewContext, StudentsListActivity.class).putExtra("groupId", groupName.getText().toString().substring(1));
            viewContext.startActivity(intent);
        }

        @Override
        public boolean onLongClick(View view) {
            Toast.makeText(context, "Long kek", Toast.LENGTH_SHORT).show();

            //TODO Try to call onCreateContextOptionMenu from inside GroupsListActivity
            return true;
        }
    }
}

