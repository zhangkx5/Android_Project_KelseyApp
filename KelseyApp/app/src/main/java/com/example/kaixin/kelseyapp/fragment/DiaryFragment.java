package com.example.kaixin.kelseyapp.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kaixin.kelseyapp.DiaryAdapter;
import com.example.kaixin.kelseyapp.MyDB;
import com.example.kaixin.kelseyapp.R;
import com.example.kaixin.kelseyapp.activity.DiaryActivity;
import com.example.kaixin.kelseyapp.activity.MainActivity;
import com.example.kaixin.kelseyapp.bean.DiaryBean;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by kaixin on 2017/3/19.
 */

public class DiaryFragment extends Fragment {

    private ListView diarylv;
    private Context mContext;
    private FloatingActionButton fab;
    private DiaryAdapter diaryAdapter;
    private boolean isCreate = false;
    private static final String DATABASE_NAME = "myApp.db";
    private static final String DIARY_SQL_SELECTALL = "select * from diary";
    private static final String DIARY_SQL_SELECTONE = "select filename from diary where time = ?";
    private static final String DIARY_SQL_DELETE = "delete from diary where time = ?";

    private List<DiaryBean> diaryBeanList;
    private MyDB myDB = new MyDB(MainActivity.getAppContext(), DATABASE_NAME, null, 1);

    @Override
    public void onResume() {
        super.onResume();
        init();
        diaryAdapter = new DiaryAdapter(MainActivity.getAppContext(), diaryBeanList);
        diarylv.setAdapter(diaryAdapter);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diary, container, false);
        diarylv = (ListView)view.findViewById(R.id.diarylv);
        fab = (FloatingActionButton)view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.getAppContext(), DiaryActivity.class);
                startActivity(intent);
            }
        });
        diarylv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView diary_select_content = (TextView)view.findViewById(R.id.diary_content);
                TextView diary_select_city = (TextView)view.findViewById(R.id.diary_city);
                TextView diary_select_time = (TextView)view.findViewById(R.id.diary_time);
                TextView diary_select_weather = (TextView)view.findViewById(R.id.diary_weather);
                Bundle bundle = new Bundle();
                bundle.putString("content", diary_select_content.getText().toString());
                bundle.putString("time", diary_select_time.getText().toString());
                bundle.putString("city", diary_select_city.getText().toString());
                bundle.putString("weather", diary_select_weather.getText().toString());

                Intent intent = new Intent(MainActivity.getAppContext(), DiaryActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        diarylv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int pos, long l) {
                final TextView diary_select_time = (TextView)view.findViewById(R.id.diary_time);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("是否删除？");
                builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SQLiteDatabase dbDelete = myDB.getWritableDatabase();
                                dbDelete.execSQL(DIARY_SQL_DELETE, new Object[] {diary_select_time.getText().toString()});
                                MainActivity.getAppContext().deleteFile("Diary_" + diary_select_time.getText().toString());
                                dbDelete.close();
                                diaryBeanList.remove(pos);
                                diaryAdapter.notifyDataSetChanged();
                                dialogInterface.dismiss();
                            }
                        });
                builder.create().show();
                return true;
            }
        });
        return view;
    }
    private void init() {
        diaryBeanList  = new ArrayList<>();
        SQLiteDatabase dbRead = myDB.getReadableDatabase();
        Cursor cursor = dbRead.rawQuery(DIARY_SQL_SELECTALL, null);
        while (cursor.moveToNext()) {
            DiaryBean diaryBean = new DiaryBean();
            String filename = cursor.getString(cursor.getColumnIndex("filename"));
            try {
                FileInputStream fis = MainActivity.getAppContext().openFileInput(filename);
                byte[] content = new byte[fis.available()];
                fis.read(content);
                diaryBean.setTime(cursor.getString(cursor.getColumnIndex("time")));
                diaryBean.setCity(cursor.getString(cursor.getColumnIndex("city")));
                diaryBean.setWeather(cursor.getString(cursor.getColumnIndex("weather")));
                diaryBean.setContent(new String(content));
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            diaryBeanList.add(diaryBean);
        }
        cursor.close();
        dbRead.close();
        Collections.reverse(diaryBeanList);
    }

}
