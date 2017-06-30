package com.example.sa.students_android.Fragments;

import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by sa on 30.06.17.
 */

public interface ContextMenuListener<T> {

    public void callback(View view, String result, @Nullable T object);
}
