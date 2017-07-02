package com.example.sa.students_android.Activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sa.students_android.Enums.ContactType;
import com.example.sa.students_android.Fragments.Adapters.UsersAdapter;
import com.example.sa.students_android.Interfaces.ContextMenuListener;
import com.example.sa.students_android.Fragments.RecyclerViewFragment;
import com.example.sa.students_android.Models.Contacts;
import com.example.sa.students_android.Models.User;
import com.example.sa.students_android.R;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static com.example.sa.students_android.Activities.LoginActivity.usersManager;

/**
 * Created by sa on 24.06.17.
 */

public class StudentsListActivity extends Activity implements ContextMenuListener<User> {

    RecyclerView recyclerViewFragment;
    EditText searchField;
    TextView searchResults;
    User selectedUser;
    Long groupId;

    @Override
    public void callback(View view, String result, User selectedUser) {
        this.selectedUser = selectedUser;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);

        Bundle stuff = getIntent().getExtras();
        groupId = Long.parseLong( stuff.get("groupId").toString());

        RecyclerViewFragment fragment = (RecyclerViewFragment) getFragmentManager().findFragmentById(R.id.users_rec_list_fragment);
        fragment.provideContextMenuListener(this);

        recyclerViewFragment = (RecyclerView) fragment.getView().findViewById(R.id.rec_list_in_fragment);
        recyclerViewFragment.setAdapter(new UsersAdapter(this, groupId));
        recyclerViewFragment.setLayoutManager(new LinearLayoutManager(this));

        searchResults = findViewById(R.id.found_amount);
        searchResults.setText(String.valueOf(recyclerViewFragment.getAdapter().getItemCount()));

        searchField = findViewById(R.id.sort_by_user);
        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }

            void filter(String text){
                List<User> temp = new ArrayList<User>();

                for(User user: usersManager.getGroupMembers(groupId)){

                    if(Pattern.compile(Pattern.quote(text), Pattern.CASE_INSENSITIVE)
                            .matcher(
                                    user.getLastName() + " "
                                            + user.getFirstName() + " "
                                            + user.getMiddleName() + " "
                                            + user.getUserGroup().toString()).find()
                            ){
                        temp.add(user);
                    }
                }
                if(temp != null) {
                    searchResults.setText(String.valueOf(temp.size()));
                    ((UsersAdapter) recyclerViewFragment.getAdapter()).updateList(temp);
                }
            }
        });
    }

    public void setUserForContextMenu(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

//        Toast.makeText(this, teacher.getFirstName().toString(), Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case R.id.call_user:
                Toast.makeText(this, "Calling " + selectedUser.getFirstName(), Toast.LENGTH_SHORT).show();
                callUser(selectedUser);
                return true;
            case R.id.sms_user:
                Toast.makeText(this, "SMSing " + selectedUser.getFirstName(), Toast.LENGTH_SHORT).show();
                return true;
            case R.id.something:
                Toast.makeText(this, "Something " + selectedUser.getFirstName(), Toast.LENGTH_SHORT).show();
                return true;
        }
        return true;
    }

    public void callUser(User user) {
        String phoneNumber = "";
        String telephone;
        for(Contacts contacts : user.getContacts())
            if(contacts.getType().equals(ContactType.PHONE)) {
                phoneNumber = contacts.getValue();
            }
        if(!phoneNumber.equals("")) {
            telephone = "tel:" + phoneNumber;
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(telephone));

            if(intent.resolveActivity(getPackageManager()) != null)
                startActivity(intent);
        } else
            Toast.makeText(this, "Long kek", Toast.LENGTH_SHORT).show();
    }
}
