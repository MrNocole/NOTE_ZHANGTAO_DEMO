package com.example.myapplication;

/*
    NoteAdapter
    this tool is aims to generate notes title fragment
    related on NoteTitleFragment
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.Bean.Note;

import java.util.List;

public class NoteAdapter extends ArrayAdapter<Note> {
    private int resourceId;
    public NoteAdapter(@NonNull Context context, int resource, List<Note> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Note note = getItem(position);
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId,null);
        } else view = convertView;
        TextView textView = (TextView) view.findViewById(R.id.Note_Item_Title);
        TextView textView1 = (TextView) view.findViewById(R.id.Note_Item_ID);
        textView1.setText(String.valueOf(note.getId()));
        textView.setText(note.getTitle());
        return view;
    }
}
