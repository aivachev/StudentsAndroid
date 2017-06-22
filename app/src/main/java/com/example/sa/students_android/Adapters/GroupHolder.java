package com.example.sa.students_android.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sa.students_android.Models.Group;
import com.example.sa.students_android.R;

import java.util.Locale;
import java.util.Map;

/**
 * Created by sa on 22.06.17.
 */

public  class GroupHolder extends RecyclerView.ViewHolder {

    private TextView groupName;
    private TextView groupCount;
    private ImageView groupIcon;

    public GroupHolder(View itemView) {
        super(itemView);
        groupName = (TextView) itemView.findViewById(R.id.groupListItemName);
        groupCount = (TextView) itemView.findViewById(R.id.groupListItemCount);
        groupIcon = (ImageView) itemView.findViewById(R.id.groupListItemIcon);
    }

    public void bindData(Object object) {

        Map.Entry mapEntry = (Map.Entry) object;
        groupName.setText(String.format(Locale.getDefault(), "#%d", (Long) mapEntry.getKey()));
        groupCount.setText(String.format(Locale.getDefault(), "В ней людей: %d" , (Integer) mapEntry.getValue()));

        groupIcon.setImageResource(R.mipmap.ic_launcher_round);
    }
}
