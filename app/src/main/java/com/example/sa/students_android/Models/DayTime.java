package com.example.sa.students_android.Models;


/**
 * Created by sa on 26.06.17.
 */

public class DayTime {

    Days day;
    Times time;

    public DayTime(Days day, Times time) {
        this.day = day;
        this.time = time;
    }

    public enum Days {
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY,
        SUNDAY
    }

    public enum Times {
        FIRST,
        SECOND,
        THIRD,
        FOURTH,
        FIFTH,
        SIXTH
    }
}