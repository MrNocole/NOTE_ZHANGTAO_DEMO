package com.example.myapplication.ui.main;

/*
    NoteTitleFragment
    this fragment is very important , it goes to generate views on MainActivity by NoteAdapter
    and turns to ContentActivity when click on any title .
    related on MainActivity , NoteAdapter
 */

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Bean.Note;
import com.example.myapplication.DataBases.DatabaseManager;
import com.example.myapplication.NoteAdapter;
import com.example.myapplication.R;

import java.util.List;

public class NoteTitleFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ListView noteTitleListView;
    private List<Note> noteList;
    private NoteAdapter adapter;
    private DatabaseManager databaseManager;
    private Context context;
    public static NoteTitleFragment instance;

    public ListView getNoteTitleListView() {
        return noteTitleListView;
    }

    public void setNoteTitleListView(ListView noteTitleListView) {
        this.noteTitleListView = noteTitleListView;
    }

    public List<Note> getNoteList_() {
        return noteList;
    }
    public void setNoteList(List<Note> noteList) {
        this.noteList = noteList;
    }

    public void setAdapter(NoteAdapter adapter) {
        this.adapter = adapter;
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public void setDatabaseManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        instance = this;
        databaseManager = new DatabaseManager(context);
    }
    public NoteAdapter getAdapter() {
        return adapter;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        noteList = getNoteList();
        adapter = new NoteAdapter(context, R.layout.note_item,noteList);
        View view = inflater.inflate(R.layout.notes_view_fragment,container,false);
        noteTitleListView = (ListView) view.findViewById(R.id.note_title_fragment);
        noteTitleListView.setAdapter(adapter);
        noteTitleListView.setOnItemClickListener(this);
        return view;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Note note = noteList.get(position);
        Log.d("TitleFragment","get Note " + note.getTitle() + " " + note.getContext() + " " + note.getTime());
        NoteContent.actionStart(getActivity(),note.getTitle(),note.getContext(),note.getId(),note.getTime(),note.getCreateTime());
    }

    public List<Note> getNoteList() {
        return databaseManager.query();
    }
}
