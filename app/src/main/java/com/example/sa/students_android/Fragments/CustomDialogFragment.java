package com.example.sa.students_android.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sa.students_android.Fragments.Adapters.UsersAdapter;
import com.example.sa.students_android.Interfaces.ContextMenuListener;
import com.example.sa.students_android.Models.User;
import com.example.sa.students_android.R;

/**
 * Created by sa on 30.06.17.
 */

public class CustomDialogFragment extends DialogFragment {

    RecyclerView basicRecView;
    User selectedUser;
    public ContextMenuListener contextMenuListener;
    AlertDialog.Builder builder;

    public static CustomDialogFragment newInstance() {

//        Bundle args = new Bundle();

//        args.putParcelable("contextListener", (Parcelable) contextMenuListener);
        CustomDialogFragment fragment = new CustomDialogFragment();
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        this.contextMenuListener = (ContextMenuListener) getActivity();

        builder = new AlertDialog.Builder(getActivity());

        basicRecView = new RecyclerView(getActivity());
        basicRecView.setAdapter(new UsersAdapter(getActivity(), 0L) {
            @Override
            public UsersHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.users_item, parent, false);

                return new UsersHolder(view) {
                    @Override
                    public void onClick(View view) {

                        contextMenuListener.callback(null, null, entries.get(getAdapterPosition()));
                        getDialog().dismiss();
                    }

                    @Override
                    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                    }
                };
            }
        });
        basicRecView.setLayoutManager(new LinearLayoutManager(getActivity()));
        builder.setView(basicRecView);
//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.dismiss();
//                basicRecView.get
//            }
//        });
//        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.cancel();
//            }
//        });

        return builder.create();
    }

}
