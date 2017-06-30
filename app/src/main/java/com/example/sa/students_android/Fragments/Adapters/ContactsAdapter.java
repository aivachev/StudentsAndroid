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
import com.example.sa.students_android.Managers.UsersManager;
import com.example.sa.students_android.Models.Contacts;
import com.example.sa.students_android.Models.User;
import com.example.sa.students_android.R;

import java.util.List;
import java.util.Map;

import static com.example.sa.students_android.Enums.ContactType.*;


/**
 * Created by sa on 26.06.17.
 */

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsHolder>{

    private Long groupId;
    private Context context;
    private UsersManager usersManager;
    private User currentUser;
    private List<Contacts> entries;

    public ContactsAdapter(Context context, Long personalId) {

        this.groupId = groupId;
        this.context = context;
        this.usersManager = new UsersManager(context);
        this.currentUser = usersManager.getUserById(personalId);
        this.entries = currentUser.getContacts();
    }

    @Override
    public ContactsAdapter.ContactsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_item, parent, false);

        return new ContactsAdapter.ContactsHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactsAdapter.ContactsHolder holder, final int position) {
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

    public void updateList(List<Contacts> entries) {
        this.entries = entries;
        notifyDataSetChanged();
    }

    class ContactsHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private TextView value;
        private TextView firstName;

        ContactsHolder(View itemView) {
            super(itemView);

            value = (TextView) itemView.findViewById(R.id.contact_value);
//            firstName = (TextView) itemView.findViewById(R.id.stud_first_name);
//            middleName = (TextView) itemView.findViewById(R.id.stud_middle_name);
//            lastName = (TextView) itemView.findViewById(R.id.stud_last_name);
//            personalId = (TextView) itemView.findViewById(R.id.stud_personal_id);
//            groupIdtv = (TextView) itemView.findViewById(R.id.stud_group_id);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        void bindData(Contacts contacts) {

//            personalId.setText(contacts.getId().toString());
            value.setText(contacts.getValue());
            Integer icon = 0;

            switch (contacts.getType()) {
                case PHONE:
                    icon = R.drawable.ic_call_black_24dp;
                    break;
                case TELEGRAM:
                    icon = R.drawable.ic_telegram_24dp;
                    break;
                default:
                    break;
            }

            value.setCompoundDrawablesWithIntrinsicBounds(
                    icon, //left
                    0, //top
                    0, //right
                    0);//bottom
//            firstName.setText(contacts.getFirstName());
//            middleName.setText(contacts.getMiddleName());
//            lastName.setText(contacts.getLastName());
//            groupIdtv.setText(contacts.getUserGroup().getGroupID().toString());

        }

        @Override
        public void onClick(View view) {
//            Context viewContext = view.getContext();
//            Intent intent = new Intent(viewContext, StudentInfoActivity.class).putExtra("currentStudent", usersManager.getGroupMembers(groupId).get(getAdapterPosition()));
//            viewContext.startActivity(intent);
            Toast.makeText(context, "Short kek", Toast.LENGTH_SHORT).show();
        }

        @Override
        public boolean onLongClick(View view) {
            Contacts selectedContacts = entries.get(getAdapterPosition());
            String valueForUri;
            switch (selectedContacts.getType()) {
                case PHONE:
                    valueForUri = "tel:" + selectedContacts.getValue();
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(valueForUri));
                    if(intent.resolveActivity(view.getContext().getPackageManager()) != null)
                        view.getContext().startActivity(intent);
                    else
                        Toast.makeText(context, "Long kek", Toast.LENGTH_SHORT).show();
                    return true;
            }
//            if(selectedContacts.getType().equals(ContactType.PHONE)) {
//                phoneNumber = selectedContacts.getValue();
//            }
//            if(!phoneNumber.equals("")) {
//                telephone = "tel:" + phoneNumber;
//                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(telephone));
//
//                if(intent.resolveActivity(view.getContext().getPackageManager()) != null)
//                    view.getContext().startActivity(intent);
//            } else
//                Toast.makeText(context, "Long kek", Toast.LENGTH_SHORT).show();
            return true;
        }
    }
//
//    Context context;
//    HashMap<String, ContactType> contacts;
//    LayoutInflater inflater;
//    List<Object> entries;
//
//    public ContactsAdapter(Context context, HashMap<String, ContactType> contacts) {
//        this.context = context;
//        this.contacts = contacts;
//        this.entries = new ArrayList<>(Arrays.asList(contacts.entrySet().toArray()));
//        this.inflater = (LayoutInflater) context
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//    }
//
//    @Override
//    public int getCount() {
//        return contacts.size();
//    }
//
//    @Override
//    public Object getItem(int i) {
//        return contacts.get(i);
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return i;
//    }
//
//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup) {
//
//        if(view == null)
//            view = inflater.inflate(R.layout.contact_item, viewGroup, false);
//
//
//
//        TextView contact = (TextView) view.findViewById(R.id.contact_value);
//
//        Integer icon = 0;
//
//        switch (((Map.Entry)entries.get(i)).getValue().toString()) {
//            case "PHONE":
//                icon = R.drawable.ic_call_black_24dp;
//                break;
//            case "TELEGRAM":
//                break;
//            default:
//                break;
//        }
//
//        contact.setCompoundDrawablesWithIntrinsicBounds(
//                icon, //left
//                0, //top
//                0, //right
//                0);//bottom
//
//        contact.setText(
//                ((Map.Entry)entries.get(i)).getKey().toString());
//
//        return view;
//    }
}
