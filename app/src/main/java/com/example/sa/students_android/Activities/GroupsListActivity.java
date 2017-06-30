package com.example.sa.students_android.Activities;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.View;
import android.widget.EditText;

import com.example.sa.students_android.Fragments.Adapters.GroupsAdapter;
import com.example.sa.students_android.Fragments.Adapters.UsersAdapter;
import com.example.sa.students_android.Managers.UsersManager;
import com.example.sa.students_android.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sa on 22.06.17.
 */

public class GroupsListActivity extends Activity {

    RecyclerView recyclerViewFragment;
    EditText searchField;
    UsersManager usersManager;

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        usersManager = new UsersManager(this);



        Fragment fragment = getFragmentManager().findFragmentById(R.id.groups_rec_list_fragment);
        recyclerViewFragment = (RecyclerView) fragment.getView().findViewById(R.id.rec_list_in_fragment);
        recyclerViewFragment.setAdapter(new GroupsAdapter(GroupsListActivity.this));

        recyclerViewFragment.setLayoutManager(new GridLayoutManager(this, 3));

        searchField = findViewById(R.id.sort_by);
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
                HashMap<Long, Integer> temp = new HashMap<>();
                for(Map.Entry o: usersManager.getGroups().entrySet()){
                    //or use .contains(text)
                    if(o.getKey().toString().contains(text)){
                        temp.put((Long) o.getKey(), (Integer) o.getValue());
                    }
                }
                //update recyclerview
                ((GroupsAdapter) recyclerViewFragment.getAdapter()).updateList(temp);
            }
        });
    }

}
