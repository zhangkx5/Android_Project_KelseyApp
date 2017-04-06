package com.example.kaixin.kelseyapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kaixin on 2017/3/29.
 */

public class MyDB extends SQLiteOpenHelper {
    private Context context;
    private static final String DIARY_TABLE_NAME = "diary";

    public MyDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String DIARY_CREATE_TABLE = "create table "
                + DIARY_TABLE_NAME
                + "(id interger primary key, "
                + "filename TEXT, "
                + "time TEXT, "
                + "city TEXT, "
                + "weather TEXT)";
        sqLiteDatabase.execSQL(DIARY_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int newVersion, int oldVersin) {
        sqLiteDatabase.execSQL("drop table if exists ");
        onCreate(sqLiteDatabase);
    }
}
