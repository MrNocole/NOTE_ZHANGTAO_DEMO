package com.example.myapplication.DataBases;

/*
    DataBaseHelper
    this tool is aims connect to sqlite
    related on DatabaseManager
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "DemoDB.db_1";
    public static final String DATABASE_TABLE_NAME = "notes";
    private static final String CREATE_TABLE = "create table notes ("
            + "id integer primary key autoincrement,"
            + "author text ,"
            + "lastdate text ,"
            + "createdate text ,"
            + "title text ,"
            + "content text)";

    private Context mContext;
    public String getDatabaseTableName() {
        return DATABASE_TABLE_NAME;
    }
    public DataBaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    public DataBaseHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
