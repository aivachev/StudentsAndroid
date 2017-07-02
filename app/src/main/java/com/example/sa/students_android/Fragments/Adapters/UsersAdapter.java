package com.example.sa.students_android.Fragments.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sa.students_android.Activities.StudentInfoActivity;
import com.example.sa.students_android.Interfaces.ContextMenuListener;
import com.example.sa.students_android.Managers.UsersManager;
import com.example.sa.students_android.Models.User;
import com.example.sa.students_android.R;

import java.util.List;

/**
 * Created by sa on 24.06.17.
 */

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersHolder>{

    private Long groupId;
    private Context context;
    private UsersManager usersManager;
    public List<User> entries;

    public UsersAdapter(Context context, Long groupId) {

        this.groupId = groupId;
        this.context = context;
        this.usersManager = new UsersManager(context);
        this.entries = usersManager.getGroupMembers(groupId);
    }

    @Override
    public UsersAdapter.UsersHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.users_item, parent, false);

        return new UsersHolder(view);
    }

    @Override
    public void onBindViewHolder(UsersHolder holder, final int position) {
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

    public void updateList(List<User> entries) {
        this.entries = entries;
        notifyDataSetChanged();
    }

    public class UsersHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener /*View.OnLongClickListener*/ {

        private TextView login;
        private TextView firstName;
        private TextView middleName;
        private TextView lastName;
        private TextView personalId;
        private TextView groupIdtv;

        public UsersHolder(View itemView) {
            super(itemView);

            login = (TextView) itemView.findViewById(R.id.stud_login);
            firstName = (TextView) itemView.findViewById(R.id.stud_first_name);
            middleName = (TextView) itemView.findViewById(R.id.stud_middle_name);
            lastName = (TextView) itemView.findViewById(R.id.stud_last_name);
            personalId = (TextView) itemView.findViewById(R.id.stud_personal_id);
            groupIdtv = (TextView) itemView.findViewById(R.id.stud_group_id);

            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnClickListener(this);
            //itemView.setOnLongClickListener(this);
        }

        void bindData(User user) {

            personalId.setText(user.getId().toString());
            login.setText(user.getLogin());
            firstName.setText(user.getFirstName());
            middleName.setText(user.getMiddleName());
            lastName.setText(user.getLastName());
            groupIdtv.setText(user.getUserGroup().getGroupID().toString());

        }

        @Override
        public void onClick(View view) {
            Context viewContext = view.getContext();
            Intent intent = new Intent(viewContext, StudentInfoActivity.class).putExtra("currentStudent", entries.get(getAdapterPosition()));
            viewContext.startActivity(intent);
            Toast.makeText(context, "Short kek", Toast.LENGTH_SHORT).show();
        }

/*        @Override
        public boolean onLongClick(View view) {
            User user = usersManager.getGroupMembers(groupId).get(getAdapterPosition());
            String phoneNumber = "";
            String telephone;
            for(Contacts contacts : user.getContacts())
                if(contacts.getType().equals(ContactType.PHONE)) {
                    phoneNumber = contacts.getValue();
                }
            if(!phoneNumber.equals("")) {
                telephone = "tel:" + phoneNumber;
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(telephone));

                if(intent.resolveActivity(view.getContext().getPackageManager()) != null)
                    view.getContext().startActivity(intent);
            } else
                Toast.makeText(context, "Long kek", Toast.LENGTH_SHORT).show();
            return true;
        }*/

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            MenuInflater menuInflater = ((Activity)context).getMenuInflater();
            menuInflater.inflate(R.menu.menu_user_context, contextMenu);
            ((ContextMenuListener<User>)context).callback(null, null, entries.get(getAdapterPosition()));
        }
    }
}
