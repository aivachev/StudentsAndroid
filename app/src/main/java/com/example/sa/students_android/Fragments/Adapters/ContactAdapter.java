package com.example.sa.students_android.Fragments.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sa.students_android.Enums.ContactType;
import com.example.sa.students_android.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by sa on 26.06.17.
 */

public class ContactAdapter extends BaseAdapter {

    Context context;
    HashMap<String, ContactType> contacts;
    LayoutInflater inflater;
    List<Object> entries;

    public ContactAdapter(Context context, HashMap<String, ContactType> contacts) {
        this.context = context;
        this.contacts = contacts;
        this.entries = new ArrayList<>(Arrays.asList(contacts.entrySet().toArray()));
        this.inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Object getItem(int i) {
        return contacts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null)
            view = inflater.inflate(R.layout.contact_item, viewGroup, false);



        TextView contact = (TextView) view.findViewById(R.id.contact_value);

        Integer icon = 0;

        switch (((Map.Entry)entries.get(i)).getValue().toString()) {
            case "PHONE":
                icon = R.drawable.ic_call_black_24dp;
                break;
            case "TELEGRAM":
                break;
            default:
                break;
        }

        contact.setCompoundDrawablesWithIntrinsicBounds(
                icon, //left
                0, //top
                0, //right
                0);//bottom

        contact.setText(
                ((Map.Entry)entries.get(i)).getKey().toString());

        return view;
    }
}
