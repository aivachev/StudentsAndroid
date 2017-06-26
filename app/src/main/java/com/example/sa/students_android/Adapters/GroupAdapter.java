package com.example.sa.students_android.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sa.students_android.Activities.GroupsListActivity;
import com.example.sa.students_android.Activities.StudentsListActivity;
import com.example.sa.students_android.Models.Group;
import com.example.sa.students_android.Models.User;
import com.example.sa.students_android.R;
import com.example.sa.students_android.SQLite.DatabaseHandler;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by sa on 22.06.17.
 */

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupHolder> {

    private DatabaseHandler databaseHandler;
    private Context context;

    public GroupAdapter(Context context) {
        this.context = context;
        this.databaseHandler = new DatabaseHandler(context);
    }

    @Override
    public GroupHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.group_item, parent, false);

        return new GroupHolder(view);
    }

    @Override
    public void onBindViewHolder(GroupHolder holder, int position) {

//            Arrays.sort(databaseHandler.getGroups().entrySet().toArray(), new Comparator<Object>() {
//                @Override
//                public int compare(Object o, Object t1) {
//                    switch ( ( (Long) (((Map.Entry)o).getKey())).compareTo(((Long)(((Map.Entry)t1).getKey())))) {
//                        case -1:
//                            return -1;
//                        case 0:
//                            return 0;
//                        case 1:
//                            return 1;
//                    }
//                    return 0;
//                }
//            });
//            Map.Entry[] entries = (Map.Entry[]) databaseHandler.getGroups().entrySet().toArray();
        holder.bindData(databaseHandler.getGroups().entrySet().toArray()[position]);
    }

    @Override
    public int getItemCount() {
        return databaseHandler.getGroups().size();
    }

    public Context getContext() {
        return context;
    }

    public void delete(int position) {
        notifyItemRemoved(position);
    }

    // GroupHolder

    class GroupHolder extends RecyclerView.ViewHolder {

        private TextView groupName;
        private TextView groupCount;

        GroupHolder(View itemView) {
            super(itemView);

            groupName = (TextView) itemView.findViewById(R.id.groupListItemName);
            groupCount = (TextView) itemView.findViewById(R.id.groupListItemCount);
            super.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, StudentsListActivity.class).putExtra("groupId", groupName.getText().toString().substring(1));
                    context.startActivity(intent);
                    Toast.makeText(getContext(), groupName.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        void bindData(Object object) {

            Map.Entry mapEntry = (Map.Entry) object;
            groupName.setText(String.format(Locale.getDefault(), "#%d", (Long) mapEntry.getKey()));
            groupCount.setText(String.format(Locale.getDefault(), "Людей: %d" , (Integer) mapEntry.getValue()));

        }
    }
}

