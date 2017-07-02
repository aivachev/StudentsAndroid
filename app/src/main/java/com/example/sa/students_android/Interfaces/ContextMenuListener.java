package com.example.sa.students_android.Interfaces;

import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by sa on 30.06.17.
 */

public interface ContextMenuListener<T> {

    public void callback(View view, String result, T object);
}
