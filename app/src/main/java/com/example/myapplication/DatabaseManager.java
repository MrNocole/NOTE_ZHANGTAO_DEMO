package com.example.myapplication;

/*
    DatabaseManager
    this tool is aims to encapsulation sqlite
    related on DataBaseHelper
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.myapplication.Bean.Note;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private DataBaseHelper helper;
    private SQLiteDatabase database;

    public DatabaseManager(Context context) {
        helper = new DataBaseHelper(context);
        database = helper.getWritableDatabase();
    }

    //Tested
    public void add(Note note) {
        ContentValues values = new ContentValues();
        values.put("author",note.getAuthor().getName());
        values.put("title",note.getTitle());
        values.put("content",note.getContext());
        values.put("lastdate",String.valueOf(System.currentTimeMillis()));
        values.put("createdate",String.valueOf(System.currentTimeMillis()));
        database.insert("notes",null,values);
        Log.d("DatabaseManager","Added!");
    }
    //Tested
    public void update(Note note) {
        ContentValues values = new ContentValues();
        values.put("title",note.getTitle());
        values.put("content",note.getContext());
        values.put("lastdate",String.valueOf(System.currentTimeMillis()));
        Log.d("DataBaseManager","Update : " + note.getTitle() + " " + note.getContext() + " " + note.getId());
//        String updateStr = "update "+DataBaseHelper.DATABASE_TABLE_NAME + " set title = " + note.getTitle() + " , content = " + note.getContext() + " where id = " + note.getId();
        database.update(DataBaseHelper.DATABASE_TABLE_NAME,values,"id=?",new String[] {String.valueOf(note.getId())});
    }

    public void delete(Note note) {
        database.delete(DataBaseHelper.DATABASE_TABLE_NAME,"id=?",new String[] {String.valueOf(note.getId())});
    }

    //Tested
    public List<Note> query() {
        List<Note> ret = new ArrayList<>();
        Cursor c = database.rawQuery("select * from "+helper.getDatabaseTableName(),null);
        while (c.moveToNext()) {
            Note note = new Note();
            note.setTitle(c.getString(c.getColumnIndexOrThrow("title")));
            note.setContext(c.getString(c.getColumnIndexOrThrow("content")));
            note.setId(Integer.parseInt(c.getString(c.getColumnIndexOrThrow("id"))));
            note.setTime(c.getString(c.getColumnIndexOrThrow("lastdate")));
            note.setCreateTime(c.getString(c.getColumnIndexOrThrow("createdate")));
            ret.add(note);
        }
        c.close();;
        return ret;
    }
}
