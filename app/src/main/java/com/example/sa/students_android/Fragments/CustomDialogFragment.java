package com.example.sa.students_android.Fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sa.students_android.Fragments.Adapters.UsersAdapter;
import com.example.sa.students_android.R;

/**
 * Created by sa on 30.06.17.
 */

public class CustomDialogFragment extends DialogFragment {

    AlertDialog alertDialog;
    View content;
    Context context;

//    public static CustomDialogFragment newInstance(Context context) {
//
//        CustomDialogFragment fragment = new CustomDialogFragment();
//        fragment.context = context;
//
//        return fragment;
//    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder myCustomBuilder = new AlertDialog.Builder(getActivity());
//        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
//
//        content = layoutInflater.inflate(R.layout.dialog_lesson_with_fragment, null);
//        RecyclerView recyclerView = (RecyclerView) content.findViewById(R.id.rec_list_in_fragment);
//        recyclerView.setAdapter(new UsersAdapter(getActivity(), 0L));
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


//        myCustomBuilder.
        myCustomBuilder.setTitle("Title");
        myCustomBuilder.setPositiveButton("Yay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //               alertDialog.findViewById(R.la)
                dialogInterface.dismiss();
            }
        });
        myCustomBuilder.setNegativeButton("Nay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
//        AlertDialog alertDialog = myCustomBuilder.create();
//        this.alertDialog = alertDialog;
//        alertDialog.show();
//        myCustomBuilder.setView(content);

        return myCustomBuilder.create();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        RecyclerViewFragment recyclerViewFragment = new RecyclerViewFragment();
        ((RecyclerView)recyclerViewFragment.getView().findViewById(R.id.rec_list_in_fragment)).setAdapter(new UsersAdapter(getActivity(), 0L));

        View v = inflater.inflate(R.layout.dialog_lesson_with_fragment, container, false);
        getChildFragmentManager().beginTransaction().add(recyclerViewFragment, null).commit();
        return v;
    }
}
