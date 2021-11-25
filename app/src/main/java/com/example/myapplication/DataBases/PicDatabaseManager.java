package com.example.myapplication.DataBases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class PicDatabaseManager {
    private PicDataBaseHelper helper;
    private SQLiteDatabase database;

    public PicDatabaseManager(Context context) {
        helper = new PicDataBaseHelper(context);
        Log.d("PicDatabase","Created!!");
        database = helper.getWritableDatabase();
    }


}
