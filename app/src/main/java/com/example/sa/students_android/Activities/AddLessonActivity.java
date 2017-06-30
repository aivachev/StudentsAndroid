package com.example.sa.students_android.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.sa.students_android.Fragments.CustomDialogFragment;
import com.example.sa.students_android.R;

/**
 * Created by sa on 30.06.17.
 */

public class AddLessonActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_lesson);
    }

    public void showChooseTeacherDialog (View view) {
        CustomDialogFragment customDialogFragment = new CustomDialogFragment();
        customDialogFragment.show(getFragmentManager(), null);
    }
}
