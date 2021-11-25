package com.example.myapplication.ui.main;

/*
    NoteContent
    this activity is aims to inquire a note,but there is a problem that is sqlite'primary key is going
    to auto_increase , when delete a note , the key is not going to down but monotonically increasing
    when notes get too much , there will be some problem.
    related on MainActivity , DatabaseManager
 */

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.myapplication.Bean.Note;
import com.example.myapplication.DataBases.DatabaseManager;
import com.example.myapplication.DataBases.PicDatabaseManager;
import com.example.myapplication.R;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NoteContent extends AppCompatActivity {
    private DatabaseManager databaseManager;
    private PicDatabaseManager picDatabaseManager;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private String currentPhotoPath ;
    private String note_id;
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
        note_id = noteId;
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
        picDatabaseManager = new PicDatabaseManager(this);
        Button button = (Button) findViewById(R.id.note_content_edit);
        button.setOnClickListener(v -> {
            Note note = new Note();
            note.setTitle(editTextTitle.getText().toString());
            note.setContext(editTextContent.getText().toString());
            note.setId(Integer.valueOf(noteId));
            Log.d("NoteContent","Note : " + editTextTitle.getText().toString() + "  " + editTextContent.getText().toString() + " " + note.getId());
            databaseManager.update(note);
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

        Button button2 = (Button) findViewById(R.id.note_content_new_pic);
        button2.setOnClickListener(v -> {
            showMenu(button2);
        });
    }

    private void showMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this,view);
        popupMenu.getMenuInflater().inflate(R.menu.new_pic_menu,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(v -> {
            switch (v.getItemId()) {
                case R.id.PHOTO_ALBUM:

                    Log.d("NoteContent","Called album");
                    break;
                case R.id.CAMARA:
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    Log.d("NoteContent","Called CAMARA");
            }
            return false;
        });
        popupMenu.show();
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String imageFileName = note_id;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            Bundle bundle = data.getExtras();
            Bitmap bitmap = (Bitmap) bundle.get("data");
            ((ImageView) findViewById(R.id.note_content_image)).setImageBitmap(bitmap);

        }
    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            ImageView imageView = (ImageView) findViewById(R.id.note_content_image);
//            imageView.setImageBitmap(imageBitmap);
//        }
//    }
}