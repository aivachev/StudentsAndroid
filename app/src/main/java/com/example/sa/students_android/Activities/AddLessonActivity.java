package com.example.sa.students_android.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sa.students_android.Fragments.CustomDialogFragment;
import com.example.sa.students_android.Interfaces.ContextMenuListener;
import com.example.sa.students_android.Models.DayTime;
import com.example.sa.students_android.Models.User;
import com.example.sa.students_android.R;

import java.util.List;

/**
 * Created by sa on 30.06.17.
 */

public class AddLessonActivity extends Activity implements ContextMenuListener {

    View content;
    User teacher;
    AlertDialog.Builder builder;

    private Long lessonID;

    private List<DayTime> daysNtimes;
    private DayTime.Days day;
    private DayTime.Times time;
    private Integer auditorium;
    private String description;
    private String subject;
    private List<Long> groups;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_lesson);
    }

    public void showChooseTeacherDialog (View view) {

        CustomDialogFragment customDialogFragment = CustomDialogFragment.newInstance();
        customDialogFragment.show(getFragmentManager(), "tag");

    }

    public void showChooseDayDialog (View view) {

        CustomDialogFragment customDialogFragment = CustomDialogFragment.newInstance();
        customDialogFragment.show(getFragmentManager(), "tag");

    }

    public void showChooseTimeDialog (View view) {

        builder = new AlertDialog.Builder(this);
        builder.setItems(DayTime.Times.getValues(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int itemPosition) {
                callback(null, null, ((AlertDialog)dialogInterface).getListView().getItemAtPosition(itemPosition));
                Toast.makeText(AddLessonActivity.this, String.valueOf(itemPosition), Toast.LENGTH_SHORT).show();
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    @Override
    public void callback(View view, String result, Object detail) {

        if(detail instanceof User) {
            this.teacher = (User) detail;
            ((TextView) this.findViewById(R.id.teacher_full_name)).setText(
                    this.teacher.getLastName() + " " +
                    this.teacher.getFirstName());
        } else if(detail instanceof DayTime.Days) {
            day = (DayTime.Days) detail;
            ((TextView) this.findViewById(R.id.day)).setText(
                    this.day.toString());
        } else if(detail instanceof DayTime.Times) {
            time = (DayTime.Times) detail;
            ((TextView) this.findViewById(R.id.time)).setText(
                    this.time.toString());
        }
    }
}
