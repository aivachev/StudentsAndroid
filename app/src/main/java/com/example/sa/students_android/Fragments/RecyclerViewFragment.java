package com.example.sa.students_android.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sa.students_android.R;

/**
 * Created by sa on 27.06.17.
 */

public class RecyclerViewFragment extends Fragment {

    ContextMenuListener contextMenuListener;

    public void provideContextMenuListener(ContextMenuListener contextMenuListener) {
        this.contextMenuListener = contextMenuListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lists, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
