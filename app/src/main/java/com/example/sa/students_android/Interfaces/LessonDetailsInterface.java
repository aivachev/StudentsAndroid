package com.example.sa.students_android.Interfaces;

import com.example.sa.students_android.Models.DayTime;
import com.example.sa.students_android.Models.User;

import java.util.List;

/**
 * Created by sa on 02.07.17.
 */

public interface LessonDetailsInterface {

    public void addLessonDetails(List<DayTime> daysNtimes, User lector);

}
