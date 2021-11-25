package com.example.myapplication;

/*
    NewNote
    this activity is aims to get a new note and insert into sqlite.
    related on DatabaseManager and MainActivity
 */

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.Bean.Note;
import com.example.myapplication.DataBases.DatabaseManager;
import com.example.myapplication.ui.main.NoteTitleFragment;

public class NewNote extends AppCompatActivity {
    private DatabaseManager databaseManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseManager = new DatabaseManager(this);
        setContentView(R.layout.activity_new_note);
        EditText editTextTitle = (EditText) findViewById(R.id.new_note_title);
        EditText editTextContent = (EditText) findViewById(R.id.new_note_content);
        Button button = (Button) findViewById(R.id.newNoteButton);
        button.setOnClickListener(v -> {
            Note note = new Note();
            note.setTitle(editTextTitle.getText().toString());
            note.setContext(editTextContent.getText().toString());
            note.setAuthor(MainActivity.instance.getAuthor());
            databaseManager.add(note);
            Log.d("New Note","Created!");
            NoteTitleFragment.instance.getAdapter().clear();
            NoteTitleFragment.instance.getAdapter().addAll(NoteTitleFragment.instance.getNoteList());
            finish();
        });
    }
}