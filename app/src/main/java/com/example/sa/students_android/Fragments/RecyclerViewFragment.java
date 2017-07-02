package com.example.sa.students_android.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sa.students_android.Interfaces.ContextMenuListener;
import com.example.sa.students_android.R;

/**
 * Created by sa on 27.06.17.
 */

public class RecyclerViewFragment extends Fragment {

    ContextMenuListener contextMenuListener;

    public static RecyclerViewFragment newInstance() {

        RecyclerViewFragment fragment = new RecyclerViewFragment();

        return fragment;
    }

    public void provideContextMenuListener(ContextMenuListener contextMenuListener) {
        this.contextMenuListener = contextMenuListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//        View v = getFragmentManager().findFragmentById()
        return inflater.inflate(R.layout.fragment_lists, container, false);
    }
}
