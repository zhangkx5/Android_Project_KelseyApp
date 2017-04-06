package com.example.kaixin.kelseyapp.activity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kaixin.kelseyapp.MyDB;
import com.example.kaixin.kelseyapp.R;
import com.example.kaixin.kelseyapp.presenter.DiaryPresenterImple;
import com.example.kaixin.kelseyapp.view.DiaryView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kaixin on 2017/3/28.
 */

public class DiaryActivity extends AppCompatActivity implements DiaryView, View.OnClickListener{
    private ImageButton ib_back, ib_done;
    private TextView diary_time, diary_weather, diary_city;
    private EditText diary_content;
    private Toolbar toolbar;
    private Bundle bundle;
    private static Context mContext;
    private boolean LookAndUpdate;
    private String strDate, strCity, strWeather, strContent;
    private static final String DATABASE_NAME = "myApp.db";
    private static final String SQL_INSERT = "insert into diary (filename, time, city, weather) values (?, ?, ?, ?)";
    private static final String DIARY_SQL_SELECTONE = "select filename from diary where time = ?";

    private DiaryPresenterImple diaryPresenterImple;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initViews();
        diaryPresenterImple = new DiaryPresenterImple(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                DiaryActivity.this.finish();
                break;
            case R.id.ib_done:
                diaryPresenterImple.addDiary(LookAndUpdate, "Diary_"+strDate,strDate,
                        strCity, strWeather,diary_content.getText().toString());
                DiaryActivity.this.finish();
                break;
            default:
                break;
        }
    }
    public static Context getAppContext() {
        return mContext;
    }
    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void initViews() {
        mContext = getApplicationContext();
        ib_back = (ImageButton)findViewById(R.id.ib_back);
        ib_done = (ImageButton)findViewById(R.id.ib_done);
        diary_content = (EditText)findViewById(R.id.diary_content);
        diary_city = (TextView)findViewById(R.id.diary_city);
        diary_time = (TextView)findViewById(R.id.diary_time);
        diary_weather = (TextView)findViewById(R.id.diary_weather);

        bundle = this.getIntent().getExtras();
        if (bundle != null) {
            LookAndUpdate = true;
            strCity = bundle.getString("city");
            strWeather = bundle.getString("weather");
            strDate = bundle.getString("time");
            strContent = bundle.getString("content");
            diary_content.setText(strContent);
        } else {
            LookAndUpdate = false;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
            Date curDate = new Date(System.currentTimeMillis());
            strDate = simpleDateFormat.format(curDate);
            strWeather = "晴";
            strCity = "广州";
        }
        diary_time.setText(strDate);
        diary_weather.setText(strWeather);
        diary_city.setText(strCity);

        ib_back.setOnClickListener(this);
        ib_done.setOnClickListener(this);
    }
}
