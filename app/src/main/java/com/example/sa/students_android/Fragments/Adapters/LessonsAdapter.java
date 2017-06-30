package com.example.sa.students_android.Fragments.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sa.students_android.Activities.StudentInfoActivity;
import com.example.sa.students_android.Enums.ContactType;
import com.example.sa.students_android.Managers.LessonsManager;
import com.example.sa.students_android.Managers.UsersManager;
import com.example.sa.students_android.Models.Lesson;
import com.example.sa.students_android.Models.User;
import com.example.sa.students_android.R;

import java.util.List;
import java.util.Map;

/**
 * Created by sa on 22.06.17.
 */

public class LessonsAdapter extends RecyclerView.Adapter<LessonsAdapter.LessonsHolder>{

    private Long groupId;
    private Context context;
    private LessonsManager lessonsManager;
    private List<Lesson> entries;

    public LessonsAdapter(Context context, Long groupId) {

        this.groupId = groupId;
        this.context = context;
        this.lessonsManager = new LessonsManager(context);
        this.entries = lessonsManager.getAllLessons();
        entries.size();
    }

    @Override
    public LessonsAdapter.LessonsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.journal_item, parent, false);

        return new LessonsAdapter.LessonsHolder(view);
    }

    @Override
    public void onBindViewHolder(LessonsAdapter.LessonsHolder holder, final int position) {
        holder.bindData(entries.get(position));
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    public void updateList(List<Lesson> entries) {
        this.entries = entries;
        notifyDataSetChanged();
    }

    class LessonsHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private TextView subject;
        private TextView lector;
        private TextView description;

        LessonsHolder(View itemView) {
            super(itemView);

            subject = (TextView) itemView.findViewById(R.id.less_subject);
            lector = (TextView) itemView.findViewById(R.id.less_lector);
            description = (TextView) itemView.findViewById(R.id.less_desc);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        void bindData(Lesson lesson) {

            StringBuilder lectorFullName = new StringBuilder();

            lectorFullName.append(
                    lesson.getLector().getLastName()).append(" ")
                    .append(lesson.getLector().getFirstName()).append(" ")
                    .append(lesson.getLector().getMiddleName());

            subject.setText(lesson.getSubject());
            lector.setText(lectorFullName.toString());
            description.setText(lesson.getDescription());
        }

        @Override
        public void onClick(View view) {
//            Context viewContext = view.getContext();
//            Intent intent = new Intent(viewContext, StudentInfoActivity.class).putExtra("currentStudent", lessonsManager.getGroupMembers(groupId).get(getAdapterPosition()));
//            viewContext.startActivity(intent);
            Toast.makeText(context, "Short kek", Toast.LENGTH_SHORT).show();
        }

        @Override
        public boolean onLongClick(View view) {
//            User user = lessonsManager.getGroupMembers(groupId).get(getAdapterPosition());
//            String phoneNumber = "";
//            String telephone;
//            for(Map.Entry mapEntry : user.getContacts().entrySet())
//                if(mapEntry.getValue().equals(ContactType.PHONE)) {
//                    phoneNumber = mapEntry.getKey().toString();
//                }
//            if(!phoneNumber.equals("")) {
//                telephone = "tel:" + phoneNumber;
//                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(telephone));
//
//                if(intent.resolveActivity(view.getContext().getPackageManager()) != null)
//                    view.getContext().startActivity(intent);
//            } else
                Toast.makeText(context, "Long kek", Toast.LENGTH_SHORT).show();
            return true;
        }
    }
}
