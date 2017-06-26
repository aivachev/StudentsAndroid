package com.example.sa.students_android.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sa.students_android.Models.User;
import com.example.sa.students_android.R;
import com.example.sa.students_android.SQLite.DatabaseHandler;

import java.util.List;

/**
 * Created by sa on 24.06.17.
 */

public class StudentAdapter extends BaseAdapter{

    private Long groupId;
    private Context context;
    private DatabaseHandler databaseHandler;
    private LayoutInflater inflater;
    private List<User> allStudents;

    public StudentAdapter(Context context, Long groupId) {

        this.context = context;
        this.groupId = groupId;
        this.databaseHandler = new DatabaseHandler(context);
        this.allStudents = databaseHandler.getGroupMembers(groupId);
        this.inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return allStudents.size();
    }

    @Override
    public User getItem(int i) {
        return allStudents.get(i);
    }

    public List<User> getAllItems() {//check if consistent
        return databaseHandler.getGroupMembers(groupId);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null)
            view = inflater.inflate(R.layout.students_item, viewGroup, false);

        TextView login = (TextView) view.findViewById(R.id.stud_login);
        TextView firstName = (TextView) view.findViewById(R.id.stud_first_name);
        TextView middleName = (TextView) view.findViewById(R.id.stud_middle_name);
        TextView lastName = (TextView) view.findViewById(R.id.stud_last_name);
        TextView personalId = (TextView) view.findViewById(R.id.stud_personal_id);
        TextView groupId = (TextView) view.findViewById(R.id.stud_group_id);

        User currentUser = getItem(i);

        personalId.setText(currentUser.getId().toString());
        login.setText(currentUser.getLogin());
        firstName.setText(currentUser.getFirstName());
        middleName.setText(currentUser.getMiddleName());
        lastName.setText(currentUser.getLastName());
        groupId.setText(currentUser.getUserGroup().getGroupID().toString());

        return view;
    }
}
