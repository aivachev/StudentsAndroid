package com.example.sa.students_android.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sa.students_android.Models.Journal;
import com.example.sa.students_android.R;

import java.util.List;

/**
 * Created by sa on 22.06.17.
 */

public class JournalListAdapter extends BaseAdapter {

    private Context context;
    private List<Journal> journals;
    private LayoutInflater inflater;

    public JournalListAdapter(
            Context context, List<Journal> journals) {
        this.context = context;
        this.journals = journals;
        this.inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return journals.size();
    }

    @Override
    public Object getItem(int i) {
        return journals.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null)
            view = inflater.inflate(R.layout.journal_item, viewGroup, false);

        TextView pupil = (TextView) view.findViewById(R.id.pupil);
        TextView subject = (TextView) view.findViewById(R.id.subject);
        ImageView presence = (ImageView) view.findViewById(R.id.presence);

        Journal currentJournal = (Journal) getItem(i);
        pupil.setText(
                currentJournal.getUser().getFirstName());
        subject.setText(
                currentJournal.getLesson().getSubject());
        presence.setImageResource(R.mipmap.ic_launcher);

        return view;
    }
}
