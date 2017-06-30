package com.example.sa.students_android.Activities;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sa.students_android.Fragments.Adapters.LessonsAdapter;
import com.example.sa.students_android.Fragments.Adapters.UsersAdapter;
import com.example.sa.students_android.Managers.LessonsManager;
import com.example.sa.students_android.Models.Lesson;
import com.example.sa.students_android.Models.User;
import com.example.sa.students_android.R;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static com.example.sa.students_android.Activities.LoginActivity.usersManager;

/**
 * Created by sa on 26.06.17.
 */

public class LessonsListActivity extends AppCompatActivity {

    RecyclerView recyclerViewFragment;
    EditText searchField;
    TextView searchResults;
    LessonsManager lessonsManager;
    Button addLesson;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons);

        addLesson = (Button) findViewById(R.id.add_lesson);

        lessonsManager = new LessonsManager(this);

        Fragment fragment = getFragmentManager().findFragmentById(R.id.lessons_rec_list_fragment);
        recyclerViewFragment = (RecyclerView) fragment.getView().findViewById(R.id.rec_list_in_fragment);
        recyclerViewFragment.setAdapter(new LessonsAdapter(this, 1L));
        recyclerViewFragment.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewFragment.getContext(),
                DividerItemDecoration.VERTICAL);
        recyclerViewFragment.addItemDecoration(dividerItemDecoration);
        //rvFragmentLessons.ItemDecoration(new RecyclerView.ItemDecoration())


        searchResults = (TextView) findViewById(R.id.found_amount);
        searchResults.setText(String.valueOf(recyclerViewFragment.getAdapter().getItemCount()));

        searchField = (EditText) findViewById(R.id.sort_by_lesson);

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

                List<Lesson> temp = new ArrayList<Lesson>();

                for(Lesson less: lessonsManager.getAllLessons()){

                    if(Pattern.compile(Pattern.quote(text), Pattern.CASE_INSENSITIVE).matcher(less.getSubject()).find()
/*                            || Pattern.compile(Pattern.quote(text), Pattern.CASE_INSENSITIVE).matcher(less.getLector().getLastName()).find()
                            || Pattern.compile(Pattern.quote(text), Pattern.CASE_INSENSITIVE).matcher(less.getAuditorium().toString()).find()*/){
                        temp.add(less);
                    }
                }
                if(temp != null) {
                    searchResults.setText(String.valueOf(temp.size()));
                    ((LessonsAdapter) recyclerViewFragment.getAdapter()).updateList(temp);
                }
            }
        });
    }

    public void startAddLessonActivity(View view) {
        Intent intent = new Intent(LessonsListActivity.this, AddLessonActivity.class);
        startActivity(intent);
    }
}

