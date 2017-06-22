package com.example.sa.students_android;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by sa on 20.06.17.
 */

public class AfterAuthActivity extends Activity {

    private ListView listContacts;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside);

        listContacts = findViewById(R.id.listContacts);
        ArrayAdapter<String> arrayAdapterContacts =
                new ArrayAdapter<String>(AfterAuthActivity.this,
                android.R.layout.simple_list_item_1,
                        new String[] { "+7 (981) 777-77-77", "@thisIsATest", "vk.com/id01"});
        listContacts.setAdapter(arrayAdapterContacts);
    }
}
