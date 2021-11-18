package com.example.myapplication.ui.main;

/*
    NoteContent
    this activity is aims to inquire a note,but there is a problem that is sqlite'primary key is going
    to auto_increase , when delete a note , the key is not going to down but monotonically increasing
    when notes get too much , there will be some problem.
    related on MainActivity , DatabaseManager
 */

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.Bean.Note;
import com.example.myapplication.DatabaseManager;
import com.example.myapplication.NoteAdapter;
import com.example.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NoteContent extends AppCompatActivity {
    private DatabaseManager databaseManager;
    public static void actionStart(Context context , String noteTitle , String noteContent , Integer noteId ,String noteTime ,String createTime) {
        Intent intent = new Intent(context,NoteContent.class);
        intent.putExtra("note_title",noteTitle);
        intent.putExtra("note_content",noteContent);
        intent.putExtra("note_Id",String.valueOf(noteId));
        intent.putExtra("note_time",noteTime);
        intent.putExtra("create_time",createTime);
        Log.d("NoteContent","Note " + noteTitle + " " + noteContent + " " + noteTime);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_content);
        String noteTitle = getIntent().getStringExtra("note_title");
        String noteContent = getIntent().getStringExtra("note_content");
        String noteId = getIntent().getStringExtra("note_Id");
        String noteTime = getIntent().getStringExtra("note_time");
        String createTime = getIntent().getStringExtra("create_time");
        Log.d("onCreate","Note : " + noteId + " " + Integer.valueOf(noteId));
        EditText editTextTitle = (EditText) findViewById(R.id.Note_content_title);
        EditText editTextContent = (EditText) findViewById(R.id.Note_content_content);
        TextView textView = (TextView) findViewById(R.id.Note_content_time);
        TextView textView1 = (TextView) findViewById(R.id.Note_create_time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(Long.valueOf(noteTime));
        textView.setText(simpleDateFormat.format(date));
        textView1.setText(simpleDateFormat.format(new Date(Long.valueOf(createTime))));
        editTextTitle.setText(noteTitle);
        editTextContent.setText(noteContent);
        databaseManager = new DatabaseManager(this);
        Button button = (Button) findViewById(R.id.note_content_edit);
        button.setOnClickListener(v -> {
            Note note = new Note();
            note.setTitle(editTextTitle.getText().toString());
            note.setContext(editTextContent.getText().toString());
            note.setId(Integer.valueOf(noteId));
            Log.d("NoteContent","Note : " + editTextTitle.getText().toString() + "  " + editTextContent.getText().toString() + " " + note.getId());
            databaseManager.update(note);
//            NoteTitleFragment.instance.getAdapter().
            NoteTitleFragment.instance.getAdapter().clear();
            NoteTitleFragment.instance.getAdapter().addAll(NoteTitleFragment.instance.getNoteList());
            finish();
        });

        Button button1 = (Button) findViewById(R.id.Note_content_del);
        button1.setOnClickListener(v -> {
            Note note = new Note();
            note.setId(Integer.valueOf(noteId));
            databaseManager.delete(note);
            NoteTitleFragment.instance.getAdapter().clear();
            NoteTitleFragment.instance.getAdapter().addAll(NoteTitleFragment.instance.getNoteList());
            finish();
        });
    }
}