package com.example.sa.students_android.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sa.students_android.Models.Group;
import com.example.sa.students_android.R;

/**
 * Created by sa on 22.06.17.
 */

public  class GroupHolder extends RecyclerView.ViewHolder {

    private TextView groupName;
    private ImageView groupIcon;

    public GroupHolder(View itemView) {
        super(itemView);
        groupName = (TextView) itemView.findViewById(R.id.GroupListItemName);
        groupIcon = (ImageView) itemView.findViewById(R.id.GroupListItemIcon);
    }

    public void bindData(Group group) {
        groupName.setText(String.valueOf(group.getGroupID()));
        groupIcon.setImageResource(R.mipmap.ic_launcher_round);
    }
}
