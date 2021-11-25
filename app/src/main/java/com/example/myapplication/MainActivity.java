package com.example.myapplication;

/*
    MainActivity
    This activity is just a container except NewNote
    related on NewNote , NoteContent
 */

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.Bean.Author;

public class MainActivity extends AppCompatActivity {

    private Author author;
    public static  MainActivity instance;
    public static MainActivity getInstance() {return instance;}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        author = new Author();
        instance = this;
        setContentView(R.layout.activity_main);
        Button newNote = (Button) findViewById(R.id.new_note);
        newNote.setOnClickListener(v -> {
//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Intent intent = new Intent(this,NewNote.class);
            startActivity(intent);
        });
    }

    public Author getAuthor() {return author;}
}