package com.example.kaixin.kelseyapp.model;

import android.database.sqlite.SQLiteDatabase;

import com.example.kaixin.kelseyapp.MyDB;
import com.example.kaixin.kelseyapp.activity.DiaryActivity;

import java.io.FileOutputStream;
import java.io.IOException;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by kaixin on 2017/3/30.
 */

public class DiaryModelImple implements DiaryModel {

    private static final String DATABASE_NAME = "myApp.db";
    private static final String SQL_INSERT = "insert into diary (filename, time, city, weather) values (?, ?, ?, ?)";
    @Override
    public void openDiaryFile(String filename, String strContent) {
        try {
            FileOutputStream fos = DiaryActivity.getAppContext().openFileOutput(filename, MODE_PRIVATE);
            fos.write(strContent.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void addDiaryDB(String filename, String strDate, String strCity, String strWeather) {
        MyDB myDB = new MyDB(DiaryActivity.getAppContext(), DATABASE_NAME, null, 1);
        SQLiteDatabase dbWrite = myDB.getWritableDatabase();
        dbWrite.execSQL(SQL_INSERT, new Object[]{filename, strDate, strCity, strWeather});
        dbWrite.close();
    }
}
