package com.example.kaixin.kelseyapp.model;

/**
 * Created by kaixin on 2017/3/30.
 */

public interface DiaryModel {
    void openDiaryFile(String filename,String strContent);
    void addDiaryDB(String strDate, String strCity, String strWeather, String strContent);
}
